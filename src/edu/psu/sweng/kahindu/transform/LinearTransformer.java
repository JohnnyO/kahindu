package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.DefaultImageDecorator;
import edu.psu.sweng.kahindu.image.KahinduImage;
/**
 * Generates a linear transformation of an image.  A linear transformation is a transformation that converts each pixel
 * of the image individually, without regard to neighboring pixels.
 * @author John
 *
 */
public abstract class LinearTransformation implements Transformer<KahinduImage> {

	/**
	 * Implemented by subclasses to define how the linear transformation is to take place.
	 * @return the new color that this is transformed into
	 */
	public abstract Color transform(Color input);

	public KahinduImage transform(final KahinduImage source) {
		return new LinearTransformedImage(source);

	}

	private class LinearTransformedImage extends DefaultImageDecorator {

		private final KahinduImage source;

		public LinearTransformedImage(final KahinduImage source) {
		    super(source);
			assert (source != null);
			this.source = source;
		}


		@Override
		public Color getColor(int x, int y) {
			return LinearTransformation.this.transform(source.getColor(x, y));
		}
	}
}
