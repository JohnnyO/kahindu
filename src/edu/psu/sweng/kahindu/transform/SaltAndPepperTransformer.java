package edu.psu.sweng.kahindu.transform;

import java.awt.Color;
import java.util.Random;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.RawImageAdapter;

/**
 * Artificially creates noise on an image to assist in other image processing
 * techniques.
 * 
 * @author John
 * 
 */
public class SaltAndPepperTransformer implements Transformer<KahinduImage> {

	private final int count;
	private final Random random = new Random(); // each new instance gets its
												// own seed

	public SaltAndPepperTransformer(int count) {
		this.count = count;
	}

	@Override
	public KahinduImage transform(KahinduImage input) {
		int width = input.getWidth();
		int height = input.getHeight();
		short red[][] = new short[width][height];
		short green[][] = new short[width][height];
		short blue[][] = new short[width][height];

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				Color c = input.getColor(x, y);
				red[x][y] = (short) c.getRed();
				green[x][y] = (short) c.getGreen();
				blue[x][y] = (short) c.getBlue();
			}

		for (int i = 0; i < count; i++) {

			int rx = random.nextInt(width);
			int ry = random.nextInt(height);
			red[rx][ry] = 255;
			green[rx][ry] = 255;
			blue[rx][ry] = 255;
			rx = random.nextInt(width);
			ry = random.nextInt(height);
			red[rx][ry] = 0;
			green[rx][ry] = 0;
			blue[rx][ry] = 0;
		}

		return new RawImageAdapter(width, height, red, green, blue);
	}

}
