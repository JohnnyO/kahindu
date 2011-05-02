package gui;
import java.awt.*;
import java.awt.event.*;

public class NegateFrame extends GrabFrame {

 private Histogram rh,gh,bh;
 
 private int min = Integer.MAX_VALUE;
 private int max = Integer.MIN_VALUE;
 
 // used for adaptive image enhancement
 private int mosaicWidth  = 2;
 private int mosaicHeight = 2;
 
 private double rBar = 0;
 private double gBar = 0;
 private double bBar = 0;
 
 private TransformTable tt = 
  	    new TransformTable(256);
 
 Menu negateMenu = new Menu("Negate");
 Menu histogramMenu = new Menu("Histogram");
 
 MenuItem negate_mi = 
	addMenuItem(negateMenu,"[E-n]egate");
 MenuItem add10_mi = 
	addMenuItem(negateMenu,"[E-1]add 10");
 MenuItem brighten_mi =
 	addMenuItem(negateMenu,"[E-b]righten");
 MenuItem darken_mi =
  	addMenuItem(negateMenu,"[E-d]arken");
 MenuItem linear_mi =
  	addMenuItem(negateMenu,"[E-l]inear transform...");

MenuItem histogram_mi =
 	addMenuItem(histogramMenu,"[E-h]istogram");
MenuItem unahe_mi =
  	addMenuItem(histogramMenu,
  	"[E-u]niform non-adaptive histogram equalization");
 MenuItem enahe_mi =
  	addMenuItem(histogramMenu,"[E-e]xponential non-adaptive histogram equalization...");
 MenuItem rnahe_mi =
  	addMenuItem(histogramMenu,
  	"[E-r]ayleigh non-adaptive histogram equalization...");

 MenuItem auhe_mi =
  	addMenuItem(histogramMenu,"[E-a]uhe adaptive uniform histogram equalization");
 MenuItem drawMosaic_mi =
  	addMenuItem(histogramMenu,"[E-d]raw Mosaic");
 MenuItem printTT_mi =
  	addMenuItem(histogramMenu,"[E-T-t]print transform table...");
 MenuItem printStats_mi =
  	addMenuItem(histogramMenu,"[E-T-p]rintStats");
 MenuItem printPMFs_mi =
  	addMenuItem(histogramMenu,"[E-1]printPMFs"); 
 MenuItem printCMFs_mi =
  	addMenuItem(histogramMenu,"[E-2]printCMF for r");
 	
 

 public void actionPerformed(ActionEvent e) {

	if (match(e, drawMosaic_mi)) {
    	drawMosaic(); 
		return;
	}
	if (match(e, rnahe_mi)) {
    	rayleighLog(); 
		return;
	}
	if (match(e, auhe_mi)) {
    	auhe(); 
		return;
	}
	if (match(e, enahe_mi)) {
    	eponentialLog(); 
		return;
	}
	if (match(e, printCMFs_mi)) {
    	printCMFs(); 
		return;
	}
	if (match(e, printPMFs_mi)) {
    	printPMFs(); 
		return;
	}
	if (match(e, printStats_mi)) {
    	printStats(); 
		return;
	}
	if (match(e, unahe_mi)) {
    	unahe(); 
		return;
	}
	if (match(e, printTT_mi)) {
    	printTT(); 
		return;
	}
	if (match(e, linear_mi)) {
    	linearLog(); 
		return;
	}
	if (match(e, darken_mi)) {
    	darken(); 
		return;
	}
	if (match(e, brighten_mi)) {
    	brighten(); 
		return;
	}
	if (match(e, histogram_mi)) {
    	histogram(); 
		return;
	}
	if (match(e, add10_mi)) {
    	add10(); 
		return;
	}
	if (match(e, negate_mi)) {
    	negate(); 
		return;
	}
	super.actionPerformed(e);  

 }
 
 /**
 	mosaic - transform an array of 
 	short into sub-images
 */
 public void auhe() {
 	auhe(mosaicWidth,mosaicHeight);
 }

public void drawMosaic() {
	AdaptiveLog.doit(this);
}
 
