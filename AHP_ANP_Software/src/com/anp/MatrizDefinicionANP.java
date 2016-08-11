package com.anp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MatrizDefinicionANP {

	private List<CriterioANP> alternativas;
	private List<CriterioANP> criterios;

	private HashMap<String, List<CriterioANP>> clusters;

	private MatrizTemplate<MatrizTemplate<Double>> matrizValues;
	private MatrizTemplate<Double> clusterValues;
	
	public static final String CLUSTER_ALTERNATIVAS = "Alternativas";
	public static final String SEPARATOR = "//";

	public MatrizDefinicionANP() {
		clusters = new HashMap<String, List<CriterioANP>>();

		addCluster(CLUSTER_ALTERNATIVAS);
		alternativas = new ArrayList<CriterioANP>();
		criterios = new ArrayList<CriterioANP>();

		matrizValues = new MatrizTemplate<MatrizTemplate<Double>>();
		clusterValues = new MatrizTemplate<Double>();
	}
	
	/******* SETS y GETS ********/
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
	
	public MatrizTemplate<MatrizTemplate<Double>> getMatrizValues() {
		return matrizValues;
	}
	public void setMatrizValues(
			MatrizTemplate<MatrizTemplate<Double>> matrizValues) {
		this.matrizValues = matrizValues;
	}
	
	public MatrizTemplate<Double> getClusterValues() {
		return clusterValues;
	}
	public void setClusterValues(MatrizTemplate<Double> clusterValues) {
		this.clusterValues = clusterValues;
	}
	/******* FUNCIONALIDAD ********/

	public void addCluster(String cluster) {
		clusters.put(cluster, new ArrayList<CriterioANP>());
	}

	public void addAlternativa(CriterioANP toAdd) {
		clusters.get(CLUSTER_ALTERNATIVAS).add(toAdd);
		toAdd.setCluster(CLUSTER_ALTERNATIVAS);
		alternativas.add(toAdd);
		try {
			generarMatriz();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addCriterio(CriterioANP toAdd) {
		criterios.add(toAdd);
	}

	public void addToCluster(String cluster, CriterioANP criterio) throws Exception {
		if (!clusters.containsKey(cluster))
			addCluster(cluster);

		clusters.get(cluster).add(criterio);
		criterio.setCluster(cluster);

		if (!criterios.contains(criterio))
			this.addCriterio(criterio);

		generarMatriz();
	}

	public List<CriterioANP> getFromCluster(String cluster) {
		return clusters.get(cluster);
	}

	public void generarMatriz() throws Exception {
		List<CriterioANP> aux = new ArrayList<CriterioANP>(alternativas);

		int size = alternativas.size();

		aux.addAll(criterios);
		if (size < alternativas.size()) {
			throw new Exception("Te esta modificando las alternativas");
		}

		for (CriterioANP c1 : aux) {
			for (CriterioANP c2 : aux) {
				if(c1.getCluster()==null || c2.getCluster()==null){
					System.out.println("se rope");
					continue;
				}
				MatrizTemplate<Double> mat = matrizValues
						.getElement(c1.getCluster() + SEPARATOR + c2.getCluster());
				if (mat == null) {
					matrizValues.setElement(c1.getCluster() + SEPARATOR + c2.getCluster(),
							new MatrizTemplate<Double>());
					clusterValues.setElement(c1.getCluster() + SEPARATOR + c2.getCluster(), 0.00);
				}
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

	public Double getValueClusterAt(String cl1, String cl2) {
		return clusterValues.getElement(cl1 + SEPARATOR + cl2);
	}

	public void setValueClusterAt(String cl1, String cl2, Double v) {
		clusterValues.setElement(cl1 + SEPARATOR + cl2, v);
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

	public int getRelacionCluster(String clusterColumna, List<String> clustersRow) {
		double suma = 0.0;
		for (String cluster : clustersRow) {
			suma += getValueClusterAt(cluster, clusterColumna);
			if (suma > 1.0+0.000000001) {
				return -1;
			}
		}
		if (suma > 0.0 && suma < 1.0-0.000000001) {
			return -1;
		}
		if (suma == 0.0) {
			return 0;
		}
		return 1; // la suma dio 1
	}

	public int getRelacion(String cl1, String cl2) {
		MatrizTemplate<Double> matriz = matrizValues.getElement(cl1 + SEPARATOR + cl2);
		boolean ok = false;
		for (CriterioANP c2 : this.getClusters().get(cl2)) {
			Double suma = 0.00;
			for (CriterioANP c1 : this.getClusters().get(cl1)) {
				suma = suma + matriz.getElement(c1.getNombre() + SEPARATOR + c2.getNombre());
				if (suma > 1.00+0.000000001) {
					return -1;
				}
				if (suma > 0.00) {
					ok = true;
				}
			}
			if (suma > 0.00 && suma < 1.00- 0.000000001) {
				return -1;
			}
		}
		return ok ? 1 : 0;
	}

	public int getRelacionColumna(CriterioANP c1, String clusterRows) {
		MatrizTemplate<Double> matriz = matrizValues
				.getElement(clusterRows + SEPARATOR + c1.getCluster());
		boolean ok = false;
		Double suma = 0.00;
		for (CriterioANP c2 : this.getClusters().get(clusterRows)) {
			suma = suma + matriz.getElement(c2.getNombre() + SEPARATOR + c1.getNombre());
			if (suma > 1.00+0.000000001) {
				return -1;
			}
			if (suma > 0.00) {
				ok = true;
			}
		}

		if (suma > 0.00 && suma < 1.00-0.000000001) {
			return -1;
		}
		return ok ? 1 : 0;
	}

	public MatrizTemplate<Double> converger() {
		MatrizTemplate<Double> resultado = new MatrizTemplate<Double>();
		MatrizTemplate<Double> anterior = new MatrizTemplate<Double>();
		
		// lista de alternativas+criterios
		List<CriterioANP> allCrit = new ArrayList<CriterioANP>(alternativas);
		allCrit.addAll(criterios);

		resultado = llenarDeCeros(allCrit);
		anterior = planchar();

		while (!converge(resultado, anterior)) {
			anterior = resultado;
			Iterator<CriterioANP> itv1 = allCrit.iterator();// [a,b,c]
			Iterator<CriterioANP> ith1 = allCrit.iterator();// [a,b,c]
			while (itv1.hasNext()) {
				CriterioANP f1 = itv1.next();
				String keyToSet = f1.getNombre();
				for (CriterioANP c2 : allCrit) {
					CriterioANP c1 = ith1.next();
					keyToSet = keyToSet + SEPARATOR + c2.getNombre();
					Double suma = 0.00;
					for (CriterioANP f2 : allCrit) {
						double v1 = this.getValueAt(f1, c1)
								* this.getValueClusterAt(f1.getCluster(), c1.getCluster());
						double v2 = this.getValueAt(f2, c2)
								* this.getValueClusterAt(f2.getCluster(), c2.getCluster());
						suma += v1 * v2;
						if(ith1.hasNext()){
							c1 = ith1.next();
						}
					}
					resultado.setElement(keyToSet, suma);
					keyToSet = f1.getNombre();
					ith1 = allCrit.iterator(); // Lo posiciono al principio	nuevamente
				}				
			}
		}
		printMatriz(anterior, allCrit);
		printMatriz(resultado, allCrit);
		return resultado;
	}

	private boolean converge(MatrizTemplate<Double> m1, MatrizTemplate<Double> m2) {
		Double delta = 0.00;
		List<CriterioANP> allCrit = new ArrayList<CriterioANP>(alternativas);
		allCrit.addAll(criterios);
		Iterator<CriterioANP> itf = allCrit.iterator();
		while (itf.hasNext()) {
			CriterioANP c1 = itf.next();
			Iterator<CriterioANP> itc = allCrit.iterator();
			while (itc.hasNext()) {
				CriterioANP c2 = itc.next();
				String key = c1.getNombre() + SEPARATOR + c2.getNombre();
				if (Math.sqrt(Math.pow((m1.getElement(key) - m2.getElement(key)), 2)) > delta) {
					return false;
				}
			}
		}
		return true;
	}

	private MatrizTemplate<Double> llenarDeCeros(List<CriterioANP> encabezado) {
		MatrizTemplate<Double> resultado = new MatrizTemplate<Double>();
		Iterator<CriterioANP> itf = encabezado.iterator();
		while (itf.hasNext()) {
			CriterioANP c1 = itf.next();
			Iterator<CriterioANP> itc = encabezado.iterator();
			while (itc.hasNext()) {
				CriterioANP c2 = itc.next();
				String key = c1.getNombre() + SEPARATOR + c2.getNombre();
				resultado.setElement(key, 0.00);
			}
		}
		return resultado;
	}
	
	private void printMatriz(MatrizTemplate<Double> m, List<CriterioANP> encabezado){
		Iterator<CriterioANP> itf = encabezado.iterator();
		System.out.println("-------------------------------------------------------------");
		String header = "    |";
		for(CriterioANP c: encabezado){
			header += c.getNombre()+" | ";
		}
		System.out.println(header);
		while (itf.hasNext()) {
			CriterioANP c1 = itf.next();
			Iterator<CriterioANP> itc = encabezado.iterator();
			String linea = "";		
			while (itc.hasNext()) {
				CriterioANP c2 = itc.next();
				String key = c1.getNombre() + SEPARATOR + c2.getNombre();
				linea += m.getElement(key)+" - ";			
			}
			System.out.println(c1.getNombre()+" | "+linea);
		}
		System.out.println("-------------------------------------------------------------");

	}
	
	private MatrizTemplate<Double> planchar(){
		MatrizTemplate<Double> resultado = new MatrizTemplate<Double>();
		List<CriterioANP> allCrit = new ArrayList<CriterioANP>(alternativas);
		allCrit.addAll(criterios);
		Iterator<CriterioANP> itf = allCrit.iterator();
		while (itf.hasNext()) {
			CriterioANP c1 = itf.next();
			Iterator<CriterioANP> itc = allCrit.iterator();
			while (itc.hasNext()) {
				CriterioANP c2 = itc.next();
				String key = c1.getNombre() + SEPARATOR + c2.getNombre();
				resultado.setElement(key, this.getValueAt(c1, c2));
			}
		}
		return resultado;
	}

}
