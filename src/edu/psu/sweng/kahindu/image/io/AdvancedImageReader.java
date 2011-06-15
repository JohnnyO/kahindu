package edu.psu.sweng.kahindu.image.io;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.media.jai.JAI;

import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;

public class AdvancedImageReader implements ImageReader
{
	public AdvancedImageReader()
	{
	}
	
	@Override
	public KahinduImage read(File file) throws IOException
	{
        RenderedImage ri = JAI.create("fileload", file.getAbsolutePath());
        return new AWTImageAdapter(ri);
    }
}
