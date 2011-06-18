package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

/**
 * Creates a photo-negative representation of an image.
 * 
 * @author John
 * 
 */
public class NegateTransformer extends LinearTransformer {

	public Color transform(Color input) {
		int red = 255 - input.getRed();
		int blue = 255 - input.getBlue();
		int green = 255 - input.getGreen();
		return new Color(red, green, blue);
	}
}
