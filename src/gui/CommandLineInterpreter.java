package gui;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class CommandLineInterpreter 
	implements Runnable {
	
private Object parentObject;
private InputStream
	commandInputStream = System.in;

Thread t = new Thread(this);
public CommandLineInterpreter(Object o) {
	parentObject = o;
	t.start();
}

public void setInputStream(InputStream is) {
	commandInputStream = is;
}
	
 public void processCommandLine(String line) {
 	StringTokenizer st = new StringTokenizer(line);
 	while (st.hasMoreTokens()) {
 		String toks = st.nextToken();
 		Object o = null;
 		try {
 		  Class parentClass
			= parentObject.getClass();

 			Method m =
 				parentClass.getMethod(toks,new Class[]{});
 			
 			m.invoke(parentObject,null);
 		}
 		catch (Exception e) {
 			System.out.println(e);
 		}
 	}
 }
 /**
 	commandLine - this should be
 	improved using a queue of commands that
 	are executed in a thread. Thus the commandLine
 	reader could just enqueue the commands for
 	execution at a later time (when 
 	free cycles are available). As it is, a prompt
 	does not appear until the command is finished.
 	Worse, input is not accepted from the user,
 	so no type-ahead is possible. Type-ahead
 	should be possible, but I am not sure how
 	to implement it. Suggestions?
 	 - DL
 	 
 */
 public void commandLine() {
 	InputStreamReader isr =
 		new InputStreamReader(commandInputStream);
 	BufferedReader br = new BufferedReader(isr);
 	System.out.print(">");
 	String line = null;
 	try {
 		while (( line = br.readLine()) != null) { 
 			if (line.equals("quit")) 
 				return;
 			processCommandLine(line);
 	    	System.out.print(">");
 		}
 	}
 	catch (IOException e) {
 		System.out.println(e);
 	}
 }
public void run() {
	commandLine();
}
}