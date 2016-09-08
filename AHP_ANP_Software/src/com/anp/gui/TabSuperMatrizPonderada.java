package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.ahp.StructureManager;
import com.anp.CriterioANP;
import com.anp.MatrizDefinicionANP;
import com.anp.MatrizTemplate;
import com.anp.gui.utils.SuperMatrizClusterPonderada;
import com.anp.gui.utils.SuperMatrizCriterioPonderado;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;

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
	
	private MatrizTemplate<Double> matrizConvergida;

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
		panelSur.setLayout(new BorderLayout(0, 0));

		JPanel panelOeste = new JPanel();
		panelSur.add(panelOeste);
		panelOeste.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panelOeste.add(panel, BorderLayout.NORTH);
		tablaIncidencia = new JTable();
		tablaIncidencia.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelOeste.add(tablaIncidencia);
		tablaIncidencia.setCellSelectionEnabled(true);
		
		JPanel panel_1 = new JPanel();
		panelOeste.add(panel_1, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panelOeste.add(panel_3, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		panelOeste.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(SystemColor.activeCaption);
		panel_2.add(panel_5, BorderLayout.NORTH);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.activeCaption);
		panel_2.add(panel_4, BorderLayout.WEST);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(SystemColor.activeCaption);
		panel_2.add(panel_6, BorderLayout.SOUTH);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(SystemColor.activeCaption);
		panel_2.add(panel_7, BorderLayout.EAST);
		
		JButton btn_converger = new JButton("Decidir");
		panel_2.add(btn_converger);
		btn_converger.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				matrizConvergida = StructureManager.getInstance().getMatrizANP().converger();
				printPromedios();
				StructureManager.getInstance().getTabbedPane().setEnabledAt(
						StructureManager.getInstance().getTabbedPane().indexOfComponent(TabDecision.getInstance()), true);
				StructureManager.getInstance().getTabbedPane().setSelectedIndex(StructureManager.getInstance().getTabbedPane().indexOfComponent(TabDecision.getInstance()));

				StructureManager.getInstance().getTabbedPane().updateUI();
			}
		});

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
	
	public JTable getTabla() {
		return tabla;
	}
	public void setTabla(JTable tabla) {
		this.tabla = tabla;
	}
	
	public JTable getTablaIncidencia() {
		return tablaIncidencia;
	}
	public void setTablaIncidencia(JTable tablaIncidencia) {
		this.tablaIncidencia = tablaIncidencia;
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
	
	private Double promedioFila(MatrizTemplate<Double> m,CriterioANP fila){		
		List<CriterioANP> allCrit = new ArrayList<CriterioANP>(StructureManager.getInstance().getMatrizANP().getAlternativas());
		allCrit.addAll(StructureManager.getInstance().getMatrizANP().getCriterios());
		
		if(!allCrit.contains(fila)){
			return -1.0;
		}
		
		Iterator<CriterioANP> itf = allCrit.iterator();
		
		double suma=0.0;
		while (itf.hasNext()) {
			CriterioANP c1 = itf.next();
			String key = fila.getNombre() + MatrizDefinicionANP.SEPARATOR + c1.getNombre();
			suma+=m.getElement(key);
		}		
		return suma/allCrit.size();	
	}
	
	private void printPromedios(){
		Double suma= 0.00;
		TabDecision.getInstance().generarDecision();
		List<CriterioANP> alternativas = StructureManager.getInstance().getMatrizANP().getAlternativas();
		HashMap<String,Double> promedios = new HashMap<String,Double>();
		for(CriterioANP c:alternativas){
			Double p = promedioFila(matrizConvergida, c);
			suma += p;
			promedios.put(c.getNombre(), p);
		}
		
		for(CriterioANP c:StructureManager.getInstance().getMatrizANP().getAlternativas()){
			TabDecision.getInstance().addCriterio(c.getNombre(), (promedios.get(c.getNombre()))/suma);
		}		
	}

}
