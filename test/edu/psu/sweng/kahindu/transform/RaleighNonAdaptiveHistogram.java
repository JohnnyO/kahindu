package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class RaleighNonAdaptiveHistogram extends HistogramTransform {

	private final double alpha;
	
	public RaleighNonAdaptiveHistogram(double alpha) {
		this.alpha = alpha;
	}

	@Override
	public short[] getLookupTable(KahinduImage input) {
		Histogram histogram = new Histogram(input);
		short lut[] = new short[256];
		double h[] = histogram.getAverageCMF();

		for (short i = 0; i < lut.length; i++) {
		    lut[i] = (short) (255 * Math.sqrt(2*alpha*alpha * Math.log(1/(1.0-h[i]))));
			lut[i] = (short) Math.min(lut[i], 255);
			lut[i] = (short) Math.max(lut[i], 0);
		}
		return lut;
	}

}
