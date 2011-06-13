package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.transform.HistogramTransform.Histogram;

public class ExponentialNonAdaptiveHistogram extends HistogramTransform {

	private final double alpha;

	public ExponentialNonAdaptiveHistogram(double alpha) {
		this.alpha = alpha;
	}

	@Override
	public short[] getLookupTable(KahinduImage input) {
		Histogram histogram = new Histogram(input);
		short lut[] = new short[256];
		double h[] = histogram.getAverageCMF();
		for (short i = 0; i < lut.length; i++) {
			lut[i] = (short) (255 * (-Math.log(1.0 - h[i]) / alpha));
			lut[i] = (short) Math.min(lut[i], 255);
			lut[i] = (short) Math.max(lut[i], 0);
		}
		return lut;
	}

}
