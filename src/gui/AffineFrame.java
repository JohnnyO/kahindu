package gui;
import java.awt.*;
import java.awt.event.*;

public class AffineFrame extends ShortCutFrame 
	implements MouseListener,MouseMotionListener{
	
	XformFrame xf = null;
	
	MenuBar mb = new MenuBar();
	
	Menu transformMenu = new Menu("Transform");
	
	Menu pointMenu = new Menu("Point...");
	
	
	MenuItem rotate_mi = addMenuItem(transformMenu,"[r]otate");
	MenuItem scale_mi = addMenuItem(transformMenu,"scale - xy");
	MenuItem scalex_mi = addMenuItem(transformMenu,"[X]scale - x");
	MenuItem scaley_mi = addMenuItem(transformMenu,"[Y]scale - y");
	MenuItem scaleRotate_mi = addMenuItem(transformMenu,"[*]scalexy+rotate");
	MenuItem shearx_mi = addMenuItem(transformMenu,"shear x");
	MenuItem sheary_mi = addMenuItem(transformMenu,"[y]shear y");
	MenuItem shearRotate_mi = addMenuItem(transformMenu,"[R]otate+shear");
	MenuItem revert_mi = addMenuItem(transformMenu,"[E-R]evert to saved");
	MenuItem apply_mi = addMenuItem(transformMenu,"[a]pply transform");
	MenuItem applyBilinear4Points_mi = addMenuItem(transformMenu,"[4]applyBilinear4Points");
	MenuItem polarTransform_mi = addMenuItem(transformMenu,"[p]olarTransform");
	
	MenuItem xformFeedback_mi = addMenuItem(transformMenu, "[f]eedback xform");
	MenuItem zedSquare_mi = addMenuItem(transformMenu, "w=z**2");
	MenuItem sqrt_mi = addMenuItem(transformMenu,"[E-s]qrt");
	MenuItem fishEye_mi = addMenuItem(transformMenu,"Fish Eye"); 

	MenuItem colorize_mi = addMenuItem(transformMenu,"[c]olorize");

	MenuItem movePoint0_mi = addMenuItem(pointMenu,"[E-0]move point 0");	
	MenuItem movePoint1_mi = addMenuItem(pointMenu,"[E-1]move point 1");	
	MenuItem movePoint2_mi = addMenuItem(pointMenu,"[E-2]move point 2");	
	MenuItem movePoint3_mi = addMenuItem(pointMenu,"[E-3]move point 3");	
	MenuItem movePointd_mi = addMenuItem(pointMenu,"[E-d]dont move points");	
	MenuItem printPoints_mi = addMenuItem(pointMenu,"print points");	
	MenuItem selection = rotate_mi;
	
	private Polygon p = new Polygon();
	Mat3 at;
	int x1 = 0;
	int y1 = 0;
	int width;
	int height;
	int centroid[] = {width/2,height/2};
	int xtranslate = 50;
	int ytranslate = 50;
	
	int pointToMove = -1;
	
	public Polygon getPolygon() {
		return at.transform(p);
	}

	public void actionPerformed(ActionEvent e) {

 		if (match(e, printPoints_mi)) {
    		printPoints(); 
			return;
		}
 		if (match(e, fishEye_mi)) {
    		xf.fishEye(); 
			return;
		}

		if (match(e,zedSquare_mi)) {
			xf.zedSquare();
			return;
		}
		if (match(e,colorize_mi)) {
			ColorGridFrame cgf = new ColorGridFrame(this);
			return;
		}
		if (match(e,sqrt_mi)) {
			xf.sqrt();
			return;
		}
		if (match(e,polarTransform_mi)) {
			xf.polarTransform();
			return;
		}
		if (match(e,movePointd_mi)) {
			pointToMove = -1;
			return;
		}
		if (match(e,movePoint0_mi)) {
			pointToMove = 0;
			return;
		}
		if (match(e,movePoint1_mi)) {
			pointToMove = 1;
			return;
		}
		if (match(e,movePoint2_mi)) {
			pointToMove = 2;
			return;
		}
		if (match(e,movePoint3_mi)) {
			pointToMove = 3;
			return;
		}
		if (match(e,applyBilinear4Points_mi)) {
			xf.revert();
			xf.applyBilinear4Points();
			return;
		}
		if (match(e,revert_mi)) {
			revert();
			return;
		}
		if (match(e,rotate_mi)) {
			selection =rotate_mi;
			return;
		}
		if (match(e,xformFeedback_mi)) {
			xformFeedback();
			return;
		}

		if (match(e,apply_mi)) {
			apply();
			return;
		}
		if (match(e,shearRotate_mi)) {
			selection =shearRotate_mi;
			return;
		}
		if (match(e,scaleRotate_mi)) {
			selection =scaleRotate_mi;
			return;
		}
		if (match(e,scale_mi)) {
			selection =scale_mi;
			return;
		}
		if (match(e,scalex_mi)) {
			selection =scalex_mi;
			return;
		}
		if (match(e,scaley_mi)) {
			selection =scaley_mi;
			return;
		}
		if (match(e,shearx_mi)) {
			selection =shearx_mi;
			return;
		}
		if (match(e,sheary_mi)) {
			selection =sheary_mi;
			return;
		}
		super.actionPerformed(e);
	}
	public void xformFeedback() {
		xf.applyBilinear4PointsFeedback();
	}

	AffineFrame(String title, XformFrame _xf, int w, int h) {
		super(title);
		xf = _xf;
		width = w;
		height = w;
		init();
		addMouseListener(this);
		addMouseMotionListener(this);
		transformMenu.add(pointMenu);
		mb.add(transformMenu);
		setMenuBar(mb);
	}
	//  p0      p1
	//  p3      p2
	private void init() {
		int x2 = x1 + width;
		int y2 = y1 + height;
		p.addPoint(x1,y1);
		p.addPoint(x2,y1);
		p.addPoint(x2,y2);
		p.addPoint(x1,y2);
		centroid = Mat3.centroid(p);
		setPose(0,1,1);

	}
	
	private void revert() {
		xf.revertNoResize();
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
		AffineFrame af = new AffineFrame(
			"AffineFrame",new XformFrame("XformFrame"),100,100);
		af.setSize(150,150);
		af.setVisible(true);
	}
	public void paint(Graphics g) {
		Polygon pt = at.transform(p);
		g.translate(xtranslate, ytranslate);
		g.drawPolygon(pt);
		for (int i=0; i < pt.npoints; i++)
			g.drawString("p"+i,pt.xpoints[i],pt.ypoints[i]);
		Rectangle r = pt.getBounds();
		g.drawString("h="+r.height+" w="+r.width,r.height/2,r.width/2);
	}
	
	public void apply() {
		p = at.transform(p);
		xf.xform(at);
	}
	
	public void movePoints(MouseEvent e) {
		int i = pointToMove;
		p.xpoints[i] = getX(e);
		p.ypoints[i] = getY(e);
		repaint();
	}
	public void printPoints() {
		for (int i=0; i < p.xpoints.length; i++) {
			System.out.println("af.setPoint("+i+","+p.xpoints[i]+","+p.ypoints[i]+");");
		}
	}
	public void setPoint(int i, int x, int y) {
		p.xpoints[i] = x;
		p.ypoints[i] = y;
		repaint();
	}
	private int getX(MouseEvent e) {
      	return (int)(e.getX()  - xtranslate);
    }
    private int getY(MouseEvent e) {
      	return (int)(e.getY() - ytranslate); 
    }
	public void mousePressed(MouseEvent e) {
    }
	
	public void mouseExited(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {

    }
    public void mouseDragged(MouseEvent e) {
        e.consume();
        double dx = getX(e) - centroid[0];
        double dy = getY(e) - centroid[1];
        double sx = 2*Math.sqrt(dx*dx+dy*dy)/getSize().width;
        double sy = sx;
        double theta = Math.atan2(dy,dx)*180/Math.PI;
        double shx =Math.abs(dx)/getSize().width;
        double shy = Math.abs(dy)/getSize().height;
        if (pointToMove != -1) {
        	movePoints(e);
        	return;
        }
        if (selection == rotate_mi) {
        	setPose(theta, 1, 1);
        }
        if (selection == scale_mi) {
        	setPose(0, sx, sy);
        }
        if (selection == scalex_mi) {
        	setPose(0, sx, 1);
        }
        if (selection == scaley_mi) {
        	setPose(0, 1, sy);
        }
        if (selection == scaleRotate_mi) {
        	setPose(theta, sx, sy);
       }
        if (selection == shearx_mi) {
        	setShear(0, shx,  0);
       }
        if (selection == sheary_mi) {
        	setShear(0, 0,  shy);
       }
        if (selection == shearRotate_mi) {
        	setShear(theta, shx, shy);
       } 
    	repaint(); 
	}
	public void mouseMoved(MouseEvent e) {
	}	
	
}