package edu.psu.sweng.kahindu.matrix;

public class Matrix {
	
	private final int width;
	private final int height;
	private final double data [];

	public Matrix(int width, int height) {
		this(width, height, new double[width*height]);
		
	}

	public Matrix(int width, int height, double[] data) {
		assert(data.length == width * height);
		this.width = width;
		this.height = height;
		this.data = data;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public double getValue(int x, int y) {
		//TODO:  Hardcoded test of the average case.
		return 1.0/9.0;
	}

}
