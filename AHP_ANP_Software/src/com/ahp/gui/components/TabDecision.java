package com.ahp.gui.components;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.ahp.ArbolDecisionAHP;
import com.ahp.NodoArbolDecision;

public class TabDecision extends JPanel {

	private static final long serialVersionUID = 1L;

	private static TabDecision instance = null;
	private JPanel panelBarritas;
	private List<CriterioPonderadoBarra> alternativas;

	private JPanel panelMatriz;
	private JTable tabla;

	private TabDecision() {
		this.setLayout(new GridLayout(0, 1, 0, 5));
		panelBarritas = new JPanel(new GridLayout(0, 1, 0, 5));
		this.add(panelBarritas);
		alternativas = new ArrayList<CriterioPonderadoBarra>();
		this.addCriterio("test1", 0.7);
		this.addCriterio("test2", 0.2);
		this.addCriterio("test3", 0.9);
		this.addCriterio("test4", 0.8);

		panelMatriz = new JPanel(new GridLayout(0, 1, 0, 5));
		this.add(panelMatriz);

		tabla = new JTable();
		panelMatriz.add(tabla);
		tabla.setBorder(null);
		tabla.setCellSelectionEnabled(false);

	}

	public static TabDecision getInstance() {
		if (instance == null)
			instance = new TabDecision();
		return instance;
	}

	public void addCriterio(String nombre, Double ptje) {
		for (CriterioPonderadoBarra barra : alternativas) {
			if (barra.getCriterioNombre().equals(nombre)) {
				barra.setPtje(ptje);
				return;
			}
		}

		CriterioPonderadoBarra nuevo = new CriterioPonderadoBarra();
		this.panelBarritas.add(nuevo);
		nuevo.setCriterioNombre(nombre);
		nuevo.setPtje(ptje);
		alternativas.add(nuevo);

	}

	public void generarDecision() {
		for (CriterioPonderadoBarra crit : this.alternativas) {
			this.panelBarritas.remove(crit);
		}
		alternativas.clear();

		ArbolDecisionAHP.getInstance().tomarDecision();
		for (NodoArbolDecision nodo : ArbolDecisionAHP.getInstance().getAlternativas()) {
			List<Entry<String, Double>> nueva = ArbolDecisionAHP.getInstance().getResultado(nodo);
			Double v = 0.0;
			for (Entry<String, Double> e : nueva) {
				// ir cargando la matriz con los getvalue, y obtenes del key la
				// doble entrada
				if (e.getValue() != null) {
					v += e.getValue();
				}
			}
			this.addCriterio(nodo.getNombre(), v);
		}

		repaint();
	}

}
