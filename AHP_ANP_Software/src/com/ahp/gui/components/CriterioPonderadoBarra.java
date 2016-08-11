package com.ahp.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CriterioPonderadoBarra extends JPanel implements Comparable<CriterioPonderadoBarra> {
	private JPanel barra;
	private JLabel criteriolbl;
	
	private Color color;
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void setCriterioNombre(String nombre) {
		if (criteriolbl == null) {
			criteriolbl = new JLabel(nombre);

		}
		this.setToolTipText(nombre);
		criteriolbl.setText(nombre);
		if (nombre.length() > 12) {
			criteriolbl.setText(nombre.substring(0, 8) + "...");
		}

	}

	public String getCriterioNombre() {
		return criteriolbl.getText();
	}

	public JLabel getCriteriolbl() {
		return criteriolbl;
	}

	private double ptje; // 0-1

	public void setPtje(double ptje) {
		if (ptje > 1 || ptje < 0) {
			return;
		}
		this.ptje = ptje;
		repaint();
	}

	public double getPtje() {
		return ptje;
	}

	public CriterioPonderadoBarra() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 140, 46, 0 };
		gridBagLayout.rowHeights = new int[] { 14, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		criteriolbl = new JLabel("New label");
		criteriolbl.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_criteriolbl = new GridBagConstraints();
		gbc_criteriolbl.gridx = 0;
		gbc_criteriolbl.fill = GridBagConstraints.HORIZONTAL;
		add(criteriolbl, gbc_criteriolbl);

		this.barra = new JPanel();
		barra.setBackground(Color.GRAY);
		this.barra.setLayout(null);
		GridBagConstraints gbc_barra = new GridBagConstraints();
		gbc_barra.fill = GridBagConstraints.BOTH;
		add(this.barra, gbc_barra);

		// this.barra.add(ptjePanel);
		// this.barra.add(relleno);
		color=null;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);

		int x = this.barra.getX();
		int y = this.barra.getY();

		double width = this.barra.getWidth();

		width *= this.getPtje();
		int height = this.barra.getHeight();

		
		g.setColor(color==null?Color.green:color);
		g.fillRect(x, y, (int) width, height);

	}

	@Override
	public int compareTo(CriterioPonderadoBarra o) {
		if(o.getPtje()>this.ptje){
			return -1;
		}
		if(o.getPtje()<this.ptje){
			return 1;
		}
		return 0;
		
	}
}
