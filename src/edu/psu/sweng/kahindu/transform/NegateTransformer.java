package edu.psu.sweng.kahindu.io;

import java.awt.Color;

public class NegateTransformer implements Transformer<Color> {

	@Override
	public Color transform(Color input) {
		int red = 255 - input.getRed();
		int blue = 255 - input.getBlue();
		int green = 255 - input.getGreen();
		return new Color(red, green, blue);
	}
}
