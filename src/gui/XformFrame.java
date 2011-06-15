package gui;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

public class XformFrame extends ColorFrame {

 public AffineFrame af = null;


 Menu xformMenu = new Menu("Xform");
 MenuItem showAffineFrame_mi =
		addMenuItem(xformMenu,"[T-A]Show affine frame..");

 Menu turnMenu = new Menu("Turn");
 MenuItem turn90_mi = 
	addMenuItem(turnMenu,"[E-9]0 turn and back");
 MenuItem turn180_mi = 
	addMenuItem(turnMenu,"[T-1] turn 90 increments");
 MenuItem mirror_mi = 
	addMenuItem(turnMenu,"[T-m] mirror");
 MenuItem rotate_mi = 
	addMenuItem(turnMenu,"rotate..."); 

 MenuItem html_mi =
 	addMenuItem(xformMenu, "[T-h]tml generator...");
 	 
  private XformFrame(String title, 
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
    child = new XformFrame(
    		"XformFrame",
    			_r,_g,_b);
 }	

 public void actionPerformed(ActionEvent e) {
 	String args[] = {""};
 	
 	if (match(e, mirror_mi)) {
    	mirror(); 
		return;
	}
 	if (match(e, showAffineFrame_mi)) {
    	showAffineFrame(); 
		return;
	}
	if (match(e, html_mi)) {
    	htmlconverter.HtmlGenerator.main(args); 
		return;
	}
	if (match(e, turn180_mi)) {
    	turn180(); 
		return;
	}
	if (match(e, turn90_mi)) {
    	turn90(); 
		return;
	}
	if (match(e, rotate_mi)) {
    	rotate(); 
		return;
	}	
	super.actionPerformed(e);  

 }
 
 	public void setPose(double theta, double sx, double sy) {
		Mat3 tr1 = new Mat3();
		Mat3 tr2 = new Mat3();
		Mat3 rt = new Mat3();
		Mat3 sc = new Mat3();
		Mat3 at;
		int xc = width/2;
		int yc = height/2;
		tr1.setTranslation(xc,yc);
		sc.setScale(sx,sy);
		rt.setRotation(theta);
		tr2.setTranslation(-xc,-yc);
		at = tr1.multiply(rt);
		at = at.multiply(sc);
		at = at.multiply(tr2);
		xform(at);
	}
 	public void setPoseFeedback(double theta, double sx, double sy) {
		Mat3 tr1 = new Mat3();
		Mat3 tr2 = new Mat3();
		Mat3 rt = new Mat3();
		Mat3 sc = new Mat3();
		Mat3 at;
		int xc = width/2;
		int yc = height/2;
		tr1.setTranslation(xc,yc);
		sc.setScale(sx,sy);
		rt.setRotation(theta);
		tr2.setTranslation(-xc,-yc);
		at = tr1.multiply(rt);
		at = at.multiply(sc);
		at = at.multiply(tr2);
		xformFeedback(at);
	}
	public void turn(double angle) {
		setPose(angle,1,1);
	}
	public void turnFeedback(double angle) {
		setPoseFeedback(angle,1,1);
	}
public void mirror() {
	turn90();
	turn180();
}	
public void turn90() {
    short ro[][] = new short[r[0].length][r.length];
    short go[][] = new short[r[0].length][r.length];
    short bo[][] = new short[r[0].length][r.length];
    for(int x=0; x<r.length; x++)
       for(int y=0; y<r[0].length; y++) {
          ro[y][x] = r[x][y];
          go[y][x] = g[x][y];
          bo[y][x] = b[x][y];       
    }
    r=ro;
    g=go;
    b=bo;
	height = r.length;
	width = r[0].length;
	short2Image();
}

public void turn180() {
    short ro[][] = new short[r[0].length][r.length];
    short go[][] = new short[r[0].length][r.length];
    short bo[][] = new short[r[0].length][r.length];
    for(int y=0,k=r[0].length-1; y<r[0].length; y++,k--)
    	for(int x=0; x<r.length; x++) {
          ro[k][x] = r[x][y];
          go[k][x] = g[x][y];
          bo[k][x] = b[x][y];              
    	}
    r=ro;
    g=go;
    b=bo;
	height = r.length;
	width = r[0].length;
	short2Image();
}
	
