package com.anp.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.ahp.StructureManager;
import com.anp.CriterioANP;

public class SuperMatrizNivelCriterioTableModel extends DefaultTableModel
		implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	protected List<CriterioANP> rows;
	protected List<CriterioANP> cols;

	public SuperMatrizNivelCriterioTableModel(List<CriterioANP> cols, List<CriterioANP> rows) {
		this.rows = rows;
		this.cols = cols;
	}

	@Override
	public int getColumnCount() {
		return cols == null ? 0 : cols.size() + 1;
	}

	@Override
	public int getRowCount() {
		return rows == null ? 0 : rows.size() + 2;
	}

	@Override
	public Object getValueAt(int row, int col) {
		if ((row == col) && col == 0) {
			return " ";
		}
		if (row == getRowCount() - 1) {
			if (col == 0) {
				return " ";
			}
			switch (StructureManager.getInstance().getMatrizANP()
					.getRelacionColumna(cols.get(col - 1), rows.get(0).getCluster())) {
			case -1:
				return ANPColors.INCOMPLETO.getColor();
			case 0:
				return ANPColors.NO_CRIT_CELL.getColor();
			default:
				return ANPColors.COMPLETO.getColor();
			}
		}
		if (row == 0) {
			return cols.get(col - 1).getNombre();
		}
		if (col == 0) {
			return rows.get(row - 1).getNombre();
		}

		return StructureManager.getInstance().getMatrizANP().getValueAt(rows.get(row - 1),
				cols.get(col - 1));
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Double v = Double.valueOf(aValue.toString());
		if (rowIndex == 0 || columnIndex == 0)
			return;
		StructureManager.getInstance().getMatrizANP().setValueAt(rows.get(rowIndex - 1),
				cols.get(columnIndex - 1), v);
		// this.fireTableDataChanged();
		StructureManager.getInstance().tabSuperMatrizANPChangedState();
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
			if (row == getRowCount() - 1) {
				JTextField field = new JTextField();
				field.setBackground((Color) value);
				return field;
			}
			JTextField field = new JTextField();
			field.setText(String.valueOf(value));
			return field;

		}

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (rowIndex == getRowCount() - 1 || columnIndex == 0 || rowIndex == 0) {
			return false;
		}
		return true;

	}

}
