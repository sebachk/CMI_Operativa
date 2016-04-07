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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
import com.ahp.StructureManager;

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
		panel_4.setBackground(SystemColor.inactiveCaption);
		panel_3.add(panel_4, BorderLayout.NORTH);

		btnAgregarAlter = new JButton("Agregar Alternativa");
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

		btnAddSubcriterio = new JButton("Agregar SubCriterio");
		btnAddSubcriterio.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddSubcriterio.addActionListener(this);
		panel_6.add(btnAddSubcriterio);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					arg0.setSource(btnAddSubcriterio);
					actionPerformed(new ActionEvent(btnAddSubcriterio, 1, null));
				}
			}
		});
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
			String alter = fieldAlternativa.getText();
			if (alter == null || alter.equals("")) {
				JOptionPane.showMessageDialog(this, "Debe ingresar una alternativa");
			} else {
				NodoArbolDecision alt = new NodoArbolDecision();
				alt.setNombre(alter);
				StructureManager.getInstance().getArbol().addAlternativa(alt);
				agregarAlternativa(alt);
				this.getRootPane().repaint();

			}
		} else if (e.getSource().equals(btnAddSubcriterio)) {
			String criterio = textField.getText();
			if (criterio == null || criterio.equals("")) {
				JOptionPane.showMessageDialog(this, "Debe ingresar un criterio");
			} else {

				addCrit(criterio);
				textField.setText("");
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
			if (!StructureManager.getInstance().getArbol().esAlternativa(nad)) {
				addCrit(nad.getNombre());
			}

		}
		if (this.getRootPane() != null)
			this.getRootPane().repaint();

	}

	public NodoArbolDecision getNodoArbolDecisionActual() {
		return nodoActual.getReferencia();
	}

	public void agregarAlternativa(NodoArbolDecision alt) {

		if (StructureManager.getInstance().getArbol().esAlternativa(alt)) {
			JLabel nuevo = new JLabel(alt.getNombre());
			nuevo.setFont(FUENTE);
			fieldAlternativa.setText("");
			listAlternativas.add(nuevo);
			alternativas.add(alt.getNombre());
			if (alternativas.size() > 9)
				((GridLayout) listAlternativas.getLayout()).setRows(alternativas.size());

		}

	}

	private void addCrit(String criterio) {
		JLabel nuevo = new JLabel(criterio);

		nuevo.setFont(FUENTE);

		panelListaCriterios.add(nuevo);

	}

	public JTree tree;
	private JLabel lblCriterion;
	private JTextField fieldAlternativa;

}
