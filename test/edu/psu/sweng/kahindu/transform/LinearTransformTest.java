package edu.psu.sweng.kahindu.transform;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.GIFReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import gui.TopFrame;

public class LinearTransformTest {

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
	public void testGrayTransform() {
		topFrame.gray();

		Transformer<KahinduImage> t = new GrayTransformer();
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testNegateTransform() {
		topFrame.negate();

		Transformer<KahinduImage> t = new NegateTransformer();
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testAdditiveTransform() {
		topFrame.add10();

		Transformer<KahinduImage> t = new AdditiveTransformer(10);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testBrighten() {
		topFrame.powImage(1.5);

		Transformer<KahinduImage> t = new PowerTransformer(1.5);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testDarken() {
		topFrame.powImage(0.9);

		Transformer<KahinduImage> t = new PowerTransformer(0.9);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testUniformNonAdaptiveHistogram() {
		topFrame.unahe();

		Transformer<KahinduImage> t = new UniformNonAdaptiveHistogram();
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testExponentialNonAdaptiveHistogram() {
		topFrame.enahe(4.0);

		Transformer<KahinduImage> t = new ExponentialNonAdaptiveHistogram();
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testRaleighHistogram() {
		topFrame.rnahe(1.3);

		ParameterizedTransformer t = new RaleighNonAdaptiveHistogram();
		t.setParameter(RaleighNonAdaptiveHistogram.KEY, 1.3);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

}
