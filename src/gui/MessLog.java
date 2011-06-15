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
	MessLog - the message dialog
*/
public class MessLog extends Dialog implements ActionListener {
 private static boolean modal = true;
 private boolean done = false;
 public Button yesButton = new Button("OK");
 public boolean ok = false;
	

 private Label label;
 private Panel buttonPanel = new Panel();
 
 public static void main(String args[]) {
 	MessLog bl = new MessLog(
 		new Frame(),
 		"MessLog!", 
 		"An informational message...");
 }
 
  public MessLog(Frame frame, String title, String prompt) {    	
	super(frame, title, modal);
    label = new Label(prompt);
 	buttonPanel.setLayout(
 		new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.add(label);
	buttonPanel.add(yesButton);
	yesButton.addActionListener(this);
	add("South", buttonPanel);
 	pack();
	show();

 }
 
  public void actionPerformed(ActionEvent e) {
   	if (e.getSource() == yesButton) ok = true;
   	
   	done = true;
   	setVisible(false);
   	return;
   }
   

}
