package com.ahp.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.ahp.NodoArbolDecision;

public class TabResults extends JPanel {

	private static final long serialVersionUID = 181905097591471696L;

	private static TabResults instance;
	private List<CriterioPonderadoBarra> criterios;
	private JPanel panelBarritas;
	private JPanel panelDatos;
	private JPanel panelInfo;
	private JPanel panelErrores;
	private JLabel lblNewLabel;
	private JLabel errorLabelConsistencia;
	private JLabel errorLblMatriz;
	private JLabel crLbl;
	private JLabel ciLbl;
	private JLabel landaLbl;

	public static void reset(){
		instance= new TabResults();
	}
	public static TabResults getinstance() {
		if (instance == null)
			reset();

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
		panelDatos.setLayout(new BorderLayout(0, 0));

		panelInfo = new JPanel();
		panelDatos.add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new GridLayout(0, 1, 0, 0));

		landaLbl = new JLabel(" ");
		landaLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfo.add(landaLbl);

		crLbl = new JLabel(" ");
		crLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfo.add(crLbl);

		ciLbl = new JLabel(" ");
		ciLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfo.add(ciLbl);

		panelErrores = new JPanel();
		panelDatos.add(panelErrores, BorderLayout.SOUTH);
		panelErrores.setLayout(new GridLayout(0, 1, 0, 0));

		lblNewLabel = new JLabel("Sin errores");
		lblNewLabel.setForeground(Color.BLUE);
		panelErrores.add(lblNewLabel);

		errorLabelConsistencia = new JLabel(" ");
		errorLabelConsistencia.setForeground(Color.RED);
		panelErrores.add(errorLabelConsistencia);

		errorLblMatriz = new JLabel(" ");
		errorLblMatriz.setForeground(Color.RED);
		panelErrores.add(errorLblMatriz);
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

	public void armarEstadistica(NodoArbolDecision nodo) {

		List<NodoArbolDecision> hijos = nodo.getHijos();

		int i = 0;

		errorLblMatriz.setVisible(false);
		lblNewLabel.setVisible(true);
		errorLabelConsistencia.setVisible(false);

		for (NodoArbolDecision n : hijos) {
			double ptje = nodo.getMatriz().getEigenVector().get(nodo.getMatriz().indexOf(n));

			if (ptje == 0 || Double.isNaN(ptje)) {
				// Mensaje de error
				errorLblMatriz.setText("Revisar Matriz de ponderacion (No se debe dejar en 0 ninguna celda)");
				errorLblMatriz.setVisible(true);
				lblNewLabel.setVisible(false);
			}

			if (i < criterios.size()) {
				CriterioPonderadoBarra barra = criterios.get(i);
				barra.setCriterioNombre(n.getNombre());
				barra.setPtje(ptje);
				i++;
			} else {
				this.addCriterio(n.getNombre(), ptje);
				i++;
			}

		}

		if (i < criterios.size()) {
			List<CriterioPonderadoBarra> afuera = criterios.subList(i, criterios.size());

			for (CriterioPonderadoBarra b : afuera) {
				this.panelBarritas.remove(b);
			}

			criterios.removeAll(afuera);
		}
		panelBarritas.repaint();

		if (hijos.isEmpty()) {
			return;
		}

		/** Detalles **/
		if (nodo.getMatriz().getCR() > 0.1) {
			errorLabelConsistencia.setText(
					"Revisar la comparacion de criterios, existe un problema de consistencias. Pondere con mejor Juicio");
			errorLabelConsistencia.setVisible(true);
			lblNewLabel.setVisible(false);
		}
		landaLbl.setText("Lambda Máximo: " + nodo.getMatriz().calcularLambda());
		ciLbl.setText("CI:" + nodo.getMatriz().getCI());
		crLbl.setText("CR:" + nodo.getMatriz().getCR());

	}

}
