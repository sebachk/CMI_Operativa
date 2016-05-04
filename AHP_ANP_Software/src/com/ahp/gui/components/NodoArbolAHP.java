package com.ahp.gui.components;

import javax.swing.tree.DefaultMutableTreeNode;

import com.ahp.NodoArbolDecision;
import com.ahp.StructureManager;

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

		StructureManager.getInstance().getArbol().addNodo(referencia, nuevo.referencia);

		this.add(nuevo);
	}

	public void addSubCriterio(NodoArbolAHP subC) {
		// Se supone que la referencias ya estan unidas

		this.add(subC);

		if (!referencia.esHijo(subC.referencia)) {
			StructureManager.getInstance().getArbol().addNodo(referencia, subC.referencia);

		}

	}

	public NodoArbolDecision getReferencia() {
		return referencia;
	}

}
