package gui;
import java.awt.event.ActionEvent;
public class GrayLog extends DoubleLog {
private NegateFrame parent;

public GrayLog(
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

public void actionPerformed(ActionEvent e)  {
  if (e.getSource() == setButton) {
  	double dui[] = getUserInputAsDouble();
  	parent.linearTransform(dui[0],dui[1]);
  	return ;
  }
	super.actionPerformed(e);
	  }
}