 	public void showAffineFrame() {
		Rectangle r = getBounds();
		Dimension d = r.getSize();
		af = new AffineFrame("Affine Frame",this,width,height);
		af.setLocation(d.width, d.height);
		af.setSize(150,150);
		af.setVisible(true);
	}

 /* fast invert map with maple....
 // [x y] = [uv u v 1] * A;
 // using destination scanning.
 with(linalg):
   readlib(C):
   b:=vector([u*v, u, v,1]):
   a:=array(0..3,0..1,[]): 
   c:=multiply(b,matrix(a)):
   C(c,optimized);
*/
 public Point invertMap(double a[][], double u, double v){
 	double t1 = u*v;
 	double c[] = new double[2];
    c[0] = t1*a[0][0]+u*a[1][0]+v*a[2][0]+a[3][0];
    c[1] = t1*a[0][1]+u*a[1][1]+v*a[2][1]+a[3][1];
    return interpolateCoordinates(c);
 }
 // the dumb interpolation...
 // This should be improved. DL...8/18/98
 public Point interpolateCoordinates(double c[]) {
 	return new Point((int)c[0],(int)c[1]);	
 }
  public void applyAffineFrame2() {
  }
  
  public Mat3 infer3PointA(Polygon sp, Polygon dp) {
  // D3 is destination
  // S3 is source
  double s3 [][] ={
  	{sp.xpoints[0],sp.xpoints[1],sp.xpoints[2]},
  	{sp.ypoints[0],sp.ypoints[1],sp.ypoints[2]},
  	{1            ,            1,           1}};
  double d3 [][] ={
  	{dp.xpoints[0],dp.xpoints[1],dp.xpoints[2]},
  	{dp.ypoints[0],dp.ypoints[1],dp.ypoints[2]},
  	{1            ,            1,           1}};
  Mat3 d3Mat = new Mat3(d3);
  Mat3 s3Mat = new Mat3(s3);
  Mat3 s3MatInverse = s3Mat.invert();
  Mat3 a = d3Mat.multiply(s3MatInverse);
  return a;
 }
 
  public double[][] infer4PointA(Polygon sp, Polygon dp) {
  // D is destination
  // S is source
  int xd[] = dp.xpoints;
  int yd[] = dp.ypoints;
  int xs[] = sp.xpoints;
  int ys[] = sp.ypoints;

  // d4 is a 2x4
  double d4 [][] ={
  	{xd[0],xd[1],xd[2],xd[3]},
  	{yd[0],yd[1],yd[2],yd[3]},
  };
  // s4 is a 4x4
  double s4[][]={
  	{      xs[0],       xs[1],       xs[2],       xs[3]},
  	{      ys[0],       ys[1],       ys[2],       ys[3]},
  	{xs[0]*ys[0], xs[1]*ys[1], xs[2]*ys[2], xs[3]*ys[3]},
  	{          1,           1,           1,           1},
  };
  Mat4 s4Mat = new Mat4(s4);
  Mat4 s4MatInverse = s4Mat.invert();
  // 2x4*4x4 = 2x4
  double [][] a = s4MatInverse.multiply2x4(d4);
  return a;
 }
 
 // select one of two roots
 // for a x**2 + bx + c
 public double quadraticRoot(double a, double b, double c) {
 	if (a == 0) a = 0.00001;
 	double sqrtArg = b*b-4*a*c;
 	double aa = 2*a;
 	if (sqrtArg < 0) return -b/aa; // ignore imaginary part.
 	double root1 = (-b + Math.sqrt(sqrtArg))/aa;
 	double root2 = (-b - Math.sqrt(sqrtArg))/aa;
 	if ((root1 >= 0) && (root1 < height)) return root1;
 	if ((root2 >= 0) && (root2 < height)) return root2;
 	if (root1 > height) return height;
 	return 0;
 }
 
