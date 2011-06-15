package gui;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

public class AnimateFrame 
	extends ClosableFrame {
	
  private Vector imageVector = 
  	new Vector();
  private int imageIndex =0;
	AnimateFrame() {
		super("AnimateFrame");
	}
	
	public void addImage(Image img) {
		imageVector.addElement(img);
	}
	public void update(Graphics g) {
		paint( g);
		// get rid of flicker
	}
	

	public void paint(Graphics g) {
		if (imageVector.size() == 0) return;
		g.drawImage(
			(Image)
			imageVector.elementAt(imageIndex++),0,0,null);
		if (imageIndex == imageVector.size()-1) 
			imageIndex =0;
		repaint(33);
	}
}
