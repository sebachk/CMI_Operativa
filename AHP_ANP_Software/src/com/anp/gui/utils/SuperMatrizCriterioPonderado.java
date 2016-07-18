package com.anp.gui.utils;

import java.util.List;

import com.ahp.StructureManager;
import com.anp.CriterioANP;

public class SuperMatrizCriterioPonderado extends
		SuperMatrizNivelCriterioTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	public Double getValueByCriterios(CriterioANP f, CriterioANP c){
		int indexf = 0; 
		int indexc = 0;
		for(CriterioANP fila: this.rows){
			indexf += 1;
			if(f.getNombre().equals(fila.getNombre())){
				break;
			}
		}
		for(CriterioANP columna: this.cols){
			indexc += 1;
			if(c.getNombre().equals(columna.getNombre())){
				break;
			}
		}
		return (Double)this.getValueAt(indexf, indexc);
	}

}
