package edu.psu.sweng.kahindu.image.io;

import java.io.File;
import java.io.IOException;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class ByteArrayImageReader implements ImageReader{
	
	private final short [][] data;
	
	public ByteArrayImageReader(short [][] data) {
		this.data = data;
	}

	@Override
	public KahinduImage read(File file) throws IOException {
		return new ByteArrayImageAdapter(data);
	}
}
