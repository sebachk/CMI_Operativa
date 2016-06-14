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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.text.FlowView.FlowStrategy;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.ahp.NodoArbolDecision;
import com.ahp.StructureManager;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.BoxLayout;
import javax.swing.border.MatteBorder;

import java.awt.Insets;
import java.awt.Point;
import java.awt.ComponentOrientation;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class TabDefiniciones extends JSplitPane implements ActionListener {

	public JPanel panelCriterios;
	private JButton btnAgregarAlter;
	private JButton btnAddSubcriterio;
	private JPanel panelAlternativas;

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

		panelAlternativas = new JPanel();
		panel_3.add(panelAlternativas, BorderLayout.CENTER);
		panelAlternativas.setLayout(new GridLayout(10, 1, 0, 0));

		// La parte de abajo
		panelCriterios = new JPanel();

		this.setRightComponent(panelCriterios);
		panelCriterios.setLayout(new BorderLayout(0, 0));

		JPanel crt_panel_sur = new JPanel();
		crt_panel_sur.setBackground(SystemColor.activeCaption);
		panelCriterios.add(crt_panel_sur, BorderLayout.SOUTH);
		crt_panel_sur.setLayout(new BorderLayout(0, 0));
		
		crt_btn_remove = new JButton("Eliminar");
		crt_btn_remove.setAlignmentX(Component.CENTER_ALIGNMENT);
		crt_btn_remove.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		crt_btn_remove.setHorizontalTextPosition(SwingConstants.RIGHT);
		crt_btn_remove.setToolTipText("Eliminar el criterio seleccionado");
		crt_btn_remove.setHorizontalAlignment(SwingConstants.RIGHT);
		crt_btn_remove.addActionListener(this);
		crt_panel_sur.add(crt_btn_remove, BorderLayout.EAST);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		crt_panel_sur.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAddSubcriterio = new JButton("Agregar SubCriterio");
		panel.add(btnAddSubcriterio);
		btnAddSubcriterio.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddSubcriterio.addActionListener(this);
		
		textField = new JTextField();
		panel.add(textField);
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
		textField.setColumns(20);
		
		label = new JLabel("");
		panel.add(label);

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
				StructureManager.getInstance().arbolCompleto();
				this.getRootPane().repaint();

			}
		} else {
			if (e.getSource().equals(btnAddSubcriterio)) {
				String criterio = textField.getText();
				if (criterio == null || criterio.equals("")) {
					JOptionPane.showMessageDialog(this, "Debe ingresar un criterio");
				} else {

					textField.setText("");
					if(nodoActual.addSubCriterio(criterio,nodoActual)){
						addCrit(criterio);
						DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
						model.reload();
						StructureManager.getInstance().arbolCompleto();
					}
					else{
						JOptionPane.showMessageDialog(this, "El criterio ya se encuentra cargado");
					}
					
				}

				this.getRootPane().repaint();
			}
		}
		if (e.getSource().equals(crt_btn_remove)) {
			nodoActual.removeFromArbol();
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			model.reload();
			StructureManager.getInstance().arbolCompleto();
			tree.setSelectionPath(new TreePath(tree.getModel().getRoot()));
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
		if (nodoActual == null)
			return null;
		return nodoActual.getReferencia();
	}

	public void agregarAlternativa(NodoArbolDecision alt) {

		if (StructureManager.getInstance().getArbol().esAlternativa(alt)) {
			JLabel nuevo = new JLabel(alt.getNombre());
			nuevo.setFont(FUENTE);
			fieldAlternativa.setText("");
			panelAlternativas.add(nuevo);
			if (StructureManager.getInstance().getArbol().getAlternativas().size() > 9)
				((GridLayout) panelAlternativas.getLayout())
						.setRows(StructureManager.getInstance().getArbol().getAlternativas().size());

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
	private JButton crt_btn_remove;
	private JPanel panel;
	private JLabel label;

	public void clearAlternativas() {
		this.panelAlternativas.removeAll();
	}
}
