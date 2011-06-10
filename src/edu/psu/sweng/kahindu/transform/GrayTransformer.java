package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

public class GrayTransformer extends LinearTransformation {


	@Override
	public Transformer<Color> getColorTransform() {
		return new Transformer<Color>() {
			@Override
			public Color transform(Color input) {
				int red = input.getRed();
				int green = input.getGreen();
				int blue = input.getBlue();
				int average = (red + green + blue) / 3;
				return new Color(average, average, average);
			}
		};
	}
}
