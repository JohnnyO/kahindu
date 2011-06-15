package htmlconverter;
import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

public class ColorControlField extends Panel {

	Choice c0, c1;
	ColorControlPanel parent;
	
	ColorControlField(ColorControlPanel ccp, String s) {
		parent = ccp;
		c0 = new Choice();
		c0.addItem("Red");
		c0.addItem("Green");
		c0.addItem("Blue");
		c0.addItem("Yellow");
		c0.addItem("Magenta");
		c0.addItem("Cyan");
		c0.addItem("Black");
		c0.addItem("White");
		
		c1 = new Choice();
		c1.addItem("Plain");
		c1.addItem("Bold");
		c1.addItem("Italic");
		
		setLayout(new GridLayout(1, 3, 10, 10));
		add(new Label(s, Label.LEFT));
		add(c0);
		add(c1);
	}
	
}
