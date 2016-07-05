package com.anp.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.ahp.StructureManager;
import com.anp.CriterioANP;

public class SuperMatrizNivelClusterTableModel extends DefaultTableModel
		implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	protected HashMap<String, List<CriterioANP>> rows;

	public SuperMatrizNivelClusterTableModel(HashMap<String, List<CriterioANP>> rows) {
		this.rows = rows;
	}

	@Override
	public int getColumnCount() {
		return rows == null ? 0 : rows.size() + 1;
	}

	@Override
	public int getRowCount() {
		return rows == null ? 0 : rows.size() + 1;
	}

	@Override
	public Object getValueAt(int row, int col) {
		if ((row == col) && col == 0) {
			return " ";
		}
		if (row == 0) {
			return rows.keySet().toArray(new String[] {})[col - 1];
		}
		if (col == 0) {
			return rows.keySet().toArray(new String[] {})[row - 1];
		}
		String colName = rows.keySet().toArray(new String[] {})[col - 1];
		String rowName = rows.keySet().toArray(new String[] {})[row - 1];
		switch (StructureManager.getInstance().getMatrizANP().getRelacion(rowName, colName)) {
		case -1:
			return ANPColors.INCOMPLETO.getColor();
		case 0:
			return ANPColors.NO_CRIT_CELL.getColor();
		default:
			return ANPColors.COMPLETO.getColor();
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		// super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public Component getTableCellRendererComponent(JTable obj, Object value, boolean isSelected,
			boolean arg3, int row, int col) {

		if (row == 0 || col == 0) {

			JTextField lbl = new JTextField();
			lbl.setEditable(false);
			lbl.setText(value.toString());
			lbl.setBackground(col == row ? Color.WHITE : ANPColors.TABLEHEADER.getColor());

			return lbl;
		} else {

			JPanel field = new JPanel();
			field.setOpaque(true);
			if (isSelected) {
				field.setBorder(new MatteBorder(1, 1, 1, 1, Color.cyan));

			}
			field.setBackground((Color) value);
			return field;

		}

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
