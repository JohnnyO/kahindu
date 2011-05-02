package gui;

import java.awt.*;

public class Mat3 {
	double a[] []  = 
		{{1,0,0},
		 {0,1,0},
		 {0,0,1}};
	
	private static final double piOn180 = Math.PI / 180.0;


public void setTranslation(double tx, double ty) {
	a[0][0] = 1;
	a[1][1] = 1;
	a[2][2] = 1;
	a[0][2] = tx;
	a[1][2] = ty;
}

public void setScale(double sx, double sy) {
	a[0][0] = sx;
	a[1][1] = sy;
	a[2][2] = 1;
}
public void  setRotation(double theta) {
	theta = theta  * Math.PI/180;
	double cas = Math.cos(theta);
	double sas = Math.sin(theta);
	a[0][0] =  cas;
	a[1][1] =  cas;
	a[0][1] = -sas;
	a[1][0] =  sas;
}

public  void setShear(double shx, double shy) {
	a[0][0] = 1;
	a[1][1] = 1;
	a[2][2] = 1;
	a[0][1] = shx;
	a[1][0] = shy;
}
// a00 a01 a02
// a10 a11 a12
// a20 a21 a22
public  void setPerspective(double px, double py) {
	a[0][0] = 1;
	a[1][1] = 1;
	a[2][2] = 1;
	a[2][0] = px;
	a[2][1] = py;
}
public static void main(String args[]) {
	Mat3 tr1 = new Mat3();
	Mat3 tr2 = new Mat3();
	Mat3 sc = new Mat3();
	Mat3 at;

	tr1.setTranslation(1,1);
	sc.setScale(2,2);
	tr2.setTranslation(-1,-1);

	at = tr1.multiply(sc);
	at = at.multiply(tr2);
	at.print();
	at = new Mat3();
	int x[] = at.multiply(1,2,1);
	at.print();
	for(int i = 0; i < x.length; i++) 
		System.out.println(x[i]);

}

public  Polygon transform(Polygon p ) {
	Polygon pp = new Polygon();
	int x[];
	for (int i = 0; i < p.npoints; i++) {
		x = multiply(p.xpoints[i],p.ypoints[i],1);
		pp.addPoint(x[0],x[1]);
	}
	return pp;
}

public static int [] centroid(Polygon p) {
	int x[] = new int[2];
	int xsum = 0;
	int ysum = 0;
	for (int i = 0; i < p.npoints; i++) {
		xsum += p.xpoints[i];
		ysum += p.ypoints[i];
	}
	x[0] = xsum / p.npoints;
	x[1] = ysum / p.npoints;
	return x;	
}

public Mat3() {};
public Mat3 (double a_[][]) {
	a = a_;
}

public double [] [] getArray() {
	return a;
}

/*
To generate a time-optimal 3x3 matrix inversion, use maple:
with(linalg):readlib(C):
a:=array(0..2,0..2,[]):
 b:=array(0..2,0..2,[]):
 b:=inverse(matrix(a)):
 C(b,optimized);

*/
public Mat3 invert() {
   double b[] [] = new double [3][3];
   double  t4 = a[0][0]*a[1][1];
   double t6 = a[0][0]*a[1][2];
   double t8 = a[0][1]*a[1][0];
   double t10 = a[0][2]*a[1][0];
   double t12 = a[0][1]*a[2][0];
   double t14 = a[0][2]*a[2][0];
   double t17 = 
   	1/(-t4*a[2][2]+t6*a[2][1]+t8*a[2][2]-t10*a[2][1]-t12*a[1][2]+t14*a
[1][1]);
   b[0][0] = -(a[1][1]*a[2][2]-a[1][2]*a[2][1])*t17;
   b[0][1] = -(-a[0][1]*a[2][2]+a[0][2]*a[2][1])*t17;
   b[0][2] = (-a[0][1]*a[1][2]+a[0][2]*a[1][1])*t17;
   b[1][0] = (a[1][0]*a[2][2]-a[1][2]*a[2][0])*t17;
   b[1][1] = (-a[0][0]*a[2][2]+t14)*t17;
   b[1][2] = -(-t6+t10)*t17;
   b[2][0] = (-a[1][0]*a[2][1]+a[1][1]*a[2][0])*t17;
   b[2][1] = -(-a[0][0]*a[2][1]+t12)*t17;
   b[2][2] = (-t4+t8)*t17;
      
   return new Mat3(b);
}
/* To generate a new matrix, transposed:
 with(linalg):readlib(C):
 a:=array(0..2,0..2,[]):
 b:=array(0..2,0..2,[]):
 b:=transpose(matrix(a)):
 C(b,optimized);
*/
public Mat3 transpose() {
	double b[][] = new double[3][3];
      b[0][0] = a[0][0];
      b[0][1] = a[1][0];
      b[0][2] = a[2][0];
      b[1][0] = a[0][1];
      b[1][1] = a[1][1];
      b[1][2] = a[2][1];
      b[2][0] = a[0][2];
      b[2][1] = a[1][2];
      b[2][2] = a[2][2];
    return new Mat3(b);
}



public Mat3 multiply(Mat3 bmat3) {
double WW [][] = new double[3][3];
double b [][] = bmat3.getArray();
	  WW[0][0] = a[0][0]*b[0][0]+a[0][1]*b[1][0]+a[0][2]*b[2][0];
      WW[0][1] = a[0][0]*b[0][1]+a[0][1]*b[1][1]+a[0][2]*b[2][1];
      WW[0][2] = a[0][0]*b[0][2]+a[0][1]*b[1][2]+a[0][2]*b[2][2];
      WW[1][0] = a[1][0]*b[0][0]+a[1][1]*b[1][0]+a[1][2]*b[2][0];
      WW[1][1] = a[1][0]*b[0][1]+a[1][1]*b[1][1]+a[1][2]*b[2][1];
      WW[1][2] = a[1][0]*b[0][2]+a[1][1]*b[1][2]+a[1][2]*b[2][2];
      WW[2][0] = a[2][0]*b[0][0]+a[2][1]*b[1][0]+a[2][2]*b[2][0];
      WW[2][1] = a[2][0]*b[0][1]+a[2][1]*b[1][1]+a[2][2]*b[2][1];
      WW[2][2] = a[2][0]*b[0][2]+a[2][1]*b[1][2]+a[2][2]*b[2][2];
      return (new Mat3(WW));
}

/*
In maple:
vec3:=vector([v1, v2, v3]):
C(multiply(matrix(a),vec3),optimized):

does a post-multiplication, v*A
*/

public int[] multiply(int v1, int v2, int v3) {
	int x[] = new int [3];
      x[0] = (int)(a[0][0]*v1+a[0][1]*v2+a[0][2]*v3);
      x[1] = (int)(a[1][0]*v1+a[1][1]*v2+a[1][2]*v3);
      x[2] = (int)(a[2][0]*v1+a[2][1]*v2+a[2][2]*v3);
      return x;
}
public float[] multiply(float v1, float v2, float v3) {
	float x[] = new float [3];
      x[0] = (int)(a[0][0]*v1+a[0][1]*v2+a[0][2]*v3);
      x[1] = (int)(a[1][0]*v1+a[1][1]*v2+a[1][2]*v3);
      x[2] = (int)(a[2][0]*v1+a[2][1]*v2+a[2][2]*v3);
      return x;
}

// The following is premultiplied!
public int[] premultiply(int v1, int v2) {
	int x[] = new int [2];
      x[0] = (int)(v1*a[0][0]+v2*a[1][0]+a[2][0]);
      x[1] = (int)(v1*a[0][1]+v2*a[1][1]+a[2][1]);
      return x;
}

/*
In maple:
vec3:=vector([v1, v2, v3]):
C(multiply(vec3,matrix(a)),optimized):

does a pre-multiplication, v*A
*/
public double[] premultiply(double v1, double v2, double v3 ) {
	double x[] = new double [3];
      x[0] = v1*a[0][0]+v2*a[1][0]+v3*a[2][0];
      x[1] = v1*a[0][1]+v2*a[1][1]+v3*a[2][1];
      x[2] = v1*a[0][2]+v2*a[1][2]+v3*a[2][2];
      return x;
}
public float[] premultiply(float v1, float v2, float v3 ) {
	float x[] = new float [3];
      x[0] = (float)(v1*a[0][0]+v2*a[1][0]+v3*a[2][0]);
      x[1] = (float)(v1*a[0][1]+v2*a[1][1]+v3*a[2][1]);
      x[2] = (float)(v1*a[0][2]+v2*a[1][2]+v3*a[2][2]);
      return x;
}
public void print(String s) {
	System.out.println(s);
	print(); 
}
public void print() {
	for (int i = 0; i < a.length; i++) {
		for (int j= 0; j < a[0].length; j++)
		  System.out.print(a[i][j]+" ");
		System.out.println();
	}
}
		
}

