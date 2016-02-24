package com.ahp.gui;

import java.util.ArrayList;
import java.util.List;

import com.ahp.NodoArbolDecision;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MatrizAHP mahp = new MatrizAHP();
		List<NodoArbolDecision> hermanos = new ArrayList<NodoArbolDecision>();

		NodoArbolDecision her = new NodoArbolDecision();
		her.setNombre("A");
		hermanos.add(her);

		her = new NodoArbolDecision();
		her.setNombre("B");
		hermanos.add(her);

		her = new NodoArbolDecision();
		her.setNombre("C");
		hermanos.add(her);

		her = new NodoArbolDecision();
		her.setNombre("D");
		hermanos.add(her);

		mahp.armarMatriz(hermanos);

		mahp.llenarRandom();

		mahp.printMatriz();
	}

}
