package edu.psu.sweng.kahindu.image.io;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;

public class GIFWriter implements ImageWriter
{
    private static final String GIF = "gif";
	
	public GIFWriter()
	{
	}
	
	public void write(KahinduImage image, File file) throws IOException
	{
	    AWTImageAdapter awtImage = new AWTImageAdapter(image);
	    ImageIO.write(awtImage.getBufferedImage(), GIF, file);
	}

}
