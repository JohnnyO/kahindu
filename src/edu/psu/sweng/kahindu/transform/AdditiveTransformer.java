package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

/**
 * Adds a fixed offset to each of the component colors in an image.
 * 
 * @author John
 * 
 */
public class AdditiveTransformer extends LinearTransformer {
	private final int offset;

	/**
	 * The offset is added to each of the color components (R,G,B) of an image,
	 * resulting in a brighter image. (or darker if the offset is negative)
	 * 
	 * @param offset
	 */
	public AdditiveTransformer(int offset) {
		this.offset = offset;
	}

	private static int bound(int value, int min, int max) {
		if (value < min)
			return min;
		if (value > max)
			return max;
		return value;
	}

	public Color transform(Color input) {
		int red = bound(offset + input.getRed(), 0, 255);
		int green = bound(offset + input.getGreen(), 0, 255);
		int blue = bound(offset + input.getBlue(), 0, 255);
		return new Color(red, green, blue);
	}

}
