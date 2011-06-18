package edu.psu.sweng.kahindu.transform;

import java.awt.Color;
import java.util.Arrays;

import edu.psu.sweng.kahindu.image.DefaultImageDecorator;
import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

/**
 * Not currently functional, mostly because the original code was
 * non-functional.
 * 
 * @author John
 * 
 */

public class MedianFilterTransformer implements Transformer<KahinduImage> {

	private interface Shape {
		Matrix getKernel(int size);
	}

	public static final Shape SQUARE = new Shape() {
		@Override
		public Matrix getKernel(final int size) {
			if (size % 2 == 1) {
				Matrix m = new Matrix(size, size);
				m.fill(1);
				return m;
			} else { // even sided matrices need to be padded up
				int sizePlusOne = size + 1;
				Matrix m = new Matrix(sizePlusOne, sizePlusOne);
				m.fill(0);
				for (int x = 0; x < size; x++)
					for (int y = 0; y < size; y++)
						m.setValueAt(x, y, 1);
				return m;

			}
		}
	};

	public static final Shape CROSS = new Shape() {
		@Override
		public Matrix getKernel(final int size) {
			if (size % 2 != 1)
				throw new IllegalArgumentException("Cross filters must have an odd-length side");
			Matrix m = new Matrix(size, size);
			m.fill(0);
			int midpoint = size / 2;
			for (int i = 0; i < size; i++) {
				m.setValueAt(i, midpoint, 1);
				m.setValueAt(midpoint, i, 1);
			}
			m.normalize();
			return m;
		}

	};

	public static final Shape OCTAGON = new Shape() {
		@Override
		public Matrix getKernel(final int size) {
			Matrix m = new Matrix(size, size);
			m.fill(1);
			// clear the four corners
			m.setValueAt(0, 0, 0);
			m.setValueAt(0, size - 1, 0);
			m.setValueAt(size - 1, 0, 0);
			m.setValueAt(size - 1, size - 1, 0);
			return m;

		}

	};

	public static final Shape DIAMOND = new Shape() {
		@Override
		public Matrix getKernel(final int size) {
			// This is a CSE 101 type problem here
			Matrix m = new Matrix(size, size);
			m.fill(0);
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					int xBar = Math.abs(x - (size/2));
					int yBar = Math.abs(y - (size/2));
					if (xBar + yBar <= (size / 2))
						m.setValueAt(x, y, 1);
				}
			}
			return m;

		}

	};
	
	   public static final Shape HORIZONTAL_LINE = new Shape() {
	        @Override
	        public Matrix getKernel(final int size) {
	            if (size % 2 != 1)
	                throw new IllegalArgumentException("Line filters must have an odd-length side");
	            Matrix m = new Matrix(size, size);
	            m.fill(0);
	            int midpoint = size / 2;
	            for (int i = 0; i <=midpoint; i++) {
	                
	                m.setValueAt(midpoint, i, 1);
	            }
	            return m;
	        }

	    };
	    
	      public static final Shape VERTICAL_LINE = new Shape() {
	            @Override
	            public Matrix getKernel(final int size) {
	                if (size % 2 != 1)
	                    throw new IllegalArgumentException("Line filters must have an odd-length side");
	                Matrix m = new Matrix(size, size);
	                m.fill(0);
	                int midpoint = size / 2;
	                for (int i = 0; i <=midpoint; i++) {
	                    
	                    m.setValueAt(i, midpoint, 1);
	                }
	                return m;
	            }

	        };


	
	
	private int size;
	private Shape shape;

	public MedianFilterTransformer(int size, Shape shape) {
		this.shape = shape;
		this.size = size;
	}

	@Override
	public KahinduImage transform(final KahinduImage input) {
		return new MedianFilteredImage(input, shape.getKernel(size));
	}

	private class MedianFilteredImage extends DefaultImageDecorator {
		private final Matrix kernel;
		private final KahinduImage source;
        private EdgeWrapTemplate template;

		public MedianFilteredImage(KahinduImage source, Matrix kernel) {
		    super(source);
			this.source = source;
			this.kernel = kernel;
			this.template = new EdgeWrapTemplate(source);
		}



		@Override
		public Color getColor(int x, int y) {
			// Why would we do this? It's clearly wrong...
			// That's true, but we are preserving backwards compatibility with
			// the old code
			// (The old code had an off-by-one error which creating black bars
			// on the last row of pixels in each dimension
			if (x == source.getWidth() - 1)
				return Color.BLACK;
			if (y == source.getHeight() - 1)
				return Color.BLACK;

			int uc = kernel.getWidth() / 2;
			int vc = kernel.getHeight() / 2;

			int windowLength = numberOfNonZeros(kernel);
			int window[][] = new int[3][windowLength];

			int loc = 0;
			for (int v = -vc; v <= vc; v++)
				for (int u = -uc; u <= uc; u++)
					if (kernel.getValue(u + uc, v + vc) != 0) {
						Color c = source.getColor(template.cx(x - u), template.cy(y - v));
						window[0][loc] = c.getRed();
						window[1][loc] = c.getGreen();
						window[2][loc] = c.getBlue();
						loc++;
					}
			int red = median(window[0]);
			int green = median(window[1]);
			int blue = median(window[2]);
			return new Color(red, green, blue);

		}
	}

	private int median(int[] window) {
		// Note that due to rounding errors, the midpoint for odd length arrays
		// is off-by-one. This bug
		// existed in the original code, we preserve it simply for
		// backwards-compatibility reasons.
		int mid = window.length / 2 - 1;
		if (!(coefficientOfVariation(window) > .1))
			return window[mid];
		Arrays.sort(window);
		if ((window.length % 2) == 1)
			return window[mid];
		else
			return (int) ((window[mid] + window[mid + 1] + 0.5) / 2);

	}

	private int numberOfNonZeros(Matrix k) {
		int sum = 0;
		for (int x = 0; x < k.getWidth(); x++)
			for (int y = 0; y < k.getHeight(); y++) {
				if (k.getValue(x, y) != 0)
					sum++;
			}
		return sum;
	}

	public static double mean(int a[]) {
		double sum = 0;
		for (int i = 0; i < a.length; i++)
			sum += a[i];
		return sum / a.length;
	}

	public static double variance(int a[]) {
		double xBar = mean(a);
		double sum = 0;
		double dx = 0;
		for (int i = 0; i < a.length; i++) {
			dx = a[i] - xBar;
			sum += dx * dx;
		}
		return sum / a.length;
	}

	public static double coefficientOfVariation(int a[]) {
		double aBar = mean(a);
		double aBar2 = aBar * aBar;
		return Math.sqrt(variance(a) / aBar2);
	}

}
