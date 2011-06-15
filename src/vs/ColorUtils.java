/**
 * Victor Silva - University of Bridgeport 20 Feb. 1997
 *
 * Various utilities for Victor Silva's image
 * processing project.
 *
 */
package vs;


public class ColorUtils
{
   static public int[] imagetoInt(short[] imageR, short[] imageG,
      short[] imageB, int numPixels)
   {
      int[] imageData = new int[numPixels];

      for(int i=0; i<numPixels; i++)
      {
         imageData[i] = ( (0xFF) << 24 ) |
            ( (imageR[i] & 0xFF) << 16 ) |
            ( (imageG[i] & 0xFF) << 8 ) |
            ( (imageB[i] & 0xFF) );
      }
      return imageData;
   }

   static public int[] imagetoInt(float[] imageR, float[] imageG,
      float[] imageB)
   {
      // Assume the Green and Blue arrays are the same size as Red.
      int numPixels = imageR.length;
      int[] imageData = new int[numPixels];

      for(int i=0; i<numPixels; i++)
      {
         imageData[i] = ( (0xFF) << 24 ) |
            ( ( ((int)imageR[i]) & 0xFF) << 16 ) |
            ( ( ((int)imageG[i]) & 0xFF) << 8 ) |
            ( ( ((int)imageB[i]) & 0xFF) );
      }
      return imageData;
   }

   static public int[] imagetoInt(double[] imageR, double[] imageG,
      double[] imageB, int numPixels)
   {
      int[] imageData = new int[numPixels];

      for(int i=0; i<numPixels; i++)
      {
         imageData[i] = ( (0xFF) << 24 ) |
            ( ( ((int)imageR[i]) & 0xFF) << 16 ) |
            ( ( ((int)imageG[i]) & 0xFF) << 8 ) |
            ( ( ((int)imageB[i]) & 0xFF) );
      }
      return imageData;
   }

   // RGB isolation utils
   public short getRed(int valARGB)
   {
      return((short)((valARGB >> 16) & 0xFF));
   }

   public short getGreen(int valARGB)
   {
      return((short)((valARGB >> 8) & 0xFF));
   }

   public short getBlue(int valARGB)
   {
      return((short)(valARGB & 0xFF));
   }

   public short getA(int valARGB)
   {
      return((short)((valARGB >> 24) & 0xFF));
   }

   public int putRGB(short r, short g, short b)
   {
      int pixRGB;

      pixRGB = (((int)0xFF) << 24) | (((int)r & 0xFF) << 16) | (((int)g & 0xFF) << 8) |
         ((int)b & 0xFF);

      return(pixRGB);
   }


   // Maximum of R,G and B
   public short max(short r, short g, short b)
   {
      if (r > g)
      {
         if (r > b)
         {
            return(r);
         }
         else
         {
            return(b);
         }
      }
      else
      {
         if (g > b)
         {
            return(g);
         }
         else
         {
            return(b);
         }
      }
   }

   // Minimum of R,G and B
   public short min(short r, short g, short b)
   {
      if (r < g)
      {
         if (r < b)
         {
            return(r);
         }
         else
         {
            return(b);
         }
      }
      else
      {
         if (g < b)
         {
            return(g);
         }
         else
         {
            return(b);
         }
      }
   }
 



   public short[] getRedArray(int[] inputArray) {

      int totalPix = inputArray.length;
    	short r[] = new short[totalPix];

    	for (int i=0; i<totalPix; i++)
    	{
         r[i] = (short)((inputArray[i] & 0x00FF0000) >> 16);
      }
      return r;
   }

   public short[] getGreenArray(int[] inputArray)
   {

      int totalPix = inputArray.length;
    	short g[] = new short[totalPix];

    	for (int i=0; i<totalPix; i++)
    	{
         g[i] = (short)((inputArray[i] & 0x0000FF00) >> 8);
      }
      return g;
   }

   public short[] getBlueArray(int[] inputArray)
   {

      int totalPix = inputArray.length;
    	short b[] = new short[totalPix];

    	for (int i=0; i<totalPix; i++)
    	{
         b[i] = (short)(inputArray[i] & 0x000000FF);
      }
      return b;
   }
}