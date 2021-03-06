package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DrawFrame extends WaveletFrame 
		implements FocusListener {
	int xsize,ysize,xcenter,ycenter;
	int delay=10;
	int numberOfPoints=1000;
	Graphics backGC;
	Image backBuffer;
	int[] pointX,pointY,pointZ;
	Color[] grayScale;
	
	boolean inFocus = true;
	
	public void focusGained(FocusEvent e) {
		inFocus = true;
		repaint(2000,0,0,256,256);
	}
	public void focusLost(FocusEvent e) {
		inFocus = false;
	}
	
	public DrawFrame(String title) {
		super(title);
	}
	public static void main(String args[]) {
		DrawFrame f = new DrawFrame("DrawFrame");
		f.setSize(256,256);
		f.show();
		f.setUpFrame();
		f.setPoints();
		f.addFocusListener(f);
		f.repaint();
	}
	
	public static void drawPoints(
		float x[],
		float y[],
		float z[]) {
		DrawFrame f = new DrawFrame("DrawFrame");
		f.setSize(256,256);
		f.show();
		f.setUpFrame();
		f.setPoints(x,y,z);
		f.addFocusListener(f);
		f.repaint();
	}
	
	

	public void setUpFrame()
	{
		//Get screen dimensions
		xsize=getSize().width;
		ysize=getSize().height;
		xcenter=xsize/2;
		ycenter=ysize/2;

		//Init double-buffering
		backBuffer = createImage(xsize, ysize);
		backGC = backBuffer.getGraphics();

		//Create grayscale tones
		grayScale=new Color[256];
		for (int n=0;n<256;n++)
		grayScale[255-n]=new Color(n,n,n);
	}
	
public void setPoints() {
		int X[]=new int[numberOfPoints];
		int Y[]=new int[numberOfPoints];
		int Z[]=new int[numberOfPoints];
		double theta = 0;
		double eps = (1.0/numberOfPoints) * 2*Math.PI;
		for (int n=0;n<numberOfPoints;n++) {
			X[n]=(int)
				((Math.sin(theta)*xsize*2.0)-xsize/2);
			Y[n]=(int)
				((Math.cos(theta)*ysize*2.0)-ysize/2);
			Z[n]=(int)
				(Math.random()*4095);
			theta = theta + eps;
		}
		setPoints(X,Y,Z);
}
public void setPoints(int x[], int y[], int z[]) {
	numberOfPoints = x.length;
	pointX = x;
	pointY = y;
	pointZ = z;
	int xmax = -1000;
	int ymax = -1000;
	int zmax = -1000;
	int xmin = 1000;
	int ymin = 1000;
	int zmin = 1000;
	
	for (int i =0; i < x.length; i++) {
		if (x[i] > xmax) xmax=x[i];
		if (y[i] > ymax) ymax=y[i];
		if (z[i] > zmax) zmax=z[i];

		if (x[i] < xmin) xmin=x[i];
		if (y[i] < ymin) ymin=y[i];
		if (z[i] < zmin) zmin=z[i];
	}
	System.out.println("x,y,z max ="+xmax +" "+ ymax +" "+zmax);
	System.out.println("x,y,z min ="+xmin +" "+ ymin +" "+zmin);
}

public void setPoints(float x[], float y[], float z[]) {
	numberOfPoints = x.length;
	pointX = new int[numberOfPoints];
	pointY = new int[numberOfPoints];
	pointZ = new int[numberOfPoints];
	int xmax = -1000;
	int ymax = -1000;
	int zmax = -1000;
	int xmin = 1000;
	int ymin = 1000;
	int zmin = 1000;
	
	for (int i =0; i < x.length; i++) {
		pointX[i] = (int)(x[i] * 384 + (1-x[i])*(-640));
		pointY[i] = (int)(y[i] * 384 + (1-y[i])*(-640));
		pointZ[i] = (int)(z[i] * 4095 + (1-z[i])*(0));
	}
	setPoints(pointX,pointY,pointZ);
}

	public void paint(Graphics g)
	{
		//Clear screen
		backGC.setColor(Color.black);
		backGC.fillRect(0,0,xsize,ysize);

		for (int n=0;n<numberOfPoints;n++)
		{
			//Move stars and clip against viewplane
			//The back clipping plane and the 
			// view plane are hard coded
			//for optimizing reasons. 
			// Viewplane=512, backplane=4095
			pointZ[n]-=50;
			if (pointZ[n]<512) pointZ[n]=4095;
			int z=pointZ[n];

			//Apply perspective projection
			int x=(pointX[n]<<9)/z+xcenter;
			int y=(pointY[n]<<9)/z+ycenter;

			//Apply dept shading
			backGC.setColor(grayScale[z>>4]);

			//Draw star
			backGC.drawLine(x,y,x,y);
		}

		//Draw buffer to screen
		g.drawImage(backBuffer, 0, 0, this);
		repaint(2000,0,0,256,256);

	}


	public void update(Graphics g) {
		if (inFocus)
			paint(g);
	}
}