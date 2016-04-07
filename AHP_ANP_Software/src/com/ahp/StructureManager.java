package com.ahp;

import com.ahp.gui.components.NodoArbolAHP;

public class StructureManager {

	private ArbolDecisionAHP arbol;
	private static StructureManager instance;

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

	}

	public void crearArbol(String name) {
		if (this.arbol == null)
			this.arbol = new ArbolDecisionAHP(name);
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

}
