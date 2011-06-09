package edu.psu.sweng.kahindu.image.io;

import java.io.File;
import java.io.IOException;

import edu.psu.sweng.kahindu.image.KahinduImage;

public interface ImageWriter
{
    public void write(KahinduImage image, File file) throws IOException;
}
