package edu.psu.sweng.kahindu.gui;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public abstract class JMenuItemBuilder {
	
	public abstract JMenuItem buildMenuItem();
	
	public abstract JMenuItemBuilder setName(String name);
	
	public abstract JMenuItemBuilder setShortcutKey(KeyStroke keystroke);
	

}
