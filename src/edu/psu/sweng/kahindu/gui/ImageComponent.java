package edu.psu.sweng.kahindu.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;

public class ImageComponent extends JComponent {

    private static final long serialVersionUID = 4104984066672879312L;
    
    private KahinduImage image;
	private Image displayableImage;

	public ImageComponent(final KahinduImage image) {
		this.updateImage(image);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Rectangle r = getBounds();
		g.drawImage(displayableImage, 0, 0, r.width, r.height, this);
	}

	public void updateImage(KahinduImage image) {
		this.image = image;
		int height = image.getHeight();
		int width =  image.getWidth();
		int[] pixels = new int[width * height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				pixels[x  + y*width] = image.getColor(x, y).getRGB();
			}
		
		MemoryImageSource source = new MemoryImageSource(image.getWidth(), image.getHeight(), pixels, 0,
				image.getWidth());
		
		displayableImage = Toolkit.getDefaultToolkit().createImage(source);
		
		this.repaint();
	}

	public KahinduImage getImage() {
		return image;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(), image.getHeight());
	}

}
