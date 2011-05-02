package gui;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

public class IconComponent extends Component {
	Dimension imageDim;
	Image image;
	Image invertImage;
	Image nonInvertImage;

	public boolean contains(int x, int y) {
		return
			(
			x >=0 && x < imageDim.width && 
			y >=0 && y < imageDim.height &&
			image != null);		
	}
	IconComponent(byte r[][]) {
		nonInvertImage = byte2Image(r);
		image = nonInvertImage;
		invertImage = byte2InvertImage(r);
		imageDim = new Dimension(r.length,r[0].length);
		addMouseListener(new MouseEventHandler());
		
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void invert(boolean t) {
		if (t) 
			image = invertImage;
		else image = nonInvertImage;
		repaint();
	}
	
	
	public Dimension getPreferredSize() {
		return imageDim;
	}
	
	public void paint(Graphics g) {
	if (image != null) {
		g.drawImage(image, 0,0,imageDim.width, imageDim.height, 
			 this);
		g.drawRect(0,0,imageDim.width+1, imageDim.height+1);
		}
	}
	private static Image byte2Image(byte r[][]) {

 		int w = r.length;
 		int h = r[0].length;
 		int v = 0;
 		Toolkit tk = Toolkit.getDefaultToolkit();
 		int pels[] = new int[w*h];
 		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++) {
				if (r[x][y] == 1) v = 255;
				else v = 0;
		 		pels[ y + x * h] = 
		     		0xff000000 
		     		| (v << 16) 
		     		| (v << 8) 
		     		|  v;
			}
  		Image i =tk.createImage(
				new MemoryImageSource(
					w, 
					h,
					ColorModel.getRGBdefault(), 
					pels, 0, 
					w));
		return i;
	}
	
		private static Image byte2InvertImage(byte r[][]) {

 		int w = r.length;
 		int h = r[0].length;
 		int v = 0;
 		Toolkit tk = Toolkit.getDefaultToolkit();
 		int pels[] = new int[w*h];
 		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++) {
				if (r[x][y] == 1) v = 0;
				else v = 255;
		 		pels[ y + x * h] = 
		     		0xff000000 
		     		| (v << 16) 
		     		| (v << 8) 
		     		|  v;
			}
  		Image i =tk.createImage(
				new MemoryImageSource(
					w, 
					h,
					ColorModel.getRGBdefault(), 
					pels, 0, 
					w));
		return i;
	}
	transient ActionListener actionListener;
    public synchronized void addActionListener(ActionListener l) {
		actionListener = AWTEventMulticaster.add(actionListener, l);
	}
	public synchronized void removeActionListener(ActionListener l) {
		actionListener = AWTEventMulticaster.remove(actionListener, l);
	}

	class MouseEventHandler extends MouseAdapter {
	public void mousePressed(MouseEvent e) {
		if (actionListener != null) {
			actionListener.actionPerformed(
				new ActionEvent(IconComponent.this,
					ActionEvent.ACTION_PERFORMED, null));
		}
	}
	
}

}