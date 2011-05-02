package gui;

public class Yuv extends FloatPlane {

// From [Martindale].
double A[][] = {
	{  0.2989,  0.5866,    0.1144},
	{ -0.1473, -0.2891,    0.4364},
	{  0.6149,  0.5145,   -0.1004}
	};

Mat3  rgb2yuvMat = new Mat3(A);
Mat3  yuv2rgbMat = rgb2yuvMat.invert();

public Yuv(ColorFrame _cf){
	super(_cf);
}
 
public void fromRgb() {
	convertSpace(rgb2yuvMat);
	System.out.println("yuv");
	rgb2yuvMat.print();
}

public void toRgb() {
	convertSpace(yuv2rgbMat);
}

}







