/***************************************************************************
// @(#)vsFFT.java
//
// Perform a non recursive in place FFT.
//
// @version 1.0, 1 JULY 1997
// @author  D. Lyon, V. Silva
****************************************************************************/
package vs;

import java.awt.*;
import java.io.*;


public class vsFFT {
   private final int FORWARD_FFT = -1;
   private final int REVERSE_FFT = 1;
   private float direction = (float)FORWARD_FFT;

   static final float twoPI = (float)(2 * Math.PI);
   private int N;
   private int numBits;
   private int width, height;
   private float minPSD = 9999999;
   private float maxPSD = -9999999;

   ColorUtils CU = new ColorUtils();
   ImageUtils iUtils = new ImageUtils();
   public boolean DisplayLogPSD = false;

   // These arrays hold the complex image data.
   public float cR_r[][]; // Red real
   public float cG_r[][]; // Green real
   public float cB_r[][]; // Blue real
   public float cR_i[][]; // Red imaginary
   public float cG_i[][]; // Green imaginary
   public float cB_i[][]; // Blue imaginary

   // These arrays hold the complex 1D FFT data.
   // These will hold either the full 1D FFT data, or the
   // data from performing an FFT on one image row.
   float r_data[] = null;
   float i_data[] = null;

private void init() {
      minPSD = 9999999;
      maxPSD = -9999999;
}

public int[] psd() {
      //-------------------------------------------------------------
      // Take PSD on complex RGB values.
      //-------------------------------------------------------------
      // Magnitude of result.
      float[] magnitudeR = magnitudeSpectrum(cR_r, cR_i);
      float[] magnitudeG = magnitudeSpectrum(cG_r, cG_i);
      float[] magnitudeB = magnitudeSpectrum(cB_r, cB_i);
      
      double scaleFactor = 100; //255.0/Math.log(256);
      

      System.out.println("Max psd = "+maxPSD);
      
      scaleFactor = 255.0/(Math.log(1+maxPSD));
      System.out.println("Scalefactor = "+scaleFactor);
      


      // Adjust 2-D FFT data so that the minimum PSD is
      // based at a value close to a black pixel.
     
      for(int i=0; i<N; i++) {      	
           magnitudeR[i] = (float) 
           	(scaleFactor * Math.log(1+magnitudeR[i])); 
           magnitudeG[i] = (float) 
           	(scaleFactor * Math.log(1+magnitudeG[i]));
           magnitudeB[i] = (float) 
           	(scaleFactor * Math.log(1+magnitudeB[i]));
      }

      System.out.println("Minimum PSD value: " + minPSD);

      // Convert to single ARGB int array and return.
      return(CU.imagetoInt(magnitudeR, magnitudeG, magnitudeB));
}

public int[] forward2dFFT(float[] imageData_R, float[] imageData_G,
            float[] imageData_B, int imageWidth, int imageHeight) {
                  init();

      // save image size to locals.
      width = imageWidth;
      height = imageHeight; 

      // We know the size of the image now, allocate
      // the required arrays to hold the complex image data.
      cR_r = new float[height][width];
      cR_i = new float[height][width];
      cG_r = new float[height][width];
      cG_i = new float[height][width];
      cB_r = new float[height][width];
      cB_i = new float[height][width];

      // Total number of pixels.
      N = width * height;

      // Number of bits in one direction, i.e. 256x256 image m = 8
      // assuming image is square, which has already been checked.
      numBits = log2(width);

      //-------------------------------------------------------------
      // This section performs a the first forward FFT on RGB values.
      //-------------------------------------------------------------
      vsTimer t1 = new vsTimer();
      t1.clear();
      t1.mark();

      // FFT on RGB values 1st set.
      // NOTE!! cX_r and cX_i are modified, they are used to hold
      // the returned FFT complex data.
      // RED
      copyFloatToComplex(cR_r, cR_i, imageData_R);
      copyFloatToComplex(cG_r, cG_i, imageData_G);
      copyFloatToComplex(cB_r, cB_i, imageData_B);
           // Do FFT by row.
      for(int i=0; i<height; i++)
      {
         // Red
         forwardFFT(cR_r[i], cR_i[i]);
         // Green
         forwardFFT(cG_r[i], cG_i[i]);
         // Blue
         forwardFFT(cB_r[i], cB_i[i]);
      }

      System.out.println("Triple FFT on rows:");
      // Stop the timer and report.
      t1.record();
      t1.report();

      //-------------------------------------------------------------
      // This section rotates complex RGB arrays CW 90 degrees.
      //-------------------------------------------------------------
      t1.clear();
      t1.mark();
      // Rotate array 90 degrees, returns a reference to a
      // a new array, array that is passed in is lost.
      cR_r = Rotate90(cR_r);
      cR_i = Rotate90(cR_i);
      cG_r = Rotate90(cG_r);
      cG_i = Rotate90(cG_i);
      cB_r = Rotate90(cB_r);
      cB_i = Rotate90(cB_i);

      System.out.println("Rotate 90 degrees CW:");
      // Stop the timer and report.
      t1.record();
      t1.report();

      //-------------------------------------------------------------
      // This section performs a the second forward FFT on RGB values.
      //-------------------------------------------------------------
      t1.clear();
      t1.mark();

      // FFT on complex data from 1st FFT.
      // NOTE!! cX_r and cX_i that are passed in are destroyed.
      // They are used to hold the returned FFT complex data.
      // Do FFT by row.
      for(int i=0; i<height; i++)
      {
         // Red
         forwardFFT(cR_r[i], cR_i[i]);
         // Green
         forwardFFT(cG_r[i], cG_i[i]);
         // Blue
         forwardFFT(cB_r[i], cB_i[i]);
      }

      System.out.println("Triple FFT on columns:");
      // Stop the timer and report.
      t1.record();
      t1.report();
      return psd();

}

public int[] forward2dFFT(short[] imageData_R, short[] imageData_G,
            short[] imageData_B, int imageWidth, int imageHeight)
   {
      init();

      // save image size to locals.
      width = imageWidth;
      height = imageHeight; 

      // We know the size of the image now, allocate
      // the required arrays to hold the complex image data.
      cR_r = new float[height][width];
      cR_i = new float[height][width];
      cG_r = new float[height][width];
      cG_i = new float[height][width];
      cB_r = new float[height][width];
      cB_i = new float[height][width];

      // Total number of pixels.
      N = width * height;

      // Number of bits in one direction, i.e. 256x256 image m = 8
      // assuming image is square, which has already been checked.
      numBits = log2(width);

      //-------------------------------------------------------------
      // This section performs a the first forward FFT on RGB values.
      //-------------------------------------------------------------
      vsTimer t1 = new vsTimer();
      t1.clear();
      t1.mark();

      // FFT on RGB values 1st set.
      // NOTE!! cX_r and cX_i are modified, they are used to hold
      // the returned FFT complex data.
      // RED
      copyShortToComplex(cR_r, cR_i, imageData_R);
      copyShortToComplex(cG_r, cG_i, imageData_G);
      copyShortToComplex(cB_r, cB_i, imageData_B);

      // Do FFT by row.
      for(int i=0; i<height; i++) {
         // Red
         forwardFFT(cR_r[i], cR_i[i]);
         // Green
         forwardFFT(cG_r[i], cG_i[i]);
         // Blue
         forwardFFT(cB_r[i], cB_i[i]);
      }

      System.out.println("Triple FFT on rows:");
      // Stop the timer and report.
      t1.record();
      t1.report();

      //-------------------------------------------------------------
      // This section rotates complex RGB arrays CW 90 degrees.
      //-------------------------------------------------------------
      t1.clear();
      t1.mark();
      // Rotate array 90 degrees, returns a reference to a
      // a new array, array that is passed in is lost.
      cR_r = Rotate90(cR_r);
      cR_i = Rotate90(cR_i);
      cG_r = Rotate90(cG_r);
      cG_i = Rotate90(cG_i);
      cB_r = Rotate90(cB_r);
      cB_i = Rotate90(cB_i);

      System.out.println("Rotate 90 degrees CW:");
      // Stop the timer and report.
      t1.record();
      t1.report();

      //-------------------------------------------------------------
      // This section performs a the second forward FFT on RGB values.
      //-------------------------------------------------------------
      t1.clear();
      t1.mark();

      // FFT on complex data from 1st FFT.
      // NOTE!! cX_r and cX_i that are passed in are destroyed.
      // They are used to hold the returned FFT complex data.
      // Do FFT by row.
      for(int i=0; i<height; i++) {
         // Red
         forwardFFT(cR_r[i], cR_i[i]);
         // Green
         forwardFFT(cG_r[i], cG_i[i]);
         // Blue
         forwardFFT(cB_r[i], cB_i[i]);
      }

      System.out.println("Triple FFT on columns:");
      // Stop the timer and report.
      t1.record();
      t1.report();
      return psd();
   }

