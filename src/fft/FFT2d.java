package fft;
//Title:        2-d mixed radix FFT.
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Dongyan Wang
//Company:      University of Wisconsin-Milwaukee.
//Description:
//              . Use FFT1d to perform FFT2d.



public class FFT2d {

  // Input of FFT, 2-d matrix.
  float dataRe[][], dataIm[][];

  // Width and height of 2-d matrix inputRe or inputIm.
  int width, height;

  // Constructor: 2-d FFT of Complex data.
  public FFT2d(float inputRe[], float inputIm[], int inputWidth) {
    // First make sure inputRe & inputIm are of the same length.
    if(inputRe.length != inputIm.length){
       System.out.println("Error: the length of real part & imaginary part "+
                          "of the input to 2-d FFT are different");
       return;
    }
    else{
       width  = inputWidth;
       height = inputRe.length/width;
       dataRe = new float[height][width];
       dataIm = new float[height][width];

       for(int i=0;i<height;i++)
         for(int j=0;j<width;j++){
           dataRe[i][j] = inputRe[i*width + j];
           dataIm[i][j] = inputIm[i*width + j];
         }

       // Calculate FFT for each row of the data.
       FFT1d fft1 = new FFT1d(width);
       for(int i=0; i<height; i++)
         fft1.fft(dataRe[i], dataIm[i]);

       // Tranpose data.
       // Calculate FFT for each column of the data.
       float temRe[][] = transpose(dataRe);
       float temIm[][] = transpose(dataIm);

       FFT1d fft2 = new FFT1d(height);
       for(int j=0; j<width; j++)
         fft2.fft(temRe[j], temIm[j]);

       // Tranpose data.
       // Copy the result to input[], so the output can be
       // returned in the input array.
       for(int i=0;i<height;i++)
         for(int j=0;j<width;j++){
           inputRe[i*width + j] = temRe[j][i];
           inputIm[i*width + j] = temIm[j][i];
         }
    }
  }

  // Transpose matrix input.
  private float[][] transpose(float [][] input)
  {
    float[][] output = new float[width][height];

    for(int j=0; j<width; j++)
       for(int i=0; i<height; i++)
          output[j][i] = input[i][j];

      return output;
  } // End of function transpose().


} // End of class FFT2d.