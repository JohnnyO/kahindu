package edu.psu.sweng.kahindu.image.io;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;

public class DefaultImageWriter implements ImageWriter
{
    private String imageType;
	
	public DefaultImageWriter(String imageType)
	{
	    this.imageType = imageType;
	}
	
	public void write(KahinduImage image, File file) throws IOException
	{
	    AWTImageAdapter awtImage = new AWTImageAdapter(image);
	    ImageIO.write(awtImage.getBufferedImage(), this.imageType, file);
	}

}
