package graphics;
import gui.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

public class DrawTest extends ShortCutFrame
	implements MouseListener, MouseMotionListener  {
	
	Font font 
		= new Font("Times",Font.PLAIN,12);
	
	MenuBar mb = new MenuBar();
	
	Menu fileMenu = new Menu("File");
	MenuItem saveAsPict_mi = 
		addMenuItem(fileMenu,"[E-s]ave as pict");
	MenuItem line_mi = 
		addMenuItem(fileMenu,"[l]ine");
	MenuItem circle_mi = 
		addMenuItem(fileMenu,"[c]ircle");
	MenuItem oval_mi = 
		addMenuItem(fileMenu,"[o]val");
	MenuItem rect_mi = 
		addMenuItem(fileMenu,"[r]ect");
	MenuItem string_mi = 
		addMenuItem(fileMenu,"[s]tring");
	MenuItem lens_mi = 
		addMenuItem(fileMenu,"[L]ens");
	MenuItem ray_mi = 
		addMenuItem(fileMenu,"[R]ay shoot");
	MenuItem erase_mi = 
		addMenuItem(fileMenu,"[e]rase");
	
	MenuItem lastSelected = line_mi;

    Shapes shapeList = new Shapes();
    int x1,y1;
    int x2,y2;
    int xl,yl;


 DrawTest(String title) {
 	super(title);
 	mb.add(fileMenu);
 	setMenuBar(mb);
 	setBackground(Color.white);
	addMouseMotionListener(this);
	addMouseListener(this);
}
 private void save() {
 	dclap.SavePICT.toFile(this);
 }
	public void actionPerformed(ActionEvent e) {

 		if (match(e, erase_mi)) {
    		shapeList = new Shapes();
    		repaint();
			return;
		}
 		if (match(e, ray_mi)) {
    		shapeList.addLineToLastCircle();
    		repaint();
			return;
		}
 		if (match(e, lens_mi)) {
    		lastSelected = lens_mi;
			return;
		}
 		if (match(e, string_mi)) {
    		lastSelected = string_mi;
			return;
		}

 		if (match(e, rect_mi)) {
    		lastSelected = rect_mi;
			return;
		}
 		if (match(e, saveAsPict_mi)) {
    		save(); 
			return;
		}

 		if (match(e, line_mi)) {
    		lastSelected = line_mi; 
			return;
		}
 		if (match(e, circle_mi)) {
    		lastSelected = circle_mi; 
			return;
		}
 		if (match(e, oval_mi)) {
    		lastSelected = oval_mi; 
			return;
		}

	}

   public static void main(String args[]) {
	DrawTest drawTest = new DrawTest("DrawTest");
	drawTest.setSize(300, 300);
	drawTest.show();
   }
   public void mouseDragged(MouseEvent e) {
      e.consume();
      xl = x2;
      yl = y2;
      x2 = e.getX();
      y2 = e.getY();
      repaint();
  }

  public void mouseMoved(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
      e.consume();
      x1 = e.getX();
      y1 = e.getY();
      x2 = -1;
  }

  public void mouseReleased(MouseEvent e) {
    e.consume();
    if (lastSelected == lens_mi) 
      	shapeList.add(
      		new Arc2d(x1, y1, e.getX(), e.getY()));
    if (lastSelected == string_mi) 
      	shapeList.add(
      		new String2d(e.getX(), e.getY(), "String me!"));
    if (lastSelected == line_mi) 
      	shapeList.add(
      		new Line2d(x1, y1, e.getX(), e.getY()));
    if (lastSelected == rect_mi) 
      	shapeList.add(
      		new Rect2d(x1, y1, e.getX(), e.getY()));
    if (lastSelected == circle_mi) 
      	shapeList.add(
      		new Circle2d(x1, y1, e.getX(), e.getY()));
    if (lastSelected == oval_mi) 
      	shapeList.add(
      		new Oval2d(x1, y1, e.getX(), e.getY()));
      x2 = xl = -1;
    repaint();  
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
  }
  public void drawRubberBand(Graphics g) {
  	if (x2 == -1) return;
	g.setXORMode(getBackground());
    if (lastSelected == lens_mi) {
      	Arc2d a =
      		new Arc2d(x1, y1, x2, y2);
      	a.draw(g);
      	return;
      }
    if (lastSelected == string_mi) {
      	String2d s =
      		new String2d(x2, y2, "String me!");
      	s.draw(g);
      	return;
      }
	if (lastSelected == line_mi) {
		g.drawLine(x1, y1, x2, y2);
		return;
	}
	
	if (lastSelected == circle_mi) {
		Circle2d c = new Circle2d(x1,y1,x2,y2);
		c.draw(g);
		return;
	}
	if (lastSelected == oval_mi) {
		Oval2d o = new Oval2d(x1,y1,x2,y2);
		o.draw(g);
		return;
	}
	if (lastSelected == rect_mi) {
		Rect2d r = new Rect2d(x1,y1,x2,y2);
		r.draw(g);
		return;
	}
  }
  public void paint(Graphics g) {
  	g.setFont(font);
	shapeList.draw(g);
	drawRubberBand( g);
  }

}
interface GraphicsObject {
	public void draw(Graphics g);
	public void erase(Frame f, Graphics g);
}
class Shapes extends Shape {
	private Vector v = new Vector();
	public void add(GraphicsObject d) {
		v.addElement(d);
	}
	public void draw(Graphics g) {
		for (int i=0; i < v.size(); i++) {
			GraphicsObject d = 
				(GraphicsObject)v.elementAt(i);
			d.draw(g);
		}
	}
	public Line2d getLastLine() {
		for (int i=v.size()-1; i >=0; i--)
			if (v.elementAt(i) instanceof Line2d)
				return 
					(Line2d)v.elementAt(i);
		return null;
	}
	public Circle2d getLastCircle() {
		for (int i=v.size()-1; i >=0; i--)
			if (v.elementAt(i) instanceof Circle2d)
				return 
					(Circle2d)v.elementAt(i);
		return null;
	}
	public Vec2d getClosestPoint(Ray2d ray) {
		double maxDist = 1000000;
		Vec2d closestSoFar=null;
		for (int i=v.size()-1; i >=0; i--) {
			if (! (v.elementAt(i) instanceof Intersects))
				continue;
			Intersects inter = (Intersects)v.elementAt(i);
			Vec2d v = inter.intersect(ray);
			if (v==null) continue;
			if (ray.t >= maxDist) continue;
			closestSoFar = v;
			maxDist = ray.t;
		}	
		return closestSoFar;
	}
	
