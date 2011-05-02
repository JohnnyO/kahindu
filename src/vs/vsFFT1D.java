/***************************************************************************
// @(#)vsFFT1D.java
//
// Perform a non recursive in place FFT using doubles.
//
// @version 1.0, 4 JULY 1997
// @author  D. Lyon, V. Silva
****************************************************************************/
package vs;

import java.awt.*;
import java.io.*;


public class vsFFT1D
{
   private final int FORWARD_FFT = -1;
   private final int REVERSE_FFT = 1;
   private float direction = (float)FORWARD_FFT;

   static final float twoPI = (float)(2 * Math.PI);

   // These arrays hold the complex 1D FFT data.
   double r_data[] = null;
   double i_data[] = null;
   
   public static void main(String args[]) {
   	vsFFT1D fft1d = new vsFFT1D();
   	fft1d.testFFT();
   }


   /**
    * A way to visually test the 1D FFT on a small amount of data.
    */
   public void testFFT()
   {
      System.out.println("Testing FFT engine in 1D class...");
      int N = 8;


      double[] in_r = new double[N];
      double[] in_i = new double[N];

      double[] holder_r = new double[N];
      double[] holder_i = new double[N];

      // Create a test ramp signal.
      for(int i=0; i<N; i++) {
         in_r[i] = (double)i / N;
         in_i[i] = (double)0;
      }

      forwardFFT(in_r, in_i);

      // Copy to new array because IFFT will destroy the FFT results.
      for(int i=0; i<N; i++) {
         holder_r[i] = in_r[i];
         holder_i[i] = in_i[i];
      }

      for(int i=0; i<N; i++)
      {
         in_r[i] = in_r[i];
         in_i[i] = in_i[i];
      }

      reverseFFT(in_r, in_i);
      double[] mag = magnitudeSpectrum(in_r, in_i);

      System.out.println("i    x1[i]    re[i]             im[i]           tv[i]");
      for(int i=0; i<N; i++)
      {
         System.out.println(i + "\t" + i + "\t" + holder_r[i] + "\t\t" +
            holder_i[i] + "\t\t" + mag[i]);
      }
      timeFFT(512*512);
 
   }
   public void timeFFT(int n) {
        // Time a 256K FFT
      double in_r[] = new double[n];
      double in_i[] = new double[n];

      // Create a test ramp signal.

      for(int i=0; i<n; i++) {
         in_r[i] = (double)i / n;
         in_i[i] = (double)0;
      }

      gui.Timer t1 = new gui.Timer();
      t1.start();

      forwardFFT(in_r, in_i);
      // Stop the timer and report.
      t1.print(n+" point 1D FFT (using double):");

   }

   /**
    * 1D FFT utility functions.
    */
   public void swap(int i,int numBits)
   {
      double tempr;

      int j = bitr(i,numBits);
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
      double tempr;
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




 int bitr(int j,int numBits) {
    int ans = 0;
    for (int i = 0; i< numBits; i++) {
       ans = (ans <<1) + (j&1);
       j = j>>1;
    }
    return ans;
 }
   public void forwardFFT(double[] in_r, double[] in_i)
   {
      direction = FORWARD_FFT;
      fft(in_r, in_i);
   }

   public void reverseFFT(double[] in_r, double[] in_i)
   {
      direction = REVERSE_FFT;
      fft(in_r, in_i);
   }
   
   public int  log2(double d) {
   	return (int) (Math.log(d)/Math.log(2));
   }

   /**
    * FFT engine.
    */
   public void fft(double in_r[], double in_i[])
   {
	   int id;
      // radix 2 number if sample, 1D of course.
      int localN;
      double wtemp, Wjk_r, Wjk_i, Wj_r, Wj_i;
      double theta, tempr, tempi;
      int ti, tj;

      int numBits = log2(in_r.length);
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


         Wj_r = Math.cos(theta);
         Wj_i = direction * Math.sin(theta);

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


   public double[] getRealData()
   {
      return(r_data);
   }

   public double[] getImaginaryData() {
      return(i_data);
   }


   public double[] magnitudeSpectrum(double[] in_r, double[] in_i) {
   	int N = in_r.length;
      double[] mag = new double[N];
      int k = 0;

      for(int i=0; i<in_r.length; i++)
         mag[i] = Math.sqrt(in_r[i] * in_r[i] + in_i[i] * in_i[i]);

      return(mag);
   }
}
