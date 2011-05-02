package gui;


public class Yiq extends FloatPlane {

// for ntsc rgb, use
double A[][] = {
	{ 0.2989, 0.5866,  0.1144},
	{ 0.5959,-0.2741, -0.3218},
	{ 0.2113,-0.5227,  0.3113}
	};


Mat3  rgbn2yiqMat = new Mat3(A);
Mat3  yiq2rgbnMat = rgbn2yiqMat.invert();

public Yiq(ColorFrame _cf){
	super(_cf);
}
 
public void fromRgb() {
	convertSpace(rgbn2yiqMat);
	System.out.println("yiq");
	rgbn2yiqMat.print();
}

public void toRgb() {
	convertSpace(yiq2rgbnMat);
}

}