	public void addLineToLastCircle2() {
		Line2d l = getLastLine();
		Circle2d c = getLastCircle();
		if (l==null) return;
		if (c == null) return;
		//System.out.println("Last circle/line="+l+c);
		Ray2d r = new Ray2d(l);
		Vec2d v = c.intersect(r);
		if (v == null) return;
		//System.out.println("point found"+v);
		add(new Line2d(r.p,v));
	}
	public void addLineToLastCircle() {
		Line2d l = getLastLine();
		if (l==null) return;
		Ray2d r = new Ray2d(l);
		Vec2d v = getClosestPoint(r);
		if (v == null) return;
		//System.out.println("point found"+v);
		add(new Line2d(r.p,v));
	}
}

abstract class Shape 
	implements GraphicsObject {
	public void erase(Frame f, Graphics g) {
		g.setXORMode(f.getBackground());
		draw(g);
	    g.setColor(f.getForeground());
	    g.setPaintMode();	
	} 
}

class Point2d extends Shape {
	int x1 = 0;
	int y1 = 0;
	public void draw(Graphics g) {
		g.drawLine(x1,y1,x1+1,y1+1);
	}
}

class Line2d extends Shape {
	int x1 = 0;
	int y1 = 0;
	int x2 = 1;
	int y2 = 1;
	Line2d(int _x1, int _y1, int _x2, int _y2) {
		x1 = _x1;
		y1 = _y1;
		x2 = _x2;
		y2 = _y2;
	}
	Line2d(Vec2d v1, Vec2d v2) {
		x1 = (int)v1.v[0];
		y1 = (int)v1.v[1];
		x2 = (int)v2.v[0];
		y2 = (int)v2.v[1];
	}
	public void draw(Graphics g) {
		g.drawLine(x1,y1,x2,y2);
	}
	public String toString() {
		return 
			"x1,y1,x2,y2="+x1+","+y1+","+x2+","+y2;
	}
}


