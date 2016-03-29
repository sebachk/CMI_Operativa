package com.ahp.gui.components;

import javax.swing.tree.DefaultMutableTreeNode;

import com.ahp.ArbolDecisionAHP;
import com.ahp.NodoArbolDecision;

public class NodoArbolAHP extends DefaultMutableTreeNode {

	public static int INSTANCIAS = 0;

	private NodoArbolDecision referencia;

	public NodoArbolAHP() {
		this("Criterio_" + NodoArbolAHP.INSTANCIAS++);
	}

	public NodoArbolAHP(String nombre) {
		super(nombre);
		referencia = new NodoArbolDecision();
		referencia.setNombre(nombre);
	}

	public NodoArbolAHP(NodoArbolDecision ref) {
		super(ref.getNombre());
		referencia = ref;

	}

	public void setNombre(String nombre) {
		this.setUserObject(nombre);
		referencia.setNombre(nombre);
	}

	public String getNombre() {
		return referencia.getNombre();
	}

	public void clicked() {

	}

	public void addSubCriterio(String nombre) {
		NodoArbolAHP nuevo = new NodoArbolAHP(nombre);

		ArbolDecisionAHP.getInstance().addNodo(referencia, nuevo.referencia);

		// referencia.addHijo(nuevo.referencia);
		this.add(nuevo);
	}

	public NodoArbolDecision getReferencia() {
		return referencia;
	}
}
