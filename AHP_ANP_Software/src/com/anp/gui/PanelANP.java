package com.anp.gui;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ahp.StructureManager;
import com.ahp.gui.components.PanelPrincipal;

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
				if (StructureManager.getInstance().getTabbedPane().getSelectedIndex() == StructureManager.getInstance()
						.getTabbedPane().indexOfComponent(TabSuperMatrizInicial.getInstance())) {
					TabSuperMatrizInicial.getInstance().actualizar();
				}

			}
		});

		getPanelDerecho().add(StructureManager.getInstance().getTabbedPane());
		getPanelDerecho().setLayout(new BorderLayout(0, 0));

		this.remove(super.getTree());

		this.getPanelIzquierdo().add(new PanelAlterClusANP());

		StructureManager.getInstance().addTab("Definiciones", StructureManager.getInstance().getTabCriterios(),
				"Definicion de Criterios");
		StructureManager.getInstance().addTab("Super Matriz", TabSuperMatrizInicial.getInstance(),
				"Super matriz sin ponderar");

		StructureManager.getInstance().enableTabs();

		this.getPanelDerecho().add(StructureManager.getInstance().getTabbedPane());

	}

}
