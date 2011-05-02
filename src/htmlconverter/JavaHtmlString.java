package htmlconverter;
import java.io.*;

public class JavaHtmlString {

	String pref;
	String postf;

	static String[] color = {
		"Red",		"\"#FF0000\"",
		"Green",	"\"#00FF00\"",
		"Blue",		"\"#0000FF\"",
		"Yellow",	"\"#FFFF00\"",
		"Magenta",	"\"#FF00FF\"",
		"Cyan",		"\"#00FFFF\"",
		"Black",	"\"#000000\"",
		"White",	"\"#FFFFFF\""
	};
	
	JavaHtmlString() {
		pref = "";
		postf = "";
	}
	
	void setColor(String c) {
		int i;
		
		for (i = 0; i < color.length; i += 2)
			if (c.equals(color[i])){
				pref += "<FONT COLOR=" + color[i + 1] + ">";
				postf += "</FONT>";
			}
	}
	
	void setColorForMainText(String c) {
		int i;
		
		for (i = 0; i < color.length; i += 2)
			if (c.equals(color[i])){
				pref += "<BODY TEXT=" + color[i + 1] + ">";
			}
	}

	
	void setStyle(String s) {
		if (s.equals("Bold")) setBold();
		else if (s.equals("Italic")) setItalic();
	}
	
	void setBold() {
		pref += "<B>";
		postf += "</B>";
		return;
	}
	
	void setItalic() {
		pref += "<I>";
		postf += "</I>";
		return;
	}
	
}	
