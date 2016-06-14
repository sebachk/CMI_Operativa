package com.ahp;

import java.awt.Component;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultTreeModel;

import com.ahp.gui.components.AHPCellRenderer;
import com.ahp.gui.components.NodoArbolAHP;
import com.ahp.gui.components.TabDecision;
import com.ahp.gui.components.TabDefiniciones;
import com.ahp.gui.components.TabMatrices;
import com.ahp.gui.components.TabResults;
import com.anp.MatrizDefinicionANP;
import com.anp.gui.ClusterLabel;
import com.anp.gui.CriterioLabel;
import com.anp.gui.TabCriteriosANP;

public class StructureManager {

	public static final int COMPLETO = 1;
	public static final int SEMICOMPLETO = 0;
	public static final int INCOMPLETO = -1;

	private ArbolDecisionAHP arbol;
	private static StructureManager instance;

	private JTabbedPane tabbedPane;

	// ANP
	private TabCriteriosANP tabCriteriosANP;
	private MatrizDefinicionANP matrizANP;
	
	private Hashtable<String, NodoArbolAHP> nodosAHP = new Hashtable<String, NodoArbolAHP>();

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void addTab(String nombreTab, Component tab, String tooltip) {
		this.tabbedPane.addTab(nombreTab, null, tab, tooltip);
		this.tabbedPane.setEnabledAt(this.tabbedPane.indexOfTab(nombreTab), false);

		tabbedPane.indexOfComponent(TabDefiniciones.getInstance());

	}

	public void enableTabs() {
		for (int i = 0; i < this.tabbedPane.getTabCount(); i++) {
			this.tabbedPane.setEnabledAt(i, true);
		}
	}

	public static StructureManager getInstance() {
		if (instance == null)
			instance = new StructureManager();
		return instance;
	}

	private StructureManager() {
		tabCriteriosANP = new TabCriteriosANP();
	}

	public ArbolDecisionAHP getArbol() {
		return arbol;
	}

	public void setArbol(ArbolDecisionAHP arbol) {
		this.arbol = arbol;
		TabDefiniciones.getInstance().tree.setModel(new DefaultTreeModel(new NodoArbolAHP(this.arbol.getGoal())));
		TabDefiniciones.getInstance().tree.setCellRenderer(new AHPCellRenderer());

		TabDefiniciones.getInstance()
				.setNodoActual((NodoArbolAHP) TabDefiniciones.getInstance().tree.getModel().getRoot());

	}

	public void crearArbol(String name) {
		this.arbol = new ArbolDecisionAHP(name);
		TabDefiniciones.getInstance().tree.setModel(new DefaultTreeModel(new NodoArbolAHP(this.arbol.getGoal())));
		TabDefiniciones.getInstance().tree.setCellRenderer(new AHPCellRenderer());

		TabDefiniciones.getInstance()
				.setNodoActual((NodoArbolAHP) TabDefiniciones.getInstance().tree.getModel().getRoot());

	}

	private NodoArbolAHP CargarArbol(NodoArbolDecision nodo) {
		if (this.arbol.esUltimoCriterio(nodo)) {
			this.getNewNodoAHP(nodo.getNombre(), null);
			return new NodoArbolAHP(nodo);
		}

		NodoArbolAHP res = new NodoArbolAHP(nodo);
		for (NodoArbolDecision h : nodo.getHijos()) {
			NodoArbolAHP nuevo = CargarArbol(h);
			res.addSubCriterio(nuevo);

		}

		return res;

	}

	public NodoArbolAHP nuevoArbool() {
		if (this.arbol != null) {
			return CargarArbol(arbol.getGoal());
		}
		return null;
	}

	public void habilitar() {
		this.tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabDefiniciones.getInstance()), true);
		this.tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabMatrices.getInstance()), true);
		this.tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabResults.getinstance()), true);

	}

	public void arbolCompleto() {
		if (this.arbol.getAlternativas().isEmpty()) {
			this.tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabDecision.getInstance()), false);
		} else {
			this.tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabDecision.getInstance()),
					arbolCompleto(this.getArbol().getGoal()));
		}
	}

	private boolean arbolCompleto(NodoArbolDecision nodo) {
		if (nodo.getHijos().isEmpty()) {
			return true;
		}
		for (NodoArbolDecision h : nodo.getHijos()) {
			if (!arbolCompleto(h)) {
				return false;
			}
		}
		if (!nodo.getMatriz().isComplete()) {
			return false;
		}
		return true;

	}

	public void addToCluster(ClusterLabel cluster) {

		List<CriterioLabel> crit = tabCriteriosANP.getSeleccionados();

		for (CriterioLabel cl : crit) {

			try {
				matrizANP.addToCluster(cluster.getName(), cl.getCriterioANP());

				cl.addToCluster(cluster);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public MatrizDefinicionANP getMatrizANP() {
		return matrizANP;
	}
	
	public void setMatrizANP(MatrizDefinicionANP matrizANP) {
		this.matrizANP = matrizANP;
	}

	public TabCriteriosANP getTabCriterios() {
		return this.tabCriteriosANP;
	}

	public void crearMatrizANP() {
		this.matrizANP = new MatrizDefinicionANP();

	}

	public void clusterChanged(ClusterLabel cl) {
		this.tabCriteriosANP.paintCriteriosFromCluster(cl);
	}

	public int[] calcularCompletitudNodo(NodoArbolDecision nodo) {
		int[] result = new int[2];

		result[0] = nodo.getMatriz().gradoCompletitud();
		result[1] = result[0];
		if (result[0] == -2) {
			return result;
		}
		Boolean esVerde = null;

		for (NodoArbolDecision hijo : nodo.getHijos()) {
			int[] hh = calcularCompletitudNodo(hijo);
			int i = hh[1];
			if (esVerde == null && i != -2) {
				esVerde = i == StructureManager.COMPLETO;
			}
			if (i == StructureManager.SEMICOMPLETO || hh[0] != hh[1]) {
				result[1] = StructureManager.SEMICOMPLETO;
				return result;
			}
			if (i == StructureManager.INCOMPLETO && esVerde) {
				result[1] = StructureManager.SEMICOMPLETO;
				return result;
			}
			if (i == StructureManager.COMPLETO && !esVerde) {
				result[1] = StructureManager.SEMICOMPLETO;
				return result;
			}

		}

		result[1] = (esVerde == null) ? result[0] : esVerde ? StructureManager.COMPLETO : StructureManager.INCOMPLETO;

		return result;
	}
	
	public NodoArbolAHP getNewNodoAHP(String nom, NodoArbolAHP ref){
		NodoArbolAHP nuevo = nodosAHP.get(nom);
		if(nuevo == null){
			nuevo = new NodoArbolAHP(nom);
			nodosAHP.put(nom, nuevo);
		}
		else{
//			if((ref != null) && (ref.getLevel() >= nuevo.getLevel()))
				return null;
		}
		return nuevo;
	}

}
