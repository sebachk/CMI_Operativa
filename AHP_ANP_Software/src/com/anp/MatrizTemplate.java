package com.anp;

import java.io.Serializable;
import java.util.HashMap;

public class MatrizTemplate<T> implements Serializable{

	// private Vector<Vector<T>> nucleo;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8992750092319068910L;
	private HashMap<String, T> nucleo;

	public MatrizTemplate() {
		nucleo = new HashMap<String, T>();
	}

	public T getElement(String key) {
		return nucleo.get(key);
	}

	public void setElement(String key, T e) {
		nucleo.put(key, e);
	}
	
	public void setNucleo(HashMap<String, T> nucleo) {
		this.nucleo = nucleo;
	}
	
	public HashMap<String, T> getNucleo() {
		return nucleo;
	}

}
