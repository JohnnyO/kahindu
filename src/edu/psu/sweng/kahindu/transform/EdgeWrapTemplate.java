package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;

/**
 * A template class that defines our edge wrapping strategy for transformations that require edge wrapping
 * (specifically, Convolutions and Median filters)
 * @author John
 *
 */
 class EdgeWrapTemplate  {
    
    private final KahinduImage source;

    EdgeWrapTemplate(KahinduImage source) {
        this.source = source;
    }


	/**
	 * Our wrapping strategy in Y (height)
	 * @param y
	 * @return
	 */
	public int cy(int y) {
		int height = source.getHeight();
		if (y > height - 1)
			return y - height + 1;
		if (y < 0)
			return height + y;
		return y;
	}

	/**
	 * Our wrapping strategy in X (width)
	 * @param y
	 * @return
	 */

	public int cx(int x) {
		int width = source.getWidth();
		if (x > width - 1)
			return x - width + 1;
		if (x < 0)
			return width + x;
		return x;
	}

	public short bound(double color) {
		if (color > 255)
			return 255;
		if (color < 0)
			return 0;
		return (short)color;
	}

}
