package com.anp;

import java.util.HashMap;

public class MatrizTemplate<T> {

	// private Vector<Vector<T>> nucleo;

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

}
