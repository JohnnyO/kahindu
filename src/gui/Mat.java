package gui;
import java.awt.*;
import java.awt.event.*;

public abstract class Mat {

public static void main(String args[]) {
	float f[][] = new float[3][3];
	for (int i = 0; i < f.length; i++) 
		for (int j= 0; j < f[0].length; j++)
		  f[i][j] = 1;
    Mat.normalize(f);
    //Mat.printKernel(f,"mean3");
}

public static void print(double  a[][]) {
	for (int i = 0; i < a.length; i++) {
		for (int j= 0; j < a[0].length; j++)
		  System.out.print(a[i][j]+" ");
		System.out.println();
	}
}
public static void print(float  a[][]) {
	for (int i = 0; i < a.length; i++) {
		for (int j= 0; j < a[0].length; j++)
		  System.out.print(a[i][j]+" ");
		System.out.println();
	}
}
public static double sum(double a[][]) {
	double s = 0.0;
	for (int i = 0; i < a.length; i++) 
		for (int j= 0; j < a[0].length; j++)
		  s += a[i][j];
	return s;
}

public static double sum(float a[][]) {
	double s = 0.0;
	for (int i = 0; i < a.length; i++) 
		for (int j= 0; j < a[0].length; j++)
		  s += a[i][j];
	return s;
}

public static double sum(short a[][]) {
	double s = 0.0;
	for (int i = 0; i < a.length; i++) 
		for (int j= 0; j < a[0].length; j++)
		  s += a[i][j];
	return s;
}

public static void normalize(double a[][] ) {
	scale(a,1.0/sum(a));
}
public static void normalize(float a[][] ) {
	scale(a,1.0/sum(a));
}

public static double average(double a[][]) {
	int n = a.length * a[0].length;
	return sum(a)/n;
}

public static double average(float a[][]) {
	int n = a.length * a[0].length;
	return sum(a)/n;
}
public static double average(short a[][]) {
	int n = a.length * a[0].length;
	return sum(a)/n;
}

public static void threshold(short a[][], short thresh) {
	for (int i =0; i < a.length; i++) 
		for (int j=0; j<a[0].length; j++)
			if (a[i][j] < thresh) a[i][j] = 0;
			else a[i][j]=255;
}

public static void threshold(short a[][]) {
	threshold(a,(short)average(a));
}
public static void printKernel(float k[][], String name) {
	  float g;
	  System.out.println(
		"\npublic void "+name+"(){\n"
		+"\tfloat k[][] = {");
	  int w = k.length;
	  int h = k[0].length;
		
	  for (int y=0; y< h; y++) {
	  	System.out.print("\t{");
	  	for (int x=0; x < w-1; x++) {
	  		g = k[x][y];
	  		if (g <10) System.out.print("  ");
	  		else if (g <100) System.out.print(" ");
	  		System.out.print(g+"f, ");
	  	}
	   String s = k[w-1][y]+"f}";
	   if(y < h-1)  s	= s +","; 
	  else s = s + "};"; 	
	   System.out.println(s);
	}
	
	//String s="\n\tconvolve(k);\n}";
	//s = "//sum="+sum(k)+s;
	
  	//System.out.println(s);

}
public static void printArray(double k[][], String name) {
	  double g;
	  int w = k.length;
	  int h = k[0].length;
	  System.out.println("w="+w+" h="+h);
	  System.out.println(
		name+"(){\n"
		+"\tfloat k[][] = {");
		
	  for (int x=0; x< w; x++) {
	  	System.out.print("{");
	  	for (int y=0; y < h; y++) {
	  		g = k[x][y];
	  		if (g <10) System.out.print("  ");
	  		else if (g <100) System.out.print(" ");
	  		System.out.print(g);
	  	}
	  	System.out.println("}");	
	}

}
public static void printKernel(double k[][], String name) {
	  double g;
	  int w = k.length;
	  int h = k[0].length;
	  System.out.println("w="+w+" h="+h);
	  System.out.println(
		"\npublic void "+name+"(){\n"
		+"\tfloat k[][] = {");
		
	  for (int y=0; y< h; y++) {
	  	System.out.print("\t{");
	  	for (int x=0; x < w-1; x++) {
	  		g = k[x][y];
	  		if (g <10) System.out.print("  ");
	  		else if (g <100) System.out.print(" ");
	  		System.out.print(g+"f, ");
	  	}
	   String s = " "+k[w-1][y]+"f }";
	   if(y < h-1)  s	= s +","; 
	   else s = s + "};"; 	
	   System.out.println(s);
	}
	
	String s="\n\tconvolve(k);\n}";
	s = "//sum="+sum(k)+s;
	
  	System.out.println(s);

}

public static void printKernel(short k[][], String name) {
	  int g;
	  System.out.println(
		"\npublic void "+name+"(){\n"
		+"\tfloat s =(float)"+Mat.sum(k)+";\n"
		+"\tfloat k[][] = {");
	  int w = k.length;
	  int h = k[0].length;
		
	  for (int y=0; y< h; y++) {
	  	System.out.print("\t{");
	  	for (int x=0; x < w-1; x++) {
	  		g = k[x][y];
	  		System.out.print(g+"/s,");
	  	}
	   String s = k[w-1][y]+"/s}";
	   if(y < h-1)  s	= s +","; 
	   else s = s + "};"; 	
	   System.out.println(s);
	}
	
	String s="\n\tconvolve(k);\n}";
	System.out.println(s);

}
public static void scale(double a[][], double k) {
	System.out.println("scale(double a[][], double k)");
	for (int i = 0; i < a.length; i++) {
		for (int j= 0; j < a[0].length; j++)
		  a[i][j] *= k;
	}

}
public static void scale(float a[][], float k) {
//System.out.println("scale(float a[][], float k)");
//System.out.println("k="+k);
	for (int i = 0; i < a.length; i++) {
		for (int j= 0; j < a[0].length; j++)
		  a[i][j] = a[i][j] * k;
	}

}

public static void scale(float a[][], double k) {
//System.out.println("scale(float a[][], double k)");
//	System.out.println("k="+k);
	scale(a, (float) k);
}
		
}