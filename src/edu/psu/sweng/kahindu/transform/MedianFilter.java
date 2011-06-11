package edu.psu.sweng.kahindu.transform;

import java.util.Arrays;


import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.RawImageAdapter;
import edu.psu.sweng.kahindu.matrix.Matrix;

/**
 * Not currently functional, mostly because the original code was non-functional.
 * 
 * @author John
 *
 */
@Deprecated
public class MedianFilter implements Transformer<KahinduImage> {

	public enum Shape {
		SQUARE, CROSS, DIAMOND, OCTAGON;

		public Matrix getKernel(int size) {
			Matrix m = new Matrix(size, size);
			m.fill(1);
			return m;
		}
	}

	private int size;
	private Shape shape;

	public MedianFilter(int size, Shape shape) {
		this.shape = shape;
		this.size = size;
	}


	@Override
	public KahinduImage transform(final KahinduImage input) {
		// Because this is a non-linear transform, we have to copy out the image
		// and do
		// it all at once
		int width = input.getWidth();
		int height = input.getHeight();

		short red[][] = new short[width][height];
		short green[][] = new short[width][height];
		short blue[][] = new short[width][height];

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				red[x][y] = (short) input.getColor(x, y).getRed();
				green[x][y] = (short) input.getColor(x, y).getGreen();
				blue[x][y] = (short) input.getColor(x, y).getBlue();
			}
		Matrix kernel = shape.getKernel(size);

		int uc = kernel.getWidth() / 2;
		int vc = kernel.getHeight() / 2;

		int windowLength = numberOfNonZeros(kernel);
		int window[][] = new int[3][windowLength];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int loc = 0;
				for (int v = -vc; v <= vc; v++)
					for (int u = -uc; u <= uc; u++)
						if (kernel.getValue(u + uc,v + vc) != 0) {
							window[0][loc] = red[cx(width, x - u)][cy(height, y - v)];
							window[1][loc] = green[cx(width, x - u)][cy(height, y - v)];
							window[2][loc] = blue[cx(width, x - u)][cy(height, y - v)];
							loc++;
						}
				red[x][y] = (short) median(window[0]);
				green[x][y] = (short) median(window[1]);
				blue[x][y] = (short) median(window[2]);
			}

		}

		return new RawImageAdapter(width, height, red, green, blue);
	}
	
	private int median(int[] window) {
		int mid = window.length / 2 - 1;
		Arrays.sort(window);
		if ((window.length % 2) == 1)
			return window[mid];
		else
			return (int) ((window[mid] + window[mid + 1] + 0.5) / 2);

	}

	private int cy(int height, int y) {
		if (y > height - 1)
			return y -height + 1;
		if (y < 0)
			return height + y;
		return y;
	}

	private int cx(int width, int x) {
		if (x > width - 1)
			return x -width + 1;
		if (x < 0)
			return width + x;
		return x;
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
}
