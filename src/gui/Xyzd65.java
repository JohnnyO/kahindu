package gui;

public class Xyzd65 extends FloatPlane {

double A[][] = {
	{ 0.3935, 0.3653,  0.1916},
	{ 0.2124, 0.7011,  0.0865},
	{ 0.0187, 0.1119,  0.9582}
	};
	
float gamma = 2.2f;
float oneOnGamma = 1/gamma;

Mat3  rgb2xyzMat = new Mat3(A);
Mat3  xyz2rgbMat = rgb2xyzMat.invert();

public Xyzd65(ColorFrame _cf){
	super(_cf);
}
 
public void fromRgb() {
	scale(1/255f);
	pow(gamma);
	convertSpace(rgb2xyzMat);
	rgb2xyzMat.print();
}

public void toRgb() {
	convertSpace(xyz2rgbMat);
	pow(oneOnGamma);
	scale(255f);
	xyz2rgbMat.print();
}

}







