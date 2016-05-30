package com.anp.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.ahp.StructureManager;
import com.anp.CriterioANP;

public class SuperMatrizNivelClusterTableModel extends DefaultTableModel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	protected List<CriterioANP> cols;
	protected List<CriterioANP> rows;

	public SuperMatrizNivelClusterTableModel(List<CriterioANP> cols, List<CriterioANP> rows) {
		this.cols = cols;
		this.rows = rows;
	}

	@Override
	public int getColumnCount() {
		return cols == null ? 0 : cols.size() + 1;
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
			return cols.get(col - 1).getNombre();
		}
		if (col == 0) {
			return rows.get(row - 1).getNombre();
		}
		return StructureManager.getInstance().getMatrizANP().getValueAt(rows.get(row - 1), cols.get(col - 1));
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public Component getTableCellRendererComponent(JTable obj, Object value, boolean isSelected, boolean arg3, int row,
			int col) {

		if (row == 0 || col == 0) {

			JTextField lbl = new JTextField();
			lbl.setEditable(false);
			lbl.setText(value.toString());
			lbl.setBackground(col == row ? Color.WHITE : Color.LIGHT_GRAY);

			return lbl;
		} else {

			JLabel field = new JLabel(value.toString());
			field.setOpaque(true);
			if (isSelected) {
				field.setBackground(Color.CYAN);
			}

			return field;

		}

	}

}
