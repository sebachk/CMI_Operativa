package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JCheckBox;

import com.anp.CriterioANP;

public class CriterioLabel extends ColoredLabel {

	private ClusterLabel cLabel;

	public CriterioLabel() {
		super();
		check = new JCheckBox("");
		add(check, BorderLayout.EAST);

		this.color.removeMouseListener(color.getMouseListeners()[0]);

	}

	public boolean isSelected() {
		return check.isSelected();
	}

	public CriterioLabel(String nombre) {
		this();
		this.setName(nombre);
		this.setColor(Color.LIGHT_GRAY);

		criterioANP = new CriterioANP();
		criterioANP.setNombre(nombre);
	}

	private CriterioANP criterioANP;
	private JCheckBox check;

	public CriterioANP getCriterioANP() {
		return criterioANP;
	}

	public void setCriterioANP(CriterioANP criterioANP) {
		this.criterioANP = criterioANP;

		/// update de las partes graficas;
	}

	public boolean estaSeleccionado() {
		return check.isSelected();
	}

	public void addToCluster(ClusterLabel cl) {
		this.cLabel = cl;
		this.setColor(cl.getColor());

		this.criterioANP.setCluster(cl.getName());
	}

	public ClusterLabel getcLabel() {
		return cLabel;
	}

}
