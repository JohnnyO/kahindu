package htmlconverter;
import java.awt.*;import java.awt.event.*;

public class FileControlPanel extends Panel {

	FileControlField fcf1, fcf2;
	Checkbox justOneCB = new Checkbox("process Just one file");
	FileControlPanel() {
		fcf1 = new FileControlField("Input :", "");
		fcf2 = new FileControlField("Output:", "");
		setLayout(new GridLayout(2, 1));
		
		add(fcf1);
		
		add(fcf2);
		
	}
// WHY IS THIS DONE DL 4/26/98	
//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == justOneCB) return true;
//		super.actionPerformed(e);  

//	}
	
	public String[] getFileNames() {
		String[] s = new String[2];
		s[0] = fcf1.getText();
		s[1] = fcf2.getText();
		return s;
	}
	
	public void setText(String t) {
		fcf1.setText(t + ((HtmlGenerator)
			getParent()).getExtention());
		fcf2.setText(t + ".html");
	}

}
