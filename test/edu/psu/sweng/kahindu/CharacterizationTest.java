package edu.psu.sweng.kahindu;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.GIFReader;
import gui.ImageFrame;
import gui.TopFrame;

import static org.junit.Assert.*;

public abstract class CharacterizationTest {

	private TopFrame imgFrame;
	private KahinduImage kahinduImage;

	@Before
	public void setUp() throws IOException {
		imgFrame = new TopFrame("Test Frame");
		imgFrame.openGif("gifs/baboon.GIF");
		kahinduImage = (new GIFReader()).read(new File("gifs/baboon.GIF"));
	}

	public abstract TopFrame constructLegacyImage(TopFrame topFrame);

	public abstract KahinduImage constructRefactoredImage(KahinduImage img);

	@Test
	public void compareImages() {
		imgFrame = constructLegacyImage(imgFrame);
		kahinduImage = constructRefactoredImage(kahinduImage);

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
	private int bound(short color) {
		if (color < 0)
			return 0;
		if (color > 255)
			return 255;
		return color;
	}

}
