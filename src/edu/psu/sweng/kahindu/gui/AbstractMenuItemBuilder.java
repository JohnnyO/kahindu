package edu.psu.sweng.kahindu.gui;

import javax.swing.KeyStroke;

public abstract class AbstractMenuItemBuilder extends JMenuItemBuilder {

	protected String name;
	protected KeyStroke shortcutKey;

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

}
