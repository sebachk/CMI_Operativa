package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.ahp.StructureManager;
import com.anp.MatrizDefinicionANP;
import com.anp.gui.utils.SuperMatrizNivelClusterTableModel;
import com.anp.gui.utils.SuperMatrizNivelCriterioTableModel;
import javax.swing.BoxLayout;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class TabSuperMatrizInicial extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static TabSuperMatrizInicial instance;

	public static TabSuperMatrizInicial getInstance() {
		if (instance == null) {
			instance = new TabSuperMatrizInicial();
		}
		return instance;
	}

	private JTable tabla;
	private JTable tablaIncidencia;

	public TabSuperMatrizInicial() {

		MatrizDefinicionANP matriz = StructureManager.getInstance().getMatrizANP();
		setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelNorte = new JPanel();
		add(panelNorte);
		panelNorte.setLayout(new BorderLayout(0, 0));
		tabla = new JTable();
		panelNorte.add(tabla);
		tabla.setBorder(null);
		tabla.setCellSelectionEnabled(true);

		JPanel panelSur = new JPanel();
		add(panelSur);
				panelSur.setLayout(new BorderLayout(0, 0));
		
				JPanel panelOeste = new JPanel();
				panelSur.add(panelOeste);
				panelOeste.setLayout(new BorderLayout(0, 0));
				
				JPanel panel = new JPanel();
				panelOeste.add(panel, BorderLayout.NORTH);
				
				JPanel panel_1 = new JPanel();
				panelOeste.add(panel_1, BorderLayout.SOUTH);
				
				JPanel panel_2 = new JPanel();
				panelOeste.add(panel_2, BorderLayout.WEST);
				
				JPanel panel_3 = new JPanel();
				panelOeste.add(panel_3, BorderLayout.EAST);
				tablaIncidencia = new JTable();
				tablaIncidencia.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
				panelOeste.add(tablaIncidencia);
				tablaIncidencia.setCellSelectionEnabled(true);

		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.addMouseListener(new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			int row = tabla.getSelectedRow();
			int col = tabla.getSelectedColumn();
			String clusterFila = (String) tabla.getModel().getValueAt(row, 0);
			String clusterCol = (String) tabla.getModel().getValueAt(0, col);
			SuperMatrizNivelCriterioTableModel modelo = new SuperMatrizNivelCriterioTableModel(
					StructureManager.getInstance().getMatrizANP().getFromCluster(clusterCol),
					StructureManager.getInstance().getMatrizANP().getFromCluster(clusterFila));
			tablaIncidencia.setModel(modelo);
			e.consume();
			tablaIncidencia.setDefaultRenderer(Object.class, modelo);
			panelOeste.updateUI();
			}
		});

		if (matriz != null) {
			// SuperMatrizNivelClusterTableModel crits = new
			// SuperMatrizNivelClusterTableModel(matriz.getAlternativas(),
			// matriz.getCriterios());
			SuperMatrizNivelClusterTableModel crits = new SuperMatrizNivelClusterTableModel(
					matriz.getClusters());
			tabla.setModel(crits);
			tabla.setDefaultRenderer(Object.class, crits);
		}
	}

	public void actualizar() {
		MatrizDefinicionANP matriz = StructureManager.getInstance().getMatrizANP();
		if (matriz != null) {
			SuperMatrizNivelClusterTableModel crits = new SuperMatrizNivelClusterTableModel(
					matriz.getClusters());
			tabla.setModel(crits);
			tabla.setDefaultRenderer(Object.class, crits);
			
			int row = tabla.getSelectedRow();
			row=row==-1?0:row;
			
			int col = tabla.getSelectedColumn();
			col=col==-1?0:col;
			
			String clusterFila = (String) tabla.getModel().getValueAt(row, 0);
			String clusterCol = (String) tabla.getModel().getValueAt(0, col);
			SuperMatrizNivelCriterioTableModel modelo = new SuperMatrizNivelCriterioTableModel(
					StructureManager.getInstance().getMatrizANP().getFromCluster(clusterCol),
					StructureManager.getInstance().getMatrizANP().getFromCluster(clusterFila));
			tablaIncidencia.setModel(modelo);
			tablaIncidencia.setDefaultRenderer(Object.class, modelo);
			
			
		}
	}

	public void changedTables() {
		((DefaultTableModel) tabla.getModel()).fireTableDataChanged();
		((DefaultTableModel) tablaIncidencia.getModel()).fireTableDataChanged();
	}

}
