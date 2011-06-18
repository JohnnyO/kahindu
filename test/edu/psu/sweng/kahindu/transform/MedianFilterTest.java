package edu.psu.sweng.kahindu.transform;

import static org.junit.Assert.assertEquals;
import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import gui.SpatialFilterFrame;
import gui.TopFrame;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class MedianFilterTest {

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
	public void testMedian3x3Square() {
		topFrame.medianSquare3x3();
		
		Transformer<KahinduImage> t = new MedianFilter(3, MedianFilter.SQUARE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testMedian2x2Square() {
		topFrame.medianSquare2x2();
		
		Transformer<KahinduImage> t = new MedianFilter(2, MedianFilter.SQUARE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testMedian5x5Square() {
		topFrame.medianSquare5x5();
		
		Transformer<KahinduImage> t = new MedianFilter(5, MedianFilter.SQUARE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testMedian7x7Square() {
		topFrame.medianSquare7x7();
		
		Transformer<KahinduImage> t = new MedianFilter(7, MedianFilter.SQUARE);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}



	
	@Test
	public void testMedian3x3Cross() {
		topFrame.medianCross3x3();
		
		Transformer<KahinduImage> t = new MedianFilter(3, MedianFilter.CROSS);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	
	@Test
	public void testMedian7x7Cross() {
		topFrame.medianCross7x7();
		
		Transformer<KahinduImage> t = new MedianFilter(7, MedianFilter.CROSS);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}
	
	@Test
	public void testMedian5x5Octagon() {
		topFrame.medianOctagon5x5();
		
		Transformer<KahinduImage> t = new MedianFilter(5, MedianFilter.OCTAGON);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}

	@Test
	public void testMedian7x7Diamond() {
		topFrame.medianDiamond7x7();
		
		Transformer<KahinduImage> t = new MedianFilter(7, MedianFilter.DIAMOND);
		TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
	}



	
	@Test
	public void testQuickSort() {
		int [] values = {145,140,123,132,140,124,127,140,124};
		int [] copy = Arrays.copyOf(values, values.length);
		
		SpatialFilterFrame.quickSort(values);
		
		Arrays.sort(copy);
		
		for (int x=0; x < values.length; x++) {
			assertEquals(values[x], copy[x]);
		}
	}

}
