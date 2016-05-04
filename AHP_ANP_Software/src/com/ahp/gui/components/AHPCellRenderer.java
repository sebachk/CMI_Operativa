package com.ahp.gui.components;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.ahp.StructureManager;

public class AHPCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		NodoArbolAHP nodo = (NodoArbolAHP) value;

		TreeLabelAHP otro = new TreeLabelAHP();
		otro.setText(nodo.getNombre());
		int[] colors = StructureManager.getInstance().calcularCompletitudNodo(nodo.getReferencia());

		otro.setEstado(colors[0] != -2, colors[0], colors[1]);

		if (sel) {
			otro.seleccionar();
		}

		return otro;
	}

}
