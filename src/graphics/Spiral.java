package graphics;
import gui.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import dclap.*;

public class Spiral extends ShortCutFrame  {
	
	MenuBar mb = new MenuBar();
	
	Menu fileMenu = new Menu("File");
	
	MenuItem saveAsPict_mi = addMenuItem(fileMenu,"[s]ave as pict");
	
	private Polygon p = new Polygon();
	Mat3 at;
	int x1 = 0;
	int y1 = 0;
	int width;
	int height;
	int centroid[] = {width/2,height/2};
	int xtranslate = width;
	int ytranslate = height;

 private void save() {
 	dclap.SavePICT.toFile(this);
 }
	
	public Polygon getPolygon() {
		return at.transform(p);
	}

	public void actionPerformed(ActionEvent e) {

 		if (match(e, saveAsPict_mi)) {
    		save(); 
			return;
		}

		super.actionPerformed(e);
	}
	Spiral(String title, int w, int h) {
		super(title);
		width = w;
		height = w;
		init();
		mb.add(fileMenu);
		setMenuBar(mb);
	}
	//  p0      p1
	//  p3      p2
	private void init() {
		int x2 = x1 + width/2;
		int y2 = y1 + height/2;
		p.addPoint(x1,y1);
		p.addPoint(x2,y1);
		p.addPoint(x2,y2);
		p.addPoint(x1,y2);
		centroid = Mat3.centroid(p);
		setPose(0,1,1);

	}
	
	private void revert() {
		p = new Polygon();
		init();
		repaint();
	}
	public void setPose(double theta, double sx, double sy) {
		Mat3 tr1 = new Mat3();
		Mat3 tr2 = new Mat3();
		Mat3 rt = new Mat3();
		Mat3 sc = new Mat3();
		centroid = rt.centroid(p);
		
		tr1.setTranslation(centroid[0],centroid[1]);
		sc.setScale(sx,sy);
		rt.setRotation(theta);
		tr2.setTranslation(-centroid[0],-centroid[1]);
		at = tr1.multiply(rt);
		at = at.multiply(sc);
		at = at.multiply(tr2);

	}
	
	public void setShear(double theta, double shx, double shy) {
		Mat3 tr1 = new Mat3();
		Mat3 tr2 = new Mat3();
		Mat3 rt = new Mat3();
		Mat3 sc = new Mat3();
		centroid = rt.centroid(p);
		
		tr1.setTranslation(centroid[0],centroid[1]);
		sc.setShear(shx,shy);
		rt.setRotation(theta);
		tr2.setTranslation(-centroid[0],-centroid[1]);
		at = tr1.multiply(rt);
		at = at.multiply(sc);
		at = at.multiply(tr2);

	}
	
	public static void main(String args[]) {
		SketchFrame af = new SketchFrame(
			"SketchFrame",new XformFrame("SketchFrame"),100,100);
		af.setSize(150,150);
		af.setVisible(true);
	}
	public void drawPolygon(Graphics g, Polygon p) {
		int n = p.xpoints.length;
		for (int i=0; i < n-1; i++) 
			g.drawLine(p.xpoints[i], p.ypoints[i],
				p.xpoints[i+1], p.ypoints[i+1]);
		g.drawLine(p.xpoints[0], p.ypoints[0],
			p.xpoints[n-1],p.ypoints[n-1]);
	}
	public void paint2(Graphics g) {
		Font f = new Font("Serif", Font.PLAIN,12);
		g.setFont(f);
		Polygon pt = at.transform(p);
		g.translate(50, 50);
		drawPolygon(g,pt);
		for (int i=0; i < pt.npoints; i++)
			g.drawString("p"+i,pt.xpoints[i],pt.ypoints[i]);
		Rectangle r = pt.getBounds();
		g.drawString("h="+r.height+" w="+r.width,r.height/2,r.width/2);
	}
	public void paint(Graphics g) {
		Font f = new Font("Serif", Font.PLAIN,12);
		g.setFont(f);
		g.translate(50, 50);
		for (float theta = 0; theta < 360; theta+=10f) {
			setPose(theta,sin(theta),sin(theta));
			Polygon pt = at.transform(p);
			drawPolygon(g,pt);
		}
	}
	public static final double PI_ON_180 
			= Math.PI / 180f;
	public float sin(float theta) {
		return
			(float)Math.sin(theta * PI_ON_180);
	}
	public float cos(float theta) {
		return
			(float)Math.cos(theta * PI_ON_180);
	}
	public void apply() {
		p = at.transform(p);
	}
	
	public void setPoint(int i, int x, int y) {
		p.xpoints[i] = x;
		p.ypoints[i] = y;
		repaint();
	}
	public void translatePoints(int x, int y) {
		for (int i=0; i < p.xpoints.length; i++) {
			p.xpoints[i] += x;
			p.ypoints[i] += y;
		}
	}
	private int getX(MouseEvent e) {
      	return (int)(e.getX()  - xtranslate);
    }
    private int getY(MouseEvent e) {
      	return (int)(e.getY() - ytranslate); 
    }
}