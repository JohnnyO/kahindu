package edu.psu.sweng.kahindu.image;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class AWTImageAdapter implements KahinduImage{
	
	private BufferedImage image;
	
	public AWTImageAdapter(BufferedImage image) {
		this.image = image;
	}
	

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public int getHeight() {
		return image.getHeight();
	}

	@Override
	public Color getColor(int x, int y) {
		return new Color(image.getRGB(x, y));
	}

}
