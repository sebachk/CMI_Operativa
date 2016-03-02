package com.ahp.gui.components;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class AHPCellTreeRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

		if (node.getUserObject().toString().equals("HOLA")) {
			ImageIcon icon = new ImageIcon("./product.ico");

			setLeafIcon(icon);
		}

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		return this;
	}

}
