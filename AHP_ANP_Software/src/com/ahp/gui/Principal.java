package com.ahp.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import com.ahp.ArbolDecisionAHP;
import com.ahp.StructureManager;
import com.ahp.XMLSaver;
import com.ahp.gui.components.PanelAHP;
import com.ahp.gui.components.TabDefiniciones;
import com.anp.CriterioANP;
import com.anp.MatrizDefinicionANP;
import com.anp.gui.ClusterLabel;
import com.anp.gui.PanelANP;
import com.anp.gui.PanelAlterClusANP;

public class Principal {
	//https://www.cma.gva.es/comunes_asp/documentos/agenda/Val/68810-3.%20Jornada%20Medioambiente%20Conseller%C3%ADa.pdf
	private JFrame frame;
	private JPanel panelPrincipal;

	private PanelAHP panelAHP;
	private PanelANP panelANP;
	private JTabbedPane tabbedPane;

	private JPanel panel_1;
	private JPanel desicionTree;
	private JPanel panel_2;
	private JTree tree;
	private JTabbedPane pestanias;
	private TabDefiniciones tabDefiniciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	public JFrame getMain() {
		return this.frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 710, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		

		JMenu mnNuevo = new JMenu("Nuevo");
		mnArchivo.add(mnNuevo);

		JMenuItem mntmNuevoAHP = new JMenuItem("AHP");
		mnNuevo.add(mntmNuevoAHP);

		JMenuItem mntmAnp = new JMenuItem("ANP");
		mntmAnp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StructureManager.getInstance().crearMatrizANP();
				panelANP = new PanelANP();
				frame.setContentPane(panelANP);
				panelANP.updateUI();
			}
		});
		mnNuevo.add(mntmAnp);
		mntmNuevoAHP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String arbolName = JOptionPane.showInputDialog(frame, "¿Que quiere decidir? sea breve",
						"Nuevo Objetivo");

				if (arbolName != null && !arbolName.isEmpty()) {
					panelAHP = new PanelAHP();

					frame.setContentPane(panelAHP);
					StructureManager.getInstance().crearArbol(arbolName);
					StructureManager.getInstance().habilitar();
					panelAHP.arbolCargado();
					panelAHP.updateUI();
				}
				
			}
		});

		JSeparator separator_1 = new JSeparator();
		mnArchivo.add(separator_1);

		/********** Menu Abrir **********/
		JMenu mnAbrir = new JMenu("Abrir");
		mnArchivo.add(mnAbrir);
		
		JMenuItem subMenuAbrirAHP = new JMenuItem("Abrir AHP");
		subMenuAbrirAHP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArbolDecisionAHP arbol = XMLSaver.loadXML(getMain());
				panelAHP = new PanelAHP();
				frame.setContentPane(panelAHP);
				StructureManager.getInstance().setArbol(arbol);
				StructureManager.getInstance().habilitar();
				panelAHP.arbolCargado();
				panelAHP.updateUI();
			}
		});
		mnAbrir.add(subMenuAbrirAHP);
		
		JMenuItem subMenuAbrirANP = new JMenuItem("Abrir ANP");
		subMenuAbrirANP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MatrizDefinicionANP matrizANP = XMLSaver.loadANPFromXML(getMain());
				StructureManager.getInstance().setMatrizANP(matrizANP);
				panelANP = new PanelANP();
				panelANP.getPanelIzquierdo().removeAll();
				PanelAlterClusANP panelIzquierdo = new PanelAlterClusANP();
				Iterator<CriterioANP> it = matrizANP.getAlternativas().iterator();
				while(it.hasNext()){
					CriterioANP next = it.next();
					panelIzquierdo.cargarAlternativa(next.getNombre());
				}
				List<String> clusters = new ArrayList<String>(matrizANP.getClusters().keySet());
				for(String cluster: clusters){
					if(cluster.equals(matrizANP.CLUSTER_ALTERNATIVAS)){continue;}
					ClusterLabel cLabel=panelIzquierdo.cargarCluster(cluster);
					for(CriterioANP c: matrizANP.getClusters().get(cluster)){
						StructureManager.getInstance().getTabCriterios().cargarCriterio(c, cLabel);
					}					
				}
				panelANP.getPanelIzquierdo().add(panelIzquierdo);
				frame.setContentPane(panelANP);
				panelANP.updateUI();
			}
		});
		mnAbrir.add(subMenuAbrirANP);
		
		/************* Menu Guardar *************/
		JMenu mnGuardar = new JMenu("Guardar");
		mnArchivo.add(mnGuardar);
		
		JMenuItem subMenuGuardarAHP = new JMenuItem("Guardar AHP");
		
		subMenuGuardarAHP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLSaver.saveXML(StructureManager.getInstance().getArbol(), getMain());
			}
		});
		mnGuardar.add(subMenuGuardarAHP);

		JMenuItem subMenuGuardarANP = new JMenuItem("Guardar ANP");
		subMenuGuardarANP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLSaver.saveANPToXML(StructureManager.getInstance().getMatrizANP(), getMain());
			}
		});
		mnGuardar.add(subMenuGuardarANP);
		
		/*********** SALIR ************/
		JSeparator separator = new JSeparator();
		mnArchivo.add(separator);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Salir");
		mnArchivo.add(mntmNewMenuItem_2);

		frame.getContentPane().setLayout(new BorderLayout(0, 0));

	}
}
