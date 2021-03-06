package gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;


public class BoundaryFrame extends MorphFrame  {
	Menu boundaryMenu = new Menu("Boundary");
	Menu countourMenu = new Menu("Countour");
	
	MenuItem grayPyramid_mi = 
		addMenuItem(boundaryMenu,"[E-p]grayPyramid");
	MenuItem diffProcess_mi = 
		addMenuItem(boundaryMenu,"[E-d]iffProcess");
	MenuItem drawFramePoints_mi = 
		addMenuItem(boundaryMenu,"drawFramePoints");
	MenuItem edge2HeightField_mi = 
		addMenuItem(boundaryMenu,"Edge->height field");
	MenuItem buildPoints_mi = 
		addMenuItem(boundaryMenu,"buildPoints");
	
	MenuItem houghDetect_mi = 
		addMenuItem(boundaryMenu,"[E-H]houghDetect");
	MenuItem houghDetectGray_mi = 
		addMenuItem(boundaryMenu,"houghDetectGray");

	MenuItem copyToChildFrame_mi = 
		addMenuItem(fileMenu,"[E-C]copyToChildFrame");
	MenuItem grabChild_mi = 
		addMenuItem(fileMenu,"[E-G]grabChild");

	MenuItem bugWalk_mi =
		addMenuItem(countourMenu,"[b]ug walk");
	MenuItem printPolys_mi = 
		addMenuItem(countourMenu,"printPolys...");
	MenuItem listPolys_mi = 
		addMenuItem(countourMenu,"listPolys");
	MenuItem filterPolys_mi = 
		addMenuItem(countourMenu,"filterPolys");
	MenuItem drawPoly_mi = 
		addMenuItem(countourMenu,"drawPoly");
	
	MenuItem displayHoughOfRed_mi = 
		addMenuItem(boundaryMenu,"[E-R]displayHoughOfRed");
	MenuItem drawSomeBigPoints_mi = 
		addMenuItem(boundaryMenu,"[E-D]drawSomeBigPoints");
	MenuItem computeHoughAndDraw_mi = 
		addMenuItem(boundaryMenu,"[E-T-C]computeHoughAndDraw");
	MenuItem computeMagnitudeAndGradiant_mi = 
		addMenuItem(boundaryMenu,"computeMagnitudeAndGradiant");
		
	MenuItem houghEdge_mi =
		addMenuItem(boundaryMenu,"[E-I]houghEdge");
	MenuItem inverseHoughToRed_mi =
		addMenuItem(boundaryMenu,"Inverse Hough To Red");
	MenuItem singlePixelEdge_mi = 
		addMenuItem(boundaryMenu,"[E-s]inglePixelEdge");

	MenuItem print_mi = addMenuItem(fileMenu, "Print...");

	private Vector lineList = new Vector();
	private Vector polyList = new Vector();

	boolean drawOnlyPolys = false;
	
	
	public Vector getPolyList() {
		return polyList;
	}
	
	public void setPolyList(Vector v){
		polyList = v;
	}

