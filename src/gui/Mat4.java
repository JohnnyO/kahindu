package gui;

import java.awt.*;

public class Mat4 {
	double a[] []  = 
		{{1,0,0,0},
		 {0,1,0,0},
		 {0,0,1,0},
		 {0,0,0,1}};
	
	private static final double piOn180 = Math.PI / 180.0;


public Mat4() {};
public Mat4 (double a_[][]) {
	a = a_;
	if (a.length != 4) 
		System.out.println("Wrong size in Mat4, Danger Will Robinson!");
	if (a.length != a[0].length)
		System.out.println("row != col in Mat4, Take cover Dr. Smith!");
}

public double [] [] getArray() {
	return a;
}

/*
To find the determinant fast, in Maple, use:
with(linalg):readlib(C):
  a:=array(0..3,0..3,[]):
 C(det(matrix(a)));
*/
public double determinant() {
      double s1 = a[0][0]*a[1][1]*a[2][2]*a[3][3]-a[0][0]*a[1][1]*a[2][3]*a[3][2]-a[0]
[0]*a[2][1]*a[1][2]*a[3][3]+a[0][0]*a[2][1]*a[1][3]*a[3][2]+a[0][0]*a[3][1]*a
[1][2]*a[2][3]-a[0][0]*a[3][1]*a[1][3]*a[2][2]-a[1][0]*a[0][1]*a[2][2]*a[3][3]+
a[1][0]*a[0][1]*a[2][3]*a[3][2]+a[1][0]*a[2][1]*a[0][2]*a[3][3]-a[1][0]*a[2][1]
*a[0][3]*a[3][2]-a[1][0]*a[3][1]*a[0][2]*a[2][3]+a[1][0]*a[3][1]*a[0][3]*a[2]
[2];
      return s1+a[2][0]*a[0][1]*a[1][2]*a[3][3]-a[2][0]*a[0][1]*a[1][3]*a[3][2]-a
[2][0]*a[1][1]*a[0][2]*a[3][3]+a[2][0]*a[1][1]*a[0][3]*a[3][2]+a[2][0]*a[3][1]*
a[0][2]*a[1][3]-a[2][0]*a[3][1]*a[0][3]*a[1][2]-a[3][0]*a[0][1]*a[1][2]*a[2][3]
+a[3][0]*a[0][1]*a[1][3]*a[2][2]+a[3][0]*a[1][1]*a[0][2]*a[2][3]-a[3][0]*a[1]
[1]*a[0][3]*a[2][2]-a[3][0]*a[2][1]*a[0][2]*a[1][3]+a[3][0]*a[2][1]*a[0][3]*a
[1][2];
}


/*
To generate a time-optimal 4x4 matrix inversion, use maple:
with(linalg):readlib(C):
 a:=array(0..3,0..3,[]):
 b:=array(0..3,0..3,[]):
 b:=inverse(matrix(a)):
 C(b,optimized);

*/
public Mat4 invert() {
	if (determinant() == 0) {
		System.out.println("invert error: determinant == 0");
		throw new ArithmeticException();
	}
   double b[] [] = new double [4][4];
      t14 = a[0][0]*a[1][1];
      t15 = a[2][2]*a[3][3];
      t17 = a[2][3]*a[3][2];
      t19 = a[0][0]*a[2][1];
      t20 = a[1][2]*a[3][3];
      t22 = a[1][3]*a[3][2];
      t24 = a[0][0]*a[3][1];
      t25 = a[1][2]*a[2][3];
      t27 = a[1][3]*a[2][2];
      t29 = a[1][0]*a[0][1];
      t32 = a[1][0]*a[2][1];
      t33 = a[0][2]*a[3][3];
      t35 = a[0][3]*a[3][2];
      t37 = a[1][0]*a[3][1];
      t38 = a[0][2]*a[2][3];
      t40 = a[0][3]*a[2][2];
      t42 = t14*t15-t14*t17-t19*t20+t19*t22+t24*t25-t24*t27-t29*t15+t29*t17+t32
*t33-t32*t35-t37*t38+t37*t40;
      t43 = a[2][0]*a[0][1];
      t46 = a[2][0]*a[1][1];
      t49 = a[2][0]*a[3][1];
      t50 = a[0][2]*a[1][3];
      t52 = a[0][3]*a[1][2];
      t54 = a[3][0]*a[0][1];
      t57 = a[3][0]*a[1][1];
      t60 = a[3][0]*a[2][1];
      t63 = t43*t20-t43*t22-t46*t33+t46*t35+t49*t50-t49*t52-t54*t25+t54*t27+t57
*t38-t57*t40-t60*t50+t60*t52;
      t65 = 1/(t42+t63);
      t71 = a[0][2]*a[2][1];
      t73 = a[0][3]*a[2][1];
      t75 = a[0][2]*a[3][1];
      t77 = a[0][3]*a[3][1];
      t81 = a[0][1]*a[1][2];
      t83 = a[0][1]*a[1][3];
      t85 = a[0][2]*a[1][1];
      t87 = a[0][3]*a[1][1];
      t101 = a[1][0]*a[2][2];
      t103 = a[1][0]*a[2][3];
      t105 = a[2][0]*a[1][2];
      t107 = a[2][0]*a[1][3];
      t109 = a[3][0]*a[1][2];
      t111 = a[3][0]*a[1][3];
      t115 = a[0][0]*a[2][2];
      t117 = a[0][0]*a[2][3];
      t119 = a[2][0]*a[0][2];
      t121 = a[2][0]*a[0][3];
      t123 = a[3][0]*a[0][2];
      t125 = a[3][0]*a[0][3];
      t129 = a[0][0]*a[1][2];
      t131 = a[0][0]*a[1][3];
      t133 = a[1][0]*a[0][2];
      t135 = a[1][0]*a[0][3];
      b[0][0] = (a[1][1]*a[2][2]*a[3][3]-a[1][1]*a[2][3]*a[3][2]-a[2][1]*a[1]
[2]*a[3][3]+a[2][1]*a[1][3]*a[3][2]+a[3][1]*a[1][2]*a[2][3]-a[3][1]*a[1][3]*a
[2][2])*t65;
      b[0][1] = -(a[0][1]*a[2][2]*a[3][3]-a[0][1]*a[2][3]*a[3][2]-t71*a[3][3]+
t73*a[3][2]+t75*a[2][3]-t77*a[2][2])*t65;
      b[0][2] = (t81*a[3][3]-t83*a[3][2]-t85*a[3][3]+t87*a[3][2]+t75*a[1][3]-
t77*a[1][2])*t65;
      b[0][3] = -(t81*a[2][3]-t83*a[2][2]-t85*a[2][3]+t87*a[2][2]+t71*a[1][3]-
t73*a[1][2])*t65;
      b[1][0] = -(t101*a[3][3]-t103*a[3][2]-t105*a[3][3]+t107*a[3][2]+t109*a[2]
[3]-t111*a[2][2])*t65;
      b[1][1] = (t115*a[3][3]-t117*a[3][2]-t119*a[3][3]+t121*a[3][2]+t123*a[2]
[3]-t125*a[2][2])*t65;
      b[1][2] = -(t129*a[3][3]-t131*a[3][2]-t133*a[3][3]+t135*a[3][2]+t123*a[1]
[3]-t125*a[1][2])*t65;
      b[1][3] = (t129*a[2][3]-t131*a[2][2]-t133*a[2][3]+t135*a[2][2]+t119*a[1]
[3]-t121*a[1][2])*t65;
      b[2][0] = (t32*a[3][3]-t103*a[3][1]-t46*a[3][3]+t107*a[3][1]+t57*a[2][3]-
t111*a[2][1])*t65;
      b[2][1] = -(t19*a[3][3]-t117*a[3][1]-t43*a[3][3]+t121*a[3][1]+t54*a[2][3]
-t125*a[2][1])*t65;
      b[2][2] = (t14*a[3][3]-t131*a[3][1]-t29*a[3][3]+t135*a[3][1]+t54*a[1][3]-
t125*a[1][1])*t65;
      b[2][3] = -(t14*a[2][3]-t131*a[2][1]-t29*a[2][3]+t135*a[2][1]+t43*a[1][3]
-t121*a[1][1])*t65;
      b[3][0] = -(t32*a[3][2]-t101*a[3][1]-t46*a[3][2]+t105*a[3][1]+t57*a[2][2]
-t109*a[2][1])*t65;
      b[3][1] = (t19*a[3][2]-t115*a[3][1]-t43*a[3][2]+t119*a[3][1]+t54*a[2][2]-
t123*a[2][1])*t65;
      b[3][2] = -(t14*a[3][2]-t129*a[3][1]-t29*a[3][2]+t133*a[3][1]+t54*a[1][2]
-t123*a[1][1])*t65;
      b[3][3] = (t14*a[2][2]-t129*a[2][1]-t29*a[2][2]+t133*a[2][1]+t43*a[1][2]-
t119*a[1][1])*t65;
      
   return new Mat4(b);
}
/* To generate a new matrix, transposed:
 with(linalg):readlib(C):
 a:=array(0..3,0..3,[]):
 b:=array(0..3,0..3,[]):
 b:=transpose(matrix(a)):
 C(b,optimized);
*/
public Mat4 transpose() {
	double b[][] = new double[3][3];
     b[0][0] = a[0][0];
      b[0][1] = a[1][0];
      b[0][2] = a[2][0];
      b[0][3] = a[3][0];
      b[1][0] = a[0][1];
      b[1][1] = a[1][1];
      b[1][2] = a[2][1];
      b[1][3] = a[3][1];
      b[2][0] = a[0][2];
      b[2][1] = a[1][2];
      b[2][2] = a[2][2];
      b[2][3] = a[3][2];
      b[3][0] = a[0][3];
      b[3][1] = a[1][3];
      b[3][2] = a[2][3];
      b[3][3] = a[3][3];
    return new Mat4(b);
}

/*
 To multiply two 4x4's, fast, use:
 
with(linalg):
  readlib(C):
  a:=array(0..3,0..3,[]):
  b:=array(0..3,0..3,[]):
  c:=array(0..3,0..3,[]):
  c:=multiply(matrix(b),matrix(a)):
  C(c,optimized);
*/

public Mat4 multiply(Mat4 bmat4) {
double c [][] = new double[4][4];
double b [][] = bmat4.getArray();
	c[0][0] = b[0][0]*a[0][0]+b[0][1]*a[1][0]+b[0][2]*a[2][0]+b[0][3]*a[3][0];
	c[0][1] = b[0][0]*a[0][1]+b[0][1]*a[1][1]+b[0][2]*a[2][1]+b[0][3]*a[3][1];
	c[0][2] = b[0][0]*a[0][2]+b[0][1]*a[1][2]+b[0][2]*a[2][2]+b[0][3]*a[3][2];
	c[0][3] = b[0][0]*a[0][3]+b[0][1]*a[1][3]+b[0][2]*a[2][3]+b[0][3]*a[3][3];
	c[1][0] = b[1][0]*a[0][0]+b[1][1]*a[1][0]+b[1][2]*a[2][0]+b[1][3]*a[3][0];
	c[1][1] = b[1][0]*a[0][1]+b[1][1]*a[1][1]+b[1][2]*a[2][1]+b[1][3]*a[3][1];
	c[1][2] = b[1][0]*a[0][2]+b[1][1]*a[1][2]+b[1][2]*a[2][2]+b[1][3]*a[3][2];
	c[1][3] = b[1][0]*a[0][3]+b[1][1]*a[1][3]+b[1][2]*a[2][3]+b[1][3]*a[3][3];
	c[2][0] = b[2][0]*a[0][0]+b[2][1]*a[1][0]+b[2][2]*a[2][0]+b[2][3]*a[3][0];
	c[2][1] = b[2][0]*a[0][1]+b[2][1]*a[1][1]+b[2][2]*a[2][1]+b[2][3]*a[3][1];
	c[2][2] = b[2][0]*a[0][2]+b[2][1]*a[1][2]+b[2][2]*a[2][2]+b[2][3]*a[3][2];
	c[2][3] = b[2][0]*a[0][3]+b[2][1]*a[1][3]+b[2][2]*a[2][3]+b[2][3]*a[3][3];
	c[3][0] = b[3][0]*a[0][0]+b[3][1]*a[1][0]+b[3][2]*a[2][0]+b[3][3]*a[3][0];
	c[3][1] = b[3][0]*a[0][1]+b[3][1]*a[1][1]+b[3][2]*a[2][1]+b[3][3]*a[3][1];
	c[3][2] = b[3][0]*a[0][2]+b[3][1]*a[1][2]+b[3][2]*a[2][2]+b[3][3]*a[3][2];
	c[3][3] = b[3][0]*a[0][3]+b[3][1]*a[1][3]+b[3][2]*a[2][3]+b[3][3]*a[3][3];      
	return (new Mat4(c));
}

public static  Mat4 polygonToMat4(Polygon p) {
	if (p.npoints != 4)
		System.out.println("wrong number of points in Mat4:polygonToMat4");
	// use the point points in the polygon to construct a
	// matrix of the form:
	// [uv u v 1]
	int u[] = p.xpoints;
	int v[] = p.ypoints;
	double b[][] = {
		{u[0], v[0], u[0]*v[0], 1},
		{u[1], v[1], u[1]*v[1], 1},
		{u[2], v[2], u[2]*v[2], 1},
		{u[3], v[3], u[3]*v[3], 1}};
	return new Mat4(b);	
}
/*
To do a fast premultply of a 2x4*4x4,
use:
with(linalg):
    readlib(C):
    a:=array(0..3,0..3,[]):
    b:=array(0..1,0..3,[]):
    c:=array(0..1,0..3,[]):
    c:=multiply(matrix(b),matrix(a)):
    C(c,optimized);
*/
public  double [][] multiply2x4(double b[][]) {
	if (b.length != 2)
		System.out.println("multiply2x4: bad b.length"); 
	if (b[0].length != 4)
		System.out.println("multiply2x4: bad b[0].length");
	double c[][] = new double[2][4]; 
      c[0][0] = b[0][0]*a[0][0]+b[0][1]*a[1][0]+b[0][2]*a[2][0]+b[0][3]*a[3][0];
      c[0][1] = b[0][0]*a[0][1]+b[0][1]*a[1][1]+b[0][2]*a[2][1]+b[0][3]*a[3][1];
      c[0][2] = b[0][0]*a[0][2]+b[0][1]*a[1][2]+b[0][2]*a[2][2]+b[0][3]*a[3][2];
      c[0][3] = b[0][0]*a[0][3]+b[0][1]*a[1][3]+b[0][2]*a[2][3]+b[0][3]*a[3][3];
      c[1][0] = b[1][0]*a[0][0]+b[1][1]*a[1][0]+b[1][2]*a[2][0]+b[1][3]*a[3][0];
      c[1][1] = b[1][0]*a[0][1]+b[1][1]*a[1][1]+b[1][2]*a[2][1]+b[1][3]*a[3][1];
      c[1][2] = b[1][0]*a[0][2]+b[1][1]*a[1][2]+b[1][2]*a[2][2]+b[1][3]*a[3][2];
      c[1][3] = b[1][0]*a[0][3]+b[1][1]*a[1][3]+b[1][2]*a[2][3]+b[1][3]*a[3][3];
     return c;
 }

/*
 To get 4x2 = 4x4*4x2 , fast, use:
 
with(linalg):
   readlib(C):
   a:=array(0..3,0..3,[]):
   b:=array(0..3,0..1,[]):
   c:=array(0..3,0..1,[]):
   c:=multiply(matrix(a),matrix(b)):
   C(c,optimized);
*/
public  double [][] multiply4x2(double b[][]) {
	if (b.length != 4)
		System.out.println("multiply4x2: bad b.length"); 
	if (b[0].length != 2)
		System.out.println("multiply4x2: bad b[0].length");
	double c[][] = new double[4][2]; 
    c[0][0] = a[0][0]*b[0][0]+a[0][1]*b[1][0]+a[0][2]*b[2][0]+a[0][3]*b[3][0];
    c[0][1] = a[0][0]*b[0][1]+a[0][1]*b[1][1]+a[0][2]*b[2][1]+a[0][3]*b[3][1];
    c[1][0] = a[1][0]*b[0][0]+a[1][1]*b[1][0]+a[1][2]*b[2][0]+a[1][3]*b[3][0];
    c[1][1] = a[1][0]*b[0][1]+a[1][1]*b[1][1]+a[1][2]*b[2][1]+a[1][3]*b[3][1];
    c[2][0] = a[2][0]*b[0][0]+a[2][1]*b[1][0]+a[2][2]*b[2][0]+a[2][3]*b[3][0];
    c[2][1] = a[2][0]*b[0][1]+a[2][1]*b[1][1]+a[2][2]*b[2][1]+a[2][3]*b[3][1];
    c[3][0] = a[3][0]*b[0][0]+a[3][1]*b[1][0]+a[3][2]*b[2][0]+a[3][3]*b[3][0];
    c[3][1] = a[3][0]*b[0][1]+a[3][1]*b[1][1]+a[3][2]*b[2][1]+a[3][3]*b[3][1];
    return c;
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
static double t0 = 0;
static double t1 = 0;
static double t2 = 0;
static double t3 = 0;
static double t4 = 0;
static double t5 = 0;
static double t6 = 0;
static double t7 = 0;
static double t8 = 0;
static double t9 = 0;
static double t10 = 0;
static double t11 = 0;
static double t12 = 0;
static double t13 = 0;
static double t14 = 0;
static double t15 = 0;
static double t16 = 0;
static double t17 = 0;
static double t18 = 0;
static double t19 = 0;
static double t20 = 0;
static double t21 = 0;
static double t22 = 0;
static double t23 = 0;
static double t24 = 0;
static double t25 = 0;
static double t26 = 0;
static double t27 = 0;
static double t28 = 0;
static double t29 = 0;
static double t30 = 0;
static double t31 = 0;
static double t32 = 0;
static double t33 = 0;
static double t34 = 0;
static double t35 = 0;
static double t36 = 0;
static double t37 = 0;
static double t38 = 0;
static double t39 = 0;
static double t40 = 0;
static double t41 = 0;
static double t42 = 0;
static double t43 = 0;
static double t44 = 0;
static double t45 = 0;
static double t46 = 0;
static double t47 = 0;
static double t48 = 0;
static double t49 = 0;
static double t50 = 0;
static double t51 = 0;
static double t52 = 0;
static double t53 = 0;
static double t54 = 0;
static double t55 = 0;
static double t56 = 0;
static double t57 = 0;
static double t58 = 0;
static double t59 = 0;
static double t60 = 0;
static double t61 = 0;
static double t62 = 0;
static double t63 = 0;
static double t64 = 0;
static double t65 = 0;
static double t66 = 0;
static double t67 = 0;
static double t68 = 0;
static double t69 = 0;
static double t70 = 0;
static double t71 = 0;
static double t72 = 0;
static double t73 = 0;
static double t74 = 0;
static double t75 = 0;
static double t76 = 0;
static double t77 = 0;
static double t78 = 0;
static double t79 = 0;
static double t80 = 0;
static double t81 = 0;
static double t82 = 0;
static double t83 = 0;
static double t84 = 0;
static double t85 = 0;
static double t86 = 0;
static double t87 = 0;
static double t88 = 0;
static double t89 = 0;
static double t90 = 0;
static double t91 = 0;
static double t92 = 0;
static double t93 = 0;
static double t94 = 0;
static double t95 = 0;
static double t96 = 0;
static double t97 = 0;
static double t98 = 0;
static double t99 = 0;
static double t100 = 0;
static double t101 = 0;
static double t102 = 0;
static double t103 = 0;
static double t104 = 0;
static double t105 = 0;
static double t106 = 0;
static double t107 = 0;
static double t108 = 0;
static double t109 = 0;
static double t110 = 0;
static double t111 = 0;
static double t112 = 0;
static double t113 = 0;
static double t114 = 0;
static double t115 = 0;
static double t116 = 0;
static double t117 = 0;
static double t118 = 0;
static double t119 = 0;
static double t120 = 0;
static double t121 = 0;
static double t122 = 0;
static double t123 = 0;
static double t124 = 0;
static double t125 = 0;
static double t126 = 0;
static double t127 = 0;
static double t128 = 0;
static double t129 = 0;
static double t130 = 0;
static double t131 = 0;
static double t132 = 0;
static double t133 = 0;
static double t134 = 0;
static double t135 = 0;
static double t136 = 0;
static double t137 = 0;
static double t138 = 0;
static double t139 = 0;
static double t140 = 0;
static double t141 = 0;
static double t142 = 0;
static double t143 = 0;
static double t144 = 0;
static double t145 = 0;
static double t146 = 0;
static double t147 = 0;
static double t148 = 0;
static double t149 = 0;
static double t150 = 0;
static double t151 = 0;
static double t152 = 0;
static double t153 = 0;
static double t154 = 0;
static double t155 = 0;
static double t156 = 0;
static double t157 = 0;
static double t158 = 0;
static double t159 = 0;
static double t160 = 0;
static double t161 = 0;
static double t162 = 0;
static double t163 = 0;
static double t164 = 0;
static double t165 = 0;
static double t166 = 0;
static double t167 = 0;
static double t168 = 0;
static double t169 = 0;
static double t170 = 0;
static double t171 = 0;
static double t172 = 0;
static double t173 = 0;
static double t174 = 0;
static double t175 = 0;
static double t176 = 0;
static double t177 = 0;
static double t178 = 0;
static double t179 = 0;
static double t180 = 0;
static double t181 = 0;
static double t182 = 0;
static double t183 = 0;
static double t184 = 0;
static double t185 = 0;
static double t186 = 0;
static double t187 = 0;
static double t188 = 0;
static double t189 = 0;
static double t190 = 0;
static double t191 = 0;
static double t192 = 0;
static double t193 = 0;
static double t194 = 0;
static double t195 = 0;
static double t196 = 0;
static double t197 = 0;
static double t198 = 0;
static double t199 = 0;
static double t200 = 0;
static double t201 = 0;
static double t202 = 0;
static double t203 = 0;
static double t204 = 0;
static double t205 = 0;
static double t206 = 0;
static double t207 = 0;
static double t208 = 0;
static double t209 = 0;
static double t210 = 0;
static double t211 = 0;
static double t212 = 0;
static double t213 = 0;
static double t214 = 0;
static double t215 = 0;
static double t216 = 0;
static double t217 = 0;
static double t218 = 0;
static double t219 = 0;
static double t220 = 0;
static double t221 = 0;
static double t222 = 0;
static double t223 = 0;
static double t224 = 0;
static double t225 = 0;
static double t226 = 0;
static double t227 = 0;
static double t228 = 0;
static double t229 = 0;
static double t230 = 0;
static double t231 = 0;
static double t232 = 0;
static double t233 = 0;
static double t234 = 0;
static double t235 = 0;
static double t236 = 0;
static double t237 = 0;
static double t238 = 0;
static double t239 = 0;
static double t240 = 0;
static double t241 = 0;
static double t242 = 0;
static double t243 = 0;
static double t244 = 0;
static double t245 = 0;
static double t246 = 0;
static double t247 = 0;
static double t248 = 0;
static double t249 = 0;
		
}

