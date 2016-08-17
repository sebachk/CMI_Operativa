package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.ahp.StructureManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClusterLabel extends ColoredLabel implements ActionListener {

	public ClusterLabel() {
		lblLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				select();
			}
		});

		JButton buttonAddCriterios = new JButton("+");
		buttonAddCriterios.addActionListener(this);
		buttonAddCriterios.setBackground(Color.WHITE);
		add(buttonAddCriterios, BorderLayout.EAST);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StructureManager.getInstance().addToCluster(this);
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		super.setColor(c);

		StructureManager.getInstance().clusterChanged(this);

	}
	
	protected void select(){
		((PanelAlterClusANP)this.getParent().getParent().getParent()).SeleccionarCluster(this);
	}


}
