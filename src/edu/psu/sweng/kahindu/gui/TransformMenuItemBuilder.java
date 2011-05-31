package edu.psu.sweng.kahindu.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import edu.psu.sweng.kahindu.transform.Transformer;

public class TransformMenuItemBuilder extends AbstractMenuItemBuilder {

	private Transformer<Color> transformer;
	private ImageComponent target;
	
	public TransformMenuItemBuilder(Transformer<Color> t, ImageComponent target) {
		this.setTransform(t);
		this.setTarget(target);
	}
	
	
	public void setTransform(Transformer<Color> transformer) {
		this.transformer = transformer;
	}
	
	public void setTarget(ImageComponent target) {
		this.target = target;
	}
	
	
	@Override
	public JMenuItem buildMenuItem() {
		Action a = new AbstractAction(this.name) {
			{
				this.putValue(ACCELERATOR_KEY, TransformMenuItemBuilder.this.shortcutKey);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				KahinduImage source = target.getImage();
				KahinduImage result = new TransformedImage(source, transformer);
				target.updateImage(result);
			}
		};

		return new JMenuItem(a);
	}




}
