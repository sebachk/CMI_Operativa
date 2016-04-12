package com.ahp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

public class ArbolDecisionAHP {

	private NodoArbolDecision goal;

	private List<NodoArbolDecision> alternativas;
	private List<NodoArbolDecision> ultimosCriterios;
	private Hashtable<String, Double> resultados = new Hashtable<String, Double>();

	// private static ArbolDecisionAHP instance;

	public static String SEPARATOR = "><";

	/*
	 * public static ArbolDecisionAHP getInstance() { if (instance == null)
	 * instance = new ArbolDecisionAHP();
	 * 
	 * return instance; }
	 */

	public NodoArbolDecision getGoal() {
		return goal;
	}

	public List<NodoArbolDecision> getAlternativas() {
		return alternativas;
	}

	public List<NodoArbolDecision> getUltimosCriterios() {
		return ultimosCriterios;
	}

	public void setUltimosCriterios(List<NodoArbolDecision> ultimosCriterios) {
		this.ultimosCriterios = ultimosCriterios;
	}

	public void setAlternativas(List<NodoArbolDecision> alternativas) {
		this.alternativas = alternativas;
	}

	public void setGoal(NodoArbolDecision goal) {
		this.goal = goal;
	}

	public Hashtable<String, Double> getResultados() {
		return resultados;
	}

	public void setResultados(Hashtable<String, Double> resultados) {
		this.resultados = resultados;
	}

	public ArbolDecisionAHP() {

	}

	public ArbolDecisionAHP(String g) {
		goal = new NodoArbolDecision();
		goal.setNombre(g);
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

	public void tomarDecision() {
		this.resultados.clear();
		this.getValorRama(goal, 1, goal);
	}

	private void getValorRama(NodoArbolDecision nodo, double value, NodoArbolDecision n) {
		if (this.esAlternativa(nodo)) { // Si soy alternativa y no tengo matriz
			Double v = resultados.get(nodo.getNombre() + SEPARATOR + n.getNombre());
			if (v == null) {
				v = 0.00;
			}
			this.resultados.put(nodo.getNombre() + SEPARATOR + n.getNombre(), v + value);
		} else {

			for (NodoArbolDecision hijo : nodo.getHijos()) {
				getValorRama(hijo, value * nodo.getMatriz().getEigenVector().get(nodo.getMatriz().indexOf(hijo)),
						n == goal ? hijo : n);
			}
		}

	}

	public List<Entry<String, Double>> getResultado(NodoArbolDecision nodo) {
		List<Entry<String, Double>> retorno = new ArrayList<Entry<String, Double>>();
		for (Entry<String, Double> e : resultados.entrySet()) {
			if (e.getKey().startsWith(nodo.getNombre())) {
				retorno.add(e);
			}
		}
		return retorno;

	}

}
