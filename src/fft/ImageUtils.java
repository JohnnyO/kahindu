//Title:        WDYLBJIP1
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Dongyan Wang & Bo Lin
//Company:      UWM
//Description:  Java Image Processing

package fft;
//package WDYLBJIP1;


public class ImageUtils {

  public ImageUtils() {
  }

  public int ARGBtoInt(short alpha, short red, short green, short blue){
    return alpha<<24 | red <<16 | green<<8 | blue;
  }

  static public int[] ARGBtoInt(short[] alpha, short[] red,
                                short[] green, short[] blue){

    int length = alpha.length;
    int[] imageInt = new int[length];

    for(int i=0; i<length; i++){
       imageInt[i] = alpha[i]<<24 | red[i] <<16 | green[i]<<8 | blue[i];
      }
    return imageInt;
  }

  static public int[] ARGBtoInt(short[] alpha, float[] red,
                                float[] green, float[] blue){

    int length = alpha.length;
    int[] imageInt = new int[length];

    for(int i=0; i<length; i++){
       imageInt[i] = alpha[i]<<24 | ((int)red[i])<<16 |
                     ((int)green[i])<<8 | ((int)blue[i]);
      }
    return imageInt;
  }

  static public int[] ARGBtoInt(short[] alpha, double[] red,
                                double[] green, double[] blue){

    int length = alpha.length;
    int[] imageInt = new int[length];

    for(int i=0; i<length; i++){
       imageInt[i] = alpha[i]<<24 | ((int)red[i])<<16 |
                     ((int)green[i])<<8 | ((int)blue[i]);
      }
    return imageInt;
   }

  static public short getAlpha(int argb){
    return (short)((argb >> 24) & 0xFF);
  }

  static public short getRed(int argb){
    return (short)((argb >> 16) & 0xFF);
  }

  static public short getGreen(int argb){
    return (short)((argb >> 8) & 0xFF);
  }

  static public short getBlue(int argb){
    return((short)(argb & 0xFF));
  }


  static public short[] getRed(int[] argb){
    int length = argb.length;
    short red[] = new short[length];

    for (int i=0; i<length; i++)
       red[i] = (short)((argb[i] & 0x00FF0000) >> 16);
    return red;
  }

  static public short[] getGreen(int[] argb){
    int length = argb.length;
    short green[] = new short[length];

    for (int i=0; i<length; i++)
       green[i] = (short)((argb[i] & 0x0000FF00) >> 8);
    return green;
  }

  static public short[] getBlue(int[] argb){
    int length = argb.length;
    short blue[] = new short[length];

    for (int i=0; i<length; i++)
       blue[i] = (short)(argb[i] & 0x000000FF) ;
    return blue;
  }

  static public short[] getAlpha(int[] argb){
    int length = argb.length;
    short alpha[] = new short[length];

    for (int i=0; i<length; i++)
       alpha[i] = (short)((argb[i] & 0xFF000000) >> 24);
    return alpha;
  }

}  // End of class ImageUtils.