 /*
 eq1:=xp=(a[0,0]*x+a[0,1]*y+a[0,2]*x*y+a[0,3]);
 eq2:=yp=(a[1,0]*x+a[1,1]*y+a[1,2]*x*y+a[1,3]);
 solve({eq1,eq2},{x,y});
*/
 public double[] inverseMap4(double a[][],double xp,double yp) {
 	double as =
 		-a[1][1]*a[0][2]
 		+a[1][2]*a[0][1];
 	double b =
 		 a[0][2]*yp + a[1][0]*a[0][1] - a[0][0]*a[1][1]
 		-a[1][2]*xp + a[1][2]*a[0][3] - a[0][2]*a[1][3];
 	double c =yp*a[0][0] 
 	           - a[1][0]*xp 
 	           + a[1][0]*a[0][3]
 	            -a[1][3]*a[0][0];
 	double y = quadraticRoot(as,b,c);
 	double x = 
 		(xp-a[0][1]*y-a[0][3])/(a[0][0]+a[0][2]*y);
 	double p[] = {x,y};
 	return p;
 }

 public void applyBilinear4Points(
	Polygon sourcePoly, Polygon destPoly) {
	double a[][];
	try {
	 	a = infer4PointA(
 		sourcePoly,
 		destPoly);
 		inverseBilinearXform(a);
 		}
 	catch (ArithmeticException e) {
 	System.out.println("error in user input, trying 3 point transform");
 	 xform(
 	 	infer3PointA(sourcePoly,destPoly));
 	}
 	
 }
 public void applyBilinear4PointsFeedback(
	Polygon sourcePoly, Polygon destPoly) {
	double a[][];
	 	a = infer4PointA(
 		sourcePoly,
 		destPoly);
 		inverseBilinearXformfeedback(a);	
 }
 public void applyBilinear4Points() {
 	Polygon sourcePoly = new Polygon();
 	// p0  p1
 	// p3  p2
 	sourcePoly.addPoint(    0,      0);
 	sourcePoly.addPoint(width,      0);
 	sourcePoly.addPoint(width, height);
 	sourcePoly.addPoint(    0, height);
 	Polygon destPoly = af.getPolygon();
 	applyBilinear4Points(sourcePoly,destPoly);
 }
 public void applyBilinear4PointsFeedback() {
 	Polygon sourcePoly = new Polygon();
 	// p0  p1
 	// p3  p2
 	sourcePoly.addPoint(    0,      0);
 	sourcePoly.addPoint(width,      0);
 	sourcePoly.addPoint(width, height);
 	sourcePoly.addPoint(    0, height);
 	Polygon destPoly = af.getPolygon();
 	applyBilinear4PointsFeedback(sourcePoly,destPoly);
 }
 
 public void inverseBilinearXform(double a[][]) {
 	int w = width;
 	int h = height;
 	short rn[][] = new short[w][h];
 	short gn[][] = new short[w][h];
 	short bn[][] = new short[w][h];
 	double p[] = new double [2];
 	int red, green, blue;
 	int xp, yp, i, j;
 	for (int x = 0; x < w; x++)
   		for (int y=0; y < h; y++) {
 			p=inverseMap4(a,x,y);
 			xp = (int) (p[0]);
 			yp = (int) (p[1]);
 	 		if ((xp < w-1) && (yp < h-1) && (xp > 0) && (yp > 0)) {
 	  			rn[x][y] = r[xp][yp];
 	  			gn[x][y] = g[xp][yp];
 	  			bn[x][y] = b[xp][yp];
 			}
 		}
 	r = rn;
 	g = gn;
 	b = bn;
 	short2Image();
}
public void colorize() {
	TransformTable tt = new TransformTable(256);
	tt.randomize();
	short lut[] = tt.getLut();
	for (int y=0;y<height;y++)
		for (int x=0;x<width;x++) {
			r[x][y] = lut[r[x][y]];
			g[x][y] = r[x][y];
			b[x][y] = r[x][y];
		}
	short2Image();	
}

public void zedSquare() {
 int w = width;
 int h = height;
 int xc = w/2;
 int yc = h/2;
 short rn[][] = new short[width][height];
 short gn[][] = new short[width][height];
 short bn[][] = new short[width][height];
 double p[] = new double [2];
 int red, green, blue;
 int xp, yp, i, j;
 double R = Math.sqrt(w*w/4+h*h/4);
 for (int x = 0; x < w; x++)
   for (int y=0; y < h; y++) {
   	double dx = x-xc;
   	double dy = y-yc;
   	double radius = Math.sqrt(dx*dx + dy*dy);
   	double a = (180/Math.PI)*Math.atan2(dy,dx);


	    a = a*2;
	    radius = radius*radius/R;
	
	
	a = a * Math.PI/180;

	p[0] = radius *Math.cos(a);
	p[1] = radius *Math.sin(a);
 	xp = (int) p[0]+xc;
 	yp = (int) p[1]+yc;
 	if ((xp < w) && (yp < h) && (xp >= 0) && (yp >= 0)) {
 	  rn[x][y] = r[xp][yp];
 	  gn[x][y] = g[xp][yp];
 	  bn[x][y] = b[xp][yp];
 	}
 }
 r = rn;
 g = gn;
 b = bn;
 short2Image();
 }
 
