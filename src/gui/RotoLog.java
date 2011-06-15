package gui;
import java.awt.event.ActionEvent;
public class RotoLog extends DoubleLog {
private XformFrame xf;

public RotoLog(
		XformFrame frame, 
		String title, 
		String prompts[], 
		String defaults[], 
		int fieldSize) {	
	super(frame, title, prompts, defaults,fieldSize);
	xf = frame;
	cancelButton.addActionListener(this);
	setButton.addActionListener(this);
}

public void actionPerformed(ActionEvent e) {
  if (e.getSource() == setButton) {
  	double dui[] = getUserInputAsDouble();
  	xf.turn(dui[0]);
  	return ;
  }
  	super.actionPerformed(e);
 }

}
