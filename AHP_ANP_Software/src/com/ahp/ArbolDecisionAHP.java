package com.ahp;

import java.util.ArrayList;
import java.util.List;

public class ArbolDecisionAHP {

	private NodoArbolDecision goal;

	private List<NodoArbolDecision> alternativas;
	private List<NodoArbolDecision> ultimosCriterios;

	private static ArbolDecisionAHP instance;

	public static ArbolDecisionAHP getInstance() {
		if (instance == null)
			instance = new ArbolDecisionAHP();

		return instance;
	}

	public NodoArbolDecision getGoal() {
		return goal;
	}

	public List<NodoArbolDecision> getAlternativas() {
		return alternativas;
	}

	private ArbolDecisionAHP() {
		goal = new NodoArbolDecision();
		goal.setNombre("Goal");
		alternativas = new ArrayList<NodoArbolDecision>();
		ultimosCriterios = new ArrayList<NodoArbolDecision>();
		ultimosCriterios.add(goal);

	}

	public boolean esUltimoCriterio(NodoArbolDecision nodo) {
		if (alternativas.isEmpty()) {
			return nodo.esHoja();
		}
		return alternativas.get(0).getPadres().contains(nodo);

	}

	public void addAlternativa(NodoArbolDecision alt) {
		for (NodoArbolDecision nodo : ultimosCriterios) {
			nodo.addHijo(alt);
		}

		alternativas.add(alt);

	}

	public void ligarCriterio(NodoArbolDecision nodo) {
		for (NodoArbolDecision alt : alternativas) {
			nodo.addHijo(alt);
		}
	}

	public void addNodo(NodoArbolDecision padre, NodoArbolDecision hijo) {
		if (this.ultimosCriterios.contains(padre)) {
			ultimosCriterios.remove(padre);
			for (NodoArbolDecision alt : alternativas) {
				alt.removePadre(padre);
			}

		}
		ultimosCriterios.add(hijo);
		padre.addHijo(hijo);
		ligarCriterio(hijo);

	}

	public boolean esAlternativa(NodoArbolDecision nodo) {
		return alternativas.contains(nodo);
	}

}
