package com.anp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.anp.gui.utils.ANPColors;

public class ColoredLabel extends JPanel {
	protected JPanel color;
	protected JLabel lblLabel;
	

	public ColoredLabel() {
		setBackground(SystemColor.controlHighlight);
		color = new JPanel();
		color.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setColor(JColorChooser.showDialog(color, "Color", Color.CYAN));

			}
		});

		color.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLabel = new JLabel();
		lblLabel.setBackground(SystemColor.controlHighlight);
		lblLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabel.setText("Label");
		setLayout(new BorderLayout(0, 0));

		this.add(color, BorderLayout.WEST);
		this.add(lblLabel, BorderLayout.CENTER);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		lblLabel.setText(name);
	}

	public void setColor(Color c) {
		this.color.setVisible(true);
		color.setBackground(c);

	}

	public Color getColor() {
		return color.getBackground();
	}

	public void seleccionar(){
		this.lblLabel.setOpaque(true);
		this.lblLabel.setBackground(ANPColors.SELECT.getColor());
		
	}
	
	public void deseleccionar(){
		this.lblLabel.setOpaque(false);
		
	}
	
	public boolean seleccionado(){
		return lblLabel.isOpaque();
	}
	
}
