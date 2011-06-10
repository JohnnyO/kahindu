package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.matrix.Matrix;

/**
 * A simple example of a convultion. The value of a pixel is the average of
 * itself and its eight neighbors.
 * 
 * @author John
 * 
 */
public class LowPassAverage extends ConvolutionTransformation {

	private final float center;

	public LowPassAverage(float center) {
		this.center = center;
	}

	@Override
	public Matrix getKernel() {
		Matrix m = new Matrix(3, 3);
		m.fill(1);
		m.setValueAt(1, 1, center);
		float sum = m.sum();
		m.scale((float) (1 / sum));
		return m;
	}

}
