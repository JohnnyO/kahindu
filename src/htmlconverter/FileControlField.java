package htmlconverter;
import java.awt.*;import java.awt.event.*;

public class FileControlField extends Panel {

	TextField tf;
	
	FileControlField(String str0, String str1) {
		tf = new TextField(str1, 31);
		setLayout(new GridLayout(2, 1));
		add(new Label(str0, Label.LEFT));
		add(tf);
	}
	
	public String getText() {
		return tf.getText();
	}
	
	public void setText(String t) {
		tf.setText(t);
	}

}