class Circle2d extends Shape 
	implements Intersects{
	int x1 = 0;
	int y1 = 0;
	int diameter  = 1;
	int radius = 1;
	Vec2d center = new Vec2d(0,0);
	float radiusSq;
	
	Circle2d(int _x1, int _y1, int _diameter) {
		x1 = _x1;
		y1 = _y1;
		diameter = _diameter;
		radius = diameter/2;
		radiusSq = radius * radius;
	}
	
	Circle2d(int _x1, int _y1, int _x2, int _y2) {
		x1 = _x1;
		y1 = _y1;		
		int dx = x1-_x2;
		int dy = y1-_y2;
		diameter = (int)Math.sqrt(dx*dx+dy*dy);
		if (_x1 > _x2) x1=_x2;		
		if (_y1 > _y2) y1=_y2;
		radius = diameter / 2;	
		radiusSq = radius * radius;	
		center.v[0] = x1+radius;
		center.v[1] = y1+radius;
	}
	public void draw(Graphics g) {
		int xc = (int)center.v[0];
		int yc = (int)center.v[1];
		g.drawOval(x1,y1,diameter,diameter);
		g.fillOval(xc,yc,2,2);
		g.drawString("("+xc+","+yc+")",xc+3,yc+3);
		g.drawLine(xc,yc,xc-radius,yc);
	}
	public String toString() {
		return 
			"xc,yc,r="+center.v[0]+","+center.v[1]+","+radius;
	}

   public Vec2d intersect(Ray2d ray) {

	double t,          /* where the ray intersects */
	    // vDotV = distance square from sphere center to
	    // ray origin
	    vDotV,        
	    dDotV,     
	    thc;        /* length sqare of the half chord */
	
	// vector to the center of the sphere from 
	// ray origin 
	Vec2d v;      
	boolean inside=false;

	/* use the closest approach algorithm */
	v = new Vec2d(center.v[0],center.v[1]);
	// v = v - ray.p
	v.sub(ray.p);
	// loc = v^2
	vDotV = v.dot(v);
	// Are we inside the circle?
	if(vDotV <= radiusSq)
	    inside = true;

	// dDotV = D dot V = 
	// cos(angle between ray and vec to center);
	dDotV = v.dot(ray.d);   /* find the closest approach */

	// object is behind the ray origin
	if ((inside != true) && (dDotV <= 0.0))
	    return null;  

	/* compute the half chord square from the ray intersection to the 
	   intersection normal. */
	thc = (dDotV * dDotV) - vDotV + radiusSq ;
	if (thc < 0.0)
	    return null;   /* ray misses the sphere */

	/* find the ray intersection */
	if (inside == true)
	    t = dDotV + Math.sqrt(thc);
	else
	    t = dDotV - Math.sqrt(thc);

		return ray.vecOnLine((float)t);
    }
 }
class Oval2d extends Shape {
	int x1 = 0;
	int y1 = 0;
	int h = 1;
	int w = 1;
	int xc = 0;
	int yc = 0;
	
	
	Oval2d(int _x1, int _y1, int _x2, int _y2) {
		x1 = _x1;
		y1 = _y1;		
		w = Math.abs(_x2-x1);
		h = Math.abs(_y2-y1);
		if (_x1 > _x2) x1=_x2;		
		if (_y1 > _y2) y1=_y2;		
		xc = x1+w/2;
		yc = y1+h/2;
	}
	public void draw(Graphics g) {
		g.drawOval(x1,y1,w,h);
		g.fillOval(xc,yc,2,2);
		g.drawString("("+xc+","+yc+")",xc+3,yc+3);
		g.drawLine(xc,yc,xc,yc-h/2);
		g.drawLine(xc,yc,xc-w/2,yc);
	}
}
class Rect2d extends Shape {
	int x1 = 0;
	int y1 = 0;
	int h = 1;
	int w = 1;
	int xc = 0;
	int yc = 0;
	
	
	Rect2d(int _x1, int _y1, int _x2, int _y2) {
		x1 = _x1;
		y1 = _y1;		
		w = Math.abs(_x2-x1);
		h = Math.abs(_y2-y1);
		if (_x1 > _x2) x1=_x2;		
		if (_y1 > _y2) y1=_y2;	
		xc = x1+w/2;
		yc = y1+h/2;
	
	}
	public void draw(Graphics g) {
		g.drawRect(x1,y1,w,h);
		g.fillOval(xc,yc,2,2);
		g.drawString("("+xc+","+yc+")",xc+3,yc+3);
	}
}
class String2d extends Shape {
	int x1 = 0;
	int y1 = 0;
	String str;

