package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;

public abstract class LinearTransformation implements Transformer<KahinduImage> {

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
