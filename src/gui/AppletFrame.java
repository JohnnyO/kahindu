
package gui;

import java.awt.*;import java.awt.event.*;
import java.applet.Applet;


// Applet to Application Frame window
public class AppletFrame extends ClosableFrame {

public static void startApplet(Applet a) {
		Dimension appletSize;
		// initialize the applet
		// create new application frame window
		AppletFrame f = new AppletFrame("Applet Frame");
	
		// add applet to frame window
		f.add("Center", a);
	

		// resize frame window to fit applet
		// assumes that the applet sets its own size
		// otherwise, you should set a specific size here.

		f.pack();
		a.init();
		a.start();
		appletSize =  a.getSize();

		f.setSize(appletSize);
		f.show();

		// show the window


}
public static void startApplet(String className) {
	String args[] = {""};
	startApplet(
		className,
		className,
		args);
}

	public static void startApplet(String className, 
	    String title, String args[]) {
		Applet a;
		Dimension appletSize;

		try {
			// create an instance of your applet class
			a = (Applet) Class.forName(className).newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException in AppletFrame");
			return;
		} catch (InstantiationException e) {
			System.out.println("InstantiationException in AppletFrame");
			return;
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException in AppletFrame");
			return;
		}

		// initialize the applet
		a.init();
		a.start();
	
		// create new application frame window
		AppletFrame f = new AppletFrame(title);
	
		// add applet to frame window
		f.add("Center", a);
	
		// resize frame window to fit applet
		// assumes that the applet sets its own size
		// otherwise, you should set a specific size here.
		appletSize =  a.getSize();
		f.pack();
		f.setSize(appletSize);
		f.setSize(200,200);

		// show the window
		f.show();
	
	}  // end startApplet()


	// constructor needed to pass window title to class Frame
	public AppletFrame(String name) {
		// call java.awt.Frame(String) constructor
		super(name);
	}

}   // end class AppletFrame

