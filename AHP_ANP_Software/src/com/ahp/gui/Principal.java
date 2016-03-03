package com.ahp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.ahp.gui.components.AHPCellTreeRenderer;

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
	private JPanel definicion;
	private JSplitPane splitPane;
	private JPanel panel_3;
	private JTable table;

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
		frame.setBounds(100, 100, 450, 300);
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
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("JTree") {
			{
				DefaultMutableTreeNode node_1;
				DefaultMutableTreeNode node_2;
				node_1 = new DefaultMutableTreeNode("asdasd");
				node_2 = new DefaultMutableTreeNode("HOLA");
				node_2.add(new DefaultMutableTreeNode("violet"));
				node_1.add(node_2);
				add(node_1);
				add(new DefaultMutableTreeNode("red"));
				node_1 = new DefaultMutableTreeNode("sports");
				node_1.add(new DefaultMutableTreeNode("basketball"));
				node_1.add(new DefaultMutableTreeNode("HOLA"));
				node_1.add(new DefaultMutableTreeNode("football"));
				node_1.add(new DefaultMutableTreeNode("hockey"));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("food");
				node_1.add(new DefaultMutableTreeNode("hot dogs"));
				node_1.add(new DefaultMutableTreeNode("pizza"));
				node_1.add(new DefaultMutableTreeNode("ravioli"));
				node_1.add(new DefaultMutableTreeNode("bananas"));
				add(node_1);
			}
		}));
		desicionTree.add(tree);

		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		pestanias = new JTabbedPane(JTabbedPane.TOP);
		panel_2.add(pestanias);

		definicion = new JPanel();
		pestanias.addTab("Defin", null, definicion, null);
		definicion.setLayout(new BorderLayout(0, 0));

		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		definicion.add(splitPane);

		panel_3 = new JPanel();
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(null);

		JButton btnAgregarAlter = new JButton("agregar alter");
		btnAgregarAlter.setBounds(10, 0, 89, 23);
		panel_3.add(btnAgregarAlter);

		table = new JTable();
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
		table.setBounds(10, 34, 305, 43);
		panel_3.add(table);

		panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panelPrincipal.setLayout(null);

		// JButton btnNewButton = new JButton("AHP");
		// btnNewButton.setBounds(113, 105, 97, 23);
		// panelPrincipal.add(btnNewButton);
		//
		// JButton btnNewButton_1 = new JButton("ANP");
		// btnNewButton_1.setBounds(215, 105, 97, 23);
		// panelPrincipal.add(btnNewButton_1);
		// btnNewButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// frame.remove(panelPrincipal);
		// frame.repaint();
		// }
		// });
	}
}
