package gui;
import java.awt.FileDialog;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import vs.WritePPM;

public class SaveFrame extends NegateFrame {
	Menu saveMenu = new Menu("Save");
	
	MenuItem saveAsJava_mi = 
		addMenuItem(saveMenu, "[T-j]ava...");
	MenuItem printIcon_mi = 
		addMenuItem(saveMenu, "binary icon...");
		
	MenuItem saveAsGif_mi = 
		addMenuItem(saveMenu, "[T-g]if...(not 24 bit!)");
	MenuItem saveAsPPM_mi = 
		addMenuItem(saveMenu, "[T-p]pm...");
	MenuItem saveAsPPMgz_mi = 
		addMenuItem(saveMenu, "[T-z]ppm.gz...");
	MenuItem saveAsShortgz_mi = 
		addMenuItem(saveMenu, "[T-s]short.gz...");
	MenuItem saveAsShortZip_mi = 
		addMenuItem(saveMenu, "[T-i]saveAsShortZip...");
	MenuItem makeTocHtml_mi = 
		addMenuItem(saveMenu, "makeTocHtml...");
		
		
		
	
	public void actionPerformed(ActionEvent e) {

		if (match(e, makeTocHtml_mi)) {
			makeTocHtml();
			return;
		}
		if (match(e, saveAsShortZip_mi)) {
			saveAsShortZip();
			return;
		}
		if (match(e, printIcon_mi)) {
			printIcon();
			return;
		}

		if (match(e, saveAsShortgz_mi)) {
			saveAsShortgz();
			return;
		}
		if (match(e, saveAsPPMgz_mi)) {
			saveAsPPMgz();
			return;
		}
		if (match(e, saveAsPPM_mi)) {
			saveAsPPM();
			return;
		}
		if (match(e, saveAsJava_mi)) {
			saveAsJava();
			return;
		}
		if (match(e, saveAsGif_mi)) {
			saveAsGif();
			return;
		}
		super.actionPerformed(e);  

	}
	
	SaveFrame(String title) {
		super(title);
		fileMenu.add(saveMenu);
	}
	
public static void main(String args[]) {
	System.out.println("Test");
	SaveFrame f = new SaveFrame("save frame");
	f.setVisible(true); 		
}



public String getSaveFileName(String prompt) {
		FileDialog fd = new 
		FileDialog(this, prompt, FileDialog.SAVE);
	fd.setVisible(true);
	fd.setVisible(false);
	String fn=fd.getDirectory()+fd.getFile();
	if (fd.getFile() == null) return null; //usr canceled
	return fn;
}
public String getSaveDirectoryName(String prompt) {
		FileDialog fd = new 
		FileDialog(this, prompt, FileDialog.SAVE);
	fd.setVisible(true);
	fd.setVisible(false);
	return fd.getDirectory();
}

public void saveAsPPM() {
	String fn = getSaveFileName("Save as PPM");
	if (fn == null) return;
	saveAsPPM(fn);

}

public void saveAsPPM(String fn) {
	vs.WritePPM.doIt(r, g, b, fn);	
}

public void saveAsPPMgz(String fn) {
    WritePPM wppm = new WritePPM(width, height);
    Timer t = new Timer();
    t.start();
    try {
         GZIPOutputStream os = new GZIPOutputStream(
            new FileOutputStream(fn));
         wppm.header(os);
         wppm.image(os, r, g, b);
         os.finish();
         os.close();
        } catch(Exception e) {
         System.out.println("Save PPM Exception - 2!");
        }
     t.stop();
     t.print(" saveAsPPMgz in "); 	
}
public void saveAsPPMgz() {
	String fn = getSaveFileName("Save as PPM.gz");
	if (fn == null) return;
	saveAsPPMgz(fn);
}
public void saveAsShortgz() {
	String fn = getSaveFileName("Save gz");
	if (fn == null) return;
	saveAsShortgz(fn);
}
public void saveAsShortZip() {
	String fn = getSaveFileName("Save ZIP");
	if (fn == null) return;
	saveAsShortZip(fn);
}
public void saveAsShortZip(String fn) {
    Timer t = new Timer();
    t.start();
    try {
    	FileOutputStream fos = new FileOutputStream(fn);
        ZipOutputStream zos = new ZipOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(zos);
     	ZipEntry ze = new ZipEntry("quad0");
     	ze.setMethod(ZipEntry.DEFLATED);
      	zos.putNextEntry(ze);
      	writeHeader(dos);
      	writeArray(r,dos);
    	writeArray(g,dos);
    	writeArray(b,dos);
        zos.close();
        zos.finish();

        } catch(Exception e) {
         System.out.println("Save saveAsShortZip:"+e);
        }
     t.stop();
     t.print(" saveAsShortZip in "); 	
}
public void writeArray(short a[][], DataOutputStream dos) 
	throws IOException {
	for (int x=0; x < width; x++)
		for (int y=0; y < height; y++)
			dos.writeShort(a[x][y]);
}
public void writeHeader(DataOutputStream dos) 
	throws IOException {
	dos.writeInt(width);
	dos.writeInt(height);
}

public void saveAsShortgz(String fn) {
    Timer t = new Timer();
    t.start();
    try {
    	FileOutputStream fos = new FileOutputStream(fn);
        GZIPOutputStream gos = new GZIPOutputStream(fos);
     	ObjectOutputStream oos = new ObjectOutputStream(gos);
         oos.writeObject(r);
         oos.writeObject(g);
         oos.writeObject(b);
         oos.close();
         gos.finish();

        } catch(Exception e) {
         System.out.println("Save saveAsShortgz:"+e);
        }
     t.stop();
     t.print(" saveAsShortgz in "); 	
}

public void saveAsGif() {
	System.out.println("Saving as gif...");
	String fn = getSaveFileName("Save as GIF");
	if (fn == null) return;
	saveAsGif(fn);
}

public void saveAsGif(String fn) {
	try {
		vs.WriteGIF.DoIt( 
				getImage(), 
        		fn);
    } catch (Exception e) {e.printStackTrace();}
}
		
public void saveAsJava() {
	String fn = getSaveFileName("Save as Java");
	if (fn == null) return;
	saveAsJava(fn);
}
 public void saveAsxyz() {
   	String fn = getSaveFileName("Save as GIF");
	if (fn == null) return;
	saveAsxyz(fn);

 }
 
