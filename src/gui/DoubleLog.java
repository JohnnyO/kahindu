package gui;
import java.awt.Frame;
public class DoubleLog extends 
	ExpandoLog  {

private String defaultStr[];

public DoubleLog(
		Frame frame,
		String title, 
		String prompts[], 
		String defaults[], 
		int fieldSize) {	
	super(frame, title, prompts, defaults,fieldSize);
}
    public static void main(String args[]) {
    	String title = "Double Dialog";
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
        
        DoubleLog dl = new 
            	DoubleLog(
            		new Frame(),
            		title, 
            		prompts,
             		defaults,
            		fieldSize);
            		
     }

public double[] getUserInputAsDouble() {
  String userInput[] = super.getUserInput();
  double dui[] = new double[userInput.length];
  int i=0;
  try {
	for ( i=0; i < userInput.length; i++) {
		Double d = Double.valueOf(userInput[i]);
		dui[i] = d.doubleValue();
	}
   }
   catch (NumberFormatException e) {
  	MessLog ml = 
  	 new MessLog(
  	 	null,
  	 	"Input Error:",
  	 	"Could not convert to Double");
  }
	return dui;
}

}
