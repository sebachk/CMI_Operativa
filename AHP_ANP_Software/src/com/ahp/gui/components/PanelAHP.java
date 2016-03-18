package com.ahp.gui.components;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultTreeModel;

public class PanelAHP extends PanelPrincipal {
	private JTabbedPane tabbedPane;
	private TabMatrices matr;
	private JTable tabla;
	private JPanel sliderPanel;
	private JLabel criterioA;
	private JLabel criterioB;
	private JSlider slider;

	public PanelAHP() {
		// getPanelDerecho().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				matr = new TabMatrices(TabDefiniciones.getInstance().getNodoArbolDecisionActual());
				tabla.setModel(matr);

				tabla.setDefaultRenderer(Object.class, matr);
			}
		});
		getPanelDerecho().add(tabbedPane);
		super.getTree().setCellRenderer(new AHPCellTreeRenderer());
		super.getTree().setModel(new DefaultTreeModel(new NodoArbolAHP("Goal")));

		// this.tabbedPane.addTab("Decision", null,
		// TabDefiniciones.getInstance(), null);

		TabDefiniciones defis = TabDefiniciones.getInstance();
		defis.setNodoActual((NodoArbolAHP) getTree().getModel().getRoot());

		JPanel panelMatriz = new JPanel();
		tabla = new JTable();
		tabla.setCellSelectionEnabled(true);
		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = tabla.getSelectedRow();
				int col = tabla.getSelectedColumn();

				if (tabla.isCellEditable(row, col)) {
					criterioA.setText(
							TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().getElement(row - 1));
					criterioB.setText(
							TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().getElement(col - 1));

				}

			}
		});

		matr = new TabMatrices();
		panelMatriz.setLayout(new GridLayout(0, 1, 0, 0));

		sliderPanel = new JPanel();
		panelMatriz.add(sliderPanel);
		sliderPanel.setLayout(new GridLayout(1, 0, 0, 0));

		criterioA = new JLabel("New label");
		sliderPanel.add(criterioA);

		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().size() > 0) {

					int i = slider.getValue();
					Double value;
					if (i < 0) {
						value = i * -1.0;
						// int c = tabla.getSelectedColumn() - 1;
						// int r = tabla.getSelectedRow() - 1;

					} else {
						value = 1.0 / i;
					}

					TabDefiniciones.getInstance().getNodoArbolDecisionActual().getMatriz().ponderar(criterioA.getText(),
							criterioB.getText(), value);

					matr.fireTableDataChanged();
				}
			}
		});
		slider.setForeground(Color.BLACK);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setValue(1);
		slider.setMinorTickSpacing(2);
		slider.setMinimum(-9);
		slider.setMaximum(9);
		sliderPanel.add(slider);

		criterioB = new JLabel("New label");
		sliderPanel.add(criterioB);
		tabla.setModel(matr);

		panelMatriz.add(tabla);

		this.tabbedPane.addTab("Definiciones", null, defis, "DEFINIDENDO LAS COSAS");
		this.tabbedPane.addTab("Matriz", null, panelMatriz, null);

		defis.tree = getTree();

	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
}
