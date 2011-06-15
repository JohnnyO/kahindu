package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class CloseTransform implements Transformer<KahinduImage> {

    
        private final Matrix kernel;

        public CloseTransform(Matrix kernel) {
            this.kernel = kernel;
        }

        @Override
        public KahinduImage transform(KahinduImage input) {
            Transformer<KahinduImage> erode = new ErodeTransform(kernel);
            Transformer<KahinduImage> dilate = new DilateTransform(kernel);
            return erode.transform(dilate.transform(input));
        }

}