	public void actionPerformed(ActionEvent e) {


		if (match(e, edge2HeightField_mi)) {
			edge2HeightField();
			return;
		}		

		if (match(e, buildPoints_mi)) {
			buildPoints();
			return;
		}		
		if (match(e, drawFramePoints_mi)) {
			drawFramePoints();
			return;
		}		

		if (match(e, diffProcess_mi)) {
			diffProcess();
			return;
		}		
		if (match(e, computeMagnitudeAndGradiant_mi)) {
			computeMagnitudeAndGradiant();
			return;
		}		
		if (match(e, listPolys_mi)) {
			listPolys(polyList);
			return;
		}		
		if (match(e, filterPolys_mi)) {
			filterPolys();
			return;
		}		
		if (match(e, printPolys_mi)) {
			printPolys();
			return;
		}		
		if (match(e, houghDetectGray_mi)) {
			houghDetect();
			return;
		}		
		if (match(e, grabChild_mi)) {
			grabChild();
			return;
		}		
		if (match(e, houghDetect_mi)) {
			houghDetect();
			return;
		}		
		if (match(e, computeHoughAndDraw_mi)) {
			computeHoughAndDraw();
			return;
		}		
		if (match(e, drawSomeBigPoints_mi)) {
			drawSomeBigPoints();
			return;
		}		
		if (match(e, inverseHoughToRed_mi)) {
			inverseHoughToRed();
			return;
		}		
		if (match(e, copyToChildFrame_mi)) {
			copyToChildFrame();
			return;
		}
		if (match(e, houghEdge_mi)) {
			houghEdge();
			return;
		}
		if (match(e, displayHoughOfRed_mi)) {
		   	displayHoughOfRed();
			return;
		}
		if (match(e, grayPyramid_mi)) {
		   	grayPyramid(kSquare);
			return;
		}
		if (match(e, singlePixelEdge_mi)) {
		   	singlePixelEdge();
			return;
		}
		if (match(e, drawPoly_mi)) {
		   	drawPolys();
			return;
		}
		if (match(e, bugWalk_mi)) {
		   	bugWalk();
			return;
		}
		if (match(e, print_mi)) {
		   	PrintContainer(this);
			return;
		}
		super.actionPerformed( e);
	}
	public void drawFramePoints() {
		String args[] = {""};
		DrawFrame.main(args);
	}
	/**
		edge2HeightField - input and image, output
		x,y,z points. 
	*/
	public void edge2HeightField() {
		int n = 1;
		for (int x=0; x < width; x++)
		  for (int y=0; y < height; y++)
		  	if (r[x][y] == 255)
		  		n++;
		System.out.println("found "+n+" points");
		float pointX[] = new float[n];
		float pointY[] = new float[n];
		float pointZ[] = new float[n];
		for (int x=0,i=0; x < width; x++)
		  for (int y=0; y < height; y++)
		  	if (r[x][y] == 255) {
		  		pointX[i] = x/((float)width);
		  		pointY[i] = y/((float)height);
		  		pointZ[i] = 1f;
		  	}
		 DrawFrame.drawPoints(pointX,pointY,pointZ);		
	}
	
	
	public void grayPyramid(float k[][]) {
		r = dilategs(erodegs(r,k),k);
		r = erodegs(dilategs(r,k),k);
		grayResample(2);
		r = trim(2,2,r);
		copyRedToGreenAndBlue();
		short2Image();
	}
	
private double radiusTable(int x) {
// wrong..measure this!
	double r0 = 1;
	double r1 = 100;
	double t = x/100; //0..1?
	return t*r1 + (1-t) * r0;
}
	
private void buildPoints() {
		int numberOfPoints = 100;
		int X[]=new int[numberOfPoints];
		int Y[]=new int[numberOfPoints];
		int Z[]=new int[numberOfPoints];
		double eps = (1.0/numberOfPoints) * 2*Math.PI;
		int n=0;
		double radius;
	  loop: for (double theta=0; theta < 2*Math.PI; theta+=eps) {
		for (int x=0; x < width; x++) 
		 for (int y=0; y < height; y++) {
		 	if (r[x][y] == 0) continue;
		 	if (n >= numberOfPoints-1) break loop;
		 	n++;
		 	radius = radiusTable(x);
			X[n]=(int)
				((Math.sin(theta)*radius)-256/2);
			Y[n]=y;
			Z[n]= (int)radius; 
		}
		//stepMotorAndDigitize...
		//diffProcess();
		// send me an e-mail if you
		// want to do this....
		// I have serial-port
		// and video digitization
		// drivers in java...
		// This is used for 3D scanners!!
		// Doug Lyon
		// lyon@DocJava.com
		}
		DrawFrame f = new DrawFrame("DrawFrame");
		f.setSize(256,256);
		f.show();
		f.setUpFrame();
		f.setPoints(X,Y,Z);
		f.addFocusListener(f);
		f.repaint();		
}

/**
 * Process a structured illumination based 
 * multi-order diffraction subimage.
 * A turntable is used to rotate the object
 * to be digitized.
 * @version 	1.0 10/22/98
 * @author 	Douglas Lyon
 */
	private void diffProcess() {
		int x1 = 145;
		int y1 = 18;
		int x2 = 215;
		int y2 = 245;
		short threshValue = 106;
		width = x2-x1;
		height = y2-y1;
		NegateFrame nf = subFrame(x1,y1,width,height);
		r = copyArray(nf.r);
		nf.setVisible(false);
		System.out.println("Copy red to green and blue");
		width = width - 1;
		height = height - 1;
		copyRedToGreenAndBlue();
		setSize(width,height);
		lp3();
		Mat.threshold(r,threshValue);
		Mat.threshold(g,threshValue);
		Mat.threshold(b,threshValue);
		skeleton();
		medianSquare2x2();
		medianSquare2x2();
		skeleton();
		thresh();
		wellConditioned();
		short2Image();

	}
	private double cos(double alpha) {
		return 
			Math.cos(alpha *Math.PI/180);
	}
	private double sin(double alpha) {
		return 
			Math.sin(alpha *Math.PI/180);
	}
  	private int atan(int y, int x) {
		return (int) 
		(180*Math.atan2(y,x)/Math.PI);
	}
  //BoundaryFrame child = null;
  public void copyToChildFrame() {
    short _r[][] = new short [width][height];
    short _g[][] = new short [width][height];
    short _b[][] = new short [width][height];
    for (int x=0; x < width ; x++) 
       for (int y=0; y < height; y++) {
    		_r[x][y] = r[x][y];
    		_g[x][y] = g[x][y];
    		_b[x][y] = b[x][y];
       }
    child = new BoundaryFrame(
    		"copy of BoundaryFrame",
    			_r,_g,_b);
 }
 private BoundaryFrame(String title, 
	short _r[][], short _g[][], short _b[][]) {
	super(title);
	r = _r;
	g = _g;
	b = _b;	
	boundaryMenu.add(countourMenu);		
	SpatialFilterMenu.add(boundaryMenu);
	setSize(width, height);
	short2Image();
}
	public BoundaryFrame(String title) {
		super(title);
		boundaryMenu.add(countourMenu);		
		SpatialFilterMenu.add(boundaryMenu);
	}
		int rhoStep = 1;
		int thetaStep = 1;
	
