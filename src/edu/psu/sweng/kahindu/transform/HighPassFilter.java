package edu.psu.sweng.kahindu.transform;

import java.util.HashMap;
import java.util.Map;

import edu.psu.sweng.kahindu.matrix.Matrix;

public class HighPassFilter extends ConvolutionTransformation {

	private static final Map<Integer, Matrix> filters = new HashMap<Integer, Matrix>();
	{
		filters.put(1, new Matrix(3, 3, new float[] { 0, -1, 0, -1, 10, -1, 0, -1, 0 }));
		filters.put(2, new Matrix(3, 3, new float[] { 0, -1, 0, -1, 8, -1, 0, -1, 0 }));
		filters.put(3, new Matrix(3, 3, new float[] { 0, -1, 0, -1, 5, -1, 0, -1, 0 }));
		filters.put(4, new Matrix(3, 3, new float[] { 1, -2, 1, -2, 5, -2, 1, -2, 1 }));
		filters.put(5, new Matrix(3, 3, new float[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 }));
		filters.put(6, new Matrix(3, 3, new float[] { -2, -1, 0, -1, 0, 1, 0, 1, 2 }));
		for (int i = 1; i <= 5; i++)
			filters.get(i).normalize();
		//we don't normalize level 6, because it sums to 0

	}

	private int level;

	public HighPassFilter(int level) {
		this.level = level;
	}

	@Override
	public Matrix getKernel() {
		Matrix m = filters.get(level);
		if (m == null)
			throw new IllegalArgumentException("Unknown filter");
		return m;

	}

}
