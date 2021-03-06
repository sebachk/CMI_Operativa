package com.ahp.gui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.ahp.NodoArbolDecision;
import com.anp.gui.utils.ANPColors;

public class MatrizDobleTableModel extends AbstractTableModel implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NodoArbolDecision nodo;

	public MatrizDobleTableModel() {
		super();
		nodo = new NodoArbolDecision();

	}

	public MatrizDobleTableModel(NodoArbolDecision nodo) {
		this.nodo = nodo;
	}

	public void setNodo(NodoArbolDecision n) {
		this.nodo = n;

	}

	@Override
	public int getColumnCount() {
		return getRowCount();
	}

	@Override
	public int getRowCount() {
		return nodo.getMatriz().size() + 1;
	}

	@Override
	public Object getValueAt(int a, int b) {
		if (a == 0 && b == 0) {
			return "---";
		}
		if (a == 0) {
			return this.nodo.getMatriz().getElement(b - 1);
		} else if (b == 0) {
			return this.nodo.getMatriz().getElement(a - 1);
		}

		return nodo.getMatriz().getValor(a - 1, b - 1);

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean isSelected, boolean arg3, int row,
			int column) {

		if (row == 0 || column == 0) {

			JTextField lbl = new JTextField();
			lbl.setEditable(false);
			lbl.setText(arg1.toString());
			lbl.setBackground(ANPColors.TABLEHEADER.getColor());

			return lbl;
		} else {

			JLabel field = new JLabel(arg1.toString());
			field.setOpaque(true);
			if (isSelected) {
				field.setBackground(ANPColors.SELECT.getColor());
			}
			if (column == row) {
				field.setBackground(ANPColors.COMPLETO.getColor());
			}

			return field;

		}

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
