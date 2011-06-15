package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;

public abstract class HistogramTransform implements Transformer<KahinduImage> {

	public abstract short[] getLookupTable(KahinduImage input);

	@Override
	public KahinduImage transform(KahinduImage input) {
		
		return new LookupImage(input, this.getLookupTable(input));
	}
	
	private class LookupImage implements KahinduImage {
		
		private final KahinduImage source;
		private final short [] lookupTable; 

		public LookupImage(KahinduImage input, short[] lut) {
			this.source = input;
			this.lookupTable = lut;
		}

		@Override
		public int getWidth() {
			return source.getWidth();
		}

		@Override
		public int getHeight() {
			return source.getHeight();
		}

		@Override
		public Color getColor(int x, int y) {
			Color c = source.getColor(x, y);
			return new Color(lookupTable[c.getRed()], lookupTable[c.getGreen()], lookupTable[c.getBlue()]);
		}
		
	}
	

	public class Histogram {

		private final KahinduImage source;
		private final double[][] probabilityMassFunction = new double[3][256];
		private final double[][] cumulativeMassFunction = new double[3][256];

		public Histogram(KahinduImage img) {
			this.source = img;
			this.computeMetrics();
		}

		public double[] getAverageCMF() {
			double[] average = new double[256];
			for (int i = 0; i < 256; i++)
				average[i] = (cumulativeMassFunction[0][i] + cumulativeMassFunction[1][i] + cumulativeMassFunction[2][i]) / 3.0;
			return average;
		}

		private void computeMetrics() {
			for (int x = 0; x < source.getWidth(); x++) {
				for (int y = 0; y < source.getHeight(); y++) {
					Color c = source.getColor(x, y);
					probabilityMassFunction[0][c.getRed()]++;
					probabilityMassFunction[1][c.getGreen()]++;
					probabilityMassFunction[2][c.getBlue()]++;
				}
			}

			double total = source.getHeight() * source.getWidth();
			for (int i = 0; i < 256; i++) {
				probabilityMassFunction[0][i] = probabilityMassFunction[0][i] / total;
				probabilityMassFunction[1][i] = probabilityMassFunction[1][i] / total;
				probabilityMassFunction[2][i] = probabilityMassFunction[2][i] / total;
			}

			for (int i = 1; i < 256; i++) {
				cumulativeMassFunction[0][i] = probabilityMassFunction[0][i] + cumulativeMassFunction[0][i - 1];
				cumulativeMassFunction[1][i] = probabilityMassFunction[1][i] + cumulativeMassFunction[1][i - 1];
				cumulativeMassFunction[2][i] = probabilityMassFunction[2][i] + cumulativeMassFunction[2][i - 1];
			}
		}
	}

}
