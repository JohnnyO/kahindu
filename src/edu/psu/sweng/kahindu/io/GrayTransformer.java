package edu.psu.sweng.kahindu.io;

import java.awt.Color;

public class GrayTransformer implements Transformer<Color>{

	@Override
	public Color transform(Color input) {
		int red = input.getRed();
		int green = input.getGreen();
		int blue = input.getBlue();
		int average = (red + green + blue) / 3;
		return new Color(average, average, average);
	}

}
