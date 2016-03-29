package com.ahp.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ahp.NodoArbolDecision;

public class TabMatrices extends JPanel {

	private JPanel sliderPanel;
	private JPanel matrizPanel;

	private MatrizDobleTableModel matrizModel;
	private JTable tabla;

	private JLabel criterioA;
	private JLabel criterioB;
	private JSlider slider;

	private static TabMatrices instance;

	public static TabMatrices getInstance() {
		if (instance == null)
			instance = new TabMatrices();
		return instance;
	}

	private TabMatrices() {
		setLayout(new GridLayout(0, 1, 0, 0));

		sliderPanel = new JPanel();
		matrizModel = new MatrizDobleTableModel();

		criterioA = new JLabel("New label");
		criterioB = new JLabel("New label");
		slider = new JSlider();

		sliderPanel.setLayout(new GridLayout(1, 0, 0, 0));
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().size() > 0) {

					int i = slider.getValue();
					Double value;
					if (i < 0) {
						value = i * -1.0;
						// int c = tabla.getSelectedColumn() - 1;
						// int r = tabla.getSelectedRow() - 1;

					} else if (i > 0) {
						value = 1.0 / i;
					} else {
						value = 0.0;
					}

					TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().ponderar(criterioA.getText(),
							criterioB.getText(), value);

					matrizModel.fireTableDataChanged();
				}
			}
		});
		slider.setForeground(Color.BLACK);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setValue(1);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(-9);
		slider.setMaximum(9);
		criterioA.setHorizontalAlignment(SwingConstants.CENTER);
		criterioB.setHorizontalAlignment(SwingConstants.CENTER);

		sliderPanel.add(criterioA);
		sliderPanel.add(slider);
		sliderPanel.add(criterioB);
		this.add(sliderPanel);

		matrizPanel = new JPanel();
		matrizPanel.setBackground(Color.WHITE);
		matrizPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "jhohl", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.DARK_GRAY));
		add(matrizPanel);
		matrizPanel.setLayout(new BorderLayout(0, 0));
		tabla = new JTable();
		matrizPanel.add(tabla);
		tabla.setBorder(null);

		tabla.setCellSelectionEnabled(true);
		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				sliderPanel.setVisible(true);
				int row = tabla.getSelectedRow();
				int col = tabla.getSelectedColumn();

				if (tabla.isCellEditable(row, col)) {
					criterioA.setText(
							TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().getElement(row - 1));
					criterioB.setText(
							TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().getElement(col - 1));

					Double v = TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().getValor(col - 1,
							row - 1);
					if (v < 1 && v > 0) {
						v = 1.0 / v;
						v *= -1;
					}

					slider.setValue(v.intValue());

				}

			}
		});

		tabla.setModel(matrizModel);

	}

	public void actualizar(NodoArbolDecision actual) {
		sliderPanel.setVisible(false);
		((TitledBorder) matrizPanel.getBorder())
				.setTitle("Matriz de comparación de a pares respecto de " + actual.getNombre());
		matrizModel = new MatrizDobleTableModel(actual);
		tabla.setModel(matrizModel);
		tabla.setDefaultRenderer(Object.class, matrizModel);
	}

}
