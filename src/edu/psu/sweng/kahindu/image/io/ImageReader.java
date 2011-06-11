package edu.psu.sweng.kahindu.image.io;

import java.io.File;
import java.io.IOException;

import edu.psu.sweng.kahindu.image.KahinduImage;

public interface ImageReader
{
	KahinduImage read(File file) throws IOException;
}
