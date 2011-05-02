package dclap;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import gui.*;

public class PictFrame extends ShortCutFrame {
	private	static final String args[] = {""};

	private MenuBar mb = getMenuBar();
	private Menu m = new Menu("Save Menu");
	private MenuItem save_mi = 
		new MenuItem("[S]save as pict");
		
	public PictFrame() {
		super("PictFrame");
		if (mb == null) {
			mb = new MenuBar();
			setMenuBar(mb);
		}
		m.add(save_mi);	
		mb.add(m);

	}
	
	public PictFrame(String name) {
		super(name);
		if (mb == null) {
			mb = new MenuBar();
			setMenuBar(mb);
		}
		m.add(save_mi);	
		mb.add(m);
		
	}
	
	public static FileOutputStream getFileOutputStream() {
    FileOutputStream fos = null;
    try {fos =  
    		new FileOutputStream(getWriteFileName());
    	}
    catch (IOException e) {
    		System.out.println("futil:Could not create file");
    	}
    	return fos;
    }
 public   static  String getWriteFileName() {
   	     	FileDialog dialog = new FileDialog(new Frame(), "Enter file name",FileDialog.SAVE);
      		dialog.show();
      		String fs = dialog.getDirectory() + dialog.getFile();
      		System.out.println("Opening file: "+fs);
      		dialog.dispose();
      		return FilterFileNameBug(fs);
   }
// Some versions of windows will
// create a .* suffix on a file name
// The following code will strip it:
public static String FilterFileNameBug(String fname) {
               if (fname.endsWith(".*.*")) {
		  fname=fname.substring(0, fname.length() - 4);
		}
    return fname;
}

	public void actionPerformed(ActionEvent e) {


  		if (match(e,save_mi)) {
    			Component c = (Component) this;
    			OutputStream os = (OutputStream) getFileOutputStream();
    			//SavePICT p = new SavePICT();
    			//p.saveAsPict( c,os);
    			return ;
    		}	
 		super.actionPerformed(e);
 	}
}