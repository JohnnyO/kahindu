package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;
import gui.TopFrame;

import static org.junit.Assert.*;

public abstract class TestingUtils {

	public static void compareImages(TopFrame imgFrame, KahinduImage kahinduImage) {

		// check that the images are the same size
		assertEquals(kahinduImage.getHeight(), imgFrame.height);
		assertEquals(kahinduImage.getWidth(), imgFrame.width);

		int height = kahinduImage.getHeight();
		int width = kahinduImage.getWidth();

		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				String error = "Unmatched color at {" + x + "," + y + "}";
				Color c = kahinduImage.getColor(x, y);
				assertEquals(error,  bound(imgFrame.r[x][y]), c.getRed());
				assertEquals(error,  bound(imgFrame.g[x][y]), c.getGreen());
				assertEquals(error,  bound(imgFrame.b[x][y]), c.getBlue());
			}
		}
	}

	/**
	 * the topFrame's image data arrays are dirty, that is, they sometimes
	 * contain data outside of the range 0..255, which doesn't get cleaned up
	 * until render time, since we are testing pre-render, we impose the bounds
	 * in the test
	 */
	private static int bound(short color) {
		if (color < 0)
			return 0;
		if (color > 255)
			return 255;
		return color;
	}

}
