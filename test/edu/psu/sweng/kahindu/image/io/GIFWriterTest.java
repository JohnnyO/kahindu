package edu.psu.sweng.kahindu.image.io;


import java.io.File;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class GIFWriterTest
{
    private File file = null;
    private KahinduImage image = null;

    @Before
    public void setUp() throws Exception
    {
        ImageReader reader = new DefaultImageReader();
        image = reader.read(new File("gifs/baboon.GIF"));
        
        file = new File("gifs/test.gif");
        ImageWriter writer = new DefaultImageWriter("GIF");
        writer.write(image, file);
    }
    
    @Test
    public void testImageWriter()
    {
        assertTrue("GIFWriterTest failed to save GIF Image", file.exists());
    }
}
