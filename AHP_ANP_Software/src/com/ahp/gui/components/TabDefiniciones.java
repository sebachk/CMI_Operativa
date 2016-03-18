package com.ahp.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultTreeModel;

import com.ahp.NodoArbolDecision;

public class TabDefiniciones extends JSplitPane implements ActionListener {

	public JPanel panelCriterios;
	private JButton btnAgregarAlter;
	private JButton btnAddSubcriterio;
	private JPanel listAlternativas;

	private List<String> alternativas;

	private static final Font FUENTE = new Font("Tahoma", Font.PLAIN, 15);
	private JPanel panelListaCriterios;
	private JTextField textField;
	private NodoArbolAHP nodoActual;

	private static TabDefiniciones instance;

	public static TabDefiniciones getInstance() {
		if (instance == null) {
			instance = new TabDefiniciones();
		}
		return instance;
	}

	private TabDefiniciones() {
		setResizeWeight(0.5);
		// JSplitPane splitPane = new JSplitPane();
		// this.setResizeWeight(0.5);
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);

		// La parte de arriba
		JPanel panel_3 = new JPanel();
		this.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

		btnAgregarAlter = new JButton("agregar alter");
		btnAgregarAlter.addActionListener(this);
		panel_4.add(btnAgregarAlter);

		listAlternativas = new JPanel();
		alternativas = new ArrayList<String>();
		panel_3.add(listAlternativas, BorderLayout.CENTER);
		listAlternativas.setLayout(new GridLayout(10, 1, 0, 0));

		// La parte de abajo
		panelCriterios = new JPanel();

		this.setRightComponent(panelCriterios);
		panelCriterios.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(SystemColor.activeCaption);
		panelCriterios.add(panel_6, BorderLayout.SOUTH);
		FlowLayout fl_panel_6 = new FlowLayout(FlowLayout.CENTER, 10, 5);
		panel_6.setLayout(fl_panel_6);

		btnAddSubcriterio = new JButton("Add SubCriterio");
		btnAddSubcriterio.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddSubcriterio.addActionListener(this);
		panel_6.add(btnAddSubcriterio);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(textField);
		textField.setColumns(20);

		lblCriterion = new JLabel("CriterioN");
		lblCriterion.setForeground(Color.BLACK);
		lblCriterion.setBackground(SystemColor.activeCaption);
		lblCriterion.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCriterion.setHorizontalAlignment(SwingConstants.CENTER);
		panelCriterios.add(lblCriterion, BorderLayout.NORTH);

		panelListaCriterios = new JPanel();
		panelListaCriterios.setLayout(new GridLayout(10, 1, 0, 0));
		panelCriterios.add(panelListaCriterios, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAgregarAlter)) {
			String alter = JOptionPane.showInputDialog(this, "Ingrese nueva alternativa");

			JLabel nuevo = new JLabel(alter);
			nuevo.setFont(FUENTE);

			listAlternativas.add(nuevo);
			alternativas.add(alter);

			if (alternativas.size() > 9)
				((GridLayout) listAlternativas.getLayout()).setRows(alternativas.size());

			this.getRootPane().repaint();

		} else if (e.getSource().equals(btnAddSubcriterio)) {
			String criterio = textField.getText();
			if (criterio == null || criterio.equals("")) {
				JOptionPane.showMessageDialog(this, "Debe ingresar un criterio");
			} else {

				addCrit(criterio);
				textField.setText("");
				// NodoArbolAHP seleccionado = (NodoArbolAHP)
				// tree.getLastSelectedPathComponent();
				// nodoActual = seleccionado != null ? seleccionado :
				// nodoActual;
				nodoActual.addSubCriterio(criterio);
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				model.reload();
			}
			this.getRootPane().repaint();

		}

	}

	public void setNodoActual(NodoArbolAHP nodo) {
		nodoActual = nodo;
		lblCriterion.setText(nodoActual.getNombre());
		panelListaCriterios.removeAll();
		for (NodoArbolDecision nad : nodoActual.getReferencia().getHijos()) {
			addCrit(nad.getNombre());
		}
		if (this.getRootPane() != null)
			this.getRootPane().repaint();

	}

	public NodoArbolDecision getNodoArbolDecisionActual() {
		return nodoActual.getReferencia();
	}

	private void addCrit(String criterio) {
		JLabel nuevo = new JLabel(criterio);

		nuevo.setFont(FUENTE);

		panelListaCriterios.add(nuevo);

	}

	public JTree tree;
	private JLabel lblCriterion;

}
