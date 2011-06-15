package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

/**
 * An abstract base class that performs an Image Convolution.  For more information on image convolutions, see
 * Douglas Lyon's book descibing the implementation details.
 * @author John
 *
 */
public class ConvolutionTransformation implements Transformer<KahinduImage> {
    
    private final Matrix kernel;


    public ConvolutionTransformation(Matrix kernel) {
        this.kernel = kernel;
    }
	
	@Override
	public KahinduImage transform(KahinduImage input) {
		// TODO Auto-generated method stub
		return new ConvolutedImage(input, this.getKernel());
	}
	
	/**
	 * Each convolution is defined by a matrix of values, called the kernel. The final value of a pixel is calculated
	 * by centering the matrix on the pixel, then summing the values of the neighboring pixels times the matrix values.
	 * @return the Matrix to be used in the convolution
	 */
	public  Matrix getKernel() {
	    return kernel;
	}

	
	/**
	 * private image implementation that actually generates a convoluted image given a source image and a matrix.
	 * @author John
	 *
	 */
	class ConvolutedImage extends EdgeWrapTemplate {

		private final Matrix kernel;
		final KahinduImage source;

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
			
			//Why would we do this?  It's clearly wrong...
			//That's true, but we are preserving backwards compatibility with the old code
			//(The old code had an off-by-one error which creating black bars on the last row of pixels in each dimension
			if (x == source.getWidth() - 1) return Color.BLACK;
			if (y == source.getHeight() - 1) return Color.BLACK;
			
			int uc = kernel.getWidth() / 2;
			int vc = kernel.getHeight() / 2;

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
			return new Color((short) bound(red), (short) bound(green), (short) bound(blue));
		}

	}

}
