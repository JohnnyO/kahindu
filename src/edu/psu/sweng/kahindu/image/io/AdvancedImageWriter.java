package edu.psu.sweng.kahindu.image.io;

import java.io.File;
import java.io.IOException;

import javax.media.jai.JAI;

import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;

public class AdvancedImageWriter implements ImageWriter
{
    private String imageType;
	
	public AdvancedImageWriter(String imageType)
	{
	    this.imageType = imageType;
	}
	
	public void write(KahinduImage image, File file) throws IOException
	{
	    AWTImageAdapter awtImage = new AWTImageAdapter(image);
	    JAI.create("filestore", awtImage.getBufferedImage(), file.getAbsolutePath(), imageType, null);
	    //ImageIO.write(awtImage.getBufferedImage(), this.imageType, file);
	}

}
