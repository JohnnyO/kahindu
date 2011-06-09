package edu.psu.sweng.kahindu.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

public abstract class AbstractMenuItemBuilder extends JMenuItemBuilder {

	protected String name;
	protected KeyStroke shortcutKey;
	
	private static final String IMAGE_DIRECTORY_NAME = "gifs";

	@Override
	public JMenuItemBuilder setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public JMenuItemBuilder setShortcutKey(KeyStroke shortcutKey) {
		this.shortcutKey = shortcutKey;
		return this;
	}
	
	protected File openFileDialog(ImageComponent component)
	{
	    JFileChooser fc = new JFileChooser();
	    fc.setCurrentDirectory(new File(IMAGE_DIRECTORY_NAME));
        fc.showOpenDialog(component);
        return fc.getSelectedFile();
	}
	
	protected File saveFileDialog(ImageComponent component)
    {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(IMAGE_DIRECTORY_NAME));
        fc.showSaveDialog(component);
        return fc.getSelectedFile();
    }
	
}
