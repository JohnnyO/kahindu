package edu.psu.sweng.kahindu.transform;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.GIFReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import gui.TopFrame;


public class HighPassFilterTest {
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
	public void testHighPassOne() {
		topFrame.hp1();
		
		Transformer<KahinduImage> t = new HighPassFilter(1);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testHighPassTwo() {
		topFrame.hp2();
		
		Transformer<KahinduImage> t = new HighPassFilter(2);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testHighPassThree() {
		topFrame.hp3();
		
		Transformer<KahinduImage> t = new HighPassFilter(3);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testHighPassFour() {
		topFrame.hp4();
		
		Transformer<KahinduImage> t = new HighPassFilter(4);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testHighPassFive() {
		topFrame.hp5();
		
		Transformer<KahinduImage> t = new HighPassFilter(5);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}






}
