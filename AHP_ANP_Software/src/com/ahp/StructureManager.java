package com.ahp;

import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultTreeModel;

import com.ahp.gui.components.NodoArbolAHP;
import com.ahp.gui.components.TabDecision;
import com.ahp.gui.components.TabDefiniciones;
import com.ahp.gui.components.TabMatrices;
import com.ahp.gui.components.TabResults;

public class StructureManager {

	private ArbolDecisionAHP arbol;
	private static StructureManager instance;

	private JTabbedPane tabbedPane;

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

	public static StructureManager getInstance() {
		if (instance == null)
			instance = new StructureManager();
		return instance;
	}

	private StructureManager() {

	}

	public ArbolDecisionAHP getArbol() {
		return arbol;
	}

	public void setArbol(ArbolDecisionAHP arbol) {
		this.arbol = arbol;
		TabDefiniciones.getInstance().tree.setModel(new DefaultTreeModel(new NodoArbolAHP(this.arbol.getGoal())));

		TabDefiniciones.getInstance()
				.setNodoActual((NodoArbolAHP) TabDefiniciones.getInstance().tree.getModel().getRoot());

	}

	public void crearArbol(String name) {
		this.arbol = new ArbolDecisionAHP(name);
		TabDefiniciones.getInstance().tree.setModel(new DefaultTreeModel(new NodoArbolAHP(this.arbol.getGoal())));

		TabDefiniciones.getInstance()
				.setNodoActual((NodoArbolAHP) TabDefiniciones.getInstance().tree.getModel().getRoot());

	}

	private NodoArbolAHP CargarArbol(NodoArbolDecision nodo) {
		if (this.arbol.esUltimoCriterio(nodo)) {
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

}
