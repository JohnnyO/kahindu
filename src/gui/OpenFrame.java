package gui;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.zip.*;

public class OpenFrame extends SaveFrame {
	private boolean fileIsPPM = false;
	
	MenuItem openImage_mi = addMenuItem(openMenu,"[o]pen image...");
	MenuItem openPPM_mi = addMenuItem(openMenu,"open ppm...");
	MenuItem sniff_mi = addMenuItem(openMenu, "sniff...");	
	MenuItem getAsShortgz_mi = addMenuItem(openMenu, "[z]openAsShortgz");	
	
	public void actionPerformed(ActionEvent e) {
	
	    if (match(e, getAsShortgz_mi)) {
	    	getAsShortgz();
	    	return;
	    }
	    if (match(e, openImage_mi)) {
	    	openImage();
	    	return;
	    }
	    if (match(e, sniff_mi)) {
	    	System.out.println("sniffer!");
	    	openAndSniffFile();
	    	return;
	    }
	    if (match(e, openPPM_mi)) {
	    	openPPM();
	    	return;
	    }
	    super.actionPerformed(e);  

}

 OpenFrame(String title) {
		super(title);
 }

public void getAsShortgz() {
	String fn = getReadFileName();
	if (fn == null) return;
    Timer t = new Timer();
    t.start();
    try {
    		FileInputStream fis = new FileInputStream(fn);
        	GZIPInputStream gis = new GZIPInputStream(fis);
     		ObjectInputStream ois = new ObjectInputStream(gis);
        	r = (short[][])ois.readObject();
        	g = (short[][])ois.readObject();
        	b = (short[][])ois.readObject();
        	ois.close();

        } catch(Exception e) {
         System.out.println("Open getAsShortgz Exception:"+e);
        }
     t.stop();
     t.print(" getAsShortgz in "); 
     width = r.length;
     height =r[0].length;
     setSize(width,height);

     short2Image();	
}
public void openImage() {
	String fn = getReadFileName();
	if (fn == null) return;
	File f = new File(fn);
	if (!f.exists()) return;
	setFileName(fn);
	InputStream is= null;
	try {
		 is = new FileInputStream(fn);
	}
	catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
	StreamSniffer ss = new StreamSniffer(is);
	int id = ss.classifyStream();
	switch (id) {
		case StreamSniffer.PPM:
			setFileName(fn);
			openPPM(fn);
			break;
		case StreamSniffer.PPM_RAWBITS:
			setFileName(fn);
			openPPM(fn);
			break;			
		case StreamSniffer.GIF87a:
			openGif(fn);
			break;
		case StreamSniffer.GIF89a:
			openGif(fn);
			break;
		case StreamSniffer.JPEG: 
			setImageResize(
					Toolkit.getDefaultToolkit().getImage(
						fn));
			setFileName(fn);
						
			break;
		case StreamSniffer.GZIP:
			openPPMgz(fn);
			break;
		case StreamSniffer.ZIP_ARCHIVE:
			getShortImageZip(fn);
			break;
		default: {
			System.out.println("Can not open "+ss+" as image");
		}

	}
}

public void getShortImageZip(String fn) {
    Timer t = new Timer();
    t.start();
    try {
    	FileInputStream fis = new FileInputStream(fn);
        ZipInputStream zis = new ZipInputStream(fis);
        DataInputStream dis = new DataInputStream(zis);
     	ZipEntry ze;
     	ze = zis.getNextEntry();
       	readHeader(dis);
      	readArray(r,dis);
    	readArray(g,dis);
    	readArray(b,dis);
        zis.close();
 
        } catch(Exception e) {
         System.out.println("Open getShortImageZip:"+e);
        }
     t.stop();
     t.print(" getShortImageZip in ");
     short2Image(); 	
}

public void readArray(short a[][], DataInputStream dis) 
	throws IOException {
	for (int x=0; x < width; x++)
		for (int y=0; y < height; y++)
			a[x][y]=dis.readShort();
}
public void readHeader(DataInputStream dis) 
	throws IOException {
	width=dis.readInt();
	height=dis.read();
}

public static void main(String args[]) {
	OpenFrame f = new OpenFrame("OpenFrame");
	f.show();
}
 public void openPPMgz(String fn) {
  	setFileName(fn);
 	Timer t = new Timer();
    try {
    	t.start();
    	readShortsGz(fn);     	   	
    	width = r.length;
    	height = r[0].length;
    	fileIsPPM = true;
    	short2Image(); 
    	t.print("Time to read and display the ppm"); 	
    }
    catch(Exception e) {
    	System.out.println("Read PPM Exception:"+e);
    }

 }
	
 public void openPPM(String fn) {
 	Timer t = new Timer();
    try {
    	t.start();
    	readShorts(fn);     	   	
    	setSize(width,height);
    	fileIsPPM = true;
    	short2Image(); 
    	t.print("Time to read and display the ppm"); 	
    }
    catch(Exception e) {
    	System.out.println("Read PPM Exception:"+e);
    }
    repaint();

 }
 
