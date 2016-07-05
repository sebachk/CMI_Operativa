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
import com.anp.gui.TabSuperMatrizInicial;

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
		tabbedPane.addTab(nombreTab, null, tab, tooltip);
		tabbedPane.setEnabledAt(tabbedPane.indexOfTab(nombreTab), false);

	}

	public void enableTabs() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			tabbedPane.setEnabledAt(i, true);
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
		TabDefiniciones.getInstance().tree
				.setModel(new DefaultTreeModel(new NodoArbolAHP(this.arbol.getGoal())));
		TabDefiniciones.getInstance().tree.setCellRenderer(new AHPCellRenderer());

		TabDefiniciones.getInstance().setNodoActual(
				(NodoArbolAHP) TabDefiniciones.getInstance().tree.getModel().getRoot());

	}

	public void crearArbol(String name) {
		arbol = new ArbolDecisionAHP(name);
		TabDefiniciones.getInstance().tree
				.setModel(new DefaultTreeModel(new NodoArbolAHP(arbol.getGoal())));
		TabDefiniciones.getInstance().tree.setCellRenderer(new AHPCellRenderer());

		TabDefiniciones.getInstance().setNodoActual(
				(NodoArbolAHP) TabDefiniciones.getInstance().tree.getModel().getRoot());

	}

	private NodoArbolAHP CargarArbol(NodoArbolDecision nodo) {
		if (arbol.esUltimoCriterio(nodo)) {
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
		if (arbol != null) {
			return CargarArbol(arbol.getGoal());
		}
		return null;
	}

	public void habilitar() {
		tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabDefiniciones.getInstance()), true);
		tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabMatrices.getInstance()), true);
		tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabResults.getinstance()), true);

	}

	public void arbolCompleto() {
		if (arbol.getAlternativas().isEmpty()) {
			tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabDecision.getInstance()), false);
		} else {
			tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(TabDecision.getInstance()),
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
		return tabCriteriosANP;
	}

	public void crearMatrizANP() {
		matrizANP = new MatrizDefinicionANP();

	}

	public void clusterChanged(ClusterLabel cl) {
		tabCriteriosANP.paintCriteriosFromCluster(cl);
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

		result[1] = (esVerde == null) ? result[0]
				: esVerde ? StructureManager.COMPLETO : StructureManager.INCOMPLETO;

		return result;
	}

	public NodoArbolAHP getNewNodoAHP(String nom, NodoArbolAHP ref) {
		NodoArbolAHP nuevo = nodosAHP.get(nom);
		if (nuevo == null) {
			nuevo = new NodoArbolAHP(nom);
			nodosAHP.put(nom, nuevo);
		} else {
			// if((ref != null) && (ref.getLevel() >= nuevo.getLevel()))
			return null;
		}
		return nuevo;
	}

	public void tabSuperMatrizANPChangedState() {
		for (Component tab : tabbedPane.getComponents()) {
			if (tab instanceof TabSuperMatrizInicial) {
				((TabSuperMatrizInicial) tab).changedTables();
			}
		}
	}

}
