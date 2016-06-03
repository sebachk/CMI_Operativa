package com.ahp;

import java.awt.Component;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.anp.MatrizDefinicionANP;

public class XMLSaver {

	private static JFileChooser jfc = new JFileChooser() {
		@Override
		public void approveSelection() {
			File f = getSelectedFile();
			if (f.exists() && getDialogType() == SAVE_DIALOG) {
				int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file",
						JOptionPane.YES_NO_CANCEL_OPTION);
				switch (result) {
				case JOptionPane.YES_OPTION:
					super.approveSelection();
					return;
				case JOptionPane.NO_OPTION:
					return;
				case JOptionPane.CLOSED_OPTION:
					return;
				case JOptionPane.CANCEL_OPTION:
					cancelSelection();
					return;
				}
			}
			super.approveSelection();
		}

	};

	public static void saveXML(ArbolDecisionAHP arbol, Component parent) {
		try {
			int i = jfc.showSaveDialog(parent);
			if (i == JFileChooser.APPROVE_OPTION) {
				String path = jfc.getSelectedFile().getName();

				if (path.split("\\.").length < 2) {
					// Si no tiene extension le agrego
					path += ".axp";
				}
				XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
						new FileOutputStream(jfc.getCurrentDirectory().getPath() + "\\" + path)));
				encoder.writeObject(arbol);
				encoder.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static ArbolDecisionAHP loadXML(Component parent) {

		try {
			int i = jfc.showOpenDialog(parent);
			if (i == JFileChooser.APPROVE_OPTION) {
				XMLDecoder decoder = new XMLDecoder(
						new BufferedInputStream(new FileInputStream(jfc.getSelectedFile())));
				return ArbolDecisionAHP.class.cast(decoder.readObject());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/****************** ANP *******************/
	
	public static void saveANPToXML(MatrizDefinicionANP matriz, Component parent) {
		try {
			int i = jfc.showSaveDialog(parent);
			if (i == JFileChooser.APPROVE_OPTION) {
				String path = jfc.getSelectedFile().getName();
				if (path.split("\\.").length < 2) {// Si no tiene extension le agrego					
					path += ".axp";
				}
				XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
						new FileOutputStream(jfc.getCurrentDirectory().getPath() + "\\" + path)));
				encoder.writeObject(matriz);
				encoder.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static MatrizDefinicionANP loadANPFromXML(Component parent) {

		try {
			int i = jfc.showOpenDialog(parent);
			if (i == JFileChooser.APPROVE_OPTION) {
				XMLDecoder decoder = new XMLDecoder(
						new BufferedInputStream(new FileInputStream(jfc.getSelectedFile())));
				return MatrizDefinicionANP.class.cast(decoder.readObject());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	} 

}
