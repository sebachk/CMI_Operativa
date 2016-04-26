package com.anp.gui;

import java.awt.BorderLayout;

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
		getSplitPane().setResizeWeight(0.01);
		// getPanelDerecho().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		StructureManager.getInstance().setTabbedPane(new JTabbedPane(JTabbedPane.TOP));
		StructureManager.getInstance().getTabbedPane().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (StructureManager.getInstance().getTabbedPane().getSelectedIndex() == StructureManager.getInstance()
						.getTabbedPane().indexOfComponent(StructureManager.getInstance().getTabCriterios())) {
					// sarasa
				}

			}
		});

		getPanelDerecho().add(StructureManager.getInstance().getTabbedPane());
		getPanelDerecho().setLayout(new BorderLayout(0, 0));

		this.remove(super.getTree());

		this.getPanelIzquierdo().add(new PanelAlterClusANP());

		StructureManager.getInstance().addTab("Definiciones", StructureManager.getInstance().getTabCriterios(),
				"Definicion de Criterios");
		this.getPanelDerecho().add(StructureManager.getInstance().getTabbedPane());

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