	public void displayHoughOfRed() {
		r = hough();
		width = r.length;
		height = r[0].length;
		setSize(width,height);
		copyRedToGreenAndBlue();
		short2Image();
		show();
	}

	private Point identifyLargestPoint() {
		int max = -1;
		Point p = null;
		for (int x=0; x < width; x++) 
			for (int y=0; y < height; y++) {
				if (g[x][y] > max) {
					max = g[x][y];
					p = new Point(x,y);
				}
			}
		return p;
	}
	public Point[] getTheLargestPoints(int n) {
		Point points[] = new Point[n];
		for (int i=0; i < n; i++) {
			Point p = identifyLargestPoint();
			points[i] = p;
			if (p == null) break;
			g[p.x][p.y] =0;
		}
		return points;
	}
	
	public void drawSomeBigPoints() {
		Point points[] = getTheLargestPoints(4);
		drawThePoints(points);
		drawHoughLines(points);
	}
	
	BoundaryFrame child = null;
	public void computeHoughAndDraw() {

		copyToChildFrame();
		child.displayHoughOfRed();
		Point points[] = child.getTheLargestPoints(40);
		child.linearTransform();
		drawHoughLines(points);
		drawThePoints(points);
		copyRedToGreenAndBlue();
		short2Image(); 
	}


