package gui;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

public class ImageFrame extends ShortCutFrame {


private Image image;
private ColorModel cm = ColorModel.getRGBdefault();
private boolean imageComesFromFile = false;


// No, you can't use bytes, cause
// Java bytes are signed. Yeah, that bites!
public  short r[][];
public	short g[][];
public	short b[][];

public int width = 128;
public int height = 128;

// A default file name..set to null
// to start with file open dialog.
// Set to string to start with an image.
// Use a fully qualified path name, in quotes.
private String fileName = null;

private MenuBar menuBar = new MenuBar();
Menu fileMenu = new Menu("File");

Menu openMenu = new Menu("Open");
	MenuItem openGif_mi = addMenuItem(openMenu,"[g]if...");
	MenuItem default_mi = addMenuItem(openMenu,"[d]efault image");
	MenuItem revert_mi = addMenuItem(openMenu,"[r]evert");
	MenuItem fitScreen_mi = addMenuItem(openMenu,"fitScreen");
	

    public void fitScreen() {
    	Toolkit tk = Toolkit.getDefaultToolkit();
    	Dimension d 
    		= tk.getScreenSize();
    	setSize(d.width, d.height);
    }

public void actionPerformed(ActionEvent e) {

	if (match(e, fitScreen_mi)) {
		fitScreen();
		return;
	}
	if (match(e, openGif_mi)) {
		openGif();
		return;
	}
	if (match(e, default_mi)) {
		grabNumImage();
		return;
	}
	if (match(e, revert_mi)) {
		revert();
		return;
	}
}
public ImageFrame(String title) {
	super(title);
	initialize();
}


private void initialize() {
	initMenu();
	grabNumImage();
	
	//show();
	setSize(width, height);
	repaint();
 }
 
 
public void grabNumImage() {
	imageComesFromFile = false;
	width = NumImage.gray.length;
	height = NumImage.gray[0].length;
	r = new short[width][height];
	g = new short[width][height];
	b = new short[width][height];
	for (int y=0; y < height  ;y++) {
		for (int x=0;x< width;x++) {
			r[x][y] = NumImage.gray[y][x];
			g[x][y] = NumImage.gray[y][x];
			b[x][y] = NumImage.gray[y][x];

		}
	}
	short2Image();
}

private void initMenu() {
	fileMenu.add(openMenu);
	menuBar.add(fileMenu);
	setMenuBar(menuBar);
}

protected String getReadFileName() {
   	FileDialog fd = new 
   		FileDialog(this, "select a file");
    fd.show();
    String file_name = fd.getFile();
    if (fd.getFile() == null) return null;
    String path_name = fd.getDirectory();
    String file_string = path_name + file_name;
    System.out.println("Opening file: "+file_string);
    fd.dispose();
    return file_string;
}

public void setImageResize(Image i) {
	setImage(i);

	setSize(width,height);
	setVisible(true);
	repaint();
}

public void setImage(Image i) {
	image = i;
	waitForImage(this, i);
	width = i.getWidth(this);
	height = i.getHeight(this);
	image2Short();
}

public void setImageNoShort(Image i) {
	image = i;
	waitForImage(this, i);
	width = i.getWidth(this);
	height = i.getHeight(this);
}

public Image getImage() {
	waitForImage(this, image);
	return image;
}

public String getFileName() {
	return fileName;
}
private static int min =  10000;
private static int max = -10000;
private boolean clipped = false;
private short clip(short i) {
	if (i < min) min=i;
	if (i > max) max=i;
	if (i < 0 ) {
		clipped = true;
		if (i < min) min=i;
		return 0;
	}
	if (i > 255) {
		clipped = true;
		return 255;
	} 
	return i;
}
public void clip() {
 for (int x = 0; x < width; x++)
	for (int y = 0; y < height; y++) {
		r[x][y] = clip(r[x][y]); 
		g[x][y] = clip(g[x][y]);
		b[x][y] = clip(b[x][y]);
	}
}

/**
   short2Image - turn 3 short arrays, r, g and b into an image.
*/
public void short2Image() {
 clipped = false;
 width = r.length;
 height = r[0].length;
 Toolkit tk = Toolkit.getDefaultToolkit();
 int pels[] = new int[width*height];
 for (int x = 0; x < width; x++)
	for (int y = 0; y < height; y++) {
		 pels[ x + y * width] = 
		     0xff000000 
		     | (r[x][y] << 16) 
		     | (g[x][y] << 8) 
		     |  b[x][y];
	}
  Image i =tk.createImage(
				new MemoryImageSource(
					width, 
					height,
					cm, 
					pels, 0, 
					width));
 	setImageNoShort( i	);
	Rectangle r = getBounds();
	repaint(0,0,r.width,r.height);
	if (clipped) System.out.println(
		"warning: clipped image. min max ="+min+","+max);
}
public void pels2Image(int pels[]) {
 width = r.length;
 height = r[0].length;
 Toolkit tk = Toolkit.getDefaultToolkit();
 Image i =tk.createImage(
				new MemoryImageSource(
					width, 
					height,
					cm, 
					pels, 0, 
					width));
 	setImageNoShort( i	);
	Rectangle r = getBounds();
	repaint(0,0,r.width,r.height);
}
/**
   image2Short - takes the private Image instance and
   makes 3 short arrays, r, g and b.
*/
public void image2Short() {
	r = new short[width][height];
	g = new short[width][height];
	b = new short[width][height];		

	int pels[] = new int[width * height];
	cm = ColorModel.getRGBdefault();

	PixelGrabber grabber = 
			new PixelGrabber(
				image, 0, 0, 
				width, height, pels, 0, width);

	try {grabber.grabPixels();}
	catch (InterruptedException e){};
	int i = 0;
	for (int x = 0; x < width; x++)
		for (int y = 0; y < height; y++) {
			i = x + y * width;
			b[x][y] = (short)cm.getBlue(pels[i]);
			g[x][y] = (short)cm.getGreen(pels[i]);
			r[x][y] = (short)cm.getRed(pels[i]);
		}
}

	
 static void waitForImage(Component component,
                                           Image image) {
  MediaTracker tracker = new MediaTracker(component);        
  try {
  	tracker.addImage(image, 0);
    tracker.waitForID(0);
    if (!tracker.checkID(0)) 
         System.out.println("Load failure!");
  }
  catch(InterruptedException e) {  }
}
 
public void openGif() {
	String fn = getReadFileName();
	if (fn == null) return;
	openGif(fn);
}
public void setFileName(String _fn) {
	File f = new File(_fn);
	if (f.exists()) {
		fileName = _fn;
		System.out.println(
		"File:"+ fileName +
		"\nis " + f.length() + " bytes long"); 
	}
	imageComesFromFile = true;
	
}
public void openGif(String fn) {
	File f = new File(fn);
	if (!f.exists()) {
		grabNumImage();
		return;
	}

	fileName = fn;
	image = Toolkit.getDefaultToolkit().getImage(
		fileName);
	setImageResize(image);
	imageComesFromFile = true;
}

public void revert() {
	Toolkit tk = Toolkit.getDefaultToolkit();
	if (imageComesFromFile) {
		if (fileName == null) 
			openGif();
		else {
			image = 
				tk.getImage(fileName);
			setImageResize(image);	
		}
	}
	else grabNumImage();

} 
public void revertNoResize() {
	Toolkit tk = Toolkit.getDefaultToolkit();
	if (imageComesFromFile) {
		if (fileName == null) 
			openGif();
		else {
			image = 
				tk.getImage(fileName);
			setImage(image);	
		}
	}
	else grabNumImage();

} 

 // Takes a packed RGB model and makes
 // the short arrays
 public void int2Short(int pels[]) {
	System.out.println("The width and height are" 
		+ width+","+height);
	r = new short[width][height];
	g = new short[width][height];
	b = new short[width][height];		

	cm = ColorModel.getRGBdefault();
	int i = 0;
	for (int x = 0; x < width; x++)
		for (int y = 0; y < height; y++) {
			i = x + y * width;
			b[x][y] = (short)cm.getBlue(pels[i]);
			g[x][y] = (short)cm.getGreen(pels[i]);
			r[x][y] = (short)cm.getRed(pels[i]);
		}
}
	

public static void main(String args[]) {
	ImageFrame imgFrm = 
		new ImageFrame("Image Frame");
	imgFrm.openGif();
}
public void paint(Graphics g) {
	if (image != null) {
		Rectangle r = getBounds();
		g.drawImage(image, 0,0,r.width, r.height, 
			 this);
	}
}
}