package edu.psu.sweng.kahindu.matrix;

import java.util.Arrays;

public class Matrix {
	
	private final int width;
	private final int height;
	private final float data [];

	
	public Matrix(int width, int height) {
		this(width, height, new float[width*height]);
	}

	public Matrix(int width, int height, float[] data) {
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

	public float getValue(int x, int y) {
		return data[x + y*width];
	}
	
	public void scale(float scale) {
		for (int i=0; i < data.length; i++) {
			data[i] = data[i] * scale;
		}
	}

	public void fill(int value) {
		for (int i=0; i < data.length; i++) {
			data[i] = value;
		}
		
	}

}
