package gui;
import java.awt.*;import java.awt.event.*;

public class DrawTest extends Frame {
	Timer t = new Timer();
	
	Polygon p = new Polygon();
	int w = 100;
	int h = 200;
	
	Color colors[] = new Color[100];
	
	void initPolygon() {
		w = getSize().width;
		h = getSize().height;
		for (int i=0; i < 3; i++)
			p.addPoint(random(w-1),random(h-1));
	}
	
	public int random(int r) {
		return (int) (Math.floor(Math.random()*r));
	}
	
	public static void main(String args[]) {
		DrawTest dt = new DrawTest();
		dt.testInitPolygon(1000);
	}
	
	DrawTest() {
		setSize(w,h);
		setVisible(true);
		for (int i=0; i < colors.length; i++) {
			colors[i] = new Color(random(255),random(255),random(255));
		}
		initPolygon();
	}
	public void testPolys(Graphics g, int N) {
		for (int i=0; i < N; i++) {
			g.drawPolygon(p);
			g.setColor(
				new Color(random(255),random(255),random(255)));
			initPolygon();
		}

	}
	public void testPolys1(Graphics g, int N) {

		for (int i=0; i < N; i++) {
			g.fillPolygon(p);
			g.setColor(
				colors[random(colors.length)]);
			initPolygon();
		}

	}
	
	public void testInitPolygon(int N) {
		t.start();
		for (int i = 0; i < N; i++) 
			initPolygon();
		t.print(N+" rans of init polygon in");
	}
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		int N = 90;
		g.clearRect(0,0,w,h);
			t.start();
			testPolys1(g,N);
			
			t.print(N+" testPolys1 took");
			g.clearRect(0,0,w,h);
			t.start();
			testPolys(g,N);
			t.print(N+" testPolys took");

	}
			
}