package gui;
import java.awt.*;
import java.awt.event.*;
import fft.*;

public class FFTFrame extends XformFrame {
	MenuItem fftpfa_mi =addMenuItem(xformMenu,"pfa fft");
	MenuItem fftipfa_mi =addMenuItem(xformMenu,"pfa ifft");
	
	MenuItem fftR2_mi = addMenuItem(xformMenu,"[E-T-f]ft radix 2");
	MenuItem ifftR2_mi = addMenuItem(xformMenu,"[E-T-i]fft radix 2");
	MenuItem complexMult_mi = addMenuItem(xformMenu,"complex mult");
	MenuItem rgb2Complex_mi = addMenuItem(xformMenu,"rgb2Complex");
	

	FFTFrame(String title) {
		super(title);
	}
	
	// Define the real and imaginary parts
	// for the color float planes (r,g,b);
	Rgb re = null;
	Rgb im = null;
	public void actionPerformed(ActionEvent e) {
		if (match(e, fftpfa_mi)) {
			fftpfa();
			return;
		}
		if (match(e, fftipfa_mi)) {
			fftipfa();
			return;
		}

		if (match(e, complexMult_mi)) {
			complexMult();
			return;
		}
		if (match(e, rgb2Complex_mi)) {
			rgb2Complex();
			return;
		}
 		if (match(e, fftR2_mi)) {
			fftR2();
			return;
		}
		if (match(e, ifftR2_mi)) {
			ifftR2();
			return;
		}
		super.actionPerformed(e);
	}
	public void test1DFFTvs() {
		String args[] = {};
		vs.vsFFT1D.main(args);
	}
	FFTImage fftimage = null;
	public void fftpfa() {
		Timer t = new Timer();
		t.start();
		fftimage = new FFTImage(getPels(),width,100.0f,true);
		pels2Image(fftimage.fft());
		image2Short();
		t.print("PSA is done");
	}
	public void fftipfa() {
		pels2Image(fftimage.ifft());
		image2Short();
	}
	
	public static int gcd(int a, int b) {
		if (b==0) return a;
		return gcd(b, a % b);
	}

	public void fftR2() {
		Timer t = new Timer();
		t.start();
		isSquareAndPowerOfTwoCheck();
		fftradix2 = new FFTRadix2(this);
		int psd[] = fftradix2.fft();
		pels2Image(psd);
		image2Short();
		t.print("fft done");
	}
	public void complexMult() {
		fftradix2.complexMult();
	}
	private static double LOG2 = Math.log(2);
	
	private int log2(int l) {
		return (int) (Math.log(l)/LOG2);
	}
	private double log2(double l) {
		 return Math.log(l)/LOG2;
	}
	
	private boolean isPowerOfTwo(double l) {
		return log2(l) == log2((int)l);
	}
	
	private boolean bothDimensionsPowerOfTwo() {
		return
			isPowerOfTwo(width) && isPowerOfTwo(height);
	}
	
	private boolean isSquare() {
		return width == height;
	}
	private boolean isSquareAndPowerOfTwo() {
		return isSquare() &&  bothDimensionsPowerOfTwo();
	}
	
	private void isSquareAndPowerOfTwoCheck() {
		if (isSquareAndPowerOfTwo()) return;
		System.out.println("Image is not square and power of two");
	}

	public void ifftR2() {
		Timer t = new Timer();
		System.out.println("Computing ifft...");
		t.start();
		int psd[] = fftradix2.ifft();
		t.print("ifft is done");
		pels2Image(psd);
		image2Short();
	}
	FFTRadix2 fftradix2 = null;
	public void rgb2Complex() {
		fftradix2 = new FFTRadix2(this);
	}
	
}