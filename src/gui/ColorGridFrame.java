package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.util.Random;

public final class ColorGridFrame extends ClosableFrame {
    private ColorGrid cgrid;
    private Label label = new Label();
    private Frame f;
    private int r,g,b,a;

    public ColorGridFrame(Frame f_) {
    	super("Color Grid");
    	
		f = f_;
        
        ColorModel colorModel = Toolkit.getDefaultToolkit().getColorModel();
        int bitsize = colorModel.getPixelSize();

        if (colorModel instanceof IndexColorModel) {
            // index color model
            cgrid = new ColorGrid(1 << bitsize);
            for (int i=0; i<1<<bitsize; i++) 
                cgrid.setColor(i, colorModel.getRGB(i));
        	} 
        else {
            // direct color model
            Random r = new Random();
            cgrid = new ColorGrid(256);
            for (int i=0; i<256; i++) 
                cgrid.setColor(i, r.nextInt());
        } // end else
        add("Center", cgrid);
        add("North", label);
        
        setSize(300, 300);
        show();
    }
   
}

// This class displays a grid that is used for displaying colors.
class ColorGrid extends Canvas {
    int rows, cols;
    int colors[];
    ColorGrid(int numColors) {
        colors = new int[numColors];
        cols = Math.min(16, numColors);
        rows = (numColors - 1) / cols + 1;
    }

    // Sets the color at the cell located at position 'i'.
    // 'rgb' is a color in the default RGB color model.
    void setColor(int i, int rgb) {
        colors[i] = rgb;
    }

    // Returns the pixel value at (x, y).  The pixel value is encoded
    // using the default color model.
    int getRGB(int x, int y) {
    	Dimension r = getSize();
        int cellW = r.width / cols;
        int cellH = r.height / rows;
        
        x /= cellW;
        y /= cellH;

        // Return the last color if out of bounds.
        return colors[Math.min(colors.length-1, y * cols + x)];
    }

    public void paint(Graphics g) {
    	Dimension rec = getSize();
        int cellW = rec.width / cols;
        int cellH = rec.height / rows;

        for (int i=0; i<colors.length; i++) {
            int r = i / cols;
            int c = i % cols;

            g.setColor(new Color(colors[i]));
            g.fillRect(c * cellW, r * cellH, cellW, cellH);
        }
    }
}
 interface Colorable {

	Color getColor();
	void setColor(Color c);
}