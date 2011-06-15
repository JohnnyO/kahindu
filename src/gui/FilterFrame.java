package gui;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;

public class FilterFrame extends ImageFrame {

 Menu filterMenu = new Menu("Filter");

 Menu rgbMenu = new Menu("RGBto");
 MenuItem gray_mi =
 	addMenuItem(rgbMenu,"[E-g]ray");
	
public void actionPerformed(ActionEvent e) {
	if (match(e,gray_mi)) {
		gray();
		return;
	}
	super.actionPerformed(e);
}
public void gray() {
   for (int x=0; x < width; x++) 
	for (int y=0; y < height; y++) {
		r[x][y] = (short)
		   ((r[x][y] + g[x][y]  + b[x][y]) / 3);
      	g[x][y] = r[x][y];
      	b[x][y] = r[x][y];
	}
	short2Image();
}

 public FilterFrame(String title) {
	super(title);
	MenuBar menuBar = getMenuBar();
	filterMenu.add(rgbMenu);
	menuBar.add(filterMenu);
	setMenuBar(menuBar);
	//filterMenu.addActionListener(this);
	//rgbMenu.addActionListener(this);
 }
  
  public static void main(String args[]) {
	FilterFrame imgFrm = 
		new FilterFrame("FilterFrame");
  }
}




