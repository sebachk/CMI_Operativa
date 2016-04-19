package com.anp.gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.ahp.NodoArbolDecision;
import com.ahp.StructureManager;
import com.ahp.gui.components.PanelPrincipal;
import com.ahp.gui.components.TabDefiniciones;

public class PanelANP extends PanelPrincipal {

	public PanelANP() {
		// getPanelDerecho().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		StructureManager.getInstance().setTabbedPane(new JTabbedPane(JTabbedPane.TOP));
		StructureManager.getInstance().getTabbedPane().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// if
				// (StructureManager.getInstance().getTabbedPane().getSelectedIndex()
				// == StructureManager.getInstance()
				// .getTabbedPane().indexOfComponent(TabMatrices.getInstance()))
				// TabMatrices.getInstance().actualizar(TabDefiniciones.getInstance().getNodoArbolDecisionActual());
				// if
				// (StructureManager.getInstance().getTabbedPane().getSelectedIndex()
				// == StructureManager.getInstance()
				// .getTabbedPane().indexOfComponent(TabResults.getinstance()))
				// {
				// TabResults.getinstance()
				// .armarEstadistica(TabDefiniciones.getInstance().getNodoArbolDecisionActual());
				//
				// }
				// if
				// (StructureManager.getInstance().getTabbedPane().getSelectedIndex()
				// == StructureManager.getInstance()
				// .getTabbedPane().indexOfComponent(TabDecision.getInstance()))
				// {
				// TabDecision.getInstance().generarDecision();
				// }

			}
		});

		getPanelDerecho().add(StructureManager.getInstance().getTabbedPane());
		this.remove(super.getTree());
		// StructureManager.getInstance().addTab("Definiciones",
		// TabDefiniciones.getInstance(),
		// "Definicion de Criterios y alternativas");

		this.getPanelIzquierdo().add(new PanelAlterClusANP());

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
