package gui;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ClosableFrame extends Frame
	implements WindowListener { 
// constructor needed to pass 
// window title to class Frame
public  ClosableFrame(String name) {
	// call java.awt.Frame(String) constructor
	super(name);
	setBackground(Color.white);
	addWindowListener(this);
}



public static void main(String args[]) {
	ClosableFrame cf = new ClosableFrame("Closable Frame");
	cf.setSize(200,200);
	cf.show();
}


public void windowClosing(WindowEvent e) {
	dispose();
}
public void windowClosed(WindowEvent e) {};
public void windowDeiconified(WindowEvent e) {};
public void windowIconified(WindowEvent e) {};
public void windowActivated(WindowEvent e) {};
public void windowDeactivated(WindowEvent e) {};
public void windowOpened(WindowEvent e) {};

	


}   // end class ClosableFrame