 public void saveAsxyz(String fn) {
	try {
		FileWriter fos = new FileWriter(fn);
		PrintWriter pw = new PrintWriter(fos);
		saveAsxyz(pw);
		pw.flush();
		fos.close();
	}
	catch (Exception e) {}	
}
public FileOutputStream getFileOutputStream(String prompt) {
    FileOutputStream fos = null;
    try {fos =  
    		new FileOutputStream(getSaveFileName(prompt));
    	}
    catch (IOException e) {
    		System.out.println("futil:Could not create file");
    	}
    	return fos;
    }

   public   void makeTocHtml() {
   	File dir = 
   	new File(getSaveDirectoryName("Enter input directory"));
   	
   	String[] files = dir.list(new FileFilter());
      	System.out.println(files.length + " file(s):");
      	FileOutputStream fos = getFileOutputStream(
      		"Enter Save File");
        PrintWriter pw = new PrintWriter(fos);
        pw.println("<HTML>");
        pw.println("<BODY>"); 
        pw.println("<ul>");
      	for (int i=0; i < files.length; i++)
      	 pw.println("<P><IMG SRC="+files[i]+"></P>"+
      	 	"<a>"+files[i]+"</a><P>");
      		//ps.println("<LI><a href = \"" + files[i]+"\">"+
      		//	files[i]+"</a><P>");
      	pw.println("</ul>");
      	pw.println("</BODY>");
      	pw.println("</HTML>");
      	try {
      		fos.close();	
      	}
      	catch (Exception e) {
      		System.out.println(e);
      	}	   		
   }
public void saveAsxyz(PrintWriter pw) {
	int y=0;
	pw.println(0+" "+0+" "+r[0][0]+" 0");
	for (int x=1; x < width; x++) {
		for (y=0; y < height; y++) {
			pw.println(x+" "+y+" "+r[x][y]+" 1");
		}
	  y=0;
	  pw.println(x+" "+y+" "+r[x][y]+" 0");
	}
}

public void saveAsJava(String fn) {
	try {
		FileWriter fos = new FileWriter(fn);
		PrintWriter pw = new PrintWriter(fos);
		saveAsJava(pw);
		pw.flush();
		fos.close();
	}
	catch (Exception e) {}	
}

	public void printIcon() {
	  int g;
	  System.out.println(
		
		"public static byte iconName[][] = {\n");
		
	  for (int y=0; y< height; y++) {
	  	System.out.print("{");
	  	for (int x=0; x < width-1; x++) {
	  		if (r[x][y] == 0) g=0;
	  		else g=1;
	  		System.out.print(g+",");
	  	} 
	  	if (r[width-1][y] == 0) g=0;
	  		else g=1;
	  		System.out.println(g+"},");

	  }
	   System.out.println("};");
	   
	}

	public void saveAsJava(PrintWriter pw) {
	  int g;
	  ProgressFrame pb = 
  			new ProgressFrame("writing java...");
  	  pb.setVisible(true);

	  pw.println("package gui;\n"
		+"\nclass NumImage {\n"
		+"\tpublic static short gray[][] = {\n");
		
	  for (int y=0; y< height; y++) {
	  	pw.print("{");
	  	for (int x=0; x < width-1; x++) {
	  		g = r[x][y];
	  		if (g <10) pw.print("  ");
	  		else if (g <100) pw.print(" ");
	  		pw.print(g+", ");
	  	}	  	
	  	pw.println(r[width-1][y]+"},");
	  	double percent = (y*1.0/height);
	  	if ((y % 5) == 0)
	  		pb.setRatioComplete(percent,
    			"Percent done:"+(int)(percent*100)+"%");
	  }
	   pw.println("};}");
	   System.out.println("Done writing image");
	   pb.setVisible(false);
	}
 class FileFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return new File(dir, name).isFile();
        }
}	

}