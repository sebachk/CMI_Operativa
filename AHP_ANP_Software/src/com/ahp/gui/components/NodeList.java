package com.ahp.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ahp.NodoArbolDecision;
import com.anp.gui.utils.ANPColors;


public class NodeList extends JPanel{
	private JLabel label;
	private NodoArbolDecision referencia;
	
	public NodeList(){
		this.label = new JLabel();
		deseleccionar();
	}
	
	public NodeList(NodoArbolDecision referencia){
		deseleccionar();
		this.referencia = referencia;
		this.label = new JLabel(referencia.getNombre());
		this.add(label);
	}
	
	public void setLabel(JLabel label) {
		this.label = label;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public void setReferencia(NodoArbolDecision referencia) {
		this.referencia = referencia;
	}
	
	public NodoArbolDecision getReferencia() {
		return referencia;
	}

	public void seleccionar(){
		this.setBackground(ANPColors.SELECT.getColor());
	}
	
	public void deseleccionar(){
		this.setBackground(ANPColors.NO_CRIT_CELL.getColor());
	}
	
}
