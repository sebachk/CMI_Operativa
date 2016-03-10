package com.ahp.gui.components;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import java.awt.BorderLayout;

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