 public void zedSquare(float dilate) {
 int w = width;
 int h = height;
 int xc = w/2;
 int yc = h/2;
 short rn[][] = new short[width][height];
 short gn[][] = new short[width][height];
 short bn[][] = new short[width][height];
 double p[] = new double [2];
 int red, green, blue;
 int xp, yp, i, j;
 double R = Math.sqrt(w*w/4+h*h/4);
 for (int x = 0; x < w; x++)
   for (int y=0; y < h; y++) {
   	double dx = x-xc;
   	double dy = y-yc;
   	double radius = Math.sqrt(dx*dx + dy*dy);
   	double a = (180/Math.PI)*Math.atan2(dy,dx);


	    a = a*2;
	    radius = radius*radius/R;
	
	
	a = a * Math.PI/180;
	radius=dilate*radius;

	p[0] = radius *Math.cos(a);
	p[1] = radius *Math.sin(a);
 	xp = (int) p[0]+xc;
 	yp = (int) p[1]+yc;
 	if ((xp < w) && (yp < h) && (xp >= 0) && (yp >= 0)) {
 	  rn[x][y] = r[xp][yp];
 	  gn[x][y] = g[xp][yp];
 	  bn[x][y] = b[xp][yp];
 	}
 }
 r = rn;
 g = gn;
 b = bn;
 short2Image();
 }


 public void inverseBilinearXformfeedback(double a[][]) {
 	int w = width;
 	int h = height;
 	double p[] = new double [2];
 	int red, green, blue;
 	int xp, yp, i, j;
 	double startPoint[] = inverseMap4(a,0,0);
 	double endPoint[] = inverseMap4(a,w-1,h-1);
 	int x1 = (int) startPoint[0];
 	int y1 = (int) startPoint[1];
 	int x2 = (int) endPoint[0];
 	int y2 = (int) endPoint[1];
 	for (int x = x1; x < x2; x++)
   		for (int y=y1; y < y2; y++) {
 			p=inverseMap4(a,x,y);
 			xp = (int) (p[0]);
 			yp = (int) (p[1]);
 	 		if ((xp < w-1) && (yp < h-1) && (xp > 0) && (yp > 0)) {
 	  			r[x][y] = r[xp][yp];
 	  			g[x][y] = g[xp][yp];
 	  			b[x][y] = b[xp][yp];
 			}
 		}
 	short2Image();
}

 public void applyAffineFrameThreePoints() {
 	Polygon sourcePoly = new Polygon();
 	sourcePoly.addPoint(0,0);
 	sourcePoly.addPoint(width,0);
 	sourcePoly.addPoint(width,height);
 	xform(
 	 	infer3PointA(sourcePoly,
 	 		af.getPolygon()));
 }

