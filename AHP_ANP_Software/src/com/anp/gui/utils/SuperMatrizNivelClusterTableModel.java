package com.anp.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.ahp.StructureManager;
import com.anp.CriterioANP;

public class SuperMatrizNivelClusterTableModel extends DefaultTableModel implements TableCellRenderer {

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
			return rows.keySet().toArray(new String[]{})[col-1];
		}
		if (col == 0) {
			return rows.keySet().toArray(new String[]{})[row-1];
		}
		return "PENDIENTE";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		//super.setValueAt(aValue, rowIndex, columnIndex);
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

			JPanel field = new JPanel();
			field.setOpaque(true);
			if (isSelected) {
				field.setBackground(Color.CYAN);
			}
			return field;

		}

	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
