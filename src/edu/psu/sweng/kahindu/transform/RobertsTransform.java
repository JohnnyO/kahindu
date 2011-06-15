package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class RobertsTransform implements Transformer<KahinduImage> {

    @Override
    public KahinduImage transform(final KahinduImage input) {
        final KahinduImage grayScale = new GrayTransformer().transform(input);
        return new KahinduImage() {

            @Override
            public int getWidth() {
                return grayScale.getWidth();
            }

            @Override
            public int getHeight() {
                return grayScale.getHeight();
            }

            @Override
            public Color getColor(int x, int y) {
                if ((x == grayScale.getWidth() - 1) || (y == grayScale.getHeight() - 1)) {
                    //Once again, we see that our previous developers didn't understand the concept of an edge case.
                    Color c = input.getColor(x, y);
                    Color grey = grayScale.getColor(x, y);
                    return new Color(grey.getRed(), c.getGreen(), c.getBlue());
                }
                int p[] = new int[4];
                float delta_u = 0;
                float delta_v = 0;
                short t;
                p[0] = grayScale.getColor(x, y).getRed();
                p[1] = grayScale.getColor(x + 1, y).getRed();
                p[2] = grayScale.getColor(x, y + 1).getRed();
                p[3] = grayScale.getColor(x + 1, y + 1).getRed();

                delta_u = p[0] - p[3];
                delta_v = p[1] - p[2];
                t = (short) Math.sqrt(delta_u * delta_u + delta_v * delta_v);
                if (t > 255)
                    t = 255;
                if (t < 0)
                    t = 0;
                return new Color(t, t, t);
            }

        };

    }

}
