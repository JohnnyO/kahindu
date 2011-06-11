package edu.psu.sweng.kahindu.transform;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.GIFReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import gui.TopFrame;

public class LowPassFilterTest extends TestingUtils {
	TopFrame topFrame;
	KahinduImage kahinduImage;

	@Before
	public void setUp() throws IOException {
		topFrame = new TopFrame("");
		topFrame.openGif("gifs/baboon.gif");

		ImageReader reader = new GIFReader();
		kahinduImage = reader.read(new File("gifs/baboon.gif"));
	}

	@Test
	public void testAverage() {
		topFrame.average();

		Transformer<KahinduImage> t = new LowPassFilter(1);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testLP1() {
		topFrame.lp1();

		Transformer<KahinduImage> t = new LowPassFilter(2);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testLP2() {
		topFrame.lp2();

		Transformer<KahinduImage> t = new LowPassFilter(4);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testLP3() {
		topFrame.lp3();

		Transformer<KahinduImage> t = new LowPassFilter(12);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	

}
