package edu.psu.sweng.kahindu.transform;


import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class OpenTransformer implements Transformer<KahinduImage> {
    private final Matrix kernel;

    public OpenTransformer(Matrix kernel) {
        this.kernel = kernel;
    }

    @Override
    public KahinduImage transform(KahinduImage input) {
        Transformer<KahinduImage> erode = new ErodeTransformer(kernel);
        Transformer<KahinduImage> dilate = new DilateTransformer(kernel);
        return dilate.transform(erode.transform(input));
    }
}

