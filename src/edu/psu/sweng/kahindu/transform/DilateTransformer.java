package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.DefaultImageDecorator;
import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class DilateTransformer implements Transformer<KahinduImage> {
    private final Matrix kernel;

    public DilateTransformer(Matrix kernel) {
        this.kernel = kernel;
    }

    @Override
    public KahinduImage transform(final KahinduImage source) {
        final KahinduImage input = new GrayTransformer().transform(source);
        return new DefaultImageDecorator(input) {

            @Override
            public Color getColor(int x, int y) {
                int uc = kernel.getWidth() / 2;
                int vc = kernel.getHeight() / 2;

                int width = input.getWidth();
                int height = input.getHeight();

                if ((x < uc || x >= width - uc) || (y < vc || y >= height - vc))
                    return Color.BLACK;

                int sum = 0;
                for (int v = -vc; v <= vc; v++)
                    for (int u = -uc; u <= uc; u++)
                        if (kernel.getValue(u + uc, v + vc) == 1) {
                            Color c = input.getColor(x - u, y - v);
                            if (c.getRed() > sum)
                                sum = input.getColor(x - u, y - v).getRed();
                        }

                return new Color(sum, sum, sum);
            }
        };
    }
}
