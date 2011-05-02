package gui;
import java.awt.*;
import java.awt.event.*;
public class AdaptiveLog extends DoubleLog {
private NegateFrame parent;

public AdaptiveLog(
		NegateFrame _parent,
		String title, 
		String prompts[], 
		String defaults[], 
		int fieldSize) {	
	super(_parent, title, prompts, defaults,fieldSize);
	parent = _parent;
	}

public static void doit(NegateFrame _parent) {
	String title = "Adaptive Histogram Equalization";
	String prompts[] ={"rows=","cols="};
	String defaults[] ={"2","2"};
	int fieldSize = 5;
	AdaptiveLog al = 
		new AdaptiveLog(
			_parent, title, prompts, defaults, fieldSize);
	}

public void actionPerformed(ActionEvent e) {
  if (e.getSource() == setButton) {
  	double dui[] = getUserInputAsDouble();
  	parent.drawMosaic((int)dui[0],(int)dui[1]);
  	return;
  }
  super.actionPerformed(e);
}

}