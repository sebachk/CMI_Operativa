package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.ahp.StructureManager;
import com.anp.MatrizDefinicionANP;
import com.anp.gui.utils.SuperMatrizNivelClusterTableModel;
import com.anp.gui.utils.SuperMatrizNivelCriterioTableModel;

import java.awt.GridLayout;

import javax.swing.JButton;

public class TabSuperMatrizInicial extends JPanel {

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
		panelSur.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelOeste = new JPanel();
		panelSur.add(panelOeste);
		
		tablaIncidencia = new JTable();
		panelOeste.add(tablaIncidencia);

		tablaIncidencia.setCellSelectionEnabled(true);
		JPanel panelEste = new JPanel();
		panelSur.add(panelEste);
		
		JButton selectAll = new JButton("Seleccionar todo");
		panelEste.add(selectAll);
		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(e.getClickCount());
				if (e.getClickCount()%2==0 && !e.isConsumed()) {
				     e.consume();
				     int row = tabla.getSelectedRow();
				     int col = tabla.getSelectedColumn();
				     String clusterFila = (String) tabla.getModel().getValueAt(row, 0);
				     String clusterCol =  (String) tabla.getModel().getValueAt(0, col);
				     SuperMatrizNivelCriterioTableModel modelo = 
				    		 new SuperMatrizNivelCriterioTableModel(
				    				 StructureManager.getInstance().getMatrizANP().getFromCluster(clusterCol), 
				    				 StructureManager.getInstance().getMatrizANP().getFromCluster(clusterFila));
				     tablaIncidencia.setModel(modelo);
				     tablaIncidencia.setDefaultRenderer(Object.class, modelo);
				     panelOeste.updateUI();
				}
			}
			
		});

		if (matriz != null) {
			//SuperMatrizNivelClusterTableModel crits = new SuperMatrizNivelClusterTableModel(matriz.getAlternativas(),
			//		matriz.getCriterios());
			SuperMatrizNivelClusterTableModel crits = new SuperMatrizNivelClusterTableModel(matriz.getClusters());
			tabla.setModel(crits);
			tabla.setDefaultRenderer(Object.class, crits);
		}
	}

	public void actualizar() {
		MatrizDefinicionANP matriz = StructureManager.getInstance().getMatrizANP();
		if (matriz != null) {
			SuperMatrizNivelClusterTableModel crits = new SuperMatrizNivelClusterTableModel(matriz.getClusters());
			tabla.setModel(crits);
			tabla.setDefaultRenderer(Object.class, crits);
		}
	}
}
