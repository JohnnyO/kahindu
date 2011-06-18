package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.SubtractionImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class InnerContour implements Transformer<KahinduImage> {
    
    private final Matrix kernel;
    
    public InnerContour(Matrix kernel) {
        this.kernel = kernel;
    }

    @Override
    public KahinduImage transform(KahinduImage input) {
        Transformer<KahinduImage> erode = new ErodeTransform(kernel);
        Transformer<KahinduImage> gray  = new GrayTransformer();
        return new SubtractionImage(erode.transform(input), gray.transform(input));
    }

}
