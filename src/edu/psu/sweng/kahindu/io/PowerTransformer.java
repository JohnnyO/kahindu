package edu.psu.sweng.kahindu.io;

import java.awt.Color;

public class PowerTransformer implements Transformer<Color> {

	private final double power;

	public PowerTransformer(double power) {
		this.power = power;
	}

	@Override
	public Color transform(Color input) {
		int red = (int) (255 * Math.pow(input.getRed() / 255.0, power));
		int green = (int) (255 * Math.pow(input.getGreen() / 255.0, power));
		int blue = (int) (255 * Math.pow(input.getBlue() / 255.0, power));
		return new Color(red, green, blue);
	}

}