	public void andWithChild() {
		if (child == null) return;
 		for (int i=0; i < width; i++)
 			for (int j=0; j < height; j++) {
 				r[i][j] = min(r[i][j], child.r[i][j]);
 				g[i][j] = min(g[i][j], child.g[i][j]);
 				b[i][j] = min(b[i][j], child.b[i][j]);
 		}
 	}
 	
 	private short min(short m1, short m2) {
 		if (m1 < m2) return m1;
 		return m2;
 	}
 	
	public void drawThePoints(Point points[]) {
		Graphics g = getGraphics();
		g.setColor(Color.white);
		int n = points.length;
		for (int i=0; i < n; i++) {
			Point p = points[i];
			g.drawOval(p.x-5, p.y -5, 10,10);
		}
	}
	public void drawHoughLines(Point points[]) {
		Graphics g = getGraphics();
		g.setColor(Color.white);
		for (int i=0; i < points.length; i++) {
			Point p = points[i];
			int rho = p.x;
			int theta = p.y;
			int x1 = 0;
			int x2 = width;
			int y1 = (int)((rho - cos(theta) * x1) / sin(theta));
			int y2 = (int)((rho - cos(theta) * x2) / sin(theta));
			
			if (theta == 0) {
				x1 = rho;
				x2 = rho;
				y1 = 0;
				y2 = height;
			}
			drawLineRed(x1,y1,x2,y2);
			g.drawLine(x1,y1,x2,y2);
			}
	}

public short [][] hough() {
		int thetaMax = 360;
		int radiusMax= (int)Math.sqrt(width*width + height*height);
		short s[][] = new short[radiusMax][thetaMax];
		for (int x=0; x < r.length; x++) {
			for (int y=0; y < r[0].length; y++) {
				if (r[x][y] == 0) continue;
				drawHoughLine(x,y,s);	
			}
		}
		return s;
	}
public short [][] houghGray2() {
		int thetaMax = 360;
		int radiusMax= (int)Math.sqrt(width*width + height*height);
		short s[][] = new short[radiusMax][thetaMax];
		for (int x=0; x < r.length; x++) {
			for (int y=0; y < r[0].length; y++) {
				if (r[x][y] == 0) continue;
				drawHoughLineGray(x,y,s);	
			}
		}
		return s;
	}
	
	public void drawHoughLine(int x, int y, 
		short s[][]) {
		for (int theta=0; theta < s[0].length; theta++) {
			int rho = (int)(x * cos(theta) + y*sin(theta));
			if (rho >= s.length) continue;
			if (rho < 0) continue;
			s[rho][theta] ++;	
		}
	}
	public void drawHoughLineGray(int x, int y, 
		short s[][]) {
		int theta = atan(g[x][y],b[x][y]);
		if (theta < 0) return;
		if (theta > s[0].length) return;
		int rho = (int)(x * cos(theta) + y*sin(theta));
		if (rho >= s.length) return;
		if (rho < 0) return;
		s[rho][theta] ++;	
	}

	public void houghEdge() {
		copyToChildFrame();
		child.displayHoughOfRed();
		child.unahe();
		child.copyToChildFrame();
		child.child.thresh();
		andHough(child.child);
		short2Image();
	}
	public void houghDetect() {
		copyToChildFrame();
		child.displayHoughOfRed();
		inverseHough();
	}

	public void andHough(BoundaryFrame bf) {
		int thetaMax = 360;
		int radiusMax = (int)Math.sqrt(width*width + height*height);
		short s[][] = new short[radiusMax][thetaMax];
		System.out.println("hough computing");

		for (int x=0; x < r.length; x++) 
			for (int y=0; y < r[0].length; y++) {
				if (r[x][y] == 0) continue;
				int theta = atan(y,x);
				if (theta < 0) continue;
				if (theta >=360) continue;
				int rho = (int) ( x * cos(theta) + y*sin(theta));
				if (rho >= bf.width) continue;
				if (rho < 0) continue;
				if (bf.r[rho][theta] == 0) 
					r[x][y] = 0;	
			}		
	}
	
	
	public void inverseHoughToRed() {
		inverseHoughToRed(rhoStep, thetaStep);
		copyRedToGreenAndBlue();
		short2Image();	
	}
	public void inverseHough() {
		Point points[] = child.getTheLargestPoints(10);
		child.linearTransform();
		copyToChildFrame();
		child.clear();
		System.out.println("About to draw hough lines");
		child.drawHoughLines(points);
		System.out.println("Anding with child");
		andWithChild();
		copyRedToGreenAndBlue();
		short2Image(); 
		show();
	}

