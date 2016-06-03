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

public class TabSuperMatrizInicial extends JPanel {

	private static TabSuperMatrizInicial instance;

	public static TabSuperMatrizInicial getInstance() {
		if (instance == null) {
			instance = new TabSuperMatrizInicial();
		}
		return instance;
	}

	private JTable tabla;

	public TabSuperMatrizInicial() {

		// SuperMatrixTableModel tablaModel = new
		// SuperMatrixTableModel(StructureManager.getInstance().getMatrizANP());
		this.setLayout(new BorderLayout());

		MatrizDefinicionANP matriz = StructureManager.getInstance().getMatrizANP();
		tabla = new JTable();
		this.add(tabla);
		tabla.setBorder(null);

		tabla.setCellSelectionEnabled(false);
		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(e.getClickCount());
				if (e.getClickCount()%2==0 && !e.isConsumed()) {
				     e.consume();
				     System.out.println("Double Click");
				     //handle double click event.
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
