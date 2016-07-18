package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.ahp.StructureManager;
import com.anp.MatrizDefinicionANP;
import com.anp.gui.utils.SuperMatrizClusterPonderada;
import com.anp.gui.utils.SuperMatrizCriterioPonderado;

public class TabSuperMatrizPonderada extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static TabSuperMatrizPonderada instance;

	public static TabSuperMatrizPonderada getInstance() {
		if (instance == null) {
			instance = new TabSuperMatrizPonderada();
		}
		return instance;
	}

	private JTable tabla;
	private JTable tablaIncidencia;

	public TabSuperMatrizPonderada() {

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
		panelSur.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panelOeste = new JPanel();
		panelSur.add(panelOeste);
		tablaIncidencia = new JTable();
		panelOeste.add(tablaIncidencia);
		tablaIncidencia.setCellSelectionEnabled(true);
		
		JPanel panelEste = new JPanel();
		panelSur.add(panelEste);

		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				int row = tabla.getSelectedRow();
				int col = tabla.getSelectedColumn();
				if(tabla.isCellEditable(row, col)){
					String clusterFila = (String) tabla.getModel().getValueAt(row, 0);
					String clusterCol = (String) tabla.getModel().getValueAt(0, col);
					SuperMatrizCriterioPonderado modelo = new SuperMatrizCriterioPonderado(
							StructureManager.getInstance().getMatrizANP().getFromCluster(clusterCol),
							StructureManager.getInstance().getMatrizANP().getFromCluster(clusterFila));
					tablaIncidencia.setModel(modelo);
					e.consume();

					tablaIncidencia.setVisible(true);
					tablaIncidencia.setDefaultRenderer(Object.class, modelo);
					panelOeste.updateUI();
				}
				else{
					tablaIncidencia.setVisible(false);
				}
				panelOeste.updateUI();
			}

		});

		if (matriz != null) {
			// SuperMatrizClusterPonderada crits = new
			// SuperMatrizClusterPonderada(matriz.getAlternativas(),
			// matriz.getCriterios());
			SuperMatrizClusterPonderada crits = new SuperMatrizClusterPonderada(
					matriz.getClusters());
			tabla.setModel(crits);
			tabla.setDefaultRenderer(Object.class, crits);
		}
	}

	public void actualizar() {
		MatrizDefinicionANP matriz = StructureManager.getInstance().getMatrizANP();
		if (matriz != null) {
			SuperMatrizClusterPonderada crits = new SuperMatrizClusterPonderada(
					matriz.getClusters());
			tabla.setModel(crits);
			tabla.setDefaultRenderer(Object.class, crits);
		}
	}

	public void changedTables() {
		((DefaultTableModel) tabla.getModel()).fireTableDataChanged();
		((DefaultTableModel) tablaIncidencia.getModel()).fireTableDataChanged();
	}

}
