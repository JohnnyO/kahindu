package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public abstract class ConvolutionTransformation implements Transformer<KahinduImage> {
	
	@Override
	public KahinduImage transform(KahinduImage input) {
		// TODO Auto-generated method stub
		return new ConvolutedImage(input, this.getKernel());
	}
	
	public abstract Matrix getKernel();

	private class ConvolutedImage implements KahinduImage {

		private final Matrix kernel;
		private final KahinduImage source;

		public ConvolutedImage(KahinduImage source, Matrix kernel) {
			this.kernel = kernel;
			this.source = source;
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
			int uc = kernel.getWidth() / 2;
			int vc = kernel.getHeight() / 2;

			// short h[][]=new short[width][height];
			double red = 0;
			double green = 0;
			double blue = 0;
			for (int v = -vc; v <= vc; v++)
				for (int u = -uc; u <= uc; u++) {
					Color color = source.getColor(cx(x - u), cy(y - v));
					float value = kernel.getValue(u + uc, v + vc);
					red += color.getRed() * value;
					green += color.getGreen() * value;
					blue += color.getBlue() * value;
				}
			return new Color((short) red, (short) green, (short) blue);
		}

		private int cy(int y) {
			if (y > source.getHeight() - 1)
				return y - source.getHeight() + 1;
			if (y < 0)
				return source.getHeight() + y;
			return y;
		}

		private int cx(int x) {
			if (x > source.getWidth() - 1)
				return x - source.getWidth() + 1;
			if (x < 0)
				return source.getWidth() + x;
			return x;
		}

	}

}
