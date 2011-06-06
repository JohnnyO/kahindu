package edu.psu.sweng.kahindu.image.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;

public class GIFReader implements ImageReader
{
	
	private File filename;
	
	public GIFReader()
	{
	}
	
	@Override
	public KahinduImage read(File file) throws IOException
	{
	    this.filename = file;
		BufferedImage image = ImageIO.read(filename);
		return new AWTImageAdapter(image);
	}

}
