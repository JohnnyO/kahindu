package gui;

import fft.FFT2d;
import fft.IFFT2d;
import fft.ImageUtils;

// Calculate the fft of input image, using fft.FFT2d.
public class FFTImage {

  int image[];
  int imageWidth, imageHeight;
  // image.length, or imageWidth * imageHeight
  int N;
  // scale is used to scale the FFT input to prevent overflow,
  // N = imageWidth * imageHeight is often used.
  int scale;
  // Scale the FFT output magnitude to get the best display result.
  float magScale;

  boolean fftShift;

  short alpha[];
  float redRe[], greenRe[], blueRe[];
  float redIm[], greenIm[], blueIm[];

  public FFTImage(int image[], int imageWidth,
                  float magScale, boolean fftShift) {
    this.image = image;
    this.imageWidth = imageWidth;
    N = image.length;
    imageHeight = N /imageWidth;
    this.magScale = magScale;
    this.fftShift = fftShift;
    scale = N;

    alpha   =  ImageUtils.getAlpha(image);
    short red[]   =  ImageUtils.getRed(image);
    short green[] =  ImageUtils.getGreen(image);
    short blue[]  =  ImageUtils.getBlue(image);

    // If fftShift is true, shift the zero frequency to the center.
    redRe   = fftReorder(red);
    greenRe = fftReorder(green);
    blueRe  = fftReorder(blue);
    redIm   = new float[N];
    greenIm = new float[N];
    blueIm  = new float[N];
  }

  public int[] fft(){
    new FFT2d(redRe  , redIm  , imageWidth);
    new FFT2d(greenRe, greenIm, imageWidth);
    new FFT2d(blueRe , blueIm , imageWidth);

    float resultRed[]   = magnitude(redRe, redIm);
    float resultGreen[] = magnitude(greenRe, greenIm);
    float resultBlue[]  = magnitude(blueRe, blueIm);

    int resultImage[] = ImageUtils.ARGBtoInt(alpha, resultRed,
                                       resultGreen, resultBlue);
    return resultImage;
  }

  public int[] ifft(){
    new IFFT2d(redRe  , redIm  , imageWidth);
    new IFFT2d(greenRe, greenIm, imageWidth);
    new IFFT2d(blueRe , blueIm , imageWidth);

    short resultRed[]   = ifftReorder(redRe);
    short resultGreen[] = ifftReorder(greenRe);
    short resultBlue[]  = ifftReorder(blueRe);

    int resultImage[] = ImageUtils.ARGBtoInt(alpha, resultRed,
                                       resultGreen, resultBlue);
    return resultImage;
  }

  // reorder color data of fft input.
  // 1. Convert color data from short to float.
  // 2. Scale the color data by scale.
  // 3. If fftShift is true, shift the zero frequency in the center of matrix.
  private float[] fftReorder(short color[]){
    float result[] = new float[N];

    if(!fftShift){   // Without zero frequency shift.
        for(int i=0; i<N; i++)
           result[i] = (float)color[i]/scale;
    }
    else{            // With zero frequency shift.
       int k = 0;
       float alternateSign = 1;
       for(int i=0; i<imageHeight; i++)
         for(int j=0; j<imageWidth; j++){
           alternateSign = ((i+j)%2 == 0) ? -1 : 1;
           result[i*imageWidth+j] = (float)(color[k++] * alternateSign/scale);
         }
    }
    return result;
  } // End of function fftReorder().

  private short[] ifftReorder(float re[]){
    short result[] = new short[N];

    if(!fftShift){   // Without zero frequency shift.
        for(int i=0; i<N; i++)
           result[i] = (short)(re[i]*scale);
    }
    else{            // With zero frequency shift.
       int k = 0;
       float alternateSign = 1;
       for(int i=0; i<imageHeight; i++)
         for(int j=0; j<imageWidth; j++){
           alternateSign = ((i+j)%2 == 0) ? -1 : 1;
           result[i*imageWidth+j] = (short)(re[k++] * alternateSign*scale);
         }
    }
    return result;
  } // End of function fftReorder().

  // Scale the FFT output magnitude to get the best display result.
  private float[] magnitude(float re[], float im[]){
    float result[] = new float[N];
    for (int i=0; i<N; i++){
       result[i] = (float)(magScale*
       	Math.log(1+Math.sqrt(re[i] * re[i] + im[i] * im[i])));
       if(result[i] > 255)
         result[i] = 255;
    }
    return result;
  } // End of function magnitude().

} // End of class FFTImage.
