package com.ahp.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.ahp.StructureManager;

public class TreeLabelAHP extends JPanel {

	Color colorPropio;
	Color colorHijo;
	JLabel text;
	JPanel panelHijos;

	private static final Color COMPLETO = new Color(50, 255, 50);
	private static final Color INCOMPLETO = new Color(255, 50, 50);
	private static final Color SEMICOMPLETO = new Color(255, 255, 50);

	public TreeLabelAHP() {
		setBorder(new LineBorder(Color.WHITE, 2));
		setLayout(new BorderLayout(0, 0));

		this.text = new JLabel("New label");
		this.text.setHorizontalAlignment(SwingConstants.LEFT);
		add(this.text, BorderLayout.CENTER);

		panelHijos = new JPanel();
		add(panelHijos, BorderLayout.SOUTH);
	}

	public void setText(String t) {
		this.text.setText(t);
	}

	public void seleccionar() {
		this.setOpaque(true);
		this.text.setForeground(new Color(150, 150, 150));
	}

	public void setEstado(boolean setear, int propio, int hijo) {

		if (setear) {
			this.setOpaque(true);
			colorPropio = propio == StructureManager.COMPLETO ? COMPLETO
					: propio == StructureManager.INCOMPLETO ? INCOMPLETO : SEMICOMPLETO;
			colorHijo = hijo == StructureManager.COMPLETO ? COMPLETO
					: hijo == StructureManager.INCOMPLETO ? INCOMPLETO
							: hijo == StructureManager.SEMICOMPLETO ? SEMICOMPLETO : colorPropio;

			setBackground(colorPropio);
			panelHijos.setBackground(colorHijo);
		} else {
			this.setOpaque(false);
			panelHijos.setOpaque(false);
		}
	}

}
