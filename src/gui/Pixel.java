package gui;
import java.awt.Color;

public class Pixel  implements Comparable {
	Color c;
	int n = 1;
	Pixel(Color _c) {
		c = _c;
	}
	//  p.equals(p1) 
	// return true if p == p1
	public boolean equals(Object p) {
		Pixel p1 = (Pixel) p;
		return p1.c.equals(c);
	}
	// Compute the Euclidean distance in 
	// RGB space
	public double distance(Pixel p1 ) {
		int dr = c.getRed() - p1.c.getRed();
		int dg = c.getGreen() - p1.c.getGreen();
		int db = c.getBlue() - p1.c.getBlue();
		return
			Math.sqrt( dr*dr + dg*dg + db*db);
	}
	//  p.isGreater(p1) 
	// return true if p > p1
	public boolean isGreater(Object p) {
		Pixel p1 = (Pixel) p;
		int dr = c.getRed() - p1.c.getRed();
		int dg = c.getGreen() - p1.c.getGreen();
		int db = c.getBlue() - p1.c.getBlue();
		int sum = dr + dg + db;
		return (sum > 0);
	}
	public boolean isLess(Object p) {
		if (isGreater(p) || equals(p)) {
			return false;
		}
		return true;
	}
	public String toString() {
		return c.toString() +"[n="+ n+"]";
	}
}