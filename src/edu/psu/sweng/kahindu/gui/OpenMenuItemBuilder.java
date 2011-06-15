package edu.psu.sweng.kahindu.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.ImageReader;

public class OpenMenuItemBuilder extends AbstractMenuLeaf {
	private final ImageReader reader;
	private final ImageComponent target;

	/*
     */

	public OpenMenuItemBuilder(ImageReader reader, ImageComponent target) {
		this.reader = reader;
		this.target = target;
	}

	@Override
	public JMenuItem build() {
		Action a = new AbstractAction(this.name) {
			{
				this.putValue(ACCELERATOR_KEY, OpenMenuItemBuilder.this.shortcutKey);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					File file = openFileDialog(target);
					if (file != null) {
						KahinduImage result = reader.read(file);
						target.updateImage(result);
						Window window = SwingUtilities.getWindowAncestor(target);
						if (window != null)
							window.pack();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		};

		return new JMenuItem(a);

	}

	protected File openFileDialog(ImageComponent component) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(IMAGE_DIRECTORY_NAME));
		fc.showOpenDialog(component);
		return fc.getSelectedFile();
	}

}
