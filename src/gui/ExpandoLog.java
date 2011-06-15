package gui;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ExpandoLog extends 
	Dialog implements ActionListener {	
     
 private TextField fields[];
 private Label labels[];
 public  Button cancelButton = new Button("Cancel");
 public  Button setButton = new Button("Set"); 
 private String userInput;
 private int fieldSize;
 
 private static int colSpace = 5; 	
 private static	int rowSpace = 10;
 private static	int colNum = 2;
 
 private int rowNum;
 
 private Panel inputPanel = new Panel();
  
 public ExpandoLog(
 	Frame frame,
    String title, 
    String prompts[], 
    String defaults[],
    int _fieldSize) {
    	
    super(frame, title, false);
    initialize(prompts, defaults, _fieldSize);
    pack();
    show();
}

 private void initialize(
 		String prompts[], 
 		String defaults[], 
 		int _fieldSize) {
 	
 	fieldSize = _fieldSize;
	rowNum = prompts.length;
	labels = new Label[rowNum];
    fields = new TextField[rowNum];
    
	inputPanel.setLayout(new 
    	GridLayout(rowNum,colNum,colSpace,rowSpace));
    for (int i = 0;i < rowNum; i++) {
    	labels[i] = new Label(prompts[i]);
    	if (defaults == null)
        	fields[i] = new TextField(fieldSize);
        else
        	fields[i] = new TextField(defaults[i],fieldSize);
        inputPanel.add(labels[i]);
        inputPanel.add(fields[i]);
    }
    add("Center", inputPanel);


	buttonPanel();

 }
  
  
 private void buttonPanel() {
    Panel p2 = new Panel();
    p2.setLayout(new FlowLayout(FlowLayout.RIGHT));

	p2.add(cancelButton);
	p2.add(setButton);
	cancelButton.addActionListener(this);
	setButton.addActionListener(this);
	add("South", p2);
	
 	pack();
 	show();
 }
 
 public void printUserInput() {	
 	String userInput[] = getUserInput();
   	for (int i=0; i<fields.length; i++) {
   		userInput[i] = fields[i].getText();
   		System.out.println(userInput[i]);
   	}
 }

public String [] getUserInput() {
 	String userInput[] = new String[fields.length];
   	for (int i=0; i<fields.length; i++)
   		userInput[i] = fields[i].getText();
   	return userInput;
}
 
public static void main(String args[]) {
    String title = "Rotation Dialog";
    int fieldSize = 6;

    String prompts[] = {
    	"X (degs):",
    	"Y (degs):",
    	"Z (degs):"
    };
    	
    String defaults[] = {
    	"1.0",
    	"2.0",
    	"3.0"
    };
        
    ExpandoLog xpol = new 
            ExpandoLog(
            	new Frame(),
            	title,
            	prompts,
            	defaults,
            	fieldSize);
}

	public void actionPerformed(ActionEvent e) {
		Button b = (Button)e.getSource();
		if (b == cancelButton) setVisible(false);
	}

}








