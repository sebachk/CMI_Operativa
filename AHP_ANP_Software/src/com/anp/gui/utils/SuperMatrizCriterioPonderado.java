package com.anp.gui.utils;

import java.util.List;

import com.ahp.StructureManager;
import com.anp.CriterioANP;

public class SuperMatrizCriterioPonderado extends
		SuperMatrizNivelCriterioTableModel {

	public SuperMatrizCriterioPonderado(List<CriterioANP> cols,
			List<CriterioANP> rows) {
		super(cols, rows);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		//super.setValueAt(aValue, rowIndex, columnIndex);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Object o =super.getValueAt(row, col);
		
		if(o instanceof Double){
			Double v=StructureManager.getInstance().getMatrizANP().getValueClusterAt(rows.get(row-1).getCluster(), cols.get(col-1).getCluster());
			return (Double)o* (v==null?0.0:v);
		}
		
		return o;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
