package com.ahp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultTreeModel;

import com.ahp.gui.components.AHPCellTreeRenderer;
import com.ahp.gui.components.NodoArbolAHP;
import com.ahp.gui.components.TabDefiniciones;

public class Principal {

	private JFrame frame;
	private JPanel panelPrincipal;

	private JPanel panelAHP;
	private JPanel panelANP;
	private JTabbedPane tabbedPane;
	private JPanel panel;
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
		panelAHP = new JPanel();
		panelANP = new JPanel();

		frame = new JFrame();
		frame.setBounds(100, 100, 710, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		panelPrincipal = new JPanel();
		frame.getContentPane().add(panelPrincipal);
		frame.getContentPane().add(panelAHP);
		panelAHP.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("HOLA");
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		panelAHP.add(tabbedPane);

		panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));

		desicionTree = new JPanel();
		desicionTree.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(desicionTree, BorderLayout.WEST);
		desicionTree.setLayout(new BorderLayout(0, 0));

		tree = new JTree();
		tree.setCellRenderer(new AHPCellTreeRenderer());
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(new NodoArbolAHP("Goal")));
		desicionTree.add(tree);

		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		pestanias = new JTabbedPane(JTabbedPane.TOP);
		panel_2.add(pestanias);

		// Tab de Definiciones

		TabDefiniciones defis = TabDefiniciones.getInstance();
		defis.setNodoActual((NodoArbolAHP) tree.getModel().getRoot());
		pestanias.addTab("Definiciones", null, defis, null);
		defis.tree = tree;

		panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panelPrincipal.setLayout(null);
	}
}
