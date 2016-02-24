package com.ahp;

import java.util.ArrayList;
import java.util.List;

public class ArbolDecisionAHP {

	private NodoArbolDecision goal;

	private List<NodoArbolDecision> alternativas;

	public NodoArbolDecision getGoal() {
		return goal;
	}

	public List<NodoArbolDecision> getAlternativas() {
		return alternativas;
	}

	public ArbolDecisionAHP() {
		goal = new NodoArbolDecision();
		alternativas = new ArrayList<NodoArbolDecision>();
	}

}
