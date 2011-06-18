package edu.psu.sweng.kahindu.transform;

import java.awt.Color;
import java.lang.reflect.Method;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.RawImageAdapter;
import gui.TopFrame;


/**
 * This is a quick and dirty way to get old Kahindu functionality into the new
 * application framework.  Its not meant as a final implementation, but rather 
 * as a stopgap measure during refactoring to ensure all the old functionality
 * is still in place.
 * @author John
 *
 */
public class LegacyTransform implements Transformer<KahinduImage> {

	private final TopFrame topFrame = new TopFrame("");
	private final String methodName;

	public LegacyTransform(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public KahinduImage transform(KahinduImage input) {
		try {
			populateTopFrame(input);
			performTransformation();
			return copyToNewImage();
		} catch (Exception e) {
			//If things go south, we'll propogate the error up the stack, there is no real way to recover gracefully
			throw new RuntimeException(e);
		}
	}

	/**
	 * We copy everything back out so that future invocations don't overwrite
	 * the data we are trying to use.
	 * 
	 * @return a copy of the image data stored in the TopFrame
	 */
	private KahinduImage copyToNewImage() {

		int width = topFrame.width;
		int height = topFrame.height;

		short red[][] = new short[width][height];
		short green[][] = new short[width][height];
		short blue[][] = new short[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				red[x][y] = topFrame.r[x][y];
				green[x][y] = topFrame.g[x][y];
				blue[x][y] = topFrame.b[x][y];
			}
		}

		return new RawImageAdapter(width, height, red, green, blue);

	}

	private void populateTopFrame(KahinduImage input) {
		int width = input.getWidth();
		int height = input.getHeight();

		topFrame.width = width;
		topFrame.height = height;

		topFrame.r = new short[width][height];
		topFrame.g = new short[width][height];
		topFrame.b = new short[width][height];

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				Color color = input.getColor(x, y);
				topFrame.r[x][y] = (short) color.getRed();
				topFrame.g[x][y] = (short) color.getGreen();
				topFrame.b[x][y] = (short) color.getBlue();
			}
	}

	private void performTransformation() throws Exception {
		Method method = topFrame.getClass().getMethod(methodName, (Class[]) null);
		method.invoke(topFrame, null);
	}

}
