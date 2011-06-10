package edu.psu.sweng.kahindu.transform;

import java.awt.Color;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import edu.psu.sweng.kahindu.matrix.Matrix;

public class SpatialTransformedImage implements KahinduImage{
	
	private final Matrix kernel;
	private final KahinduImage source;
	
	
	public SpatialTransformedImage(KahinduImage source, Matrix kernel) {
		this.kernel = kernel;
		this.source = source;
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
			    int uc = kernel.getWidth() /2;   
			    int vc = kernel.getHeight() / 2;

			    //short h[][]=new short[width][height];
			    double red = 0;
			    double green = 0;
			    double blue = 0;
					for(int v = -vc; v <= vc; v++) 
				  		for(int u = -uc; u <= uc; u++) {
				  			Color color = source.getColor(cx(x-u), cy(y-v));
				    		red += color.getRed() * kernel.getValue(u+uc,v+vc);
				    		green += color.getGreen() * kernel.getValue(u+uc,v+vc);
				    		blue += color.getBlue() * kernel.getValue(u+uc,v+vc);
				  		}
				return new Color((int)red, (int)green, (int)blue);
	}

	private int cy(int y) {
		if (y > source.getHeight() - 1)
			return y - source.getHeight() + 1;
		if (y < 0)
			return source.getHeight() + y;
		return y;
	}

	private int cx(int x) {
		if (x > source.getWidth() -1)
			return x - source.getWidth() + 1;
		if (x < 0) 
			return source.getWidth() + x;
		return x;
	}
	

}
