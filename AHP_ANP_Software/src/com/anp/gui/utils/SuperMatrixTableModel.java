package com.anp.gui.utils;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.anp.CriterioANP;
import com.anp.MatrizDefinicionANP;

public class SuperMatrixTableModel extends AbstractTableModel implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected MatrizDefinicionANP matriz;

	public SuperMatrixTableModel() {
		super();

	}

	public SuperMatrixTableModel(MatrizDefinicionANP nodo) {
		this.matriz = nodo;
	}

	public void setMatriz(MatrizDefinicionANP m) {
		this.matriz = m;

	}

	@Override
	public int getColumnCount() {
		return matriz.getCriterios().size() + matriz.getAlternativas().size() + 2;
	}

	@Override
	public int getRowCount() {
		return matriz.getCriterios().size() + matriz.getAlternativas().size() + 2;
	}

	@Override
	public Object getValueAt(int a, int b) {
		if (a < 2 && b < 2) {
			return "////";
		}
		if (a < 2) {
			if (a == 0)
				return matriz.getCriterioAt(b - 2).getCluster();
			else
				return matriz.getCriterioAt(b - 2).getNombre();
		} else if (b < 2) {
			if (b == 0)
				return matriz.getCriterioAt(a - 2).getCluster();
			else
				return matriz.getCriterioAt(a - 2).getNombre();
		}
		return matriz.getValueAt(matriz.getCriterioAt(a - 2), matriz.getCriterioAt(b - 2));

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object value, boolean isSelected, boolean arg3, int row,
			int column) {

		Rectangle r = arg0.getCellRect(row, column, false);

		JPanel retorno = new JPanel();
		if (row == 0 && column == 0) {
			r.width *= 2;
			r.height *= 2;

			retorno = new JPanel();
			retorno.setBounds(r);
			return retorno;

		} else if ((row == 0 && column > 1) || (column == 0 && row > 1)) {

			int pos = column == 0 ? row - 2 : column - 2;

			CriterioANP criterio = matriz.getCriterioAt(pos);
			String clusterName = criterio.getCluster();
			int span = matriz.getFromCluster(clusterName).size();
			if (matriz.getFromCluster(clusterName).indexOf(criterio) > 0) {
				return null;
			}
			retorno = new JPanel();
			r.width *= (column == 0) ? 1 : span;
			r.height *= (column == 0) ? span : 1;

			retorno.setBounds(r);
			JLabel nombre = new JLabel(clusterName);

			retorno.add(nombre);

			return retorno;
		} else if ((row == 1 && column > 1) || (row > 1 && column == 1)) {
			int pos = column == 1 ? row - 2 : column - 2;

			CriterioANP criterio = matriz.getCriterioAt(pos);
			retorno = new JPanel();
			JLabel nombre = new JLabel(criterio.getNombre());

			retorno.add(nombre);
			return retorno;
		} else if (row > 1 && column > 1) {

			JCheckBox checkbox = new JCheckBox("");
			checkbox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (checkbox.isSelected()) {
						matriz.setValueAt(matriz.getCriterioAt(row - 2), matriz.getCriterioAt(column - 2), 1.0);
					} else {
						matriz.setValueAt(matriz.getCriterioAt(row - 2), matriz.getCriterioAt(column - 2), 0.0);
					}
				}
			});

			return checkbox;

		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (rowIndex > 0 && columnIndex > 0 && rowIndex != columnIndex);
	}
	
	
	
	
}
