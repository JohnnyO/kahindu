package edu.psu.sweng.kahindu.image;

import java.awt.Color;

import edu.psu.sweng.kahindu.io.Transformer;

public class TransformedImage implements KahinduImage {
	
	private final KahinduImage source;
	private final Transformer<Color> transformer;

	public TransformedImage(final KahinduImage source, final Transformer<Color> transformer) {
		assert(source != null);
		assert(transformer != null);
		this.source = source;
		this.transformer = transformer;
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
		return transformer.transform(source.getColor(x, y));
	}

}
