package com.ahp.gui.components;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;

import com.ahp.NodoArbolDecision;
import com.ahp.StructureManager;

public class PanelAHP extends PanelPrincipal {
	private JTabbedPane tabbedPane;

	public PanelAHP() {
		// getPanelDerecho().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tabbedPane.getSelectedIndex() == tabbedPane.indexOfComponent(TabMatrices.getInstance()))
					TabMatrices.getInstance().actualizar(TabDefiniciones.getInstance().getNodoArbolDecisionActual());
				if (tabbedPane.getSelectedIndex() == tabbedPane.indexOfComponent(TabResults.getinstance())) {
					TabResults.getinstance()
							.armarEstadistica(TabDefiniciones.getInstance().getNodoArbolDecisionActual());

				}
				if (tabbedPane.getSelectedIndex() == tabbedPane.indexOfComponent(TabDecision.getInstance())) {
					TabDecision.getInstance().generarDecision();
				}

			}
		});

		getPanelDerecho().add(tabbedPane);
		super.getTree().setCellRenderer(new AHPCellTreeRenderer());
		StructureManager.getInstance().crearArbol("Goal");
		super.getTree()
				.setModel(new DefaultTreeModel(new NodoArbolAHP(StructureManager.getInstance().getArbol().getGoal())));

		// this.tabbedPane.addTab("Decision", null,
		// TabDefiniciones.getInstance(), null);

		TabDefiniciones defis = TabDefiniciones.getInstance();
		defis.setNodoActual((NodoArbolAHP) getTree().getModel().getRoot());

		this.tabbedPane.addTab("Definiciones", null, defis, "DEFINIDENDO LAS COSAS");
		this.tabbedPane.addTab("Matriz", null, TabMatrices.getInstance(), null);
		this.tabbedPane.addTab("Resultados", TabResults.getinstance());
		this.tabbedPane.addTab("Decision", TabDecision.getInstance());
		defis.tree = getTree();

	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void arbolCargado() {
		super.getTree().setModel(new DefaultTreeModel(StructureManager.getInstance().nuevoArbool()));
		DefaultTreeModel model = (DefaultTreeModel) super.getTree().getModel();
		model.reload();

		for (NodoArbolDecision alt : StructureManager.getInstance().getArbol().getAlternativas()) {
			TabDefiniciones.getInstance().agregarAlternativa(alt);
		}

		this.getRootPane().repaint();

	}
}
