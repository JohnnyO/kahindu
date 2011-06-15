package gui;

// here is the frame that should always be on 
// top.
// To add frames, insert them below TopFrame.
// 8/23/98 - DL
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.Vector;
public class TopFrame extends WaveletFrame {

	Menu reflectionMenu = new Menu("reflection");
	Menu diffractionMenu = new Menu("diffraction");
	Menu movieMenu = new Menu("movies");
	Menu utilMenu = new Menu("utilities");
	
	MenuItem processDiffractionImages_mi =
		addMenuItem(diffractionMenu,"process linear images");
	MenuItem processDiffractionImage_mi =
		addMenuItem(diffractionMenu,"process edge image");
	MenuItem processCylindrical_mi =
		addMenuItem(diffractionMenu,"process cylindrical images");
	MenuItem threeDImageCylindrical_mi =
		addMenuItem(diffractionMenu,"display dgt file");

	
	MenuItem playImages_mi =
		addMenuItem(movieMenu,"playImages");
	MenuItem makeMovie_mi =
		addMenuItem(movieMenu,"makeMovie");
		
	MenuItem saveAsxyz_mi =
		addMenuItem(saveMenu,"save as x y z c");
		
	MenuItem threeDImage_mi =
		addMenuItem(fileMenu,"threeDImage");

	MenuItem benchMark_mi =
		addMenuItem(utilMenu,"bench mark");
	MenuItem rename_mi =
		addMenuItem(utilMenu,"rename");
	MenuItem ls_mi =
		addMenuItem(utilMenu,"ls");
	MenuItem commandLine_mi =
		addMenuItem(utilMenu,"command line");		
	MenuItem printInfo_mi =
		addMenuItem(reflectionMenu,"printInfo");
	MenuItem printMethods_mi =
		addMenuItem(reflectionMenu,"printMethods");
	MenuItem systemInfo_mi =
		addMenuItem(reflectionMenu,"systemInfo");

	public TopFrame(String title) {
		super(title);
		utilMenu.add(reflectionMenu);
		fileMenu.add(diffractionMenu);
		fileMenu.add(movieMenu);
		fileMenu.add(utilMenu);
		//commandLine();
	}
 public static void main(String args[]) {
 	String title = "Kahindu by D. Lyon";
 	if (args.length == 1) 
 		title = args[0];
	TopFrame tf = 
		new TopFrame(title);
	tf.pack();
	tf.setVisible(true);
	//tf.setSize(64,64);

 }
 public void actionPerformed(ActionEvent e) {

 	if (match(e,commandLine_mi)) {
 		commandLine();
 		return;
 	}
 	if (match(e,processCylindrical_mi)) {
 		processCylindrical();
 		return;
 	}
 	if (match(e,threeDImageCylindrical_mi)) {
 		threeDImageCylindrical();
 		return;
 	}
 	if (match(e,benchMark_mi)) {
 		benchMark();
 		return;
 	}
 	if (match(e,printMethods_mi)) {
 		printMethods();
 		return;
 	}
 	if (match(e,rename_mi)) {
 		rename();
 		return;
 	}
 	if (match(e,playImages_mi)) {
 		playImages();
 		return;
 	}
 	if (match(e,makeMovie_mi)) {
 		makeMovie();
 		return;
 	}
 	if (match(e,saveAsxyz_mi)) {
 		saveAsxyz();
 		return;
 	}
 	if (match(e,processDiffractionImages_mi)) {
 		processDiffractionImages();
 		return;
 	}
 	if (match(e,processDiffractionImage_mi)) {
 		processDiffractionImage();
 		return;
 	}
 	if (match(e,ls_mi)) {
 		ls();
 		return;
 	}
 	if (match(e,threeDImage_mi)) {
 		threeDImage();
 		return;
 	}
 	if (match(e,systemInfo_mi)) {
 		systemInfo();
 		return;
 	}
 	if (match(e,printInfo_mi)) {
 		printInfo();
 		return;
 	}
 	super.actionPerformed(e);
 }
 public String[] getFileNames() {
 	FileDialog fd = new FileDialog(this, "find gifs");
 	fd.show();
 	dir = fd.getDirectory();
 	FileFilter files = new FileFilter();
 	File f = new File(dir);
 	String fn[] = f.list(files);
 	for (int i=0; i < fn.length; i++) 
 		fn[i] = dir + fn[i];
 	return fn;
 }
 String dir = null;
 public void processDiffractionImage() {
  		 erode(kh);
 		//System.out.println("Erode h done...");
 		roberts2();
 		//System.out.println("Robert2 done...");
 		close(kSquare);
 		//System.out.println("Close square done...");
 		skeleton();
 		//System.out.println("Skeleton done...");
 		//double ip[]={16.0, 16.0, 16.0, 16.0, 0.0};
 		//doit(ip);

 }
  public void threeDImageCylindrical() {
 	float radius[][]=new float[20][50];
	MatFloat mf = new MatFloat(radius);
	String fn = getReadFileName();
	mf.readAsgz(fn);
	radius=mf.f;
 	
 	idx.Application.image3D(getImage(),radius);
 }
 
