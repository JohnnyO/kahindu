package edu.psu.sweng.kahindu.transform;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.GIFReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import edu.psu.sweng.kahindu.matrix.Matrix;
import gui.TopFrame;

import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.*;

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
		
		Transformer<KahinduImage> t = new ConvolutionTransformer(HIGH_PASS_ONE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testHighPassTwo() {
		topFrame.hp2();
		
		Transformer<KahinduImage> t =new ConvolutionTransformer(HIGH_PASS_TWO);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testHighPassThree() {
		topFrame.hp3();
		
		Transformer<KahinduImage> t = new ConvolutionTransformer(HIGH_PASS_THREE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testHighPassFour() {
		topFrame.hp4();
		
		Transformer<KahinduImage> t =new ConvolutionTransformer(HIGH_PASS_FOUR);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testHighPassFive() {
		topFrame.hp5();
		
		Transformer<KahinduImage> t = new ConvolutionTransformer(HIGH_PASS_FIVE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	

	







}