	int log2(double n) {
		return (int)(Math.log(n)/Math.log(2));
	}

   public int[] reverse2dFFT() {
      init();

      // Total number of pixels.
      N = width * height;

      // Number of bits in one direction, i.e. 256x256 image m = 8
      // assuming image is square, which has already been checked.
      numBits = log2(width);

      //-------------------------------------------------------------
      // This section performs the first reverse FFT on
      // the complex RGB values obtained from a forward FFT
      // on an image.
      //-------------------------------------------------------------
      vsTimer t1 = new vsTimer();
      t1.clear();
      t1.mark();

      // Do IFFT by row.
      for(int i=0; i<height; i++) {
         // Red
         reverseFFT(cR_r[i], cR_i[i]);
         // Green
         reverseFFT(cG_r[i], cG_i[i]);
         // Blue
         reverseFFT(cB_r[i], cB_i[i]);
      }

      System.out.println("Triple IFFT on rows:");
      // Stop the timer and report.
      t1.record();
      t1.report();

      //-------------------------------------------------------------
      // This section performs a rotate CW 90 degrees to
      // prepare the complex data for the second reverse FFT.
      //-------------------------------------------------------------
      t1.clear();
      t1.mark();
      // Rotate array 90 degrees, returns a reference to a
      // a new array, array that is passed in is lost.
      cR_r = Rotate90(cR_r);
      cR_i = Rotate90(cR_i);
      cG_r = Rotate90(cG_r);
      cG_i = Rotate90(cG_i);
      cB_r = Rotate90(cB_r);
      cB_i = Rotate90(cB_i);

      System.out.println("Rotate 90 degrees CW:");
      // Stop the timer and report.
      t1.record();
      t1.report();

      //-------------------------------------------------------------
      // This section performs the second reverse FFT on
      // the complex RGB values obtained from a forward FFT
      // on an image.
      //-------------------------------------------------------------
      t1.clear();
      t1.mark();

      // IFFT on complex data from 1st IFFT.
      // NOTE!! cX_r and cX_i that are passed in are destroyed.
      // They are used to hold the returned FFT complex data.
      // Do FFT by row.
      for(int i=0; i<height; i++) {
         // Red
         reverseFFT(cR_r[i], cR_i[i]);
         // Green
         reverseFFT(cG_r[i], cG_i[i]);
         // Blue
         reverseFFT(cB_r[i], cB_i[i]);
      }

      System.out.println("Triple IFFT on columns:");
      // Stop the timer and report.
      t1.record();
      t1.report();


      //-------------------------------------------------------------
      // We now rotate the image 180 degrees otherwise it would be
      // upside down.  This is a due to the process we used to
      // apply the FFT to columns (i.e. we rotated 90 degrees twice).
      //-------------------------------------------------------------
      rotateInPlace180(cR_r);
      rotateInPlace180(cR_i);
      rotateInPlace180(cG_r);
      rotateInPlace180(cG_i);
      rotateInPlace180(cB_r);
      rotateInPlace180(cB_i);

      //-------------------------------------------------------------
      // Take PSD - power spectral density giving us original
      // RGB values back.
      //-------------------------------------------------------------
      float[] realR = magnitudeSpectrum(cR_r, cR_i);
      float[] realG = magnitudeSpectrum(cG_r, cG_i);
      float[] realB = magnitudeSpectrum(cB_r, cB_i);

      // Convert to single ARGB int array and return.
      return(CU.imagetoInt(realR, realG, realB));
   }


