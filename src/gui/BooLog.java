package gui;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
	BooLog - the boolean dialog
*/
public class BooLog extends Dialog 
	implements ActionListener {

 private Label label;
 public Button yesButton;
 public Button noButton;

 
 private boolean ok = false;
 private boolean done = false;
 private Panel buttonPanel = new Panel();
 

 public static void main(String args[]) {
 	BooLog bl = new BooLog(
 		new Frame(),
 		"Boo!", 
 		"Scared ya??", 
 		"ok", "cancel");
 }
 
 public BooLog(
  	Frame frame,
 	String title, 
 	String prompt, 
 	String yes, String no) {    	
    super(frame, title, true);
    label = new Label(prompt);
    yesButton = new Button(yes);
    noButton = new Button(no);
    setButtonPanel();
}
  
 private void setButtonPanel() {
 	buttonPanel.setLayout(
 		new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.add(label);
	buttonPanel.add(noButton);
	buttonPanel.add(yesButton);
	noButton.addActionListener(this);
	yesButton.addActionListener(this);
	add("South", buttonPanel);
 	pack();
	show();
 }
 
	public boolean getUserInput() {
 		return ok;
 	}
   	public void actionPerformed(ActionEvent e) {
    	Object arg = e.getSource();
   		if (arg == yesButton) ok = true;
   		System.out.println("Input="+ok);
   	
   		done = true;
   		setVisible(false);
   }
}