	private  void inverseHoughToRed(int rhoStep, int thetaStep) {
		for (int hx=0; hx < g.length; hx++)
			for (int hy=0; hy<g[0].length; hy++) {
				if (g[hx][hy] == 0) continue;
				int x1=0;
				int x2=width-1;
				int theta = hy * thetaStep;
				int rho = hx * rhoStep;
				double cosTheta = cos(theta);
				double sinTheta = sin(theta);
				int y1 = clip((rho - x1 * cosTheta) / sinTheta);
				int y2 = clip((rho - x2 * cosTheta) / sinTheta);
				drawLineRed(x1,y1,x2,y2);
			}
	}
	

	public void drawLineRed2(int x1, int y1, int x2, int y2) {
		double dx = Math.abs(x1-x2);
		double dy = Math.abs(y1-y2);
		double length = 2*Math.sqrt(dx*dx + dy*dy);
		double eps = 1/length;
		for (double t =0; t < 1 ; t=t+eps) {
			int x = (int)((1-t)*x1 + t * x2);
			int y = (int)((1-t)*y1 + t * y2);
			if (x >= width) continue;
			if (y >= height) continue;
			if (x < 0) continue;
			if (y < 0) continue;
			
			r[x][y] = 255;
		}
	}
	private int sign(int x) {
		if (x==0) return 0;
		if (x < 0) return -1;
		return 1;
	}
	
	public void grabChild() {
		MorphFrame child = (MorphFrame)super.child;
		if (child == null) {
			System.out.println("Child is null");
			return;
		}
 		width = child.width;
 		height = child.height;
 		r=child.r;
 		g=child.g;
 		b=child.b;
 		short2Image();
	}

// Bresenham's Algorithm;	
// Adapted from Newman and Sproull, 
// "Principles of Interactive Computer Graphics"
// and
// Graphics Gems Vol. 1, pp 99 by
// Paul S. Heckbert.
public void drawLineRed(int x1, int y1,int x2, int y2) {
    int deltaX = x2-x1;  
    int deltaY = y2-y1; 
    int absDeltaX = Math.abs(deltaX);
    int absDeltaY = Math.abs(deltaY);
    int absDeltaX2 = absDeltaX*2;  
    int absDeltaY2 = absDeltaY*2;  
    int sx = sign(deltaX);
    int sy = sign(deltaY);

    int x = x1;
    int y = y1;
    // e is the error
    int e = 0;
    
    if (absDeltaX2>absDeltaY2) {
		e = absDeltaY2-absDeltaX;
		while (true) {
			setPel(x,y);
			if (x == x2) return;
	    	if (e>=0) {
				y += sy;
				e -= absDeltaX2;
	    		}
	    	x += sx;
	    	e += absDeltaY2;
		}
    }
	e = absDeltaX2-absDeltaY;
	while (true) {
	    setPel(x,y);
	    if (y == y2) return;
	    if (e>=0) {
			x += sx;
			e -= absDeltaY2;
	    }
	    y += sy;
	    e += absDeltaX2;
	}
}

private void setPel(int x,int y) {
	if (x < 0) return;
	if (x >= r.length) return;
	if (y < 0) return;
	if (y >= r[0].length) return;
	r[x][y] = 255;
	g[x][y] = 255;
	b[x][y] = 255;
}

