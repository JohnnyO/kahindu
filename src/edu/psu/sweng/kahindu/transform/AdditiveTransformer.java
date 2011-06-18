package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.DefaultImageDecorator;
import edu.psu.sweng.kahindu.image.KahinduImage;



/**
 * Adds a fixed offset to each of the component colors in an image.
 * 
 * @author John
 * 
 */
public class AdditiveTransformer implements Transformer<KahinduImage> {
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

	@Override
	public KahinduImage transform(final KahinduImage input) {
		return new DefaultImageDecorator(input) {
			public Color getColor(int x, int y) {
				Color c = input.getColor(x, y);
				int red = bound(offset + c.getRed(), 0, 255);
				int green = bound(offset + c.getGreen(), 0, 255);
				int blue = bound(offset + c.getBlue(), 0, 255);
				return new Color(red, green, blue);
			}
		};
	}

}
