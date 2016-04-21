package com.anp.gui;

import com.anp.CriterioANP;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CriterioLabel extends ColoredLabel {
	public CriterioLabel() {
		
		check = new JCheckBox("");
		
		add(check, BorderLayout.EAST);
	}

	private CriterioANP criterioANP;
	private JCheckBox check;
	
	public CriterioANP getCriterioANP() {
		return criterioANP;
	}
	public void setCriterioANP(CriterioANP criterioANP) {
		this.criterioANP = criterioANP;
		
		///update de las partes graficas;
	}
	
	public boolean estaSeleccionado(){
		return check.isSelected();
	}
	
	
}
