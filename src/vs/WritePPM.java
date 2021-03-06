/**
 * WritePPM is a class that takes an Image and saves it to
 * a PPM format file.
 *
 * Victor Silva (victor@bridgeport.edu).
 * Modified by D.L. (lyon@DocJava.com)
 *
 */
package vs;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WritePPM
{
   int width;
   int height;
   
   public static void doIt(
   	short r[][], short g[][], short b[][], 
   	String fn) {
      WritePPM wppm = new WritePPM(r.length, r[0].length);
      try {
         OutputStream os = new BufferedOutputStream(
            new FileOutputStream(fn));
         wppm.header(os);
         //System.out.println("Header OK");
         wppm.image(os, r, g, b);
         //System.out.println("Image written");
         os.flush();
         os.close();
      }
      catch(IOException e) {
         System.out.println(e+" IOException");
      }  	
   }

 
   public WritePPM(int imageWidth, int imageHeight) {
      width = imageWidth;
      height = imageHeight;
   }


  public void header(OutputStream out) {
	   writeString( out, "P6\n" );
	   writeString( out, width + " " + height + "\n" );

	   // Write the maximum value of each color.
	   // i.e. r,g,b all vary between 0 and 255.
	   writeString( out, "255\n" );
	}

   static void writeString(OutputStream out, String str) {
	   int len = str.length();
	   byte[] buf = new byte[len];
	   // now deprecated
	   // str.getBytes( 0, len, buf, 0 );
	   // use the following instead: 
	     buf = str.getBytes();
	     try 
	     	{out.write(buf);}
	     catch (Exception e) 
	     	{System.out.println(e);}
	}
	
  private void printDimensions(short r[][],String nm) {
  	  	 System.out.println(
	  	 	nm+".length ="+r.length+" "
	  	 	+ nm+"[0].length ="+r[0].length);

  }
  public void image(OutputStream out, 
  	short r[][], short g[][], short b[][])
	   {
      int j=0;
      width = r.length;
      height = r[0].length;
	  byte[] ppmPixels = new byte[width* height * 3];
	  //System.out.println("Writing image "+width+"x"+height);
	 // yowsa!!
	 // tuff error to spot!
	 // for ( int col = 0; col < width ; ++col ) { is wrong!! 
	 // for ( int col = 0; col < height ; col++ ) { is correct!
	try {
	  for ( int col = 0; col < height ; col++ ) {  
	       for ( int row = 0; row < width; row++ ) {  
		      ppmPixels[j++] = (byte)r[row][col];
		      ppmPixels[j++] = (byte)g[row][col];
		      ppmPixels[j++] = (byte)b[row][col];
			 }
	   }
	  }
	  catch (Exception e) {e.printStackTrace();}
	  // Write ppm file all at once.
      try {
	       out.write(ppmPixels);
	      } catch (Exception e) {
	      	System.out.println(e+" out.write");
	  }
	}
}
