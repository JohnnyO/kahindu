package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

/**
 * Converts a color image to its grayscale representation.
 * @author John
 *
 */
public class GrayTransformer extends LinearTransformer {


	
	@Override
	public Color transform(Color input) {
		int red = input.getRed();
		int green = input.getGreen();
		int blue = input.getBlue();
		int average = (red + green + blue) / 3;
		return new Color(average, average, average);

	}

	
}