	String2d(int _x1, int _y1, String _str) {
		x1 = _x1;
		y1 = _y1;
		str = _str;		
	}
	public void draw(Graphics g) {
		g.drawString(str,x1,y1);
	}
}
class Arc2d extends Shape {
	int x1 = 0;
	int y1 = 0;
	int w = 1;
	int h = 1;
	int xc = 0;
	int yc = 0;
	int startAngle = 90;
	int arcAngle = 180;
	Line2d plane = null;

	Arc2d(int _x1, int _y1, int _x2, int _y2) {
		x1 = _x1;
		y1 = _y1;		
		w = Math.abs(_x2-x1);
		h = Math.abs(_y2-y1);
		int _x1PluswOn2 = _x1+w/2;
		plane = new Line2d(_x1PluswOn2,_y1,_x1PluswOn2,_y1+h);
		xc = x1 + w/2;
		yc = y1 + h/2;
	}
	public void draw(Graphics g) {
		g.drawArc(x1, y1, w, h, startAngle, arcAngle);
		plane.draw(g);
		g.fillOval(xc,yc,2,2);
		g.drawString("("+xc+","+yc+")",xc+3,yc+3);
	}
}
class Vec2d {
  public float v[] = new float[2];

  public Vec2d(float x, float y) {
    v[0] = x; 
    v[1] = y;
  }
  // methods
  public final float dot(Vec2d B) {
    return (v[0]*B.v[0] + v[1]*B.v[1]);
  }

  public final float dot(float Bx, float By) {
    return (v[0]*Bx + v[1]*By);
  }

  public static final float dot(Vec2d A, Vec2d B) {
    return (A.v[0]*B.v[0] + A.v[1]*B.v[1] );
  }
  
  // forms c = a*this+B
  public Vec2d linearComb(float a, Vec2d B) {
  	return
  		new Vec2d(
  			a * v[0]  + B.v[0],
  			a * v[1]  + B.v[1]); 			
  }


  public final float length( ) {
    return (float) Math.sqrt(v[0]*v[0] + v[1]*v[1] );
  }


  public final void normalize( ) {
    float t = v[0]*v[0] + v[1]*v[1];
    if (t != 0 && t != 1) t = (float) (1 / Math.sqrt(t));
    v[0] *= t;
    v[1] *= t;
  }

  public String toString() {
    return new String("["+v[0]+", "+v[1]+"]");
  }
  
  // A = a*A;
  public void mult(float factor) {
	v[0]=v[0]*factor; 
	v[1]=v[1]*factor;
   }

   // A = A + B
   public void add(Vec2d B) {
	v[0]=v[0]+B.v[0]; 
	v[1]=v[1]+B.v[1];
   }

    // A = A - B
   public void sub(Vec2d B) {
	v[0]=v[0]-B.v[0]; 
	v[1]=v[1]-B.v[1];
   }

}
interface Intersects {
	Vec2d intersect(Ray2d r);
}

class Ray2d  {
	Vec2d p; // origin
	Vec2d d; // direction
	float t=0;
	GraphicsObject object;
	Vec2d vecOnLine(float _t) {
		t = _t;
		return d.linearComb(t,p);
	}
	Ray2d(Line2d l) {
		p = new Vec2d(l.x1,l.y1);
		d = new Vec2d(l.x2-l.x1,l.y2-l.y1);
		d.normalize();
	}
}

