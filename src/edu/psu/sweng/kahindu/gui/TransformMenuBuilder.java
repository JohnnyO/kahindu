package edu.psu.sweng.kahindu.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import edu.psu.sweng.kahindu.io.Transformer;

public class TransformMenuBuilder {

	private final ImageComponent target;
	private final List<String> names;
	private final List<Transformer<Color>> transformers;
	private final List<KeyStroke> shortcutKeys;

	public TransformMenuBuilder(ImageComponent target) {
		this.target = target;
		this.names = new ArrayList<String>();
		this.transformers = new ArrayList<Transformer<Color>>();
		this.shortcutKeys = new ArrayList<KeyStroke>();
	}

	public TransformMenuBuilder addTransformer(String name, Transformer<Color> t) {
		return this.addTransformer(name, t, null);
	}

	public TransformMenuBuilder addTransformer(String name,
			Transformer<Color> t, KeyStroke keyStroke) {
		names.add(name);
		transformers.add(t);
		shortcutKeys.add(keyStroke);
		
		return this;
	}

	public JMenu build() {
		JMenu menu = new JMenu("Transforms");
		for (int i=0; i < names.size(); i++) {
			final String name = names.get(i);
			final Transformer<Color> t = transformers.get(i);
			final KeyStroke shortcutKey = shortcutKeys.get(i);
			Action a = new AbstractAction(name) {
				{
					this.putValue(ACCELERATOR_KEY, shortcutKey);
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					KahinduImage source = target.getImage();
					KahinduImage result = new TransformedImage(source, t);
					target.updateImage(result);
				}
			};
			menu.add(a);
		}
		return menu;
	}

}
