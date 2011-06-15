package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class UniformNonAdaptiveHistogram extends HistogramTransform {

	@Override
	public short[] getLookupTable(KahinduImage input) {
		Histogram histogram = new Histogram(input);
		short lut [] = new short[256];
		double h[] = histogram.getAverageCMF();
		for (short i = 0; i < lut.length; i++)
			lut[i] = (short) (255 * h[i]);
		return lut;
	}

}
