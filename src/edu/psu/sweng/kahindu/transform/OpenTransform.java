package edu.psu.sweng.kahindu.transform;


import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class OpenTransform implements Transformer<KahinduImage> {
    private final Matrix kernel;

    public OpenTransform(Matrix kernel) {
        this.kernel = kernel;
    }

    @Override
    public KahinduImage transform(KahinduImage input) {
        Transformer<KahinduImage> erode = new ErodeTransform(kernel);
        Transformer<KahinduImage> dilate = new DilateTransform(kernel);
        return dilate.transform(erode.transform(input));
    }
}

