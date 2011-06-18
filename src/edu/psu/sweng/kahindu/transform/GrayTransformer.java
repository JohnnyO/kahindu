package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.DefaultImageDecorator;
import edu.psu.sweng.kahindu.image.KahinduImage;

/**
 * Converts a color image to its grayscale representation.
 * 
 * @author John
 * 
 */
public class GrayTransformer implements Transformer<KahinduImage> {

	@Override
	public KahinduImage transform(final KahinduImage input) {
		return new DefaultImageDecorator(input) {
			@Override
			public Color getColor(int x, int y) {
				Color c = input.getColor(x, y);
				int average = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
				return new Color(average, average, average);
			}
		};
	}
}
