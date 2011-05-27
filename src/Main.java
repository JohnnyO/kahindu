import gui.*;
import java.applet.*; 
/**
 *  The main class for
 *  starting Kahindu Vision
 *  @author Douglas Lyon
 *  @version 1.9
 *  @parm args String[] is ignored.
 *  @return void
 *  @see gui.TopFrame.main
 *  @exception OutOfMemoryError too many open frames
 *  Copyright 1998.
 *  Send e-mail to lyon@DocJava.com
 *  for licensing for commercial use.
 *
 *  This software is shareware
 *  for non-commercial use.
 *
 *  COMPILATION INSTRUCTIONS  
 *     UNIX WINDOWS 95/98/NT:
 *       javac Main.java * /*.java
 *           "remove space^"
 *       java Main
 *     IDES:
 *       drag src folder to 
 *       IDE project window and
 *       recompile. The target
 *       is 'Main'
 *     
 */
public class Main extends Applet {
/**
	@args - unprocessed
*/
	private static final  String title[] = {"v2.7 Kahindu by Douglas Lyon"};
	
	public void init() {
	 	TopFrame.main(title);
	 
  	}
	public static void main(String args[]) {
		TopFrame.main(title);
  	}		
}
