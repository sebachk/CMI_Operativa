package com.ahp;

import java.util.ArrayList;
import java.util.List;

import com.ahp.gui.MatrizAHP;

public class NodoArbolDecision {
	private List<NodoArbolDecision> padres;
	private List<NodoArbolDecision> hijos;

	private String nombre;

	private MatrizAHP matriz;

	public MatrizAHP getMatriz() {
		return matriz;
	}

	public NodoArbolDecision() {
		padres = new ArrayList<NodoArbolDecision>();
		hijos = new ArrayList<NodoArbolDecision>();
		matriz = new MatrizAHP();
		nombre = "";
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean esHoja() {
		return hijos == null || hijos.isEmpty();
	}

	public boolean esGoal() {
		return padres == null || padres.isEmpty();
	}

	public void setHijos(List<NodoArbolDecision> hijos) {
		this.hijos = hijos;
	}

	public void setPadres(List<NodoArbolDecision> padres) {
		this.padres = padres;
	}

	public List<NodoArbolDecision> getHijos() {
		return hijos;
	}

	public List<NodoArbolDecision> getPadres() {
		return padres;
	}

	public void addHijo(NodoArbolDecision hijo) {
		hijos.add(hijo);
		hijo.addPadre(this);

		matriz.addElemento(hijo);

	}

	private void addPadre(NodoArbolDecision padre) {
		padres.add(padre);
	}

	public void generarMatriz(NodoArbolDecision ref) {
		if (ref.esHoja())
			return;

		List<NodoArbolDecision> hijosRef = ref.getHijos();

	}

	public void removePadre(NodoArbolDecision padre) {
		this.padres.remove(padre);
		padre.hijos.remove(this);
		padre.getMatriz().removeElemento(this);

	}
	
//	public double getValorRama(NodoArbolDecision nodo, double value){
//		if(nodo.getMatriz().calcularLambda() == 0){ //Si soy alternativa y no tengo matriz
//			double[] valores = {};
//			double result = 0.00;
//			int n = 0;
//			for(NodoArbolDecision padre: nodo.getPadres()){
//				valores[n] = getValorRama(nodo, 1);
//			}
//			for(int i=0;i<=n;i++){
//				result += valores[i];
//			}
//			return result;
//		}
//		else{
//			for(NodoArbolDecision padre: nodo.getPadres()){
//				return getValorRama(nodo, value * padre.getMatriz().calcularLambda());
//			}
//		}
//	}

}
