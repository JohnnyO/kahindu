package gui;
import java.awt.Point;

public class Edgel {
	public static final int OPEN = 1;
	public static final int CLOSED = 0;

	private Edgel parent=null;
	Point p1 = new Point(0,0);
	Point p2 = new Point(0,0);
	private int type = OPEN;
	private int cost = 0;
	public Edgel() {
  	}
	public Edgel(Edgel e) {
			p1.x = e.p1.x;
			p1.y = e.p1.y;
  			p2.x = e.p2.x;
 			p2.y = e.p2.y;
 			cost = e.cost;
 			type=e.type;
 	}
 	public void setCoordinates(Point _p1, Point _p2) {
			p1.x = _p1.x;
			p1.y = _p1.y;
			p2.x = _p2.x;
			p2.y = _p2.y;
 	}
 	public void setType(int _type) {
			type=_type;
 	}
 	public int getType() {
 		return type;
 	}
	public void setParent(Edgel _parent) {
		parent=_parent; 
	}
	public Edgel getParent() {
		return parent;
	}
	public void setCost(int _c) {
		cost =  _c;
	}
	public int getPly() {
		return getPly(1);
	}
	private int getPly(int ply) {
		if (parent == null) {
			return ply;
		};
		ply++;
		return parent.getPly(ply);
	}
	public int getCost() { return cost;}
}
