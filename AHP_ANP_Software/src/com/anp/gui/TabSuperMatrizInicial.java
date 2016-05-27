package com.anp.gui;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.ahp.StructureManager;
import com.anp.gui.utils.SuperMatrixTableModel;

public class TabSuperMatrizInicial extends JPanel {

	private static TabSuperMatrizInicial instance;

	public static TabSuperMatrizInicial getInstance() {
		if (instance == null) {
			instance = new TabSuperMatrizInicial();
		}
		return instance;
	}

	private JTable tabla;

	public TabSuperMatrizInicial() {

		SuperMatrixTableModel tablaModel = new SuperMatrixTableModel(StructureManager.getInstance().getMatrizANP());
		tabla = new JTable();
		this.add(tabla);
		tabla.setBorder(null);

		tabla.setCellSelectionEnabled(true);
		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabla.setModel(tablaModel);
		tabla.setDefaultRenderer(Object.class, tablaModel);

	}

	public void actualizar() {
		SuperMatrixTableModel tableModel = new SuperMatrixTableModel(StructureManager.getInstance().getMatrizANP());

		tabla.setModel(tableModel);
		tabla.setDefaultRenderer(Object.class, tableModel);

	}
}
