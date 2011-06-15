package edu.psu.sweng.kahindu.image;

import java.awt.Color;

/**
 * An implementation of image that returns the difference between two images.
 * 
 * @author jjo135
 * 
 */
public class SubtractionImage implements KahinduImage {

    private final KahinduImage a;
    private final KahinduImage b;

    public SubtractionImage(KahinduImage a, KahinduImage b) {
        this.a = a;
        this.b = b;
        assert (a.getWidth() == b.getWidth());
        assert (a.getHeight() == b.getHeight());

    }

    @Override
    public int getWidth() {
        return a.getWidth();
    }

    @Override
    public int getHeight() {
        return a.getHeight();
    }

    @Override
    public Color getColor(int x, int y) {
        Color c1 = a.getColor(x, y);
        Color c2 = b.getColor(x, y);

        int red = Math.abs(c1.getRed() - c2.getRed());
        int green = Math.abs(c1.getGreen() - c2.getGreen());
        int blue = Math.abs(c1.getBlue() - c2.getBlue());

        return new Color(red, green, blue);
    }

}
