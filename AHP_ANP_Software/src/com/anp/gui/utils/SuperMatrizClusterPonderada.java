package com.anp.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import com.ahp.StructureManager;
import com.anp.CriterioANP;
import com.anp.MatrizDefinicionANP;

public class SuperMatrizClusterPonderada extends SuperMatrizNivelClusterTableModel {

	List<String> clus;

	public SuperMatrizClusterPonderada(HashMap<String, List<CriterioANP>> rows) {
		super(rows);
		
		
		clus = new ArrayList<String>();
		
		for(String s:this.rows.keySet()){
			if(s.equals(MatrizDefinicionANP.CLUSTER_ALTERNATIVAS)){
				clus.add(0, s);
			}
			else{
				clus.add(s);
			}
		}
		
		//clus.addAll(this.rows.keySet());

		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object obj = super.getValueAt(row, col);
		if (obj instanceof Color) {
			return StructureManager.getInstance().getMatrizANP().getValueClusterAt(
					getValueAt(row, 0).toString(), getValueAt(0, col).toString());
		}
		return obj;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		StructureManager.getInstance().getMatrizANP().setValueClusterAt(
				getValueAt(rowIndex, 0).toString(), getValueAt(0, columnIndex).toString(),
				Double.valueOf((String) aValue));

		StructureManager.getInstance().tabMatrizPonderadaANPChangedState();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		Object obj = super.getValueAt(rowIndex, columnIndex);

		if (obj instanceof Color) {
			return ((Color) obj).equals(ANPColors.COMPLETO.getColor());
		}
		return false;
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
			if (isCellEditable(row, col)) {
				JTextField field = new JTextField();
				field.setText(String.valueOf(value));
				if (isSelected) {
					field.setBorder(new MatteBorder(1, 1, 1, 1, Color.cyan));

				}

				switch (StructureManager.getInstance().getMatrizANP()
						.getRelacionCluster((String) this.getValueAt(0, col), clus)) {
				case 1:
					field.setBackground(ANPColors.COMPLETO.getColor());
					break;
				case 0:
					field.setBackground(ANPColors.NO_CRIT_CELL.getColor());
					break;
				case -1:
					field.setBackground(ANPColors.INCOMPLETO.getColor());
				default:
					break;
				}
				;

				return field;

			} else
				return super.getTableCellRendererComponent(obj, ANPColors.NO_CRIT_CELL.getColor(),
						false, arg3, row, col);
		}

	}
}
