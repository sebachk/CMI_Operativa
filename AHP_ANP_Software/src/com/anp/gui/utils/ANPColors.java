package com.anp.gui.utils;

import java.awt.Color;

public enum ANPColors {

	TABLEHEADER(0xDDDDDD),
	NO_CRIT_CELL(0xAAAAAA),
	SELECT(0xCCFFFF),
	COMPLETO(0x55FF55),
	INCOMPLETO(0xFF5555),
	SEMICOMPLETO(0xFFFF55);

	private Color color;

	private ANPColors(int color) {
		this.color = new Color(color);
	}

	public Color getColor() {
		return color;
	}
}
