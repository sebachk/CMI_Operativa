package com.ahp.gui.components;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultTreeModel;

import java.awt.BorderLayout;

public class PanelAHP extends PanelPrincipal {
	private JTabbedPane tabbedPane;
	
	public PanelAHP() {
		
		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getPanelDerecho().add(tabbedPane, BorderLayout.NORTH);
		super.getTree().setCellRenderer(new AHPCellTreeRenderer());
		super.getTree().setModel(new DefaultTreeModel(new NodoArbolAHP("Goal")));
		//this.tabbedPane.addTab("Decision", null, TabDefiniciones.getInstance(), null);
		
		TabDefiniciones defis = TabDefiniciones.getInstance();
		defis.setNodoActual((NodoArbolAHP) getTree().getModel().getRoot());
		this.tabbedPane.addTab("Definiciones", null, defis, null);
		defis.tree = getTree();
	}
	

}
