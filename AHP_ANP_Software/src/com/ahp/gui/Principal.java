package com.ahp.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class Principal {

	private JFrame frame;
	private JPanel panelPrincipal;

	private PanelAHP panelAHP;
	private JPanel panelANP;
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
		panelAHP = new PanelAHP();
		panelANP = new JPanel();

		frame = new JFrame();
		frame.setBounds(100, 100, 710, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenuItem mntmNewMenuItem = new JMenuItem("Abrir");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				ArbolDecisionAHP arbol = XMLSaver.loadXML(getMain());

				StructureManager.getInstance().setArbol(arbol);
				StructureManager.getInstance().habilitar();

				panelAHP.arbolCargado();
			}
		});

		JMenuItem mntmNuevo = new JMenuItem("Nuevo");
		mntmNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String arbolName = JOptionPane.showInputDialog(frame, "¿Que quiere decidir? sea breve",
						"Nuevo Objetivo");

				if (arbolName != null && !arbolName.isEmpty()) {
					StructureManager.getInstance().crearArbol(arbolName);
					StructureManager.getInstance().habilitar();
					panelAHP.arbolCargado();
				}

			}
		});
		mnArchivo.add(mntmNuevo);

		JSeparator separator_1 = new JSeparator();
		mnArchivo.add(separator_1);
		mnArchivo.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Guardar");
		mnArchivo.add(mntmNewMenuItem_1);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLSaver.saveXML(StructureManager.getInstance().getArbol(), getMain());
			}
		});

		JSeparator separator = new JSeparator();
		mnArchivo.add(separator);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Salir");
		mnArchivo.add(mntmNewMenuItem_2);

		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		frame.setContentPane(panelAHP);

	}
}