   /**
    * A way to visually test the 1D FFT on a small amount of data.
    */
   public void TestFFT()
   {
      init();
      System.out.println("Testing FFT engine in 2D class...");

      numBits = 3;
      N = 8;

      float[] in_r = new float[N];
      float[] in_i = new float[N];

      float[] holder_r = new float[N];
      float[] holder_i = new float[N];

      // Create a test ramp signal.
      for(int i=0; i<N; i++)
      {
         in_r[i] = (float)i / N;
         in_i[i] = (float)0;
      }

      forwardFFT(in_r, in_i);

      // Copy to new array because IFFT will destroy the FFT results.
      for(int i=0; i<N; i++)
      {
         holder_r[i] = in_r[i];
         holder_i[i] = in_i[i];
      }

      for(int i=0; i<N; i++)
      {
         in_r[i] = in_r[i];
         in_i[i] = in_i[i];
      }

      reverseFFT(in_r, in_i);
      float[] mag = magnitudeSpectrum(in_r, in_i);

      System.out.println("i    x1[i]    re[i]             im[i]           tv[i]");
      for(int i=0; i<N; i++)
      {
         System.out.println(i + "\t" + i + "\t" + holder_r[i] + "\t\t" +
            holder_i[i] + "\t\t" + mag[i]);
      }

      // Time a 256K FFT
      in_r = new float[262144];
      in_i = new float[262144];

      // Create a test ramp signal.
      N = 262144;
      numBits = 18;

      for(int i=0; i<N; i++)
      {
         in_r[i] = (float)i / N;
         in_i[i] = (float)0;
      }

      vsTimer t1 = new vsTimer();
      t1.clear();
      t1.mark();

      forwardFFT(in_r, in_i);

      System.out.println("256K point 1D FFT (using float):");
      // Stop the timer and report.
      t1.record();
      t1.report();

   }

