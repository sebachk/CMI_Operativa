package com.anp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatrizDefinicionANP {

	List<CriterioANP> alternativas;
	List<CriterioANP> criterios;

	HashMap<String, List<CriterioANP>> clusters;

	private MatrizTemplate<MatrizTemplate<Double>> matrizValues;

	private static final String CLUSTER_ALTERNATIVAS = "Alternativas";
	private static final String SEPARATOR = "//";

	public MatrizDefinicionANP() {
		clusters = new HashMap<String, List<CriterioANP>>();

		addCluster(CLUSTER_ALTERNATIVAS);
		alternativas = new ArrayList<CriterioANP>();
		criterios = new ArrayList<CriterioANP>();

		matrizValues = new MatrizTemplate<MatrizTemplate<Double>>();

	}

	public void addCluster(String cluster) {
		clusters.put(cluster, new ArrayList<CriterioANP>());

	}

	public void addAlternativa(CriterioANP toAdd) {

		clusters.get(CLUSTER_ALTERNATIVAS).add(toAdd);
		toAdd.setCluster(CLUSTER_ALTERNATIVAS);

		this.alternativas.add(toAdd);
	}

	public void addCriterio(CriterioANP toAdd) {
		this.criterios.add(toAdd);
	}

	public List<CriterioANP> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<CriterioANP> alternativas) {
		this.alternativas = alternativas;
	}

	public List<CriterioANP> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<CriterioANP> criterios) {
		this.criterios = criterios;
	}

	public HashMap<String, List<CriterioANP>> getClusters() {
		return clusters;
	}

	public void setClusters(HashMap<String, List<CriterioANP>> clusters) {
		this.clusters = clusters;
	}

	public void addToCluster(String cluster, CriterioANP criterio) throws Exception {
		if (!clusters.containsKey(cluster))
			addCluster(cluster);

		clusters.get(cluster).add(criterio);
		criterio.setCluster(cluster);

		if (!this.criterios.contains(criterio))
			this.addCriterio(criterio);

		generarMatriz();
	}

	public List<CriterioANP> getFromCluster(String cluster) {
		return clusters.get(cluster);
	}

	private void generarMatriz() throws Exception {
		List<CriterioANP> aux = new ArrayList<CriterioANP>(alternativas);

		int size = alternativas.size();

		aux.addAll(criterios);
		if (size < alternativas.size()) {
			throw new Exception("Te esta modificando las alternativas");
		}

		for (CriterioANP c1 : aux) {
			for (CriterioANP c2 : aux) {
				MatrizTemplate<Double> mat = matrizValues.getElement(c1.getCluster() + SEPARATOR + c2.getCluster());
				if (mat == null)
					matrizValues.setElement(c1.getCluster() + SEPARATOR + c2.getCluster(),
							new MatrizTemplate<Double>());

				Double v = getValueAt(c1, c2);
				if (v == null)
					setValueAt(c1, c2, 0.0);

			}
		}

	}

	public Double getValueAt(CriterioANP c1, CriterioANP c2) {

		return matrizValues.getElement(c1.getCluster() + SEPARATOR + c2.getCluster())
				.getElement(c1.getNombre() + SEPARATOR + c2.getNombre());

	}

	public void setValueAt(CriterioANP c1, CriterioANP c2, Double v) {

		matrizValues.getElement(c1.getCluster() + SEPARATOR + c2.getCluster())
				.setElement(c1.getNombre() + SEPARATOR + c2.getNombre(), v);

	}

	/* Manejo Matriz Doble entrada */

	public CriterioANP getCriterioAt(int pos) {

		for (String key : clusters.keySet()) {
			List<CriterioANP> crit = clusters.get(key);
			if (crit.size() > pos) {
				return crit.get(pos);
			} else {
				pos -= crit.size();
			}
		}

		return null;
	}

}
