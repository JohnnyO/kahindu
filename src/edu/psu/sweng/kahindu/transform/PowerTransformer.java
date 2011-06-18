package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.DefaultImageDecorator;
import edu.psu.sweng.kahindu.image.KahinduImage;

/**
 * Applies a power law transformation to an image.
 * 
 * @author John
 * 
 */
public class PowerTransformer implements Transformer<KahinduImage> {

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

	public KahinduImage transform(final KahinduImage input) {
		return new DefaultImageDecorator(input) {

			@Override
			public Color getColor(int x, int y) {
				Color c = input.getColor(x, y);
				int red = (int) (255 * Math.pow(c.getRed() / 255.0, power));
				int green = (int) (255 * Math.pow(c.getGreen() / 255.0, power));
				int blue = (int) (255 * Math.pow(c.getBlue() / 255.0, power));
				return new Color(red, green, blue);
			}
		};
	}
}
