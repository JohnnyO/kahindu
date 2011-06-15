package edu.psu.sweng.kahindu.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.KeyStroke;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.DefaultImageWriter;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import edu.psu.sweng.kahindu.image.io.ImageWriter;
import edu.psu.sweng.kahindu.transform.ParameterizedTransformer;
import edu.psu.sweng.kahindu.transform.Transformer;

public class JMenuBuilder extends MenuNode {

	private final String title;
	private final List<MenuNode> builders;
	private final ImageComponent target;
	


	public JMenuBuilder(String title, ImageComponent defaultTarget) {
		this.title = title;
		this.target = defaultTarget;
		this.builders = new LinkedList<MenuNode>();
	}


	public void addItem(MenuNode jmiBuilder) {
		builders.add(jmiBuilder);
	}
	
	public void add(String name, Transformer<KahinduImage> t) {
		this.add(name,t,null);
	}
	public void add(String name, ParameterizedTransformer t) {
		this.add(name,t,null);
	}


	
	public void add(String name, Transformer<KahinduImage> t, KeyStroke shortcutKey) {
		TransformMenuItemBuilder mi = new TransformMenuItemBuilder(t, target);
		mi.setName(name);
		if (shortcutKey != null)
			mi.setShortcutKey(shortcutKey);
		this.addItem(mi);
	}
	
	public void add(String name, ParameterizedTransformer t, KeyStroke shortcutKey) {
		ParameterizedTransformMI mi = new ParameterizedTransformMI(t, target);
		mi.setName(name);
		if (shortcutKey != null)
			mi.setShortcutKey(shortcutKey);
		this.addItem(mi);
	}


	public JMenu build() {
		JMenu menu = new JMenu(title);
		for (MenuNode b : builders)
			menu.add(b.build());
		return menu;
	}


	public void add(String name, ImageReader reader) {
		OpenMenuItemBuilder mi = new OpenMenuItemBuilder(reader, target);
		mi.setName(name);
		this.addItem(mi);
		// TODO Auto-generated method stub
		
	}


	public void add(String name, ImageWriter writer) {
		SaveMenuItemBuilder mi = new SaveMenuItemBuilder(writer, target);
		mi.setName(name);
		this.addItem(mi);
		
	}

}