  public void processCylindrical() {
  	//x and y are used as looping variables when parsing each image
 	int y=0;
 	int x=0;
 	int steps=35;//the number of y values from an image (taken at equal intervals)
 	int NumberOfImages; 
 	String files[] = getFileNames();
	NumberOfImages=files.length;
 	float radius[][]=new float[steps][NumberOfImages];
 	//according to the next four variables, 
 	//the images will only be searched in the specified region
 	int ymin=0;
	int ymax=234;
 	int xmin=140;
	int xmax=200;

 	//the next two variables, after parsing all the images,
 	//will hold the most left and most right values of the radius.
 	//useful for scaling the object
 	float maxr=0;
 	float minr=10000;
	System.out.println("ymin="+ymin);
	System.out.println("ymax="+ymax);	
	System.out.println("xmin="+xmin);
	System.out.println("xmax="+xmax);	

 	for (int i=0; i < NumberOfImages; i++) {
	  openGif(files[i]);
 	  processDiffractionImage();
 	  System.out.println(i);
 	  try {
 		for (y=0; y <steps; y++){
 			for (x=xmin; x <xmax; x++) {
 				if (r[x][ymin+y*((ymax-ymin)/steps)] == 0) continue;
 				radius[y][i] =x;
				if (x>maxr) maxr=x;
				if (x<minr) minr=x;
				break;
 			}
 			if (x==xmax) radius[y][i]=0;
 		}
      }
 	  catch (Exception e) {
 			System.out.println(e+"x,y="+x+","+y);
 	  }
	}
	
	//scaling section
	//values of r[][] less than zero will not be triangulated later on
	System.out.println("min="+minr);
	System.out.println("max="+maxr);	
 	for (int i=0; i < NumberOfImages; i++) {
    	for (y=0; y <steps; y++){
    		radius[y][i] =radius[y][i]-minr;
    		radius[y][i] =(float)((radius[y][i])/(maxr-minr));
	    }
	}	
 	
	MatFloat mf = new MatFloat(radius);
	String fn = mf.getSaveFileName("flt.gz file");
	mf.saveAsgz(fn);
	mf.readAsgz(fn);
 	idx.Application.image3D(getImage(),radius);
}
 public void processMovieImage(double t) {
 	System.out.println("t="+t);
 	//polarTransform(0.5,t*2);
 	//fishEye((int)(width/2),(int)(height/2),2*t+2.1);
 	sqrt(t);
 } 
 public void playImages() {
 	AnimateFrame af = new AnimateFrame();
 	String files[] = getFileNames();
 	af.setSize(width,height);
 	Toolkit tk = Toolkit.getDefaultToolkit();
 	for (int i=0; i < files.length; i++) {
 		Image img = tk.getImage(files[i]);
 		waitForImage(this, img);
 		af.addImage(img);
 		if ((i % 100) == 0) {
 			System.out.println("read file "+i);
 		}
 	}
 	af.setVisible(true);
 }
 public void makeMovie() {
 	String files[] = getFileNames();
 	for (int i=0; i < files.length; i++) {
 		openGif(files[i]);
 		processMovieImage(i/(double)files.length);
 		int outFileNumber = i + files.length;
 		String outFileName = dir+"eclaire"+outFileNumber+".GIF";
 		System.out.println("reading:"+files[i]);
 		System.out.println("writing:"+outFileName);
 		saveAsGif(outFileName);
 	}
 }
 public void processDiffractionImages() {
 	int y=0;
 	int x=0;
 	String files[] = getFileNames();
 	short zimage[][]=new short[files.length][256];
 	for (int i=0; i < files.length; i++) {
 		openGif(files[i]);
 		processDiffractionImage();
 		try {
 		for (y=0; y < height; y++)
 			for (x=0; x <width; x++) {
 				if (r[x][y] == 0) continue;
 				zimage[i][y] = (short) x;
 			}
 		}
 		catch (Exception e) {
 			System.out.println(e+"x,y="+x+","+y);
 		}
 	}
 	r = copyArray(zimage);
	width = files.length;
	height = height;
	copyRedToGreenAndBlue();
	short2Image();
 }
 public void ls() {
 	String files[] = getFileNames();
 	for (int i=0; i < files.length; i++) {
 		File f = new File(files[i]);
 		String fn = f.getName()+"\t\t\t";
 		if (f.canRead()) fn = fn+"r";
 			else fn = fn+"-";
 		if (f.canWrite()) fn = fn+"w";
 		    else fn = fn+"-";
 		fn = fn +"\t"+f.length();
 		System.out.println(fn);
 	}
 }
 public void rename() {
 	String files[] = getFileNames();
 	for (int i=0; i < files.length; i++) {
 		File f = new File(files[i]);
 		String fn = f.getName();
 		File f2 = new File(dir+"d"+fn);
 		if (fn.length() > 13)
 		   f.renameTo(f2);
 	}
 }
 
