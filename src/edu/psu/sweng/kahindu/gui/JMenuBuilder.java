package edu.psu.sweng.kahindu.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenu;

public class JMenuBuilder {

	private List<JMenuItemBuilder> builders;
	private final String title;
	
	public JMenuBuilder(String title) {
		this.title = title;
		builders = new LinkedList<JMenuItemBuilder>();
	}
	
	public void addMenuItem(JMenuItemBuilder jmiBuilder) {
		builders.add(jmiBuilder);
	}
	
	public JMenu getMenu() {
		JMenu menu = new JMenu(title);
		for (JMenuItemBuilder jmiBuilder : builders) {
			menu.add(jmiBuilder.buildMenuItem());
		}
		return menu;
	}
}
