package edu.psu.sweng.kahindu.transform;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.GIFReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import gui.TopFrame;

import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.*;

public class EdgeTestCase {

    private TopFrame topFrame;
    private KahinduImage kahinduImage;

    @Before
    public void setUp() throws IOException {
        topFrame = new TopFrame("");
        topFrame.openGif("gifs/baboon.gif");

        ImageReader reader = new GIFReader();
        kahinduImage = reader.read(new File("gifs/baboon.gif"));
    }
    
    @Test
    public void testRobert2() {
        topFrame.roberts2();
        
        Transformer<KahinduImage> t = new RobertsTransform();
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }
    
    
    @Test
    public void testMedian1x2() {
        topFrame.median1x2();
        
        Transformer<KahinduImage> t = new MedianFilter(3, MedianFilter.HORIZONTAL_LINE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }
    
    @Test
    public void testMedian2x1() {
        topFrame.median2x1();
        
        Transformer<KahinduImage> t = new MedianFilter(3, MedianFilter.VERTICAL_LINE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }



}