 public void rotate() {
    String prompts[] = {
    		"angle (degs):"
    };
    	
    String defaults[] = {"0.0"};
    String title = "Rotation Dialog";
    
 	RotoLog dd = 
 		new RotoLog(
 			this,
 			"Rotation dialog",
 			prompts,
 			defaults,9); 
 }
public void scale(int scale) { 
	int d = 0;
	int nw = width * scale;
	int nh = height*scale;
	short sr[][] = new short[nw][nh];
	short sg[][] = new short[nw][nh];
	short sb[][] = new short[nw][nh];
		for (int y=0; y < height; y++)
			for (int j=0; j < nw; j++)
				for (int x =0 ; x < width; x++)
					for (int i=0; i < nh; i++) {
						sr[i][j] = r[x][y];
						sg[i][j] = g[x][y];
						sb[i][j] = b[x][y];
					}
	r = sr;
	g = sg;
	b = sb;
	short2Image();		
}

public XformFrame(String title) {
	super(title);	
	MenuBar menuBar = getMenuBar();
	xformMenu.add(turnMenu);
	menuBar.add(xformMenu);
	setMenuBar(menuBar);	
 }
 public static void main(String args[]) {
 	String title = "Kahindu by D. Lyon";
 	if (args.length == 1) 
 		title = args[0];
	XformFrame xf = 
		new XformFrame(title);
	xf.setVisible(true);
 }
 public void fishEye() {
 	fishEye(width/2, height/2,2.1);
 }
 public void fishEye(double gamma) {
 	fishEye(width/2, height/2,gamma);
 }
 
public void fishEye(int xc, int yc, double gamma) {
 int w = width;
 int h = height;
 short rn[][] = new short[width][height];
 short gn[][] = new short[width][height];
 short bn[][] = new short[width][height];
 double p[] = new double [2];
 int red, green, blue;
 int xp, yp, i, j;
 double R = Math.sqrt(w*w/4+h*h/4);
 for (int x = 0; x < w; x++)
   for (int y=0; y < h; y++) {
   	double dx = x-xc;
   	double dy = y-yc;
   	double radius = Math.sqrt(dx*dx + dy*dy);
   	// From [Holzmann] pp. 60
   	double u = Math.pow(radius,gamma)/R;
   	double a = Math.atan2(dy,dx);

	p[0] = u *Math.cos(a);
	p[1] = u *Math.sin(a);
 	xp = (int) p[0]+xc;
 	yp = (int) p[1]+yc;
 	if ((xp < w) && (yp < h) && (xp >= 0) && (yp >= 0)) {
 	  rn[x][y] = r[xp][yp];
 	  gn[x][y] = g[xp][yp];
 	  bn[x][y] = b[xp][yp];
 	}
 }
 r = rn;
 g = gn;
 b = bn;
 short2Image();
 }
 
 public void polarTransform() {
 int w = width;
 int h = height;
 int xc = w/2;
 int yc = h/2;
 short rn[][] = new short[width][height];
 short gn[][] = new short[width][height];
 short bn[][] = new short[width][height];
 double p[] = new double [2];
 int red, green, blue;
 int xp, yp, i, j;
 for (int x = 0; x < w; x++)
   for (int y=0; y < h; y++) {
   	double dx = x-xc;
   	double dy = y-yc;
   	double radius = Math.sqrt(dx*dx + dy*dy);
   	double a = (180/Math.PI)*Math.atan2(dy,dx);

    a = a + radius;

	a = a * Math.PI/180;
	p[0] = radius *Math.cos(a);
	p[1] = radius *Math.sin(a);
 	xp = (int) p[0]+xc;
 	yp = (int) p[1]+yc;
 	if ((xp < w) && (yp < h) && (xp >= 0) && (yp >= 0)) {
 	  rn[x][y] = r[xp][yp];
 	  gn[x][y] = g[xp][yp];
 	  bn[x][y] = b[xp][yp];
 	}
 }
 r = rn;
 g = gn;
 b = bn;
 short2Image();
 }

