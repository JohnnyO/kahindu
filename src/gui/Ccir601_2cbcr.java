package gui;

public class Ccir601_2cbcr extends FloatPlane {

// See CCIR 601-2
// available at 
// http://www.igd.fhg.de/icib/tv/ccir/rec_601-2/scan.html
double A[][] = {
	{ 0.299, 0.587,  0.114},
	{ 0.701,-0.587, -0.114},
	{ 0.299,-0.587,  0.886}
	};
	

Mat3  rgb2cycbcrMat = new Mat3(A);
Mat3  cycbcr2rgbMat = rgb2cycbcrMat.invert();

public Ccir601_2cbcr(ColorFrame _cf){
	super(_cf);
}
 
public void fromRgb() {
	convertSpace(rgb2cycbcrMat);
	System.out.println("cycbcr");
	rgb2cycbcrMat.print();
}

public void toRgb() {	
	convertSpace(cycbcr2rgbMat);
	cycbcr2rgbMat.print();
}

}