	public void testDrawLineRed() {
		clear();
		drawLineRed(0,0,width,height);
		drawLineRed(width,0,0,height);		
		drawLineRed(width/2,height/2,0,height/2);
		copyRedToGreenAndBlue();
		short2Image();
	}

	private int clip(double y) {
		if (y > height) return height -1;
		if (y < 0) return 0;
		return (int)y;
	} 

	public short [][] trim(int dx, int dy, short s[][]) {
		int nw = s.length-2*dx;
		int nh = s[0].length-2*dy;
		short ns[][] = new short[nw][nh];
		for (int x=dx; x < width-dx; x++)
			for (int y=dy; y < height-dy; y++)
				ns[x-dx][y-dy] = s[x][y];
		width = nw;
		height = nh;
		return ns;
	}
	private void grayResample(int ratio) {
	 	width = width/2;
 		height = height/2;
 		r = resampleArray(r,2);
 	}	

	private Point getNextNonZeroPoint() {
		for (int x=1; x <width-1; x++) 
			for (int y=1; y < height-1; y++)
				if (r[x][y] != 0) return 
					new Point(x,y);
		return null;
	}

	public static void PrintContainer(Container c) {
		Toolkit tk = Toolkit.getDefaultToolkit();
			PrintJob printJob = 
		    	tk.getPrintJob(
		    		(Frame) c,
		    		"print me!",
		    		null);
			Graphics g = printJob.getGraphics();
			c.paint(g);
			printJob.end();
	}
	

	public static void main(String args[]) {
		BoundaryFrame bf = new BoundaryFrame("BoundaryFrame");
		bf.testDrawLineRed();
	}
	private void clearRedPlane() {
		r = new short[width][height];
	}
	private void clear() {
		r = new short[width][height];
		g = new short[width][height];
		b = new short[width][height];
	}

  
	
	private int pointCount() {
		int i = 0;
      	for (int x=0; x < width; x++) 
        	for (int y=0; y < height; y++)
        		if (r[x][y] !=0) i++;
        return i;
	}
	
