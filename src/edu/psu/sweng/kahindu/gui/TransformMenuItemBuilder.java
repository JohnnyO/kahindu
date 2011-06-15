package edu.psu.sweng.kahindu.gui;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.transform.Transformer;

public class TransformMenuItemBuilder extends AbstractMenuLeaf {

	private Transformer<KahinduImage> transformer;
	private ImageComponent target;
	
	public TransformMenuItemBuilder(Transformer<KahinduImage> t, ImageComponent target) {
		this.setTransform(t);
		this.setTarget(target);
	}
	
	
	public void setTransform(Transformer<KahinduImage> transformer) {
		this.transformer = transformer;
	}
	
	public void setTarget(ImageComponent target) {
		this.target = target;
	}
	
	
	@Override
	public JMenuItem build() {
		Action a = new AbstractAction(this.name) {
			{
				this.putValue(ACCELERATOR_KEY, TransformMenuItemBuilder.this.shortcutKey);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				KahinduImage source = target.getImage();
				KahinduImage result = transformer.transform(source);
				target.updateImage(result);
			}
		};

		return new JMenuItem(a);
	}


	protected File openFileDialog(ImageComponent component) {
	    JFileChooser fc = new JFileChooser();
	    fc.setCurrentDirectory(new File(IMAGE_DIRECTORY_NAME));
	    fc.showOpenDialog(component);
	    return fc.getSelectedFile();
	}


	protected File saveFileDialog(ImageComponent component) {
	    JFileChooser fc = new JFileChooser();
	    fc.setCurrentDirectory(new File(IMAGE_DIRECTORY_NAME));
	    fc.showSaveDialog(component);
	    return fc.getSelectedFile();
	}




}
