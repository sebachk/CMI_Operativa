package com.ahp.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.ahp.Ahp;
import com.ahp.NodoArbolDecision;

public class MatrizAHP {

	static final String SEPARATOR = "//";

	List<String> elementos;

	Hashtable<String, Double> parwise;

	List<Double> eigenVector;

	public MatrizAHP() {
		elementos = new ArrayList<String>();
		parwise = new Hashtable<String, Double>();
		eigenVector = null;
	}

	public void addElemento(NodoArbolDecision nodo) {
		elementos.add(nodo.getNombre());
		parwise.put(nodo.getNombre() + SEPARATOR + nodo.getNombre(), 1.0);

		eigenVector = null;
	}

	public boolean estaCompleta() {
		for (String a : elementos) {
			for (String b : elementos) {
				if (getValor(a, b).equals(0.0)) {
					return false;
				}
			}
		}
		return true;
	}

	public void removeElemento(NodoArbolDecision nodo) {
		if (elementos.remove(nodo.getNombre())) {
			List<String> keysASacar = new ArrayList<String>();
			for (String key : parwise.keySet()) {
				if (key.startsWith(nodo.getNombre() + SEPARATOR) || key.endsWith(SEPARATOR + nodo.getNombre())) {
					keysASacar.add(key);
				}
			}
			for (String key : keysASacar) {
				parwise.remove(key);
			}
		}
	}

	public void armarMatriz(List<NodoArbolDecision> hermanos) {
		List<String> aux = new ArrayList<>();
		for (NodoArbolDecision hermano : hermanos) {
			elementos.add(hermano.getNombre());
		}

		aux.addAll(elementos);

		while (!aux.isEmpty()) {
			for (String b : aux) {
				Double valor = 0.0;
				if (aux.get(0).equals(b)) {
					valor = 1.0;
				}
				parwise.put(aux.get(0) + SEPARATOR + b, valor);
			}
			aux.remove(0);
		}

	}

	public void ponderar(String a, String b, Double valor) {

		if (elementos.indexOf(a) > elementos.indexOf(b))
			parwise.put(b + SEPARATOR + a, valor > 0 ? 1 / valor : 0.0);
		else
			parwise.put(a + SEPARATOR + b, valor);

		eigenVector = null;
	}

	public Double getValor(String a, String b) {
		Double v = parwise.get(a + SEPARATOR + b);

		if (v == null)
			v = parwise.get(b + SEPARATOR + a);
		else {
			return v;
		}

		if (v != null) {
			if (v > 0)
				return 1 / v;
			else {
				return 0.0;
			}
		} else {
			ponderar(a, b, 0.0);
			return getValor(a, b);
		}
	}

	public void llenarRandom() {
		for (String key : parwise.keySet()) {
			if (!parwise.get(key).equals(1.0)) {
				String[] par = key.split(SEPARATOR);

				ponderar(par[0], par[1], Math.rint(Math.random() * 8.1 + 1));
			}
		}
	}

	public void printMatriz() {
		for (String a : elementos) {
			for (String b : elementos) {
				System.out.print(getValor(a, b) + "  |  ");
			}
			System.out.println("");
		}

		List<Double> eigen = getEigenVector();

		System.out.println("EIGENVECTOR:");
		for (Double d : eigen) {
			System.out.println(d);
		}

		System.out.println("LamdaMax:  " + calcularLambda());

		System.out.println("CI: " + getCI());

		System.out.println("CR: " + getCR());

		System.out.println("La consistencia es: " + ((getCR() > 0.1) ? "mala" : "buena"));

	}

	public List<Double> getEigenVector() {
		if (eigenVector == null) {
			Double[] values = new Double[elementos.size()];
			Double producto;
			for (int i = 0; i < elementos.size(); i++) {
				String a = elementos.get(i);
				producto = 1.0;
				for (String b : elementos) {
					producto *= getValor(a, b);
				}

				values[i] = Math.pow(producto, 1.0 / elementos.size());
			}

			producto = 0.0;
			for (Double d : values) {
				producto += d;
			}
			List<Double> eigen = new ArrayList<Double>();
			for (Double d : values) {
				eigen.add(d / producto);
			}
			eigenVector = eigen;
		}

		return eigenVector;
	}

	public Double calcularLambda() {
		Double producto = 0.0;
		Double[] landa = new Double[elementos.size()];
		for (int j = 0; j < elementos.size(); j++) {
			producto = 0.0;
			String a = elementos.get(j);
			for (int i = 0; i < elementos.size(); i++) {
				String b = elementos.get(i);
				producto += getValor(a, b) * getEigenVector().get(i);
			}
			landa[j] = producto;

		}
		producto = 0.0;
		for (int i = 0; i < landa.length; i++) {
			producto += landa[i] / getEigenVector().get(i);
		}

		return producto / elementos.size();
	}

	public Double getCI() {

		Double l = calcularLambda() - elementos.size();

		l = l / (elementos.size() - 1);
		return l;
	}

	public Double getCR() {
		Double ri = Ahp.RI[elementos.size() - 1];

		if (ri == 0.0) {
			return ri;
		}

		return getCI() / ri;
	}

	public int size() {
		return elementos.size();
	}

	public Double getValor(int a, int b) {
		if (elementos.isEmpty())
			return 0.0;
		return getValor(elementos.get(a), elementos.get(b));
	}

	public String getElement(int i) {
		if (elementos.isEmpty())
			return null;
		return elementos.get(i);
	}

	public int indexOf(NodoArbolDecision nodo) {
		for (int i = 0; i < this.elementos.size(); i++) {
			if (elementos.get(i).equals(nodo.getNombre())) {
				return i;
			}
		}
		return -1;
	}

}