  private boolean isAdjacent(Point p1, Point p2) {
    	double dx = p1.x - p2.x;
    	double dy = p1.y - p2.y;
    	double r = dx * dx + dy * dy;    
    	return r <= 1;
	} 

private void computeMagnitudeAndGradiant() {
	for (int x=1; x < width - 1; x++) 
		for (int y=1; y < height-1; y++) 
			computeGradient(x,y);
	for (int x=1; x < width - 1; x++) 
		for (int y=1; y < height-1; y++) 
				r[x][y] = (short)Math.sqrt(
					g[x][y]*g[x][y] + b[x][y]*b[x][y]);
	short2Image();
}
// abc
// hid
// gfe
private void computeGradient(int x, int y) {
	int a1, b1, c1, d1, e1, f1, h1, i1, g1;
	a1 = r[x-1][y+1];
	b1 = r[x][y+1];
	c1 = r[x+1][y+1];
	d1 = r[x+1][y];
	e1 = r[x+1][y-1];
	f1 = r[x][y-1];
	g1 = r[x-1][y-1];
	h1 = r[x-1][y];
	i1 = r[x][y];

	g[x][y] = (short)((c1-g1) + (e1-a1) + 2 * (d1-h1));
	b[x][y] = (short)((c1-g1) + (a1-e1) + 2 * (b1-f1));	
}


private void singlePixelEdge() {
	colorPyramid(kSquare);
	show();
	thresh();
	outsideContour(kCross);
}
public void bugWalk() {
	int n = 0;
	polyList = new Vector();
	System.out.println("number of points = "+pointCount());
	Timer t = new Timer();
	t.start();
    for (int x=1; x < width-1; x++) 
       for (int y=1; y < height-1; y++)
          if (r[x][y] !=0) 
        		buildPolygonList(x,y);
     t.print("poly done!");  			
     polyStats();
     filterPolys();
}
private void addLine(int x1, int y1, int x2, int y2) {
		Polygon p = new Polygon();
		p.addPoint(x1,y1);
		p.addPoint(x2,y2);
		polyList.addElement(p);
}
private void buildPolygonList(int x,int y) {
	Polygon p = new Polygon();
	p.addPoint(x,y);
	r[x][y]=0;
	if (r[x+1][y] != 0) {
		buildPolygonList(x+1,y,p);
		polyList.addElement(p);
		return;
	}
	if (r[x+1][y+1] != 0) {
		buildPolygonList(x+1,y+1,p);
		polyList.addElement(p);
		return;
	}
	if (r[x][y+1] != 0) {
		buildPolygonList(x,y+1,p);
		polyList.addElement(p);
		return;
	}
	
}
private void buildPolygonList(int x,int y,Polygon p) {
	p.addPoint(x,y);
	r[x][y]=0;
	try {
		if (r[x+1][y] != 0) {
			buildPolygonList(x+1,y,p);
			return;
		}
		if (r[x+1][y+1] != 0) {
			buildPolygonList(x+1,y+1,p);
			return;
		}
		if (r[x][y+1] != 0) {
			buildPolygonList(x,y+1,p);
			return;
		}
	}
	catch (Exception e) {
	};
}
public void printPolys() {
	Toolkit tk = Toolkit.getDefaultToolkit();
	PrintJob printJob = 
		tk.getPrintJob(
			new Frame(),
		    "print me!",
		    null);
	Graphics g = printJob.getGraphics();
	Polygon p;
	for (int i=0; i < polyList.size(); i++) {
			p = (Polygon)polyList.elementAt(i);
			g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
	}
	printJob.end();
}

public void listPolys(Vector v) {
	for (int i=0; i < polyList.size(); i++) 
		printPoly((Polygon)polyList.elementAt(i));
}
public void filterPolys() {
	Timer t = new Timer();
	t.start();
	Polygon p =  (Polygon)polyList.elementAt(0);
	Polygon pp;
	Vector newPolys = new Vector();
	newPolys.addElement(p);
	polyList.removeElementAt(0);
	while( polyList.size() >0) {
		int i = nextClosestPoly(p);
		pp = (Polygon)polyList.elementAt(i);
		polyList.removeElementAt(i);
		pp = thinPoly(pp); // implement this for hw!
		//if (combinePolys(p,pp)) continue;
		
		newPolys.addElement(pp);
	}
	polyList = newPolys;	
	t.print("Polys ordered");
	polyStats();
	drawPolys();
	//listPolys(polyList);
}

// here is the basic idea...thin poly
// can you order the polys?
public Polygon thinPoly(Polygon p) {
	if (p.npoints <=2) return p;
	Polygon pp = new Polygon();
	int x0 = p.xpoints[0];
	int y0 = p.ypoints[0];
	int x1 = p.ypoints[1];
	int y1 = p.ypoints[1];
	pp.addPoint(x0,y0);
	for (int i = 1; i < p.npoints-1; i++) {
		int xi = p.xpoints[i];
		int yi = p.ypoints[i];
		if (onLine(x0,y0,x1,y1,xi,yi)&&nextTo(x1,y1,xi,yi)) {
			//System.out.println("onLine:\n"
			//	+x0+","+y0+","+x1+","+y1+","+xi+","+yi);
			x1 = xi;
			y1 = yi;
			continue;
		}		
		pp.addPoint(p.xpoints[i-1],p.ypoints[i-1]);
		x0 = p.xpoints[i-1];
		y0 = p.ypoints[i-1];
		x1 = xi;
		y1 = yi;
	}
	pp.addPoint(p.xpoints[p.npoints-1],p.ypoints[p.npoints-1]);
	return pp;
}

private boolean nextTo(
	int x0, int y0, 
	int x1, int y1) {
	int dx = x1 - x0;
	int dy = y1 - y0;
	return Math.sqrt(dx*dx+dy*dy) < 2;
}
public boolean onLine(
	int x0, int y0, 
	int x1, int y1, 
	int x2, int y2) {
	int dx = x1 - x0;
	int dy = y1 - y0;
	int dx2 = x2 - x0;
	int dy2 = y2 - y0;
	return Math.abs(dy*dx2-dy2*dx) <
		max(Math.abs(dx),Math.abs(dy));
}
private double max(int x, int y) {
	if (x > y) return x;
	return y;
}
private void printPoly(Polygon p) {
	System.out.println("i x y");
	int n = p.npoints;
	for (int i =0; i < n - 1; i++) 
		System.out.println(i+" "+p.xpoints[i]+" "+p.ypoints[i]);
	System.out.println(n-1+" "+p.xpoints[n-1]+" "+p.ypoints[n-1]);
}
private boolean goodTail(
	int x0, int y0, 
	int x1, int y1, 
	int xi, int yi,
	int dx, int dy) {
	if (!goodSlope(x0,y0,x1,y1,xi,yi,dx,dy)) return false;
	return isAdjacent(x0,y0,x1,y1,xi,yi);
}

private boolean isAdjacent(
	int x0, int y0, 
	int x1, int y1, 
	int xi, int yi) {
		int dx = x1 - xi;
		int dy = y1 - yi;
		return Math.sqrt(dx*dx+dy*dy) < 2;
}

private boolean goodSlope(
	int x0, int y0, 
	int x1, int y1, 
	int xi, int yi,
	int dx, int dy) {
	double eps = 0.1;
	int dx2 = xi - x0;
	int dy2 = yi - y0;
	if (dx == 0 && dx2 == 0) return true;
	if ((dx != 0) &&( dx2 != 0)) {
		return (Math.abs((dy/dx) - (dy2/dx2))  < eps);
	}
	return false;
}

public int nextClosestPoly(Polygon p) {
	double dMin = 10000;
	int next = 0;
	for (int i=0; i < polyList.size(); i++) {
		Polygon pp = (Polygon) polyList.elementAt(i);
		if (distance(p,pp) < dMin) {
			dMin = distance(p,pp);
			next = i;
		}
	}
	return next;
}

public boolean combinePolys(Polygon p1, Polygon p2) {
	if (distance(p1,p2) < 2) {
		for (int i=0; i < p2.npoints; i++) 
			p1.addPoint(p2.xpoints[i],p2.ypoints[i]);
		return true;
	}
	return false;
}

public double distance(Polygon p1, Polygon p2) {
	double x1 = p1.xpoints[p1.npoints-1];
	double y1 = p1.ypoints[p1.npoints-1];
	double x2 = p2.xpoints[0];
	double y2 = p2.ypoints[0];
	double dx = x1 - x2;
	double dy = y1 - y2;
	return Math.sqrt(dx*dx + dy * dy);
}
public void polyStats() {
	int numberOfPolys = polyList.size();
	Polygon p;
	int n = 0;
	int maxN = 0;
	int minN = 10000;
	for (int i=0; i < numberOfPolys; i++) {
		p= (Polygon)polyList.elementAt(i);
		n += p.npoints;
		if (p.npoints > maxN) maxN = p.npoints;
		if (p.npoints < minN) minN = p.npoints;
	}
	int avgSize = n / numberOfPolys;
	System.out.println("numberOfPolys="+numberOfPolys);
	System.out.println("numberOfPoints="+n);
	System.out.println("avgSize="+avgSize);
	System.out.println("maxN="+maxN);
	System.out.println("minN="+minN);	
}
public void drawPoly(Polygon p) {
	Graphics g = getGraphics();
	g.setColor(Color.green);
	g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
}
public void drawPolys() {
	Graphics g = getGraphics();
	g.setColor(Color.green);
	Polygon p;
	for (int i=0; i < polyList.size(); i++) {
		p = (Polygon)polyList.elementAt(i);
		g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
	}
}

}