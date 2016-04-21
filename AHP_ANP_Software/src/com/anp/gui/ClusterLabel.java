package com.anp.gui;
import javax.swing.JPanel;

import com.ahp.StructureManager;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClusterLabel extends ColoredLabel implements ActionListener {


	public ClusterLabel() {
		
		JButton buttonAddCriterios = new JButton("+");
		
		buttonAddCriterios.addActionListener(this);
		buttonAddCriterios.setBackground(Color.WHITE);
		add(buttonAddCriterios, BorderLayout.EAST);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		StructureManager.getInstance().addToCluster(this);
	}
}
