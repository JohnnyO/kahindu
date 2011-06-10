package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

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
	 * @return
	 */
	public abstract Transformer<Color> getColorTransform();

	public KahinduImage transform(final KahinduImage source) {
		return new LinearTransformedImage(source, this.getColorTransform());

	}

	private class LinearTransformedImage implements KahinduImage {

		private final KahinduImage source;
		private final Transformer<Color> transformer;

		public LinearTransformedImage(final KahinduImage source, final Transformer<Color> transformer) {
			assert (source != null);
			assert (transformer != null);
			this.source = source;
			this.transformer = transformer;
		}

		@Override
		public int getWidth() {
			return source.getWidth();
		}

		@Override
		public int getHeight() {
			return source.getHeight();
		}

		@Override
		public Color getColor(int x, int y) {
			return transformer.transform(source.getColor(x, y));
		}
	}
}
