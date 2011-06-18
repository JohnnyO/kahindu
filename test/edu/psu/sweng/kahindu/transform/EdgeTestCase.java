package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import gui.TopFrame;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class EdgeTestCase {

    private TopFrame topFrame;
    private KahinduImage kahinduImage;

    @Before
    public void setUp() throws IOException {
        topFrame = new TopFrame("");
        topFrame.openGif("gifs/baboon.gif");

        ImageReader reader = new DefaultImageReader();
        kahinduImage = reader.read(new File("gifs/baboon.gif"));
    }
    
    @Test
    public void testRobert2() {
        topFrame.roberts2();
        
        Transformer<KahinduImage> t = new RobertsTransformer();
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }
    
    
    @Test
    public void testMedian1x2() {
        topFrame.median1x2();
        
        Transformer<KahinduImage> t = new MedianFilterTransformer(3, MedianFilterTransformer.HORIZONTAL_LINE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }
    
    @Test
    public void testMedian2x1() {
        topFrame.median2x1();
        
        Transformer<KahinduImage> t = new MedianFilterTransformer(3, MedianFilterTransformer.VERTICAL_LINE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }



}