  public void polarTransform(double t,double ta) {
 int w = width;
 int h = height;
 int xc = w/2;
 int yc = h/2;
 short rn[][] = new short[width][height];
 short gn[][] = new short[width][height];
 short bn[][] = new short[width][height];
 double p[] = new double [2];
 int red, green, blue;
 int xp, yp, i, j;
 for (int x = 0; x < w; x++)
   for (int y=0; y < h; y++) {
   	double dx = x-xc;
   	double dy = y-yc;
   	double radius =  (t*2)*Math.sqrt(dx*dx + dy*dy);
   	double a = (180/Math.PI)*Math.atan2(dy,dx);

    a = a + radius;

	a = ta*a * Math.PI/180;
	p[0] = radius *Math.cos(a);
	p[1] = radius *Math.sin(a);
 	xp = (int) p[0]+xc;
 	yp = (int) p[1]+yc;
 	if ((xp < w) && (yp < h) && (xp >= 0) && (yp >= 0)) {
 	  rn[x][y] = r[xp][yp];
 	  gn[x][y] = g[xp][yp];
 	  bn[x][y] = b[xp][yp];
 	}
 }
 r = rn;
 g = gn;
 b = bn;
 short2Image();
 }
 
public void sqrt() {
	sqrt(1.0);
}
public void sqrt(double t) {
 int w = width;
 int h = height;
 int xc = w/2;
 int yc = h/2;
 short rn[][] = new short[width][height];
 short gn[][] = new short[width][height];
 short bn[][] = new short[width][height];
 double p[] = new double [2];
 int red, green, blue;
 int xp, yp, i, j;
 double R = Math.sqrt(w*w/4+h*h/4);
 for (int x = 0; x < w; x++)
   for (int y=0; y < h; y++) {
   	double dx = x-xc;
   	double dy = y-yc;
   	double radius = t*Math.sqrt(dx*dx + dy*dy);
   	double a = (180/Math.PI)*Math.atan2(dy,dx);
    radius = Math.sqrt(radius*R);

	a = t*a * Math.PI/180;
	p[0] = radius *Math.cos(a);
	p[1] = radius *Math.sin(a);
 	xp = (int) p[0]+xc;
 	yp = (int) p[1]+yc;
 	if ((xp < w) && (yp < h) && (xp >= 0) && (yp >= 0)) {
 	  rn[x][y] = r[xp][yp];
 	  gn[x][y] = g[xp][yp];
 	  bn[x][y] = b[xp][yp];
 	}
 }
 r = rn;
 g = gn;
 b = bn;
 short2Image();
 }
//This is an example of an inverse mapping
public void xform(Mat3 transform) {
	
 int w = width;
 int h = height;
 int xc = w/2;
 int yc = h/2;
 short rn[][] = new short[width][height];
 short gn[][] = new short[width][height];
 short bn[][] = new short[width][height];
 int p[] = new int [3];
 int red, green, blue;
 int xp, yp, i, j;
 transform = transform.invert();
 for (int x = 0; x < w; x++)
   for (int y=0; y < h; y++) {
 	p=transform.multiply(x,y,1);
 	xp = (int) (p[0]/p[2]);
 	yp = (int) (p[1]/p[2]);
 	if ((xp < w) && (yp < h) && (xp >= 0) && (yp >= 0)) {
 	  rn[x][y] = r[xp][yp];
 	  gn[x][y] = g[xp][yp];
 	  bn[x][y] = b[xp][yp];
 	}
 }
 r = rn;
 g = gn;
 b = bn;
 short2Image();
}

//This is an example of an inverse mapping
public void xformFeedback(Mat3 transform) {	
 int w = width;
 int h = height;
 int xc = w/2;
 int yc = h/2;
 int p[] = new int [3];
 int red, green, blue;
 int xp, yp, i, j;
 transform = transform.invert();
 int startPoint[]=transform.multiply(0,0,1);
 int endPoint[]=transform.multiply(w-1,h-1,1);
 for (int x = startPoint[0]; x < endPoint[0]; x++)
   for (int y=startPoint[1]; y < endPoint[1]; y++) {
 	p=transform.multiply(x,y,1);
 	xp = (int) p[0];
 	yp = (int) p[1];
 	if (x < 0||x>=r.length) continue;
 	if (y < 0||y>=r[0].length) continue;
 	try {
 	if ((xp < w) && (yp < h) && (xp >= 0) && (yp >= 0)) {
 	  r[x][y] = r[xp][yp];
 	  g[x][y] = g[xp][yp];
 	  b[x][y] = b[xp][yp];
 	}
 	}
 	catch (Exception e) {
 		System.out.println(e+
 			"x,y="+x+","+y+" xp,yp="+xp+","+"yp");
 		break;
 	}
 }
 short2Image();
}

}


