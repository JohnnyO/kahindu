package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class CloseTransformer implements Transformer<KahinduImage> {

    
        private final Matrix kernel;

        public CloseTransformer(Matrix kernel) {
            this.kernel = kernel;
        }

        @Override
        public KahinduImage transform(KahinduImage input) {
            Transformer<KahinduImage> erode = new ErodeTransformer(kernel);
            Transformer<KahinduImage> dilate = new DilateTransformer(kernel);
            return erode.transform(dilate.transform(input));
        }

}
