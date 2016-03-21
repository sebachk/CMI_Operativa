package com.ahp.gui.components;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;

public class PanelAHP extends PanelPrincipal {
	private JTabbedPane tabbedPane;

	public PanelAHP() {
		// getPanelDerecho().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tabbedPane.getSelectedIndex() == tabbedPane.indexOfComponent(TabMatrices.getInstance()))
					TabMatrices.getInstance().actualizar(TabDefiniciones.getInstance().getNodoArbolDecisionActual());

			}
		});

		getPanelDerecho().add(tabbedPane);
		super.getTree().setCellRenderer(new AHPCellTreeRenderer());
		super.getTree().setModel(new DefaultTreeModel(new NodoArbolAHP("Goal")));

		// this.tabbedPane.addTab("Decision", null,
		// TabDefiniciones.getInstance(), null);

		TabDefiniciones defis = TabDefiniciones.getInstance();
		defis.setNodoActual((NodoArbolAHP) getTree().getModel().getRoot());

		this.tabbedPane.addTab("Definiciones", null, defis, "DEFINIDENDO LAS COSAS");
		this.tabbedPane.addTab("Matriz", null, TabMatrices.getInstance(), null);
		this.tabbedPane.addTab("RESULTADOS", TabResults.getinstance());
		defis.tree = getTree();

	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
}
