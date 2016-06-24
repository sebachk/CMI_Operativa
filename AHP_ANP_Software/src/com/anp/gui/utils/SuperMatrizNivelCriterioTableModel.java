package com.anp.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.ahp.StructureManager;
import com.anp.CriterioANP;

public class SuperMatrizNivelCriterioTableModel extends DefaultTableModel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	protected List<CriterioANP> rows;
	protected List<CriterioANP> cols;
	
	public SuperMatrizNivelCriterioTableModel(List<CriterioANP> cols, List<CriterioANP> rows) {
		this.rows = rows;
		this.cols = cols;
	}

	@Override
	public int getColumnCount() {
		return cols == null ? 0 : cols.size()+1;
	}

	@Override
	public int getRowCount() {
		return rows == null ? 0 : rows.size()+1;
	}

	@Override
	public Object getValueAt(int row, int col) {
		if ((row == col) && col == 0) {
			return " ";
		}
		if (row == 0) {
			return cols.get(col-1).getNombre();
		}
		if (col == 0) {
			return rows.get(row-1).getNombre();
		}
		return StructureManager.getInstance().getMatrizANP().getValueAt(rows.get(row-1), cols.get(col-1)) ==null?0:1;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Double v=null;
		if((Integer)aValue==1)
			v=0.0;
		if(rowIndex==0 || columnIndex==0) return;
		System.out.println("NADA");
		StructureManager.getInstance().getMatrizANP().setValueAt(rows.get(rowIndex), cols.get(columnIndex),v);
		
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

			JTextField field = new JTextField();
			if (isSelected) {
				field.setBackground(Color.CYAN);
			
			}
			field.setText(String.valueOf(value));
		
			return field;

		}

	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}