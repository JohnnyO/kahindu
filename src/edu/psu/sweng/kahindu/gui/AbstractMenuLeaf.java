package edu.psu.sweng.kahindu.gui;


import javax.swing.KeyStroke;

public abstract class AbstractMenuLeaf extends MenuNode {

	protected String name;
	protected KeyStroke shortcutKey;
	
	protected static final String IMAGE_DIRECTORY_NAME = "gifs";

	public MenuNode setName(String name) {
		this.name = name;
		return this;
	}

	public MenuNode setShortcutKey(KeyStroke shortcutKey) {
		this.shortcutKey = shortcutKey;
		return this;
	}
	
}
