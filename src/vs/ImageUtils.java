/**
 * Victor Silva - University of Bridgeport 26 April 1997
 *
 * Various image translation utilities.
 */
package vs;

public class ImageUtils {
   int centerX;
   int centerY;
   int[] pixArray;
   int bgColor=0;

   public int[] rotateImage(int[] inputImage, int width,
      int height, double angle) {
      if (angle%360!=0) {
         centerX = (int)Math.round(width / 2);
         centerY = (int)Math.round(height / 2);
         pixArray = new int[width * height];

         //Convert from degrees to radians and calculate cos and sin of angle
         //Negate the angle to make sure the rotation is clockwise
         double angleRadians = -angle / (180.0 / Math.PI);
         double ca = Math.cos(angleRadians);
         double sa = Math.sin(angleRadians);

         int index=0;
         for (int y=-centerY; y<centerY; y++) {
            for (int x=-centerX; x<centerX; x++) {
               int xs = (int)(x * ca - y * sa) + centerX;
               int ys = (int)(y * ca + x * sa) + centerY;
               if ((xs>=0) && (xs<width) && (ys>=0) && (ys<height))
                  pixArray[index++] = inputImage[width * ys + xs];
               else
                  pixArray[index++] = bgColor;
            }
         }
      }
      return(pixArray);
   }

public int[] zoomImage(
	int[] inputImage, 
	int width, int height, int percent){
	//Duplicate image
    centerX = (int)Math.round(width / 2);
    centerY = (int)Math.round(height / 2);
    pixArray = new int[width * height];

    int index=0;
    for (int y=-centerY;y<centerY;y++) {
       for (int x=-centerX;x<centerX;x++) {
			 int xs=(x*percent)/100+centerX;
			 int ys=(y*percent)/100+centerY;
			 if (xs>=0&&xs<width&&ys>=0&&ys<height)
			    pixArray[index++]=inputImage[width*ys+xs];
			 else
			    pixArray[index++]=bgColor;
	}
   }
   return(pixArray);
  }
}