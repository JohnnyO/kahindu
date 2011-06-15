package edu.psu.sweng.kahindu.image.io;


import java.io.File;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class PNGWriterTest
{
    private File file = null;
    private KahinduImage image = null;

    @Before
    public void setUp() throws Exception
    {
        ImageReader reader = new DefaultImageReader();
        image = reader.read(new File("gifs/baboon.GIF"));
        
        file = new File("gifs/test.png");
        ImageWriter writer = new DefaultImageWriter("PNG");
        writer.write(image, file);
    }
    
    @Test
    public void testImageWriter()
    {
        assertTrue("PNGWriterTest failed to save PNG Image", file.exists());
    }
}
