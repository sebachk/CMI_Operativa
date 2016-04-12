package com.ahp.gui.components;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.ahp.NodoArbolDecision;
import com.ahp.StructureManager;

public class PanelAHP extends PanelPrincipal {

	public PanelAHP() {
		// getPanelDerecho().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		StructureManager.getInstance().setTabbedPane(new JTabbedPane(JTabbedPane.TOP));
		StructureManager.getInstance().getTabbedPane().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (StructureManager.getInstance().getTabbedPane().getSelectedIndex() == StructureManager.getInstance()
						.getTabbedPane().indexOfComponent(TabMatrices.getInstance()))
					TabMatrices.getInstance().actualizar(TabDefiniciones.getInstance().getNodoArbolDecisionActual());
				if (StructureManager.getInstance().getTabbedPane().getSelectedIndex() == StructureManager.getInstance()
						.getTabbedPane().indexOfComponent(TabResults.getinstance())) {
					TabResults.getinstance()
							.armarEstadistica(TabDefiniciones.getInstance().getNodoArbolDecisionActual());

				}
				if (StructureManager.getInstance().getTabbedPane().getSelectedIndex() == StructureManager.getInstance()
						.getTabbedPane().indexOfComponent(TabDecision.getInstance())) {
					TabDecision.getInstance().generarDecision();
				}

			}
		});

		getPanelDerecho().add(StructureManager.getInstance().getTabbedPane());
		super.getTree().setCellRenderer(new AHPCellTreeRenderer());
		// StructureManager.getInstance().crearArbol("Goal");
		// super.getTree()
		// .setModel(new DefaultTreeModel(new
		// NodoArbolAHP(StructureManager.getInstance().getArbol().getGoal())));

		// this.tabbedPane.addTab("Decision", null,
		// TabDefiniciones.getInstance(), null);

		super.getTree().setModel(new DefaultTreeModel(null));

		StructureManager.getInstance().addTab("Definiciones", TabDefiniciones.getInstance(),
				"Definicion de Criterios y alternativas");
		StructureManager.getInstance().addTab("Matriz", TabMatrices.getInstance(), "Ponderacion de Criterios");
		StructureManager.getInstance().addTab("Resultados", TabResults.getinstance(),
				"Visualizar los resultados parciales");
		StructureManager.getInstance().addTab("Decision", TabDecision.getInstance(), "Ver alternativa mas adecuada");

		StructureManager.getInstance().getTabbedPane().setEnabledAt(
				StructureManager.getInstance().getTabbedPane().indexOfComponent(TabDecision.getInstance()), false);
		TabDefiniciones.getInstance().tree = getTree();

	}

	public void arbolCargado() {
		super.getTree().setModel(new DefaultTreeModel(StructureManager.getInstance().nuevoArbool()));
		DefaultTreeModel model = (DefaultTreeModel) super.getTree().getModel();
		model.reload();

		this.getTree().setSelectionPath(new TreePath(this.getTree().getModel().getRoot()));

		TabDefiniciones.getInstance().clearAlternativas();

		for (NodoArbolDecision alt : StructureManager.getInstance().getArbol().getAlternativas()) {
			TabDefiniciones.getInstance().agregarAlternativa(alt);
		}
		StructureManager.getInstance().arbolCompleto();
		this.getRootPane().repaint();

	}
}
