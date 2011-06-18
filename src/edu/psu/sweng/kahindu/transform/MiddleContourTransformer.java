package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.SubtractionImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class MiddleContourTransformer implements Transformer<KahinduImage> {

    private final Matrix kernel;

    public MiddleContourTransformer(Matrix kernel) {
        this.kernel = kernel;
    }

    @Override
    public KahinduImage transform(KahinduImage input) {
        Transformer<KahinduImage> dilate = new DilateTransformer(kernel);
        Transformer<KahinduImage> erode = new ErodeTransformer(kernel);
        return new SubtractionImage(dilate.transform(input), erode.transform(input));
    }

}
