package fft;
//Title:        1-d mixed radix Inverse FFT.
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Dongyan Wang
//Company:      University of Wisconsin-Milwaukee.
//Description:
//              According to the definition of the inverse fourier transform,
//              We can use FFT to calculate the IFFT,
//                 IFFT(x) = 1/N * conj(FFT(conj(x)).
//
//              . Change the sign of the imaginary part of the FFT input.
//              . Calculate the FFT.
//              . Change the sign of the imaginary part of the FFT output.
//              . Scale the output by 1/N.
//


public class IFFT1d {
  int N;
  // Constructor: IFFT of Complex data.
  public IFFT1d(int N){
    this.N = N;
  }

  public void ifft(float inputRe[], float inputIm[]) {

//  Change the sign of the imaginary part of the FFT input.
    for(int i=0; i<N; i++)
       inputIm[i]=-inputIm[i];

//  Calculate the FFT.
    FFT1d fft1 = new FFT1d(N);
    fft1.fft(inputRe, inputIm);

//  Change the sign of the imaginary part of the FFT output.
//  Scale output by 1/N.

    for(int i=0; i<inputRe.length; i++){
       inputRe[i]=inputRe[i]/N;
       inputIm[i]=-inputIm[i]/N;
    }
  }
}
