package gui;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;

public class MartelliFrame extends PaintFrame  {
	private Vector nodes = new Vector(); 
	// a list of all edges
	private Vector expanded = new Vector();
	
	
	
	private int MaxI=0;
	//Point Begin;
	//Point End;
	
	// The following number is image specific.
	// Martelli only finds one countour at a time.
	// on multiple runs, you will 
	// alter the following.
	public static final int maximumNumberOfEdges = 15000;
	public static final int edgeLength = 20;
	
	// *I have set the search time here, ------\/  - DL
	public static final int runTimeInSeconds = 10;
	
	private Timer t = new Timer();

	
Menu heuristicMenu = new Menu("Heuristic Edge Detection");
MenuItem processUserPoints_mi = 
	addMenuItem(heuristicMenu,"[E-M]process user points");
MenuItem negativeRobertsOnGreen_mi = 
	addMenuItem(heuristicMenu,"use NegativeRobertsOnGreen");
MenuItem averageWithChild_mi = 
	addMenuItem(fileMenu,"averageWithChild");

MenuItem printPath_mi = addMenuItem(heuristicMenu,"printPath");

// Inputs the green plane and outputs edges found on the
// red plane. The red plane is cleared upon initialization.

public MartelliFrame(String title) {
	super(title);
	boundaryMenu.add(heuristicMenu);
}

public static void main(String args[]) {
	MartelliFrame xf =new MartelliFrame("MartelliFrame");
}


//evaluates whether Martelli should stop searching or not
private boolean terminateSearch(Edgel el){
	//if (edges.size() > maximumNumberOfEdges ) return true;
	if (t.getElapsedTime() > runTimeInSeconds) 
		return true;
	if (distance(el,endPoint) < 2) return true;
	return false;
}


// Finds out whether the current edge coordinates 
// have been visited
Edgel getMarked(Edgel e) {
	for(int i=0; i < nodes.size(); i++) {
		Edgel el = (Edgel) nodes.elementAt(i);
		if ((el.p1.x==e.p1.x) && 
     		(el.p1.y==e.p1.y) &&
     		(el.p2.x==e.p2.x) && 
     		(el.p2.y==e.p2.y)) {
			return el;
		}
	}
	return null;	
}

//The cost of an edge element
private int C2(Edgel e) {
	int pc = 0;
	Edgel p = e.getParent();
	int d = distance(e,endPoint);
	if (p != null) ;//pc = p.getCost()/e.getPly();
	return 
		clip(pc+ 
			d+MaxI-(
				g[e.p1.x][e.p1.y]-
				g[e.p2.x][e.p2.y])
				);
}
// assume that local cost is stored in the 
// green plane.
private int C(Edgel e) {
	int pc = 0;
	Edgel p = e.getParent();
	int d = distance(e,endPoint);
	//if (p != null) pc = p.getCost()-e.getPly();
	return 
		clip(pc+ 
			d+child.g[e.p1.x][e.p1.y]);
}

public void averageWithChild() {
	for (int x=0; x < width; x++) 
		for (int y=0; y < height; y++) {
			r[x][y] = (short)((r[x][y] + child.r[x][y]) / 2);
			g[x][y] = (short)((r[x][y] + child.g[x][y]) / 2);
			b[x][y] = (short)((r[x][y] + child.b[x][y]) / 2);
	    }
	short2Image();
}


private void negativeRobertsOnGreen() {
   int p[] = new int[4];
   float delta_u = 0;
   float delta_v = 0;
   for (int x=0; x < width-1; x++) 
	for (int y=0; y < height-1; y++) {
		p[0] = r[x][y];
		p[1] = r[x+1][y];
		p[2] = r[x][y+1];
		p[3] = r[x+1][y+1];
		delta_u = p[0] - p[3];
		delta_v = p[1] - p[2];
		g[x][y] = 
		  (short) (255- Math.sqrt(delta_u*delta_u + delta_v*delta_v));
   }
}

private int clip(int x) {
	if ( x < 0 ) return 0;
	return x;
}

private int distance(Edgel e, Point p) {
	int dx = e.p2.x - p.x;
	int dy = e.p2.y - p.y;
	return (int)Math.sqrt(dx*dx + dy*dy);
}

//adds an edge element to the 'expanded' list, 
//if it is within the boundaries of the image
private void expAdd(int x1, int y1, int x2, int y2, Edgel parent ) {
  if (
	(x1>=0)&&(x1<width)&&
	(x2>=0)&&(x2<width)&&
	(y1>=0)&&(y1<height)&&
	(y2>=0)&&(y2<height)) {
		Edgel e = new Edgel();
		e.setCoordinates(new Point(x1,y1), new Point(x2,y2));
		e.setParent(parent);
		e.setCost(C(e));
		expanded.addElement(e);
	}
}

//expands the current edge element in 
// three directions, depending on its 
//orientation 
private void expand(Edgel el){
	int choice=0;
	int x1, y1, x2, y2;
	expanded = new Vector();
	x1=el.p1.x;
	y1=el.p1.y;
	x2=el.p2.x;
	y2=el.p2.y;
	int dx = x2 - x1;
	int dy = y2 - y1;

	if (dx>0) choice=1;
	if (dx<0) choice=2;
	if (dy>0) choice=3;
	if (dy<0) choice=4;
	if (choice == 0) {
		System.out.println("This should never happen!! Choice=0!!");
	}
	//E = current edge
	switch( choice){
		case 1: 
			//        E
			//      -- --
			//        |
			expAdd(  x1,  y1,  x1,y1+1,el); 
			expAdd(  x1,y1+1,x1+1,y1+1,el);
			expAdd(x1+1,y1+1,x1+1,  y1,el);
			break;
		case 2:
			//        |
			//      -- --
			//        E
			expAdd(  x1,  y1,  x1,y1-1,el);
			expAdd(  x1,y1-1,x1-1,y1-1,el); //dd
			expAdd(x1-1,y1-1,x1-1,  y1,el);
			break;
		case 3:
			//        |
			//      -- E
			//        |
			expAdd(  x1,  y1,x1-1,  y1,el); //dd
			expAdd(x1-1,  y1,x1-1,y1+1,el);
			expAdd(x1-1,y1+1,  x1,y1+1,el);
			break;
		case 4:
			//        |
			//       E --
			//        |
			expAdd(  x1,  y1,x1+1,  y1,el);
			expAdd(x1+1,  y1,x1+1,y1-1,el);
			expAdd(x1+1,y1-1,  x1,y1-1,el); //dd
			break;
	}
}


//returns the edge with the lower cost that has been marked as OPEN
public Edgel minOpenNode(){
	if (nodes.size() == 0)	return null;
	int minCost = 1000000;
	Edgel lowCostNode = new Edgel();
	lowCostNode.setType(Edgel.OPEN);
	lowCostNode.setCost(minCost);
	for (int i=0; i< nodes.size(); i++){
		Edgel el = (Edgel) nodes.elementAt(i);
		if (el.getType()!=Edgel.OPEN)  continue;
		if (el.getCost() > lowCostNode.getCost()) continue;
		lowCostNode=el;
	}
	if (lowCostNode.getCost()==minCost) return null;
	return lowCostNode;
}

public int countPath(Edgel pos){
	int i=0;
	while (pos!=null){
		i++;
		pos=pos.getParent();
	}
	return i;
}

public Polygon getPath(Edgel pos){
	Polygon p = new Polygon();
	while (pos!=null){
		p.addPoint(pos.p2.x,pos.p2.y);
		pos=pos.getParent();
	}
	return p;
}

public void drawPath(Edgel pos) {
	Polygon p = getPath(pos);
	//printPoly(p);
	drawPoly(p);
}

//clears the red plain so that the edge can be draw with it
//public void clearRedPlane2() {
//	r = new short[width][height];
//}

//returns the maximum difference between two adjacent points in the image
// --key number for the cost function
private int computeMaxI() {
	int maxi =-10;
	for(int i=0;i<width-1;i++)
		for (int j=0;j<height-1;j++){
			int tmpdiff=Math.abs(g[i][j]-g[i+1][j]);
			if (tmpdiff>maxi) maxi=tmpdiff;
		}
	return maxi;
}

private void initialize() {
	nodes = new Vector();
	expanded = new Vector();
	MaxI=0;
}

private void processUserPoints(Vector v){
	if (v.size() < 2) {
		System.out.println("Select start and end point(s)");
		return;
	}
	if (child == null) {
		System.out.println("You need a child frame for a cost matrix"
			+"\ncreating one for you!");
		copyToChildFrame();
		child.roberts2();
		child.negate();
	}
	setPolyList( new Vector());
	Point r = (Point) v.elementAt(0);
	Vector polyList = getPolyList();
	for (int i = 1; i < v.size(); i++) {
		Point r2 = (Point) v.elementAt(i);
		endPoint = new Point(r2.x,r2.y);
		Polygon p1 = getPath(
			searchFromPoint(new Point(r.x,r.y)));
		r = r2;
		polyList.addElement(p1);
	}

}

private Point endPoint = null;

// the A* algorithm
public Edgel searchFromPoint(Point startPoint){
	initialize();
	Edgel lowestCostNode;

	//get Maxi, the highest difference between two adjacent pixels
	MaxI = computeMaxI();
	//A* starts here
	Edgel s = new Edgel(); 
	s.setCoordinates(startPoint, 
		new Point(
			startPoint.x+1,
			startPoint.y)); 
	s.setType(Edgel.OPEN); 
	nodes.addElement(s); 
	while ((lowestCostNode = minOpenNode()) != null) {
		if (terminateSearch(lowestCostNode)) break;	
		lowestCostNode.setType(Edgel.CLOSED);
		expand(lowestCostNode);
		if (expanded.size() == 0) continue;
		processExpandedNodes();
		drawTracks(lowestCostNode);
	} 
	lowestCostNode = minOpenNode();
 	//System.out.println("ply for lcn="+lowestCostNode.getPly());
 	//System.out.println("cost for lcn="+lowestCostNode.getCost());
 	return lowestCostNode;
} // end martelli

// Let the user see the search run...
// great fun!
private void drawTracks(Edgel lowestCostNode) {
	if (child == null) System.out.println(
		"You need a child frame for a cost matrix");
	Graphics g = child.getGraphics();
	g.setColor(Color.red);
	g.setXORMode(Color.red);
	g.drawOval(lowestCostNode.p1.x-1,lowestCostNode.p1.y-1,1,1);
}

private void processExpandedNodes() {
	for (int i=0; i < expanded.size();i++){
		Edgel e = (Edgel) expanded.elementAt(i);
		Edgel MarkedNode=getMarked(e);
		if (MarkedNode==null) {
			nodes.addElement(e);
			continue;
		}
		if (e.getCost()<MarkedNode.getCost())
			MarkedNode = e;
	} 
}

private Point getMaxPoint() {
	int max = -10;
	Point p=null;
	for (int x=0; x < width-1; x++) 
		for (int y=0; y < height; y++) {
			if (Math.abs(g[x][y]-g[x+1][y]) > max) {
				p = new Point(x,y);
				max = Math.abs(g[x][y]-g[x+1][y]);
			}
		}
	return p;
}



private Edgel pathStart = null;

public void actionPerformed(ActionEvent e) {

	if (match(e, averageWithChild_mi)) {
		averageWithChild();
		return ;
		}
	if (match(e, negativeRobertsOnGreen_mi)) {
		negativeRobertsOnGreen();
		return ;
		}
	if (match(e, printPath_mi)) {
		drawPath(pathStart);
		return ;
		}
	if (match(e, processUserPoints_mi)) {
		System.out.println("shapes.size()="+shapes.size());
		t.start();
		processUserPoints(shapes);
		t.print("Martelli done");
		drawPolys();
		return ;
		}
	super.actionPerformed(e);
	}
}


