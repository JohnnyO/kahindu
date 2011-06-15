package htmlconverter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaStream extends BufferedReader 
	implements JavaText, CText, CplusplusText {

	JavaHtmlString mainText = new JavaHtmlString();
	JavaHtmlString comments = new JavaHtmlString();
	JavaHtmlString strings = new JavaHtmlString();
	JavaHtmlString keywords = new JavaHtmlString();
	boolean lineNumbers = false;
	boolean justOutputPublic = false;
	DataOutputStream dos;
	public static String[] reservedWords = javaReservedWords;
		
	JavaStream(DataInputStream s0, DataOutputStream s1) {
		//super(new BufferedInputStream(s0));
		super(new InputStreamReader(s0));
		dos = s1;
	}
	
	public void convertToHtml() {
		int index;
		boolean isCommentedOut = false;
		boolean isQuoted = false;
		boolean isSingleQuoted = false;
		String line;
		String str1, str2;
		int lineNumber = 1;
				
	try {
		while ((line = readLine()) != null) {
			if (justOutputPublic &&
				(line.lastIndexOf("public") == -1)
				)
				continue; 
				// only output public classes	 

			// output a line number for each line.
			if (lineNumbers)
				line = lineNumber + ".\t" + line;
			lineNumber++;
				
			// for replacing \t with four spaces
			for (int counter = 0; counter < line.length(); counter++) {
				index = line.indexOf("\t", counter);
				if (index < 0) break;
				// if \t is not find then break this loop
				line = line.substring(0, index) + "    " 
						+ line.substring(index + 1, line.length());
											// replace \t to four spaces;
				counter = index + 4;
											// update counter
			}
					
			// for replacing "<" to "&lt;"
					
			for (int counter = 0; counter < line.length(); counter++) {
					index = line.indexOf("<", counter);
					if (index < 0) break;
					// if < is not find then break this loop
					line = line.substring(0, index) + "&lt;" 
						+ line.substring(index + 1, line.length());
					// replace < to &lt;
					counter = index + 4;
					// update counter
			}
					
			while (line != "") {
				if (isCommentedOut) {
					index = line.indexOf("*/");
					if (index >= 0) {	
						// if "*/" exists then
						isCommentedOut = false;
						dos.writeBytes(line.substring(0, index) + "*/" 
								+ comments.postf);
						if (index + 2 < line.length())
							line = line.substring(index + 2, line.length());
						else break;
						// break this loop
					} else {
							dos.writeBytes(line);
							break;		// break this loop and get next line
						}
					} else if (isQuoted) {
						index = line.indexOf("\"");
						if (index == 0 || index > 0 && line.indexOf("\\") != index - 1) {	
											// if "\"" exists then
							isQuoted = false;
							dos.writeBytes(line.substring(0, index) + "\"" + strings.postf);
							if (index + 1 < line.length())
								line = line.substring(index + 1, line.length());
							else
								break;		// break this loop and get next line
						} else if (index > 0 && line.indexOf("\\") == index - 1) {
							dos.writeBytes(line.substring(0, index + 1));
							line = line.substring(index + 1, line.length());
						}
					} else if (isSingleQuoted) {		// added this block on 11/4
						index = line.indexOf("'");
						if (index == 0 || index > 0 && line.indexOf("\\") != index - 1) {	
												// if "'" exists then
							isSingleQuoted = false;
							dos.writeBytes(line.substring(0, index) + "'");
							if (index + 1 < line.length())
								line = line.substring(index + 1, line.length());
							else
								break;		// break this loop and get next line
						} else if (index > 0 && line.indexOf("\\") == index - 1) {
							dos.writeBytes(line.substring(0, index + 1));
							line = line.substring(index + 1, line.length());
						}
					} else {
						int index1 = line.indexOf("/*");
						int index2 = line.indexOf("//");
						int index3 = line.indexOf("\"");
						int index4 = line.indexOf("'");	// added on 11/4
						if (index1 < 0) index1 = 99999;
						if (index2 < 0) index2 = 99999;
						if (index3 < 0) index3 = 99999;
						if (index4 < 0) index4 = 99999;	// added on 11/4
							
												// if "/*" is lefter than "//" and "\""
						if (index1 < index2 && index1 < index3 && index1 < index4) {
														// modified on 11/4
							isCommentedOut = true;
							if (index1 > 0)
								print(line.substring(0, index1), 0, 0);
							dos.writeBytes(comments.pref + "/*");	
							line = line.substring(index1 + 2, line.length());
												// if "//" is lefter than "/*" and "\""
						} else if (index2 < index1 && index2 < index3 && index2 < index4) {
														// modified on 11/4
							if (index2 > 0)
								print(line.substring(0, index2), 0, 0);
							dos.writeBytes(comments.pref + "//" 
								+ line.substring(index2 + 2, line.length()) + comments.postf);
							break;			// break this loop and get next line
												// if "\"" is lefter than "//" and "/*"
						} else if (index3 < index1 && index3 < index2 && index3 < index4) {
														// modified on 11/4
							isQuoted = true;
							if (index3 > 0)
								print(line.substring(0, index3), 0, 0);
							dos.writeBytes(strings.pref + "\"");	
							line = line.substring(index3 + 1, line.length());
						} else if (index4 < index1 && index4 < index2 && index4 < index3) {
														// added on 11/4
							isSingleQuoted = true;
							if (index4 > 0)
								print(line.substring(0, index4), 0, 0);
							dos.writeBytes("'");	
							line = line.substring(index4 + 1, line.length());
						} else {
							print(line, 0, 0);
							break;
						}
					}
				}
				dos.writeBytes("\n");
			}					
		} catch (IOException e) {
		}
	}
	
	
	public void print(String line, int offset, int index) {
		try {
			if (index >= reservedWords.length) {
				dos.writeBytes(line);
				return;
			}
			
			if (offset == 0) 
				offset = line.indexOf(reservedWords[index]);
				
			if (offset >= 0) {
				char beforeKeyword = ' ';
				char afterKeyword = ' ';
					
				if (offset - 1 >= 0) 
					beforeKeyword = line.charAt(offset - 1);
				if (offset + reservedWords[index].length() < line.length()) 
					afterKeyword = line.charAt(offset + reservedWords[index].length());
				
				switch (beforeKeyword) {
				case ' ':
				case '\t':
				case '\r':
				case '(':
				case '{':
				case ')':
				case ':':
				case ';':
				case '.':
				case ',':
					switch (afterKeyword) {
					case ' ':
					case '\t':
					case '\r':
					case '(':
					case '{':
					case ')':
					case ':':
					case ';':
					case '.':
					case ',':
						String str1 = line.substring(0, offset);
						String str2 = line.substring(offset + reservedWords[index].length(),
							line.length());
						line = str1 + keywords.pref + reservedWords[index] + keywords.postf + str2;
						
						offset = line.indexOf(reservedWords[index], 
							offset + keywords.pref.length() + reservedWords[index].length() 
							+ keywords.postf.length());

						print(line, offset, index);
						return;
					}
				}
			}
			print(line, 0, index + 1);			
		} catch (IOException e) {
		}
		return;
 	}
	
}