   private void readShorts(String fn){
   	Timer t = new Timer();
   	t.start();
   	InputStream in = null;
   	try {
         in = new FileInputStream(fn);
     	} catch(Exception e) 
          {e.printStackTrace();}
    getShortImage(in);
    t.print("End ReadShorts");
  }
  private void readShortsGz(String fn){
   	Timer t = new Timer();
   	t.start();
   	GZIPInputStream in = null;
   	
   	try {
         in = new GZIPInputStream(
         	new FileInputStream(fn));
     	} catch(Exception e) 
          {e.printStackTrace();}
    getShortImageGz(in);
    t.print("End ReadShorts");
  }
    private void readHeader(InputStream in) throws IOException
   {
      char c1, c2;

      c1 = (char) readByte(in);
      c2 = (char) readByte(in);

      if (c1 != 'P')
      {
         throw new IOException("not a PPM file");
      }
      if (c2 != '6')
      {
         throw new IOException("not a PPM file");
      }

      width = readInt(in);
      height = readInt(in);
      System.out.println("ReadPPM:" + width + "x" + height);

      // Read maximum value of each color, and ignore it.
      // In PPM_RAW we know r,g,b use full range (0-255).
      readInt(in);
   }
   
     private static int readInt(InputStream in)
      throws IOException
   {
      char c;
      int i;

      c = readNonwhiteChar(in);
      if ( (c < '0') || (c > '9') )
      {
         throw new IOException(
         	"Invalid integer when reading PPM image file.");
      }

      i = 0;
      do
      {
         i = i * 10 + c - '0';
         c = readChar(in);
      }
      while( (c >= '0') && (c <= '9'));

      return(i);
   }
   private static int readByte(InputStream in)
   	 throws IOException {
      int b = in.read();

      // if end of file
      if (b == -1) {
         throw new EOFException();
      }
      return b;
   }
     private static char readNonwhiteChar(InputStream in)
      throws IOException
   {
      char c;

      do
      {
         c = readChar(in);
      }
      while( (c == ' ') || (c == '\t') || (c == '\n') || (c == '\r') );

      return c;
   }
   private static char readChar(InputStream in)
      throws IOException
   {
      char c;

      c = (char)readByte(in);
      if (c == '#')
      {
         do
         {
            c = (char)readByte(in);
         }
         while( (c != '\n') && (c != '\r') );
      }

      return(c);
   }

   private  void getShortImage(InputStream in) {
   	Timer t = new Timer();
   	try {
        readHeader(in);
      }
    catch(Exception e)
      	{e.printStackTrace();}
    r = new short[width][height];
   	g = new short[width][height];
    b = new short[width][height];
    byte buf[] = new byte[width*height*3];
    int offset = 0;
    int count = buf.length;
    int n = 0;
    t.start();
    try {
      	 n = in.read(buf, offset, count);
    } catch (IOException e) 
    	{e.printStackTrace();}
    t.print("Read in "+n+" bytes");		
      try { 
      	t.start();
      	int j=0;
      	for (int col=0; col < height; col++) 
      		for (int row=0; row < width; row++) {
      			r[row][col] = UByte.us(buf[j++]);
      			g[row][col] = UByte.us(buf[j++]);
      			b[row][col] = UByte.us(buf[j++]);
      	}
        t.print("r[row][col] = buf[j++]");
      } catch(Exception e)
      	{ e.printStackTrace();}


   }
  private  void getShortImageGz(GZIPInputStream in) {
   	Timer t = new Timer();
   	try {
        readHeader(in);
      }
    catch(Exception e)
      	{e.printStackTrace();}
    r = new short[width][height];
   	g = new short[width][height];
    b = new short[width][height];
    byte buf[] = new byte[width*height*3];
    int len = 0;
    byte bufgz[] = new byte[1024];
    
    int n = 0;
    t.start();
    try {
    	while ((len = in.read(bufgz)) > 0) {
      	 	System.arraycopy(bufgz, 0, buf, n, len);
      	 	n = n + len;      	 	
      	 }
    } catch (IOException e) 
    	{e.printStackTrace();}
    t.print("Read in "+n+" bytes");		
      try { 
      	t.start();
      	int j=0;
      	for (int col=0; col < height; col++) 
      		for (int row=0; row < width; row++) {
      			r[row][col] = UByte.us(buf[j++]);
      			g[row][col] = UByte.us(buf[j++]);
      			b[row][col] = UByte.us(buf[j++]);
      	}
        t.print("r[row][col] = buf[j++]");
      } catch(Exception e)
      	{ e.printStackTrace();}


   }
	
 public void openPPM() {
	String fn = getReadFileName();
	if (fn == null) return;
	File f = new File(fn);
	if (!f.exists()) return;
	setFileName(fn);
	openPPM(fn);
}

 public StreamSniffer openAndSniffFile() {
 	String fn = getReadFileName();
 	InputStream is;
	if (fn == null) return null;
	try {
		 is = new FileInputStream(fn);
	}
	catch (FileNotFoundException e) {
		return null;
	}
	StreamSniffer ss = new StreamSniffer(is);
	System.out.println("Hmm, this smells like a "+ss);
	System.out.println("The id is :"+ss.classifyStream());
	return ss;	
 }

}