package edu.psu.sweng.kahindu.image.io;

import java.io.File;
import java.io.IOException;

import edu.psu.sweng.kahindu.image.KahinduImage;

public interface ImageReader {
	
	public void setFile(File file);
	
	KahinduImage read() throws IOException;

}
