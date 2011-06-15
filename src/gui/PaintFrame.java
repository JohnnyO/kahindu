package gui;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class PaintFrame extends BoundaryFrame 	
		implements MouseListener,MouseMotionListener {
	IconFrame iconFrame = new IconFrame();
	Menu paintMenu = new Menu("Paint");
	MenuItem showIconFrame_mi = addMenuItem(paintMenu,"show paint bar");
	MenuItem eraseShapes_mi = addMenuItem(paintMenu,"Erase shapes");
	MenuItem resizeFrame_mi = addMenuItem(paintMenu,"[E-R]esize Frame");
	int x1, y1, x2, y2;
	
	Vector shapes = new Vector();
 public void actionPerformed(ActionEvent e) {
 
	if (match(e,resizeFrame_mi)) {		
	    		resizeFrame();
	    		return;
	    }
	if (match(e,eraseShapes_mi)) {		
	    		eraseShapes();
	    		return;
	    }
	if (match(e,showIconFrame_mi)) {		
	    		showIconFrame();
	    		return;
	    }
	 super.actionPerformed(e);
	 }
	
	public void resizeFrame() {
		setSize(width,height);
	}
	public void showIconFrame() {
		Rectangle r = getBounds();
		Dimension d = r.getSize();
		iconFrame.setLocation(d.width, d.height);
		iconFrame.setVisible(true);
	}
	public void paint(Graphics g) {
		if (iconFrame != null) 
			iconFrame.setLabels(width,height,red,green,blue);
		super.paint(g);
		paintShapes(g);		
	}
  	public void eraseShapes() {
  		shapes = new Vector();
  		repaint();
  	}
	public void paintShapes(Graphics g) {
		if (shapes == null) return;
		for (int i=0; i <shapes.size(); i++) {
	    		Point p = (Point)shapes.elementAt(i);
	    		g.drawLine(p.x-1,p.y-1,p.x+1,p.y+1);
	    		g.drawLine(p.x+1,p.y-1,p.x-1,p.y+1);
		}
	 }

	   	
	public PaintFrame(String title) {
		super(title);
		SpatialFilterMenu.add(paintMenu);
		showIconFrame();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void mousePressed(MouseEvent e) {
     		 setP1(e);
      }
	
	public void mouseExited(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
		IconComponent ic = iconFrame.getSelectedIcon();
		if (ic == iconFrame.xImageIcon)
     			shapes.addElement( new Point( e.getX(), e.getY()));
     		if (ic == iconFrame.eyeDropperIcon) {
     			iconFrame.setPosition(x1,y1);
     			getColor();
     		}
		if (ic == iconFrame.magnifyingGlassIcon) magnify();
      	repaint();	
      	}
      	
      	public void magnify() {
      		int m=0;
      		int n=0;
      		short rs[][] = new short[width*2][height*2];
      		short gs[][] = new short[width*2][height*2];
      		short bs[][] = new short[width*2][height*2];
      		
      		for (int i=0; i < width; i++) {
      			for (int j=0; j < height; j++) {
      				rs[m][n]=r[i][j];
      				gs[m][n]=g[i][j];
      				bs[m][n]=b[i][j];
      				rs[m+1][n]=r[i][j];
      				gs[m+1][n]=g[i][j];
      				bs[m+1][n]=b[i][j];
      				rs[m][n+1]=r[i][j];
      				gs[m][n+1]=g[i][j];
      				bs[m][n+1]=b[i][j];
   				rs[m+1][n+1]=r[i][j];
      				gs[m+1][n+1]=g[i][j];
      				bs[m+1][n+1]=b[i][j];
      				n +=2;
      			}
      			m+=2;
      			n=0;
      		}
      		r = rs;
      		g = gs;
      		b = bs;
      		short2Image();
      		resizeFrame();
      	}
      	private int getX(MouseEvent e) {
      		return (int)(e.getX() *  width /getSize().width );
      	}
      	private int getY(MouseEvent e) {
      		return (int)(e.getY() *height / getSize().height ); 
      	}
      	
      	private Point scalePoint(Point p) {
      		return
      		new Point(p.x * getSize().width/width,
      				p.y = p.y * getSize().height /height);
      	}
 
 	private void clearPel(int x, int y) {
  			r[x][y] = 0;
			g[x][y] = 0;
			b[x][y] = 0;
    	}
    	public void erasePoint() {
    			clearPel(x1,y1);
			short2Image();
	}
      	private void getColor() {
      		red = r[x1][y1];
      		green = g[x1][y1];
      		blue = b[x1][y1];
      	}
      	private short red = 0;
      	private short green = 0;
      	private short blue = 0;
      	
    	public void pencilPoint() {
    			setColor(x1,y1);
			short2Image();
	}
    	public void brushPoint() {
    			setColor(x1,y1);
    			setColor(x1+1,y1+1);
    			setColor(x1-1,y1-1);
    			setColor(x1-1,y1+1);
    			setColor(x1+1,y1-1);
			short2Image();
	}
	
	private void setColor(int x, int y) {
		r[x][y] = red;
		g[x][y] = green;
		b[x][y] = blue;
	}
	public void mouseDragged(MouseEvent e) {
   		 e.consume();
		IconComponent ic = iconFrame.getSelectedIcon();
		if (ic == iconFrame.eraserIcon) erasePoint();
		if (ic == iconFrame.brushIcon) brushPoint();
		if (ic == iconFrame.pencilIcon) pencilPoint();

     		setP1(e);
    		repaint(); 
	}
	
	private void setP1(MouseEvent e) {
		x1 = getX(e);
      		y1 = getY(e);
      	}
	public void mouseMoved(MouseEvent e) {
	}	
	
	public static void main(String args[]) {
		PaintFrame pf = new PaintFrame("Paint Frame");
		pf.show();
	}


}