package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.SubtractionImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class MiddleContour implements Transformer<KahinduImage> {

    private final Matrix kernel;

    public MiddleContour(Matrix kernel) {
        this.kernel = kernel;
    }

    @Override
    public KahinduImage transform(KahinduImage input) {
        Transformer<KahinduImage> dilate = new DilateTransform(kernel);
        Transformer<KahinduImage> erode = new ErodeTransform(kernel);
        return new SubtractionImage(dilate.transform(input), erode.transform(input));
    }

}