   /**
    * 1D FFT utility functions.
    */
   public void swap(int i)
   {
      float tempr;
      int j = bitr(i);
      String js = Integer.toBinaryString(j);
      String is = Integer.toBinaryString(i);

      // System.out.println("swap "+is+","+js);
      // System.out.println(Integer.toBinaryString(i)+"bitr="+Integer.toBinaryString(bitr(i)));
      tempr = r_data[j];
      r_data[j] = r_data[i];
      r_data[i] = tempr;
      tempr = i_data[j];
      i_data[j] = i_data[i];
      i_data[i] = tempr;
   }

   // swap Zi with Zj
   public void swapInt(int i, int j)
   {
      float tempr;
      int   ti;
      int   tj;
      ti = i-1;
      tj = j-1;
      // System.out.print("swapInt " + ti+","+tj+"\t");
      // System.out.println(Integer.toBinaryString(ti)+","+Integer.toBinaryString(tj));
      // System.out.println(Integer.toBinaryString(ti)+"bitr="+Integer.toBinaryString(bitr(ti)));
      tempr = r_data[tj];
      r_data[tj] = r_data[ti];
      r_data[ti] = tempr;
      tempr = i_data[tj];
      i_data[tj] = i_data[ti];
      i_data[ti] = tempr;
   }

   double getMaxValue(double in[])
   {
      double max;
      max = -0.99e30;

     for(int i=0; i<in.length; i++)
       if(in[i]  >max)
      max = in[i];
     return max;

  }

   void bitReverse2()
   {
      /* bit reversal */
      int n = r_data.length;
      int j=1;
      int k;

      for (int i=1; i<n; i++)
      {
         if (i<j)
         {
            swapInt(i,j);
         } //if

         k = n / 2;
         while (k >=1 &&  k < j)
         {
            j = j - k;
            k = k / 2;
         }
         j = j + k;

      } // for
   }

   void bitReverse()
   {
     /* bit reversal */
     int n = r_data.length;
     int j=1;
     int k;

     for (int i=1; i<n; i++){
       if  (i<j) {
          // this does not work...
          // why?
           swap(i);
          } //if
   j = bitr(i);

  } // for
 }


 int bitr(int j) {
    int ans = 0;
    for (int i = 0; i< numBits; i++) {
       ans = (ans <<1) + (j&1);
       j = j>>1;
    }
    return ans;
 }
   public void forwardFFT(float[] in_r, float[] in_i)
   {
      direction = FORWARD_FFT;
      fft(in_r, in_i);
   }

   public void reverseFFT(float[] in_r, float[] in_i)
   {
      direction = REVERSE_FFT;
      fft(in_r, in_i);
   }

   /**
    * FFT engine.
    */
   public void fft(float in_r[], float in_i[])
   {
	   int id;
      // radix 2 number if sample, 1D of course.
      int localN;
      float wtemp, Wjk_r, Wjk_i, Wj_r, Wj_i;
      float theta, tempr, tempi;
      int ti, tj;

      // Truncate input data to a power of two
      int length = 1 << numBits; // length = 2**nu
      int n=length;

      // Copy passed references to variables to be used within
      // fft routines & utilities
      r_data = in_r;
      i_data = in_i;

      bitReverse2();
      for(int m=1; m<=numBits; m++)
      {
         // localN = 2^m;
         localN = 1 << m;
         Wjk_r = 1;
         Wjk_i = 0;

         theta = twoPI / localN;


         Wj_r = (float)Math.cos(theta);
         Wj_i = (float)(direction * Math.sin(theta));

         int nby2 = localN / 2;
         for(int j=0; j<nby2; j++)
         {
            // This is the FFT innermost loop
            // Any optimizations that can be made here will yield
            // great rewards.
            for(int k=j; k<n; k+=localN)
            {
               id = k + nby2;
               tempr = Wjk_r * r_data[id] - Wjk_i * i_data[id];
               tempi = Wjk_r * i_data[id] + Wjk_i * r_data[id];

               // Zid = Zi -C
               r_data[id] = r_data[k] - tempr;
               i_data[id] = i_data[k] - tempi;
               r_data[k] += tempr;
               i_data[k] += tempi;
            }

            // (eq 6.23) and (eq 6.24)

            wtemp = Wjk_r;

            Wjk_r = Wj_r * Wjk_r  - Wj_i * Wjk_i;
            Wjk_i = Wj_r * Wjk_i  + Wj_i * wtemp;
         }
      }
   }

