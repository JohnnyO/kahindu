package gui;
import java.awt.Frame;
import java.awt.event.ActionEvent;
public class DoLog extends DoubleLog {
private Doable doAble;

public DoLog(
		Doable d,
		String title, 
		String prompts[], 
		String defaults[], 
		int fieldSize) {	
	super(new Frame(), title, prompts, defaults,fieldSize);
	doAble =d;
	setButton.addActionListener(this);
	cancelButton.addActionListener(this);
}

public void actionPerformed(ActionEvent e) {
  if (e.getSource() == setButton) {
  	doAble.doit(getUserInputAsDouble());
  	return;
  }
	super.actionPerformed(e);
 }

}
