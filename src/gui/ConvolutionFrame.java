package gui;
import java.awt.*;
import java.awt.event.*;
public class ConvolutionFrame extends OpenFrame {

 ConvolutionFrame(String title) {
		super(title);
 }
private boolean showKernal = false;
/**
	@author Douglas Lyon
	@version 1.6
	Toggles the convolutions kernel boolean
	on and off in the 
	@see SpatialFilterFrame
*/
public void showConvolutionKernal() {
	showKernal = ! showKernal;
}
  
public int cx(int x) {
	if (x > width -1)
		return x - width + 1;
	if (x < 0) 
		return width + x;
	return x;
}
public int cy(int y) {
	if (y > height - 1)
		return y - height + 1;
	if (y < 0)
		return height + y;
	return y;
}
	
  public short[][] convolveBrute(short f[][], float k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;

    short h[][]=new short[width][height];
    double sum = 0;
    
    for(int y = 0; y < height; y++) {
      for(int x = 0; x < width; x++) {
		sum = 0.0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	    		sum += f[cx(x-u) ][cy(y-v)] * k[ u+uc][v+vc];
	    //if (sum < 0) sum = 0;
	    //if (sum > 255) sum = 255;
		h[x][y] = (short)sum;
      }
    }
    return h;
  }
  

// Convolution, optimze the edges	
  public short[][] convolve(short f[][], float k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;

    short h[][]=convolveNoEdge(f,k);
    double sum = 0;
    //convolve bottom
    for (int x=0; x < width-1; x++)  
    	for(int y=0; y < vc; y++) {
		sum = 0.0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	    		sum += f[cx(x-u)][cy(y-v)] * k[ u+uc][v+vc];
	    //if (sum < 0) sum = 0;
	    //if (sum > 255) sum = 255;
		h[x][y] = (short)sum;
	}
    //convolve left
    for (int x=0; x < uc; x++) 
      for(int y=vc; y < height - vc; y++) {
    	
		sum = 0.0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	    		sum += f[cx(x-u)][y-v] * k[ u+uc][v+vc];
	    //if (sum < 0) sum = 0;
	    //if (sum > 255) sum = 255;
		h[x][y] = (short)sum;
	}
    //convolve right
        for (int x=width-uc; x < width -1; x++)
        	for(int y=vc; y < height - vc; y++) { 

		sum = 0.0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	    		sum += f[cx(x-u) ][y-v] * k[ u+uc][v+vc];
	    //if (sum < 0) sum = 0;
	    //if (sum > 255) sum = 255;
		h[x][y] = (short)sum;
	}

    //convolve top
    for (int x=0; x < width-1; x++) {
    	for(int y=height-vc; y < height-1; y++) {
		sum = 0.0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	    		sum += f[cx(x-u)][cy(y-v)] * k[ u+uc][v+vc];
	    //if (sum < 0) sum = 0;
	    //if (sum > 255) sum = 255;
		h[x][y] = (short)sum;
		}
	}
    return h;
  }
// Convolution, ignoring the edges	
  public short[][] convolveNoEdge(short f[][], float k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;

    short h[][]=new short[width][height];
    double sum = 0;
    
    for(int x = uc; x < width-uc; x++) {
      for(int y = vc; y < height-vc; y++) {
      
		sum = 0.0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	    		sum += f[x-u ][y-v] * k[ u+uc][v+vc];
	    //if (sum < 0) sum = 0;
	    //if (sum > 255) sum = 255;
		h[x][y] = (short)sum;
      }
    }
    return h;
  }
  
 // return a random int between min and max, inclusive.
 public static int rand(int min, int max) {
 	return (int) (Math.floor(
 		Math.random() * (max - min) + min) + 1);
 } 
 private void showMatLog() {
		Rectangle r = getBounds();
		Dimension d = r.getSize();
		ml.setLocation(d.width, d.height/2);
		ml.setVisible(true);
}
	private MatLog ml;
 public void convolve (float k[] []) {
 	// a 1kx1k image allocates not more than
 	// 1 MB at a time.
 	//Mat.print(k);
 	//Timer t = new Timer();
 	//t.start();
    if (showKernal) {
    	ml = new MatLog(k);
    	showMatLog();
    }
    r = convolve(r,k);
    g = convolve(g,k);
    b = convolve(b,k);
    clip();
  	short2Image();
  	//t.print("convolution done");
 }
}

