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
import javax.swing.filechooser.FileFilter;

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
			//jfc.removeChoosableFileFilter(jfc.getFileFilter());
			jfc.resetChoosableFileFilters();
			FileFilter[] ff=jfc.getChoosableFileFilters();
			for(FileFilter f:ff){
				jfc.removeChoosableFileFilter(f);
			}
			jfc.addChoosableFileFilter(NewFileFilter("AHP Files", new String[] { "ahp" }));
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
			jfc.resetChoosableFileFilters();
			FileFilter[] ff=jfc.getChoosableFileFilters();
			for(FileFilter f:ff){
				jfc.removeChoosableFileFilter(f);
			}
			jfc.addChoosableFileFilter(NewFileFilter("ANP Files", new String[] { "anp" }));
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
	
	private static FileFilter NewFileFilter(final String desc, final String[] allowed_extensions) {
        return new FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                if (f.isDirectory()) {
                    return true;
                }
                int pos = f.getName().lastIndexOf('.');
                if (pos == -1) {
                    return false;
                } else {
                    String extension = f.getName().substring(pos + 1);
                    for (String allowed_extension : allowed_extensions) {
                        if (extension.equalsIgnoreCase(allowed_extension)) {
                            return true;
                        }
                    }
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return desc;
            }
        };
    }

}
