package com.ahp.gui.components;

import javax.swing.tree.DefaultMutableTreeNode;

import com.ahp.NodoArbolDecision;

public class NodoArbolAHP extends DefaultMutableTreeNode {

	public static int INSTANCIAS = 0;

	private NodoArbolDecision referencia;

	public NodoArbolAHP() {
		super("Criterio_" + NodoArbolAHP.INSTANCIAS++);
		referencia = new NodoArbolDecision();
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

}
