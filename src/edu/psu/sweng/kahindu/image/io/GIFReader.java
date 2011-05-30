package edu.psu.sweng.kahindu.image.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;

public class GIFReader implements ImageReader {
	
	private final File filename;
	
	public GIFReader(File filename) {
		assert(filename != null);
		assert(filename.getAbsolutePath().toLowerCase().endsWith(".gif"));
		
		this.filename = filename;
	}

	@Override
	public KahinduImage read() throws IOException {
		BufferedImage image = ImageIO.read(filename);
		return new AWTImageAdapter(image);
		
	}

}