 /**
 	mosaic - transform an array of 
 	short into sub-images
 	ignoring fractional parts.
 */ 
 public void auhe(int blocksHigh, int blocksWide ) {
 	
 	int pelsWide = width / blocksWide;
 	int pelsHigh = height / blocksHigh;
 	int newWidth = pelsWide * blocksWide;
 	int newHeight = pelsHigh * blocksHigh;
 	NegateFrame nf;

    for (int x1=0; x1 < newWidth ; x1+=pelsWide) 
       for (int y1=0; y1 < newHeight; y1+=pelsHigh) {
    	 nf = subFrame(x1, y1,pelsWide,pelsHigh);
    	 nf.unahe();
    	 assembleMosaic(nf,x1,y1);
       }
     short2Image();
   }
 public void assembleMosaic(NegateFrame nf, int x1, int y1) {
  int x2 = nf.width + x1;
  int y2 = nf.height + y1;
  int xs = 0;
  int ys = 0;
     for (int x=x1; x < x2 ; x++) { 
       for (int y=y1; y < y2; y++) {
    		r[x][y] = nf.r[xs][ys];
    		g[x][y] = nf.g[xs][ys];
    		b[x][y] = nf.b[xs][ys];
    		ys++;
       }
       ys = 0;
       xs++;
    }
 }
 public void drawMosaic(int blocksHigh, int blocksWide ) {
 	mosaicWidth = blocksWide;
 	mosaicHeight = blocksHigh;
 	
 	int pelsWide = width / blocksWide;
 	int pelsHigh = height / blocksHigh;
 	int newWidth = pelsWide * blocksWide;
 	int newHeight = pelsHigh * blocksHigh;
	
 	int x1=0, y1=0;
 	System.out.println("DrawMosaic"+
 		" newWidth="+newWidth +
 		" newHeight="+newHeight +
 		" pelsWide="+pelsWide +
 		" pelsHigh="+pelsHigh);
 	Graphics gx = getGraphics();
    for (x1=0; x1 < newWidth ; x1+=pelsWide) 
       for (y1=0; y1 < newHeight; y1+=pelsHigh) {
          gx.drawRect(x1, y1, pelsWide, pelsHigh);
       }
   }
 
 public NegateFrame subFrame(int x1, int y1, int w, int h) {
    short _r[][] = new short [w][h];
    short _g[][] = new short [w][h];
    short _b[][] = new short [w][h];
    int x2 = x1 + w;
    int y2 = y1 + h;
    // for loop computes source coordinates
    int xd =0;
    int yd =0;

    System.out.println("Subframe"+
     	" x1=" + x1 +
     	" y1=" + y1 +
     	" x2=" + x2 +
     	" y2=" + y2);
    for (int x=x1; x < x2 ; x++) { 
       for (int y=y1; y < y2; y++) {
    		_r[xd][yd] = r[x][y];
    		_g[xd][yd] = g[x][y];
    		_b[xd][yd] = b[x][y];
    		yd++;
       }
       yd = 0;
       xd++;
    }
    return new 
        NegateFrame(_r,_g,_b,"frame");	
 }
 
 private void doMenus() {
 	negateMenu.add(histogramMenu);
 	filterMenu.add(negateMenu);
 }
 public NegateFrame(
 	short _r[][], short _g[][], short _b[][], 
 	String title) {
 	super(title);
 	doMenus();
 	System.out.println("New constructor invoked");
 	r = _r;
 	b = _b;
 	g = _b;
 	// show image ...very slow
 	// but interesting!
 	short2Image();
 }
 public void printTT() {
 	tt.print();
 }
 public void add10() {
  for (int x=0; x < width; x++) 
	for (int y=0; y < height; y++) {
		r[x][y] = (short) (r[x][y]+10);
      	g[x][y] = (short) (g[x][y]+10);
      	b[x][y] = (short) (b[x][y]+10);
	}
	short2Image();
 }

public void histogram() {
   	rh = new Histogram(r,"Red");
	gh = new Histogram(g,"Green");
	bh = new Histogram(b,"Blue");
	rh.myShow();
	gh.myShow();
	bh.myShow();
}
 public void negate() {
  for (int x=0; x < width; x++) 
	for (int y=0; y < height; y++) {
		r[x][y] = (short) (255 - r[x][y]);
      	g[x][y] = (short) (255 - g[x][y]);
      	b[x][y] = (short) (255 - b[x][y]);
	}
	short2Image();
 }
 
 private void brighten() {
 	powImage(0.9);
 }
 
