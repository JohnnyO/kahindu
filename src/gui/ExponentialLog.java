package gui;
import java.awt.event.ActionEvent;
public class ExponentialLog extends 
	DoubleLog {
private NegateFrame parent;

public ExponentialLog(
		NegateFrame _parent,
		String title, 
		String prompts[], 
		String defaults[], 
		int fieldSize) {	
	super(_parent, title, prompts, defaults,fieldSize);
	parent = _parent;
	setButton.addActionListener(this);
	cancelButton.addActionListener(this);
}

public void actionPerformed(ActionEvent e) {
  if (e.getSource() == setButton) {
  	double dui[] = getUserInputAsDouble(); 
  	parent.enahe(dui[0]);
  	return;
  }
  super.actionPerformed(e);

}
}
