package com.anp.gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.ahp.gui.components.CriterioPonderadoBarra;

public class TabDecision extends JPanel {

	private static final long serialVersionUID = 1L;

	private static TabDecision instance = null;
	private JPanel panelBarritas;
	private List<CriterioPonderadoBarra> alternativas;

	private JPanel panelMatriz;
	

	private TabDecision() {
		this.setLayout(new GridLayout(0, 1, 0, 5));
		panelBarritas = new JPanel(new GridLayout(0, 1, 0, 5));
		this.add(panelBarritas);
		alternativas = new ArrayList<CriterioPonderadoBarra>();
	

		panelMatriz = new JPanel(new GridLayout(0, 1, 0, 5));
		this.add(panelMatriz);

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

		/*StructureManager.getInstance().getArbol().tomarDecision();
		for (NodoArbolDecision nodo : StructureManager.getInstance().getArbol().getAlternativas()) {
			List<Entry<String, Double>> nueva = StructureManager.getInstance().getArbol().getResultado(nodo);
			Double v = 0.0;
			for (Entry<String, Double> e : nueva) {
				// ir cargando la matriz con los getvalue, y obtenes del key la
				// doble entrada
				if (e.getValue() != null) {
					v += e.getValue();
				}
			}
			this.addCriterio(nodo.getNombre(), v);
		}*/

		repaint();
	}

}