 private void darken() {
 	powImage(1.5);
 }
 
 public void powImage(double p) {
   for (int x=0; x < width; x++) 
	for (int y=0; y < height; y++) {
		r[x][y] = (short) 
			(255 * Math.pow((r[x][y]/255.0),p));
      	g[x][y] = (short) 
      		(255 * Math.pow((g[x][y]/255.0),p));
      	b[x][y] = (short) 
      		(255 * Math.pow((b[x][y]/255.0),p));
	}
	short2Image();
 }
 
 public double[] average(
 	double a[], double b[], double c[]) {
 	double avg[] = new double[a.length];
 	
 	for (int i=0; i < a.length; i++) {
 	  avg[i] = (a[i]+b[i]+c[i])/3.0;
 	}
 	return avg;
 }
 
 // Uniform Non Adaptive Histogram
 // Equalization
 public void unahe() {
   short lut[] = tt.getLut();
   double h[] = getAverageCMF();
   for (short i = 0; i < lut.length; i++)
       lut[i] = (short)(255*h[i]);
   applyLut(lut);
 }

 // Rayleigh Non Adaptive Histogram
 // Equalization
 public void rnahe(double alpha) {
   short lut[] = tt.getLut();
   double h[] = getAverageCMF();
   double alpha2 = 2*alpha * alpha;
   double v;
   double g;
   for (short i = 0; i < h.length; i++) {
   	g = alpha2 * Math.log(1/(1.0-h[i]));
   	v = Math.sqrt(g);
    lut[i] = (short) (255 * v);
    }
   tt.clip();
   applyLut(lut);
 }
 // Exponential Non Adaptive Histogram
 // Equalization
 public void enahe(double alpha) {
   short lut[] = tt.getLut();
   double h[] = getAverageCMF();
   for (short i = 0; i < 256; i++)
       lut[i] = (short)
        (255*(-Math.log(1.0-h[i])/alpha));
   tt.clip();
   applyLut(lut);
 }
 
 
 public double[] getAverageCMF() {
   rh = new Histogram(r,"Red");
   gh = new Histogram(g,"Green");
   bh = new Histogram(b,"Blue");
   double CMFr[] = rh.getCMF();
   double CMFg[] = gh.getCMF();
   double CMFb[] = bh.getCMF();  
   return average(CMFr,CMFg,CMFb);
 }
 
 public void applyLut(short lut[]) {
   wellConditioned(); //Shorts could be out of range;
   for (int x = 0; x < width; x++) 
	for (int y = 0; y < height; y++) {
	    
		r[x][y] = lut[r[x][y]];
      	g[x][y] = lut[g[x][y]];
      	b[x][y] = lut[b[x][y]];
    }
	short2Image();
 }

public void wellConditioned() {
   for (int x = 0; x < width; x++) 
	for (int y = 0; y < height; y++) {
		r[x][y] = inRange(r[x][y],x,y);
      	g[x][y] = inRange(g[x][y],x,y);
      	b[x][y] = inRange(b[x][y],x,y);
    }
}

public short inRange(short v, int x, int y) {
	if (v > 255) {
		System.out.println(
			"out of range x="+x+" y="+y+
			"v="+v+" clipping to 255");
		return 255;
	}
	if (v < 0) {
		System.out.println(
			"out of range x="+x+" y="+y+
			"v="+v+" clipping to 0");
		return 0;
	}
	return v;
}
 
  public short linearMap(short v, 
 		double c, double b) {
 	// scale gray value to 0..1 range
 	double f = c * v + b;
 	// scale f into 0..255 range
 	// clip f into range
 	if (f > 255) f = 255;
 	if (f < 0) f = 0;
 	return (short) f;	
 }
 
