package edu.psu.sweng.kahindu.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.KeyStroke;

public class JMenuBuilder extends MenuNode {

	private final String title;
	private final List<MenuNode> builders;
	


	public JMenuBuilder(String title) {
		this.title = title;
		this.builders = new LinkedList<MenuNode>();
	}


	public void addItem(MenuNode jmiBuilder) {
		builders.add(jmiBuilder);
	}

	public JMenu build() {
		JMenu menu = new JMenu(title);
		for (MenuNode b : builders)
			menu.add(b.build());
		return menu;
	}

}
