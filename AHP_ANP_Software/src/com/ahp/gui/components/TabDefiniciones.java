package com.ahp.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class TabDefiniciones extends JSplitPane {

	public JPanel panelCriterios;
	private JTable alternativasTable;
	private JTable subCriteriosTable;

	public JTable getSubCriteriosTable() {
		return subCriteriosTable;
	}

	public void setSubCriteriosTable(JTable subCriteriosTable) {
		this.subCriteriosTable = subCriteriosTable;
	}

	public TabDefiniciones() {
		// JSplitPane splitPane = new JSplitPane();
		this.setResizeWeight(0.5);
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);

		// La parte de arriba
		JPanel panel_3 = new JPanel();
		this.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

		JButton btnAgregarAlter = new JButton("agregar alter");
		panel_4.add(btnAgregarAlter);

		alternativasTable = new JTable();
		alternativasTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
		panel_3.add(alternativasTable);

		// La parte de abajo
		panelCriterios = new JPanel();

		this.setRightComponent(panelCriterios);
		panelCriterios.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panelCriterios.add(panel_6, BorderLayout.WEST);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));

		JButton btnAddSubcriterio = new JButton("Add SubCriterio");
		panel_6.add(btnAddSubcriterio);

		JLabel lblCriterion = new JLabel("CriterioN");
		lblCriterion.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCriterion.setHorizontalAlignment(SwingConstants.CENTER);
		panelCriterios.add(lblCriterion, BorderLayout.NORTH);

		subCriteriosTable = new JTable();
		panelCriterios.add(subCriteriosTable, BorderLayout.CENTER);

	}

}
