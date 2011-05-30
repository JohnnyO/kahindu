package edu.psu.sweng.kahindu.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import edu.psu.sweng.kahindu.image.io.ImageReader;

public class OpenMenuItemBuilder extends AbstractMenuItemBuilder
{
	private final ImageReader reader;
	private final ImageComponent target;
	
	public OpenMenuItemBuilder(ImageReader reader, ImageComponent target)
	{
		this.reader = reader;
		this.target = target;
	}
    
    @Override
    public JMenuItem buildMenuItem()
    {
		Action a = new AbstractAction(this.name) {
			{
				this.putValue(ACCELERATOR_KEY, OpenMenuItemBuilder.this.shortcutKey);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Show a dialog to pick a file
				try {
				reader.setFile(new File("gifs/baboon.gif"));
				KahinduImage result = reader.read();
				target.updateImage(result);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		};

		return new JMenuItem(a);

    }

}
