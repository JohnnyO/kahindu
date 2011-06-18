package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.SubtractionImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class OuterContourTransformer implements Transformer<KahinduImage> {
    
    private final Matrix kernel;
    
    public OuterContourTransformer(Matrix kernel) {
        this.kernel = kernel;
    }

    @Override
    public KahinduImage transform(KahinduImage input) {
        Transformer<KahinduImage> dilate = new DilateTransformer(kernel);
        Transformer<KahinduImage> gray  = new GrayTransformer();
        return new SubtractionImage(dilate.transform(input), gray.transform(input));
    }

}