   public float[][] getRedReal()
   {
      return(cR_r);
   }

   public float[][] getRedImaginary()
   {
      return(cR_i);
   }

   public float[][] getGreenReal()
   {
      return(cG_r);
   }

   public float[][] getGreenImaginary()
   {
      return(cG_i);
   }

   public float[][] getBlueReal()
   {
      return(cB_r);
   }

   public float[][] getBlueImaginary()
   {
      return(cB_i);
   }

   /**
   * The two arrays must be the same size.
   */
   private void copy2dArray(float[][] dst, float[][] src)
   {
      for(int i=0; i<height; i++)
      {
         for(int j=0; j<width; j++)
         {
            dst[i][j] = src[i][j];
         }
      }
   }
private void copyFloatToComplex(float[][] dst_r, float[][] dst_i,
      float[] imageData) {
      int k = 0;

      float alternateSign = 1;

      for(int i=0; i<height; i++)
      {
         for(int j=0; j<width; j++)
         {
            // Calculate (-1)**(i+j)
            alternateSign = ((i+j)%2 == 0) ? -1 : 1;

            // 1. Put short image array into a complex pair,
            // (-1)**(i+j) is to put the zero frequency in the
            // center of the image when it is displayed.
            // 2. We also take this opportunity to scale the input
            // by N (width * height).
            dst_r[i][j] = (float)(imageData[k++] * alternateSign / N);
            dst_i[i][j] = (float)0.0;
         }
      }
   }
   private void copyShortToComplex(float[][] dst_r, float[][] dst_i,
      short[] imageData) {
      int k = 0;

      float alternateSign = 1;

      for(int i=0; i<height; i++)
         for(int j=0; j<width; j++) {
            // Calculate (-1)**(i+j)
            alternateSign = ((i+j)%2 == 0) ? -1 : 1;

            // 1. Put short image array into a complex pair,
            // (-1)**(i+j) is to put the zero frequency in the
            // center of the image when it is displayed.
            // 2. We also take this opportunity to scale the input
            // by N (width * height).
            dst_r[i][j] = (float)(imageData[k++] * alternateSign / N);
            dst_i[i][j] = (float)0.0;
         }
   }

   private float[][] Rotate90(float[][] in)
   {
      float[][] out = new float[height][width];

      for(int i=0; i<height; i++)
      {
         for(int j=0; j<width; j++)
         {
            out[i][j] = in[height-j-1][i];
         }
      }

      return(out);
   }

   private void rotateInPlace180(float[][] in)
   {
      float temp;

      for(int i=0; i<height/2; i++)
      {
         for(int j=0; j<width; j++)
         {
            temp = in[i][j];
            in[i][j] = in[height-i-1][width-j-1];
            in[height-i-1][width-j-1] = temp;
         }
      }
   }

   private float[] copyRealToFloat(float[][] in_r)
   {
      float[] f_data = new float[N];
      int k = 0;

      for(int i=0; i<height; i++)
      {
         for(int j=0; j<width; j++)
         {
            f_data[k++] = in_r[i][j];
         }
      }

      return(f_data);
   }

   private float[] magnitudeSpectrum(float[][] in_r, float[][] in_i)
   {
      float[] mag = new float[N];
      int k = 0;

      for(int i=0; i<height; i++)
      {
         for(int j=0; j<width; j++)
         {
            mag[k] = (float)Math.sqrt(in_r[i][j] * in_r[i][j] +
               in_i[i][j] * in_i[i][j]);

            // Since we're iterating through the loop anyway, see what min is.
            if (minPSD > mag[k])
               minPSD = mag[k];
            if (maxPSD < mag[k])
               maxPSD = mag[k];

            k++;
         }
      }

      return(mag);
   }

   public float[] magnitudeSpectrum(float[] in_r, float[] in_i)
   {
      N = in_r.length;
      float[] mag = new float[N];

      for(int i=0; i<N; i++)
      {
         mag[i] = (float)Math.sqrt(in_r[i] * in_r[i] +
            in_i[i] * in_i[i]);

         // Since we're iterating through the loop anyway,
         // find what min is.
         if (minPSD > mag[i])
         {
            minPSD = mag[i];
         }
      }

      return(mag);
   }

}
