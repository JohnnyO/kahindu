package edu.psu.sweng.kahindu.transform;

import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.GUASSIAN15;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.GUASSIAN3;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.GUASSIAN31;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.GUASSIAN7;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.LOW_PASS_AVERAGE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.LOW_PASS_ONE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.LOW_PASS_THREE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.LOW_PASS_TWO;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.MEAN3;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.MEAN9;
import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import gui.TopFrame;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
public class LowPassFilterTest  {
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
	public void testAverage() {
		topFrame.average();

		Transformer<KahinduImage> t = new ConvolutionTransformer(LOW_PASS_AVERAGE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testLP1() {
		topFrame.lp1();

		Transformer<KahinduImage> t = new ConvolutionTransformer(LOW_PASS_ONE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testLP2() {
		topFrame.lp2();

		Transformer<KahinduImage> t = new ConvolutionTransformer(LOW_PASS_TWO);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testLP3() {
		topFrame.lp3();

		Transformer<KahinduImage> t = new ConvolutionTransformer(LOW_PASS_THREE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	   @Test
	    public void testMean3() {
	        topFrame.mean3();

	        Transformer<KahinduImage> t = new ConvolutionTransformer(MEAN3);
	        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	    }
	   
       @Test
       public void testMean9() {
           topFrame.mean9();

           Transformer<KahinduImage> t = new ConvolutionTransformer(MEAN9);
           TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
       }
       
       @Test
       public void testGuass3() {
           topFrame.gauss3();

           Transformer<KahinduImage> t = new ConvolutionTransformer(GUASSIAN3);
           TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
       }
       
       
       @Test
       public void testGuass7() {
           topFrame.gauss7();

           Transformer<KahinduImage> t = new ConvolutionTransformer(GUASSIAN7);
           TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
       }
       
       @Test
       public void testGuass31() {
           topFrame.gauss31();

           Transformer<KahinduImage> t = new ConvolutionTransformer(GUASSIAN31);
           TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
       }
       
       @Test
       public void testGuass15() {
           topFrame.gauss15();

           Transformer<KahinduImage> t = new ConvolutionTransformer(GUASSIAN15);
           TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
       }

       





	
	

}
