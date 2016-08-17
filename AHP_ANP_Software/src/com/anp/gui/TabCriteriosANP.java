package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ahp.StructureManager;
import com.anp.CriterioANP;
import com.anp.MatrizDefinicionANP;

public class TabCriteriosANP extends JPanel {

	private JTextField textFieldCriterios;
	private JButton btnAgregarCriterio;
	private JPanel panelListaCrit;
	private JLabel labelCrit;

	private List<CriterioLabel> criteriosLabel;
	private JButton btnEliminarCrit;
	private JPanel panel;

	public TabCriteriosANP() {
		criteriosLabel = new ArrayList<CriterioLabel>();
		setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		this.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel_1.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						
								btnAgregarCriterio = new JButton("Agregar Criterio");
								panel.add(btnAgregarCriterio);
								
										textFieldCriterios = new JTextField();
										panel.add(textFieldCriterios);
										textFieldCriterios.setHorizontalAlignment(SwingConstants.CENTER);
										textFieldCriterios.setColumns(20);
										textFieldCriterios.addKeyListener(new KeyAdapter() {
											@Override
											public void keyPressed(KeyEvent arg0) {
												if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
													btnAgregarCriterio.doClick();

												}
											}
										});
								btnAgregarCriterio.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										// Crear el crit (visual)
										String nombreCriterio = textFieldCriterios.getText();
										if (nombreCriterio == null || nombreCriterio.isEmpty()) {
											JOptionPane.showMessageDialog(textFieldCriterios, "Debe ingresar un Criterio");
										} else {
											CriterioLabel nuevo = new CriterioLabel(nombreCriterio);
											StructureManager.getInstance().getMatrizANP().addCriterio(nuevo.getCriterioANP());
											panelListaCrit.add(nuevo);
											textFieldCriterios.setText("");
											panelListaCrit.updateUI();
											panelListaCrit.repaint();
											criteriosLabel.add(nuevo);
										}
									}
								});
		
		btnEliminarCrit = new JButton("Eliminar");
		btnEliminarCrit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<CriterioLabel> crits=getSeleccionados();
				StructureManager.getInstance().getMatrizANP().eliminarCriterios(crits);
				for(CriterioLabel c:crits){
					panelListaCrit.remove(c);
				}
				criteriosLabel.removeAll(crits);
				TabSuperMatrizInicial.getInstance().actualizar();
				TabSuperMatrizPonderada.getInstance().actualizar();
				updateUI();
				
			}
		});
		panel_1.add(btnEliminarCrit, BorderLayout.EAST);

		labelCrit = new JLabel("Criterios");
		labelCrit.setHorizontalAlignment(SwingConstants.CENTER);
		labelCrit.setForeground(Color.BLACK);
		labelCrit.setFont(new Font("Tahoma", Font.PLAIN, 25));
		labelCrit.setBackground(SystemColor.activeCaption);
		this.add(labelCrit, BorderLayout.NORTH);

		panelListaCrit = new JPanel();
		this.add(panelListaCrit, BorderLayout.CENTER);
		panelListaCrit.setLayout(new GridLayout(10, 1, 0, 0));
	}

	public List<CriterioLabel> getSeleccionados() {
		List<CriterioLabel> resultado = new ArrayList<CriterioLabel>();
		for (CriterioLabel crit : criteriosLabel) {
			if (crit.isSelected()) {
				resultado.add(crit);
				crit.deseleccionar();
			}
		}

		return resultado;
	}

	public void paintCriteriosFromCluster(ClusterLabel cl) {

		for (CriterioLabel crit : criteriosLabel) {
			if (cl.equals(crit.getcLabel())) {
				crit.setColor(cl.getColor());
			}
		}

	}
	
	public void cargarCriterio(CriterioANP criterio, ClusterLabel cLabel){
		CriterioLabel nuevo = new CriterioLabel(criterio);
		nuevo.addToCluster(cLabel);
		panelListaCrit.add(nuevo);
		panelListaCrit.updateUI();
		criteriosLabel.add(nuevo);
	}

	public void clusterEliminado(String cl){
		for (CriterioLabel crit : criteriosLabel) {
			if (cl.equals(crit.getcLabel().getName())) {
				crit.addToCluster(null);
			}
		}
		updateUI();
	}
	
}
