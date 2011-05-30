package edu.psu.sweng.kahindu.image.io;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class ByteArrayImageAdapter implements KahinduImage {
	
	private final short [][] data;
	
	public ByteArrayImageAdapter(short [][] data) {
		this.data = data;
	}

	@Override
	public int getWidth() {
		return data.length;
	}

	@Override
	public int getHeight() {
		return data[0].length;
	}

	@Override
	public Color getColor(int x, int y) {
		short value = data[y][x];
		return new Color(value, value, value);
	}

	
	

}
