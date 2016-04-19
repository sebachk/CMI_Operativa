package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class PanelAlterClusANP extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField fieldAlternativa;

	private JButton btnAddCluster;

	public PanelAlterClusANP() {
		// La parte de arriba
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.inactiveCaption);
		panel_3.add(panel_4, BorderLayout.SOUTH);

		JButton btnAgregarAlter = new JButton("Agregar Alternativa");
		btnAgregarAlter.addActionListener(this);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_4.add(btnAgregarAlter);

		fieldAlternativa = new JTextField();
		fieldAlternativa.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(fieldAlternativa);
		fieldAlternativa.setColumns(20);
		fieldAlternativa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					arg0.setSource(btnAgregarAlter);
					actionPerformed(new ActionEvent(btnAgregarAlter, 1, null));
				}
			}
		});
		setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelAlternativas = new JPanel();
		panel_3.add(panelAlternativas, BorderLayout.CENTER);
		panelAlternativas.setLayout(new GridLayout(10, 1, 0, 0));

		this.add(panel_3);

		JLabel lblAlternativas = new JLabel("Alternativas");
		lblAlternativas.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlternativas.setForeground(Color.BLACK);
		lblAlternativas.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblAlternativas.setBackground(SystemColor.activeCaption);
		panel_3.add(lblAlternativas, BorderLayout.NORTH);

		// La parte de abajo
		JPanel panelClusters = new JPanel();
		panelClusters.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		panelClusters.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(SystemColor.activeCaption);
		panelClusters.add(panel_6, BorderLayout.SOUTH);
		FlowLayout fl_panel_6 = new FlowLayout(FlowLayout.CENTER, 10, 5);
		panel_6.setLayout(fl_panel_6);

		btnAddCluster = new JButton("Agregar SubCriterio");
		btnAddCluster.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddCluster.addActionListener(this);
		panel_6.add(btnAddCluster);

		JTextField textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					arg0.setSource(btnAddCluster);
					actionPerformed(new ActionEvent(btnAddCluster, 1, null));
				}
			}
		});
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(textField);
		textField.setColumns(20);

		JLabel lblCriterion = new JLabel("Grupos");
		panelClusters.add(lblCriterion, BorderLayout.NORTH);
		lblCriterion.setForeground(Color.BLACK);
		lblCriterion.setBackground(SystemColor.activeCaption);
		lblCriterion.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCriterion.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panelListaCriterios = new JPanel();
		panelListaCriterios.setLayout(new GridLayout(10, 1, 0, 0));
		panelClusters.add(panelListaCriterios, BorderLayout.CENTER);

		this.add(panelClusters);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource().equals(btnAddCluster)) {
			ColoredLabel cluster = new ColoredLabel();
			cluster.setName("Nuevo");

		}
	}

}
