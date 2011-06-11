package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

/**
 * Applies a power law transformation to an image.
 * 
 * @author John
 * 
 */
public class PowerTransformer extends LinearTransformation {

	private final double power;

	/**
	 * Power values greater than 1 will brighten an image, and less than 1 will
	 * darken it.
	 * 
	 * @param power
	 */
	public PowerTransformer(double power) {
		this.power = power;
	}

	public Color transform(Color input) {
		int red = (int) (255 * Math.pow(input.getRed() / 255.0, power));
		int green = (int) (255 * Math.pow(input.getGreen() / 255.0, power));
		int blue = (int) (255 * Math.pow(input.getBlue() / 255.0, power));
		return new Color(red, green, blue);
	}

}
