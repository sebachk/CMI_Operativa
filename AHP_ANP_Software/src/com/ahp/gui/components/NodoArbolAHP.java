package com.ahp.gui.components;

import javax.swing.tree.DefaultMutableTreeNode;

import com.ahp.NodoArbolDecision;
import com.ahp.StructureManager;

public class NodoArbolAHP extends DefaultMutableTreeNode {

	public static int INSTANCIAS = 0;

	private NodoArbolDecision referencia;

	public NodoArbolAHP(String nombre){
		super(nombre);
		referencia = new NodoArbolDecision(nombre);
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

	public boolean addSubCriterio(String nombre, NodoArbolAHP actual) {
		NodoArbolAHP nuevo = StructureManager.getInstance().getNewNodoAHP(nombre, actual);
		if(nuevo!=null){
			StructureManager.getInstance().getArbol().addNodo(referencia, nuevo.referencia);

			this.add(nuevo);
			return true;
		}
		return false;
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
	
	public void removeFromArbol(){
		StructureManager.getInstance().getArbol().removeNodo(this.referencia);
		this.removeFromParent();
	}

}
