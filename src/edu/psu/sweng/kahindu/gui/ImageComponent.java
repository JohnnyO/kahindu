package edu.psu.sweng.kahindu.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class ImageComponent extends JComponent {

	private KahinduImage image;

	public ImageComponent(final KahinduImage image) {
		this.image = image;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int x = 0; x < image.getHeight(); x++) {
			for (int y = 0; y < image.getWidth(); y++) {
				g.setColor(image.getColor(x, y));
				g.drawRect(x, y, 1, 1);
			}
		}
	}
	
	public void updateImage(KahinduImage image) {
		this.image = image;
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
