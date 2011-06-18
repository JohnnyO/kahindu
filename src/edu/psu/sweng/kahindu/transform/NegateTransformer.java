package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.DefaultImageDecorator;
import edu.psu.sweng.kahindu.image.KahinduImage;

/**
 * Creates a photo-negative representation of an image.
 * 
 * @author John
 * 
 */
public class NegateTransformer implements Transformer<KahinduImage> {

	public Color transform(Color input) {
		int red = 255 - input.getRed();
		int blue = 255 - input.getBlue();
		int green = 255 - input.getGreen();
		return new Color(red, green, blue);
	}

	@Override
	public KahinduImage transform(final KahinduImage input) {
		return new DefaultImageDecorator(input) {
			
			public Color getColor(int x, int y) {
				Color c= input.getColor(x,y);
				int red = 255 - c.getRed();
				int blue = 255 - c.getBlue();
				int green = 255 - c.getGreen();
				return new Color(red, green, blue);

			}
		};
	}
}
