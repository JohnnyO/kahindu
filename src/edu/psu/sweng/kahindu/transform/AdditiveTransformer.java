package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

public class AdditiveTransformer extends LinearTransformation {
	private final int offset;

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

	@Override
	public Transformer<Color> getColorTransform() {
		return new Transformer<Color>() {
			@Override
			public Color transform(Color input) {
				int red = bound(offset + input.getRed(), 0, 255);
				int green = bound(offset + input.getGreen(), 0, 255);
				int blue = bound(offset + input.getBlue(), 0, 255);
				return new Color(red, green, blue);
			}
		};
	}

}