 public void linearTransform() {
     computeStats();
    int Vmin = getMin();
    int Vmax = getMax();
    int Dmin = 0;
    int Dmax = 255;
    double deltaV = Vmax - Vmin;
    double deltaD = Dmax - Dmin;
    double c = deltaD/deltaV;
    double b = (Dmin * Vmax-Dmax*Vmin)/deltaV;
    linearTransform(c,b);
 }
 public void linearTransform(double c, double br) {
   for (int x = 0; x < width; x++) 
	for (int y = 0; y < height; y++) {
		r[x][y] = (short)(c * r[x][y] + br);
      	g[x][y] = (short)(c * g[x][y] + br);
      	b[x][y] = (short)(c * b[x][y] + br);
	}
 }
 // The following transform is fast, but
 // only works on well conditioned input.
 // I.e., r,g,b [0..255].
 public void linearTransform2(double c, double br) {

   short lut[] = tt.getLut();
   for (short i = 0; i < 256; i++)
       lut[i] = linearMap(i,c,br);
   for (int x = 0; x < width; x++) 
	for (int y = 0; y < height; y++) {
		r[x][y] = lut[r[x][y]];
      	g[x][y] = lut[g[x][y]];
      	b[x][y] = lut[b[x][y]];
	}
	short2Image();
 }
 
 public void computeStats() {
 	 min = Integer.MAX_VALUE;
 	 max = Integer.MIN_VALUE;
 	 rBar = 0;
 	 gBar = 0;
 	 bBar = 0;
 	 double N = width * height;
 	for (int x=0; x < width; x++) 
 		for (int y=0; y < height; y++) {
 			rBar += r[x][y];
 			gBar += g[x][y];
 			bBar += b[x][y];
 			min = Math.min(r[x][y],min);
 			min = Math.min(g[x][y],min);
 			min = Math.min(b[x][y],min);
 			max = Math.max(r[x][y],max);
 			max = Math.max(g[x][y],max);
 			max = Math.max(b[x][y],max);
 		}
 	rBar /= N;
 	gBar /= N;
 	bBar /= N;
 }
 
 public void printPMFr() {
    rh = new Histogram(r,"Red");
    rh.printPMF();
 }
 public void printCMFs() {
    rh = new Histogram(r,"Red");
    rh.printCMF();
 }
 public void printPMFg() {
    gh = new Histogram(g,"Green");
    gh.printPMF();

 }
 public void printPMFb() {
    bh = new Histogram(b,"Blue");
    bh.printPMF();

 }
 
 public void printPMFs() {
 	printPMFr();
 	printPMFg();
 	printPMFb();
 }
 
 public void printStats() {
 	computeStats();
 	System.out.println(
 		"Min Vij=" + getMin()+"\n"+
 		"Max Vij=" + getMax()+"\n"+
 		"rBar = " + getRBar()+"\n"+
 		"gBar = " + getGBar()+"\n"+
 		"bBar = " + getBBar()		
 		);
 	
 }
 public double getRBar() {
 	return rBar;
 }
 public double getGBar() {
 	return gBar;
 }
 public double getBBar() {
 	return bBar;
 }
 public int getMin() {
 	return min;
 }
 
 public int getMax() {
 	return max;
 }
 public void eponentialLog() {
 	String prompts[] = {
 		"alpha = "
 	};
 	    String defaults[] = {
    	"4.0"};
    String title = "Exponential Transform Dialog";
    
 	ExponentialLog el = 
 		new ExponentialLog(
 			this,
 			title,
 			prompts,
 			defaults,9); 
 }
 public void rayleighLog() {
 	String prompts[] = {
 		"alpha = "
 	};
 	    String defaults[] = {
    	"4.0"};
    String title = "Rayleigh Transform Dialog";
    
 	RayleighLog el = 
 		new RayleighLog(
 			this,
 			title,
 			prompts,
 			defaults,9); 
 }
 
 public void linearLog() {
 	    String prompts[] = {
    		"Contrast = c =",
    		"Brightness = b ="
    };
    computeStats();
    int Vmin = getMin();
    int Vmax = getMax();
    int Dmin = 0;
    int Dmax = 255;
    double deltaV = Vmax - Vmin;
    double deltaD = Dmax - Dmin;
    double c = deltaD/deltaV;
    double b = (Dmin * Vmax-Dmax*Vmin)/deltaV;
    System.out.println("C="+c+" b="+b);
    	
    String defaults[] = {
    	Double.toString(c),
    	Double.toString(b)};
    String title = "Linear Grayscale Transform Dialog";
    
 	GrayLog gl = 
 		new GrayLog(
 			this,
 			title,
 			prompts,
 			defaults,9); 

 }
 public NegateFrame(String title) {
 	super(title);
 	doMenus();
 }
 public static void main(String args[]) {
	NegateFrame nf = 
		new NegateFrame("NegateFrame");
 }
}