 public void systemInfo() {
 	System.out.println(getTitle());
	printProp("java.version");
	printProp("java.class.version");
	printProp("os.arch");
	printProp("os.name");
	printProp("os.version");
	printProp("java.vendor");
	printProp("java.vendor.url");
	printProp("user.home");
	printProp("user.dir");
	printProp("java.home");
	printProp("java.class.path");

 }
 public void printProps() {
	System.getProperties().list(System.out);
 }
 public void printProp(String p) {
 	System.out.println(p+":"+System.getProperty(p));
 }
 public void threeDImage() {
 	if (child == null) {
 		idx.Application.image3D(getImage(),r);
 		return;
 	}
		idx.Application.image3D(child.getImage(),r);
 	
 }
 public void printMethods(Method methods[]) {
 	for (int i=0; i < methods.length; i++) {
 		System.out.print(methods[i]+"\t");
 		if (i % 4 == 0) System.out.println();
 	}
 }
 public void printMethodNames(Method ma[]) {
 	int length = 0;
 	for (int i=0; i < ma.length; i++) {
 		Class ca[] = ma[i].getParameterTypes();
 		System.out.print(ma[i].getName()+"   ");
 		length += ma[i].getName().length()+1;
 		if (length > 70) {
 		  System.out.print("\n..");
 		  length = 0;
 		}
 	}
 	System.out.println();
 }
 public Method[] getMethodsWithNoArguments() {
 	Vector v = new Vector();
 	Class c = this.getClass();
 	Method ma[] = c.getMethods();
 	for (int i=0; i < ma.length; i++) {
 		Class ca[] = ma[i].getParameterTypes();
 		if (ca.length == 0) 
 		v.addElement(ma[i]);
 	}
 	ma = new Method[v.size()];
 	for (int i=0; i < v.size(); i++)
 		ma[i] = ((Method)v.elementAt(i));
 	return ma;
 }
 public void printMethods() {
 	Class c = this.getClass();
 	printMethodNames(getMethodsWithNoArguments());
 }
 
 public void printInfo() {
 	Vector v = new Vector();
 	for (Class c = this.getClass();c != null;c=c.getSuperclass()) 
 		v.addElement(c);
 	for (int i=v.size()-1;i >=0 ; i--)
 		System.out.println("("+((Class)v.elementAt(i)).getName());
 	for (int i=v.size();i >=0; i--)
 		System.out.print(")");
 	System.out.println();
 	for (int i=0;i >=0 ; i--) {
 		Class c = (Class)v.elementAt(i);
 		System.out.println(c);
 		printMethods(c.getMethods());
 	}
 }
public void commandLine() {
	CommandLineInterpreter
		cli = new CommandLineInterpreter(this);
	System.out.println("Type 'help' to get help");
	
}
public void help() {
	System.out.println(
	"Kahindu is Shareware. To register send a check for $30 to:\n"+
	"Douglas Lyon\nCSE Dept.\nUniversity of Bridgeport\n"+
	"Bridgeport CT 06601\n"+
	"Please add $5 for each additional computer that\n"+
	"Kahindu is installed on\n"+
	"Further upgrades are $10\n"+
	"Consulting and teaching services are available\n\n"+
	"Web Page: <http://www.DocJava.com> \n"+
	"Internet: lyon@DocJava.com\n"+
	"Those people sending in the shareware fee get\n"+
	"The latest version and an instruction manual\n"+
	"\nType a method name and carrage return\n"+
	"and the method will be invoked\n"+
	"'quit' - exits from CLI\n"+
	"'printMethods' will print the methods\n"
 );
}
 public void benchMark() {
 	BenchMark bm = new BenchMark();
 	bm.run(this);
 }
}

 class FileFilter implements FilenameFilter{
  public boolean accept(File dir, String name) {
    return new File(dir,name).exists();
  }
}
