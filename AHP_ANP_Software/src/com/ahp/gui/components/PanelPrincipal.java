package com.ahp.gui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class PanelPrincipal extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSplitPane splitPane;
	private JPanel panelDerecho;
	private JPanel panelIzquierdo;
	private JTree tree;

	public PanelPrincipal() {
		setLayout(new BorderLayout(0, 0));

		this.splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		add(splitPane);

		this.panelDerecho = new JPanel();
		splitPane.setRightComponent(panelDerecho);
		panelDerecho.setLayout(new BorderLayout(0, 0));

		this.panelIzquierdo = new JPanel();
		splitPane.setLeftComponent(panelIzquierdo);
		panelIzquierdo.setLayout(new BorderLayout(0, 0));

		this.tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				if (arg0.isAddedPath()) {
					TabDefiniciones.getInstance().setNodoActual((NodoArbolAHP) tree.getLastSelectedPathComponent());
					TabMatrices.getInstance().actualizar(TabDefiniciones.getInstance().getNodoArbolDecisionActual());
					TabResults.getinstance()
							.armarEstadistica(TabDefiniciones.getInstance().getNodoArbolDecisionActual());
				}
			}
		});
		panelIzquierdo.add(tree);

	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JPanel getPanelDerecho() {
		return panelDerecho;
	}

	public void setPanelDerecho(JPanel panelDerecho) {
		this.panelDerecho = panelDerecho;
	}

	public JPanel getPanelIzquierdo() {
		return panelIzquierdo;
	}

	public void setPanelIzquierdo(JPanel panelIzquierdo) {
		this.panelIzquierdo = panelIzquierdo;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

}
