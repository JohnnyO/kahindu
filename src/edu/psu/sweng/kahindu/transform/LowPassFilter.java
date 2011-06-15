package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.matrix.Matrix;

/**
 * A simple example of a convultion. The value of a pixel is the average of
 * itself and its eight neighbors.  See page 196 of "Image Processing in Java for more information"
 * 
 * @author John
 * 
 */
public class LowPassFilter extends ConvolutionTransformation {
	
	

	private final float center;

	/**
	 * Low pass filters set the center of the kernel as specified, and all the other elements to 1.  The
	 * matrix is normalized to prevent gain on the image.
	 * @param center
	 */
	public LowPassFilter(float center) {
		this.center = center;
	}

	@Override
	public Matrix getKernel() {
		Matrix m = new Matrix(3, 3);
		m.fill(1);
		m.setValueAt(1, 1, center);
		m.normalize();
		return m;
	}


}
