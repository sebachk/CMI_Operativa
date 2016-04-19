package com.anp;

public class CriterioANP {

	String nombre;
	String cluster;

	public CriterioANP() {
		nombre = "NO_CLUSTER";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
}
