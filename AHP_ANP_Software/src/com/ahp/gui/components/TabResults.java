package com.ahp.gui.components;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TabResults extends JPanel {

	private static final long serialVersionUID = 181905097591471696L;

	private static TabResults instance;
	private List<CriterioPonderadoBarra> criterios;
	private JPanel panelBarritas;
	private JPanel panelDatos;

	public static TabResults getinstance() {
		if (instance == null)
			instance = new TabResults();

		return instance;
	}

	private TabResults() {
		this.setLayout(new GridLayout(0, 1, 0, 5));
		criterios = new ArrayList<CriterioPonderadoBarra>();
		panelBarritas = new JPanel(new GridLayout(0, 1, 0, 5));
		this.add(panelBarritas);

		panelDatos = new JPanel();
		panelDatos.setBorder(new TitledBorder(null, "Detalles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(panelDatos);
		this.addCriterio("test1", 0.7);
		this.addCriterio("test2", 0.2);
		this.addCriterio("test3", 0.9);
		this.addCriterio("test4", 0.8);
	}

	public void addCriterio(String nombre, Double ptje) {
		for (CriterioPonderadoBarra barra : criterios) {
			if (barra.getCriterioNombre().equals(nombre)) {
				barra.setPtje(ptje);
				return;
			}
		}

		CriterioPonderadoBarra nuevo = new CriterioPonderadoBarra();
		this.panelBarritas.add(nuevo);
		nuevo.setCriterioNombre(nombre);
		nuevo.setPtje(ptje);
		criterios.add(nuevo);

	}
}
