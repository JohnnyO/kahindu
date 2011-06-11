package edu.psu.sweng.kahindu.gui;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.ByteArrayImageReader;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.DefaultImageWriter;
import edu.psu.sweng.kahindu.image.io.PPMReader;
import edu.psu.sweng.kahindu.transform.AdditiveTransformer;
import edu.psu.sweng.kahindu.transform.GrayTransformer;
import edu.psu.sweng.kahindu.transform.LowPassFilter;
import edu.psu.sweng.kahindu.transform.NegateTransformer;
import edu.psu.sweng.kahindu.transform.PowerTransformer;
import gui.NumImage;

public class ImageFrame extends JFrame
{
    private static final long serialVersionUID = 3848669250991405715L;
    
	private final ImageComponent component;

	private KahinduImage image;

	public ImageFrame() {
		super("Kahindu Refactor - Team 2");
		File defaultImage = new File("gifs/baboon.GIF");
		try {
			this.image = new ByteArrayImageReader(NumImage.gray).read(defaultImage);
			//this.image = new GIFReader(new File("gifs/baboon.GIF")).read();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		JMenuBuilder builder = new JMenuBuilder("Spatial");
		
        TransformMenuItemBuilder mi = new TransformMenuItemBuilder(new LowPassFilter(1), component);
        mi.setName("LowPass-Average");
        builder.addMenuItem(mi);
        
        mi = new TransformMenuItemBuilder(new LowPassFilter(2), component);
        mi.setName("LowPass-P1");
        builder.addMenuItem(mi);

        mi = new TransformMenuItemBuilder(new LowPassFilter(4), component);
        mi.setName("LowPass-P2");
        builder.addMenuItem(mi);
        
        mi = new TransformMenuItemBuilder(new LowPassFilter(12), component);
        mi.setName("LowPass-P3");
        builder.addMenuItem(mi);
        




		return builder.getMenu();
	}

	private JMenu getFileMenu()
	{
	    JMenu fileMenu = new JMenu("File");

	    // Open
	    JMenuBuilder openBuilder = new JMenuBuilder("Open");
	    
	    OpenMenuItemBuilder loadGIF = new OpenMenuItemBuilder(new DefaultImageReader(), component);
	    loadGIF.setName("Load GIF");
	    openBuilder.addMenuItem(loadGIF);
	    
	    OpenMenuItemBuilder loadJPG = new OpenMenuItemBuilder(new DefaultImageReader(), component);
        loadJPG.setName("Load JPG");
        openBuilder.addMenuItem(loadJPG);
        
        OpenMenuItemBuilder loadPNG = new OpenMenuItemBuilder(new DefaultImageReader(), component);
        loadPNG.setName("Load PNG");
        openBuilder.addMenuItem(loadPNG);
	    
	    OpenMenuItemBuilder loadPPM = new OpenMenuItemBuilder(new PPMReader(), component);
        loadPPM.setName("Load PPM");
        openBuilder.addMenuItem(loadPPM);
	    
	    // Save
        JMenuBuilder saveBuilder = new JMenuBuilder("Save");
        SaveMenuItemBuilder saveGIF = new SaveMenuItemBuilder(new DefaultImageWriter("gif"), component);
        saveGIF.setName("Save GIF");
        saveBuilder.addMenuItem(saveGIF);
        
        SaveMenuItemBuilder saveJPG = new SaveMenuItemBuilder(new DefaultImageWriter("jpg"), component);
        saveJPG.setName("Save JPG");
        saveBuilder.addMenuItem(saveJPG);
        
        SaveMenuItemBuilder savePNG = new SaveMenuItemBuilder(new DefaultImageWriter("png"), component);
        savePNG.setName("Save PNG");
        saveBuilder.addMenuItem(savePNG);
        
        fileMenu.add(openBuilder.getMenu());
        fileMenu.add(saveBuilder.getMenu());
	    
	    return fileMenu;
	}
	
	private JMenu getFilterMenu()
	{
	    JMenuBuilder builder = new JMenuBuilder("Filters");

        TransformMenuItemBuilder negate = new TransformMenuItemBuilder(new NegateTransformer(), component);
        negate.setName("Negate").setShortcutKey(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        builder.addMenuItem(negate);

        TransformMenuItemBuilder gray = new TransformMenuItemBuilder(new GrayTransformer(), component);
        gray.setName("Gray");
        builder.addMenuItem(gray);

        TransformMenuItemBuilder add10 = new TransformMenuItemBuilder(new AdditiveTransformer(10), component);
        add10.setName("Add 10");
        builder.addMenuItem(add10);

        TransformMenuItemBuilder brighten = new TransformMenuItemBuilder(new PowerTransformer(0.9), component);
        brighten.setName("Brighten");
        builder.addMenuItem(brighten);

        TransformMenuItemBuilder darken = new TransformMenuItemBuilder(new PowerTransformer(1.5), component);
        darken.setName("Darken");
        builder.addMenuItem(darken);
        
        return builder.getMenu();
	}

}
