package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import gui.TopFrame;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class LegacyTransformTest {
	
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
	public void testHighPassOne() {
		topFrame.hp1();
		
		Transformer<KahinduImage> t = new LegacyTransform("hp1");
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	
	


}
