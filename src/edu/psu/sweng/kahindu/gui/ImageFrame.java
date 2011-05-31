package edu.psu.sweng.kahindu.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import edu.psu.sweng.kahindu.image.io.ByteArrayImageAdapter;
import edu.psu.sweng.kahindu.image.io.ByteArrayImageReader;
import edu.psu.sweng.kahindu.image.io.GIFReader;
import edu.psu.sweng.kahindu.matrix.Matrix;
import edu.psu.sweng.kahindu.transform.AdditiveTransformer;
import edu.psu.sweng.kahindu.transform.GrayTransformer;
import edu.psu.sweng.kahindu.transform.NegateTransformer;
import edu.psu.sweng.kahindu.transform.PowerTransformer;
import edu.psu.sweng.kahindu.transform.SpatialTransformedImage;
import gui.NumImage;

public class ImageFrame extends JFrame {

	private final JFrame frame = new JFrame("Kahindu Refactor - Team 2");
	private final ImageComponent component;

	private KahinduImage image;

	public ImageFrame() {
		super("Kahindu Refactor - Team 2");
		File defaultImage = new File("gifs/baboon.GIF");
		try {
			this.image = new ByteArrayImageReader(NumImage.gray).read();
			//this.image = new GIFReader(new File("gifs/baboon.GIF")).read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		this.image = image;
		this.component = new ImageComponent(image);
		this.getContentPane().add(component);
		this.setJMenuBar(this.getMenu());
		this.pack();
	}

	private JMenuBar getMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getFileMenu());
		menuBar.add(getFilterMenu());
		menuBar.add(getSpatialFilterMenu());
		
		return menuBar;
	}
	
	private JMenu getSpatialFilterMenu() {
		JMenuItem item = new JMenuItem(new AbstractAction("LowPass - Average") {

			@Override
			public void actionPerformed(ActionEvent e) {
				KahinduImage source = component.getImage();
				Matrix matrix = new Matrix(3,3);
				KahinduImage result = new SpatialTransformedImage(source, matrix);
				component.updateImage(result);

			}
			
		});
		JMenu menu = new JMenu("Spatial");
		menu.add(item);
		return menu;
	}

	private JMenu getFileMenu()
	{
	    JMenuBuilder builder = new JMenuBuilder("File");
	    
	    OpenMenuItemBuilder loadGIF = new OpenMenuItemBuilder(new GIFReader(), component);
	    loadGIF.setName("Load GIF");
	    builder.addMenuItemBuilder(loadGIF);
	    
	    return builder.getMenu();
	}
	
	private JMenu getFilterMenu()
	{
	    JMenuBuilder builder = new JMenuBuilder("Filters");

        TransformMenuItemBuilder negate = new TransformMenuItemBuilder(new NegateTransformer(), component);
        negate.setName("Negate").setShortcutKey(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        builder.addMenuItemBuilder(negate);

        TransformMenuItemBuilder gray = new TransformMenuItemBuilder(new GrayTransformer(), component);
        gray.setName("Gray");
        builder.addMenuItemBuilder(gray);

        TransformMenuItemBuilder add10 = new TransformMenuItemBuilder(new AdditiveTransformer(10), component);
        add10.setName("Add 10");
        builder.addMenuItemBuilder(add10);

        TransformMenuItemBuilder brighten = new TransformMenuItemBuilder(new PowerTransformer(0.9), component);
        brighten.setName("Brighten");
        builder.addMenuItemBuilder(brighten);

        TransformMenuItemBuilder darken = new TransformMenuItemBuilder(new PowerTransformer(1.5), component);
        darken.setName("Darken");
        builder.addMenuItemBuilder(darken);
        
        return builder.getMenu();
	}

}
