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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.ahp.StructureManager;
import com.anp.CriterioANP;
import com.anp.gui.utils.ANPColors;

public class PanelAlterClusANP extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField fieldAlternativa;

	private JButton btnAddCluster;

	private JButton btnAgregarAlter;

	private JPanel panelListaAlternativas;

	private JTextField newClustertextField;

	private JPanel panelClusters;

	private JPanel panelListaCluster;
	private JPanel panel;
	private JButton btnEliminarAlter;
	private JPanel panel_1;
	private JButton btnEliminarClus;

	public PanelAlterClusANP() {
		// La parte de arriba
		JPanel panelAlternativas = new JPanel();
		panelAlternativas.setLayout(new BorderLayout(0, 0));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		panel = new JPanel();
		panelAlternativas.add(panel, BorderLayout.SOUTH);
				panel.setLayout(new BorderLayout(0, 0));
		
				JPanel panelInputAlternativa = new JPanel();
				panel.add(panelInputAlternativa);
				panelInputAlternativa.setBackground(SystemColor.inactiveCaption);
				
						btnAgregarAlter = new JButton("Agregar Alternativa");
						btnAgregarAlter.addActionListener(this);
						panelInputAlternativa.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						panelInputAlternativa.add(btnAgregarAlter);
						
								fieldAlternativa = new JTextField();
								fieldAlternativa.setHorizontalAlignment(SwingConstants.CENTER);
								panelInputAlternativa.add(fieldAlternativa);
								fieldAlternativa.setColumns(20);
								
								btnEliminarAlter = new JButton("Eliminar");
								btnEliminarAlter.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										for(Component c:panelListaAlternativas.getComponents()){
											if(c instanceof JLabel){
												if(c.isOpaque()){
													StructureManager.getInstance().getMatrizANP().eliminarAlternativa(((JLabel) c).getText());
													c.getParent().remove(c);
													updateUI();
												}
											}
										}
												
									}
								});
								panel.add(btnEliminarAlter, BorderLayout.EAST);
								fieldAlternativa.addKeyListener(new KeyAdapter() {
									@Override
									public void keyPressed(KeyEvent arg0) {
										if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
											arg0.setSource(btnAgregarAlter);
											actionPerformed(new ActionEvent(btnAgregarAlter, 1, null));
										}
									}
								});
	
		panelListaAlternativas = new JPanel();
		panelAlternativas.add(panelListaAlternativas, BorderLayout.CENTER);
		panelListaAlternativas.setLayout(new GridLayout(10, 1, 0, 0));

		this.add(panelAlternativas);

		JLabel lblAlternativas = new JLabel("Alternativas");
		lblAlternativas.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlternativas.setForeground(Color.BLACK);
		lblAlternativas.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblAlternativas.setBackground(SystemColor.activeCaption);
		panelAlternativas.add(lblAlternativas, BorderLayout.NORTH);
		

		panelClusters = new JPanel();
		panelClusters.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		panelClusters.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panelClusters.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panelInputCluster = new JPanel();
		panel_1.add(panelInputCluster);
		panelInputCluster.setBackground(SystemColor.activeCaption);
		FlowLayout fl_panelInputCluster = new FlowLayout(FlowLayout.CENTER, 10, 5);
		panelInputCluster.setLayout(fl_panelInputCluster);

		btnAddCluster = new JButton("Agregar grupo");
		btnAddCluster.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddCluster.addActionListener(this);
		panelInputCluster.add(btnAddCluster);

		newClustertextField = new JTextField();
		newClustertextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					arg0.setSource(btnAddCluster);
					actionPerformed(new ActionEvent(btnAddCluster, 1, null));
				}
			}
		});
		newClustertextField.setHorizontalAlignment(SwingConstants.CENTER);
		panelInputCluster.add(newClustertextField);
		newClustertextField.setColumns(20);
		
		btnEliminarClus = new JButton("Eliminar");
		btnEliminarClus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Component c:panelListaCluster.getComponents()){
					if(c instanceof ClusterLabel){
						if(((ClusterLabel) c).seleccionado()){
							StructureManager.getInstance().eliminarCluster(c.getName());
							c.getParent().remove(c);
							updateUI();
						}
						
					}
				}
						
			}
		});
		panel_1.add(btnEliminarClus, BorderLayout.EAST);

		JLabel lblCriterion = new JLabel("Grupos");
		panelClusters.add(lblCriterion, BorderLayout.NORTH);
		lblCriterion.setForeground(Color.BLACK);
		lblCriterion.setBackground(SystemColor.activeCaption);
		lblCriterion.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCriterion.setHorizontalAlignment(SwingConstants.CENTER);

		panelListaCluster = new JPanel();
		panelListaCluster.setLayout(new GridLayout(10, 1, 0, 0));
		panelClusters.add(panelListaCluster, BorderLayout.CENTER);

		this.add(panelClusters);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAddCluster)) {
			String grupo = newClustertextField.getText();
			if (grupo == null || grupo.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Debe ingresar un Grupo");
			}
			else {
				ColoredLabel cluster = new ClusterLabel();
				cluster.setName(grupo);
				int r = (int) (Math.random() * 25) * 10;
				int g = (int) (Math.random() * 25) * 10;
				int b = (int) (Math.random() * 25) * 10;
				Color c = new Color(r, g, b);
				cluster.setColor(c);
	
				panelListaCluster.add(cluster);
				panelListaCluster.updateUI();
				newClustertextField.setText("");
			}
		}
		if (e.getSource().equals(btnAgregarAlter)) {
			String alter = fieldAlternativa.getText();
			if (alter == null || alter.equals("")) {
				JOptionPane.showMessageDialog(this, "Debe ingresar una alternativa");
			} else {
				JLabel nuevo = new JLabel(alter);
				panelListaAlternativas.add(nuevo);
				panelListaAlternativas.updateUI();
				fieldAlternativa.setText("");
				nuevo.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						seleccionarAlternativa(nuevo);
					}
				});
				CriterioANP alt = new CriterioANP();
				alt.setNombre(alter);
				StructureManager.getInstance().getMatrizANP().addAlternativa(alt);
			}
		}
	}
	
	public ClusterLabel cargarCluster(String grupo){
		if (grupo == null || grupo.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe ingresar un Grupo");
		}
		ClusterLabel cluster = new ClusterLabel();
		cluster.setName(grupo);
		int r = (int) (Math.random() * 25) * 10;
		int g = (int) (Math.random() * 25) * 10;
		int b = (int) (Math.random() * 25) * 10;
		Color c = new Color(r, g, b);
		cluster.setColor(c);

		panelListaCluster.add(cluster);
		panelListaCluster.updateUI();
		newClustertextField.setText("");
		return cluster;
	}
	
	public void cargarAlternativa(String alter){
		if (alter == null || alter.equals("")) {
			JOptionPane.showMessageDialog(this, "Debe ingresar una alternativa");
		} else {
			JLabel nuevo = new JLabel(alter);
			panelListaAlternativas.add(nuevo);
			panelListaAlternativas.updateUI();
			fieldAlternativa.setText("");
			nuevo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					seleccionarAlternativa(nuevo);
				}
			});
//			CriterioANP alt = new CriterioANP();
//			alt.setNombre(alter);
//			StructureManager.getInstance().getMatrizANP().addAlternativa(alt);
		}
	}
	
	public void SeleccionarCluster(ClusterLabel sel){
		for (Component c :panelListaCluster.getComponents() ){
			if(c instanceof ClusterLabel){
				((ClusterLabel)c).deseleccionar();
			}
		}

		sel.seleccionar();

		updateUI();
	}
	
	public void seleccionarAlternativa (JLabel alter){
		for(Component child:panelListaAlternativas.getComponents()){
			if(child instanceof JLabel){
				((JLabel) child).setOpaque(false);
				
			}
			alter.setOpaque(true);
			alter.setBackground(ANPColors.SELECT.getColor());
		}
		updateUI();
		
	}

}
