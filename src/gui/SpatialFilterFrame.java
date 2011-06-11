package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SpatialFilterFrame extends ConvolutionFrame {
 public SpatialFilterFrame child = null;
 Menu SpatialFilterMenu = new Menu("SpatialFilter");

 Menu lowPassMenu = new Menu("LowPass");
 Menu medianMenu = new Menu("Median");
 Menu highPassMenu = new Menu("Hi-pass");
 Menu unsharpMenu = new Menu("unsharp");
 
 MenuItem showConvolutionKernal_mi
 	= addMenuItem(SpatialFilterMenu,"show convolution kernel");
 
 MenuItem average_mi = addMenuItem(lowPassMenu,"[a]verage");
 MenuItem lp1_mi = addMenuItem(lowPassMenu,"[E-1]lp1");
 MenuItem lp2_mi = addMenuItem(lowPassMenu,"[E-2]lp2");
 MenuItem lp3_mi = addMenuItem(lowPassMenu,"[E-3]lp3");
 MenuItem mean3_mi = addMenuItem(lowPassMenu,"[E-4]mean3");
 MenuItem mean9_mi = addMenuItem(lowPassMenu,"[E-5]mean9");
 
 MenuItem gauss3_mi = addMenuItem(lowPassMenu,"[E-6]gauss 3x3");
 MenuItem gauss7_mi = addMenuItem(lowPassMenu,"[E-7]gauss 7x7");
 MenuItem gauss15_mi = addMenuItem(lowPassMenu,"[E-9]gauss 15x15");
 MenuItem gauss31_mi = addMenuItem(lowPassMenu,"[E-T-G]auss 31x31");
 
 MenuItem medianCross3x3_mi = addMenuItem(medianMenu,"[E-T-+]cross3x3");
 MenuItem medianSquare3x3_mi = addMenuItem(medianMenu,"[E-T-s]quare 3x3");
 MenuItem medianOctagon5x5_mi = addMenuItem(medianMenu,"[E-T-o]catgon 5x5");
 MenuItem medianSquare5x5_mi = addMenuItem(medianMenu,"[E-T-S]quare 5x5");
 MenuItem medianDiamond7x7_mi = addMenuItem(medianMenu,"[E-T-D]iamond 7x7");
 MenuItem medianCross7x7_mi = addMenuItem(medianMenu,"[E-T-C]ross 7x7");
 MenuItem outlierEstimate_mi = addMenuItem(medianMenu,"[E-T-O]utlier estimate");
 MenuItem saltAndPepper100_mi = addMenuItem(medianMenu,"[E-T-1]saltAndPepper100");
 MenuItem saltAndPepper1000_mi = addMenuItem(medianMenu,"[E-T-2]saltAndPepper1000");
 MenuItem saltAndPepper2000_mi = addMenuItem(medianMenu,"[E-T-3]saltAndPepper2000");
 MenuItem saltAndPepper4000_mi = addMenuItem(medianMenu,"[E-T-4]saltAndPepper4000");

 MenuItem hp1_mi = addMenuItem(highPassMenu,"[T-1]hp1");
 MenuItem hp2_mi = addMenuItem(highPassMenu,"[T-2]hp2");
 MenuItem hp3_mi = addMenuItem(highPassMenu,"[T-3]hp3");
 MenuItem hp4_mi = addMenuItem(highPassMenu,"[T-4]hp4"); 
 MenuItem hp5_mi = addMenuItem(highPassMenu,"[T-5]hp5");

 MenuItem shadowMask_mi = addMenuItem(highPassMenu,"[T-6]shadowMask");
 
 MenuItem usp1_mi = addMenuItem(unsharpMenu,"[T-7]usp1");

 MenuItem subtractChild_mi = addMenuItem(fileMenu,"[T-8]subtract child"); 
 
 MenuItem short2Image_mi = addMenuItem(fileMenu,"[T-9]short2Image");
 MenuItem clip_mi = addMenuItem(fileMenu,"[T-0]clip");
  
 
 private boolean computeOutlier = true;
 private int numberOfOutliers = 0;
 
 public void makeChild() {
 	child = new SpatialFilterFrame("child");
 	child.width = width;
 	child.height = height;
 	child.r = copyArray(r);
 	child.g = copyArray(g);
 	child.b = copyArray(b);
 }
 
 public short[][] copyArray(short a[][]) {
 	int w = a.length;
 	int h = a[0].length;
 	short c[][] = new short[w][h];
 	for (int i=0; i < w; i++)
 		for (int j=0; j < h; j++)
 			c[i][j] = a[i][j];
 	return c;	
 }
 private static float[][] shortToFloat(short a[][]) {
 	int w = a.length;
 	int h = a[0].length;
 	float c[][] = new float[w][h];
 	for (int i=0; i < w; i++)
 		for (int j=0; j < h; j++)
 			c[i][j] = a[i][j];
 	return c;	
 }
 
 public void subtractChild() {
 	subtract(child);
 	short2Image();	
 }
 
 public void subtract(SpatialFilterFrame f) {
 	for (int i=0; i < width; i++)
 		for (int j=0; j < height; j++) {
 			r[i][j] = (short)(r[i][j] - f.r[i][j]);
 			g[i][j] = (short)(g[i][j] - f.g[i][j]);
 			b[i][j] = (short)(b[i][j] - f.b[i][j]);
 	}
 	
 }
 
 public void outlierEstimate() {
 	computeOutlier = ! computeOutlier;

 	System.out.println(
 		"computeOutlier = "+ computeOutlier);
 	System.out.println(
 		"numberOfOutliers = "+ numberOfOutliers);
 	numberOfOutliers = 0;
 }

  public SpatialFilterFrame(String title) {
		super(title);
		MenuBar mb = getMenuBar();
		SpatialFilterMenu.add(lowPassMenu);
		SpatialFilterMenu.add(medianMenu);
		highPassMenu.add(unsharpMenu);
		SpatialFilterMenu.add(highPassMenu);
		mb.add(SpatialFilterMenu);
		setMenuBar(mb);
 }



public void clip() {
 for (int x = 0; x < width; x++)
	for (int y = 0; y < height; y++) {
		if (r[x][y] > 255) r[x][y] = 255;
		if (g[x][y] > 255) g[x][y] = 255;
		if (b[x][y] > 255) b[x][y] = 255;
		if (r[x][y] < 0) r[x][y] = 0;
		if (g[x][y] < 0) g[x][y] = 0;
		if (b[x][y] < 0) b[x][y] = 0;
	}
}
 	public void actionPerformed(ActionEvent e) {

	    if (match(e, showConvolutionKernal_mi)) {
	    	showConvolutionKernal();
	    	return;
	    }
	    if (match(e, clip_mi)) {
	    	clip();
	    	return;
	    }
	    if (match(e, short2Image_mi)) {
	    	short2Image();
	    	return;
	    }
	    if (match(e, subtractChild_mi)) {
	    	subtractChild();
	    	return;
	    }
	    if (match(e, usp1_mi)) {
	    	usp1();
	    	return;
	    }
	    if (match(e, outlierEstimate_mi)) {
	    	outlierEstimate();
	    	return;
	    }
	    if (match(e, medianCross7x7_mi)) {
	    	medianCross7x7();
	    	return;
	    }
	    if (match(e, medianCross3x3_mi)) {
	    	medianCross3x3();
	    	return;
	    }
	    if (match(e, medianSquare3x3_mi)) {
	    	medianSquare3x3();
	    	return;
	    }
	    if (match(e, medianOctagon5x5_mi)) {
	    	medianOctagon5x5();
	    	return;
	    }
	    if (match(e, medianSquare5x5_mi)) {
	    	medianSquare5x5();
	    	return;
	    }
	    if (match(e, medianDiamond7x7_mi)) {
	    	medianDiamond7x7();
	    	return;
	    }
	    if (match(e, mean9_mi)) {
	    	mean9();
	    	return;
	    }
	    if (match(e, mean3_mi)) {
	    	mean3();
	    	return;
	    }
	    if (match(e, saltAndPepper100_mi)) {
	    	saltAndPepper(100);
	    	return;
	    }
	    if (match(e, saltAndPepper1000_mi)) {
	    	saltAndPepper(1000);
	    	return;
	    }
	    if (match(e, saltAndPepper2000_mi)) {
	    	saltAndPepper(2000);
	    	return;
	    }
	    if (match(e, saltAndPepper4000_mi)) {
	    	saltAndPepper(4000);
	    	return;
	    }
	    if (match(e, gauss3_mi)) {
	    	gauss3();
	    	return;
	    }
	    if (match(e, gauss7_mi)) {
	    	gauss7();
	    	return;
	    }
	    if (match(e, gauss15_mi)) {
	    	gauss15();
	    	return;
	    }
	    if (match(e, gauss31_mi)) {
	    	gauss31();
	    	return;
	    }

	    if (match(e, lp1_mi)) {
	    	lp1();
	    	return;
	    }
	    if (match(e, lp2_mi)) {
	    	lp2();
	    	return;
	    }
	    if (match(e, lp3_mi)) {
	    	lp3();
	    	return;
	    }
	    if (match(e, hp1_mi)) {
	    	hp1();
	    	return;
	    }
	    if (match(e, hp2_mi)) {
	    	hp2();
	    	return;
	    }
	    if (match(e, hp3_mi)) {
	    	hp3();
	    	return;
	    }
	    if (match(e, hp4_mi)) {
	    	hp4();
	    	return;
	    }
	    if (match(e, hp5_mi)) {
	    	hp5();
	    	return;
	    }
	    if (match(e, average_mi)) {
	    	average();
	    	return;
	    }
	
	   super.actionPerformed(e);  

	}
 public void saltAndPepper(int n) {
 	for (int i=0;i < n; i++) {
    	int rx = rand(0,width-1);
 		int ry = rand(0,height-1);
 		r[rx][ry] = 255;
 		g[rx][ry] = 255;
 		b[rx][ry] = 255;
 		rx = rand(0,width-1);
 		ry = rand(0,height-1);
 		r[rx][ry] = 0;
 		g[rx][ry] = 0;
 		b[rx][ry] = 0;
 	}
 	short2Image();	  
 }

 public void average() {
 float k[][] = {
	{1, 1, 1},
	{1, 1, 1},
	{1, 1, 1}
	};
	Mat.scale(k,1/9.0);
  	convolve(k);
 }

 
public void hp1() {
 float k[][] = {
	{ 0, -1,  0},
	{-1, 10, -1},
	{ 0, -1,  0}
	};
	Mat.normalize(k);
  	convolve(k);
 }
public void hp2() {
 float k[][] = {
	{ 0, -1,  0},
	{-1,  8, -1},
	{ 0, -1,  0}
	};
  	Mat.normalize(k);
  	convolve(k);
 }
public void hp3() {
 float k[][] = {
	{ 0, -1,  0},
	{-1,  5, -1},
	{ 0, -1,  0}
	};
  	Mat.normalize(k);
  	convolve(k);
 }

public void hp4() {
 float k[][] = {
	{ 1, -2,  1},
	{-2,  5, -2},
	{ 1, -2,  1}
	};
  	convolve(k);
 }

public void hp5() {
 float k[][] = {
	{-1,  -1,  -1},
	{-1,   9,  -1},
	{-1,  -1,  -1}
	};
  	convolve(k);
 }


public void usp1() {
	makeChild();
	child.gauss3();
	subtract(child);
	short2Image();	
}
public void lp1() {
 float k[][] = {
	{1, 1, 1},
	{1, 2, 1},
	{1, 1, 1}
	};
	Mat.scale(k,1/10.0);
  	convolve(k);
 }
public void lp2() {
 float k[][] = {
	{1, 1, 1},
	{1, 4, 1},
	{1, 1, 1}
	};
	Mat.scale(k,1/12.0);
  	convolve(k);
 }
public void lp3() {
 float k[][] = {
	{1, 1,  1},
	{1, 12, 1},
	{1, 1,  1}
	};
	Mat.scale(k,1/20.0);
  	convolve(k);
 }

public void mean9(){
	float s =(float)81.0;
	float k[][] = {
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s},
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s},
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s},
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s},
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s},
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s},
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s},
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s},
	{1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s,1/s}};

	convolve(k);
}
public void mean3(){
	float k[][] = {
	{  0.11111111f,   0.11111111f, 0.11111111f},
	{  0.11111111f,   0.11111111f, 0.11111111f},
	{  0.11111111f,   0.11111111f, 0.11111111f}};
//sum=1.0000000074505806
	convolve(k);
}

public void gauss3() {
 float k[][] = {
	{1, 2,  1},
	{2, 4,  2},
	{1, 2,  1}
	};
	Mat.scale(k,1/16.0);
  	convolve(k);
 } 
 
 public static double gauss(
 		double x, double y, 
 		double xc, double yc, double sigma) {
 	double dx = x - xc;
 	double dy = y - yc;
 	double dx2 = dx*dx;
 	double dy2 = dy*dy;
 	double sigma2 = sigma * sigma;
 	double oneOnSigma2 = 1/sigma2;
 	return
 		Math.exp(-(dx2+dy2)*
 			oneOnSigma2/2)/Math.PI*oneOnSigma2/2;	
 }

public static void printGaussKernel(
		int M, int N, 
		double sigma, double centerMax) {
	short k[][] = new short[M][N];
	int xc = M / 2;
	int yc = N / 2;
	double scale = centerMax*2*Math.PI*sigma*sigma;
	for (int x=0; x < k.length; x++)
		for (int y=0; y < k[0].length; y++)
			k[x][y] = (short)
				(scale*gauss(x,y,xc,yc,sigma));
	//Mat.printKernel(k,"gauss"+k.length);
}
public static void printGaussKernel(
		int M, int N, 
		double sigma) {
	float k[][] = getGaussKernel(M, N, sigma);
	//Mat.printKernel(k,"gauss"+k.length);
}

// computes an MxN kernel using (9.1)
public static float [][] getGaussKernel(
		int M, int N, 
		double sigma) {
	float k[][] = new float[M][N];
	int xc = M / 2;
	int yc = N / 2;
	for (int x=0; x < k.length; x++)
		for (int y=0; y < k[0].length; y++)
			k[x][y] = (float)gauss(x,y,xc,yc,sigma);
	Mat.normalize(k);
	return k;
}


public static void testQuickSort() {
	int a[] = {1,2,3,5,4,3,2,5,6,7};
	quickSort(a);
	for (int i=0; i < a.length;i++)
		System.out.println(a[i]); 
}
public void gauss7() {
  float k [][] = {

    {  1, 1, 2,  2, 2, 1, 1},
	{  1, 2, 2,  4, 2, 2, 1},
	{  2, 2, 4,  8, 4, 2, 2},
	{  2, 4, 8, 16, 8, 4, 2},
	{  2, 2, 4,  8, 4, 2, 2},
	{  1, 2, 2,  4, 2, 2, 1},
	{  1, 1, 2,  2, 2, 1, 1}
	};
	Mat.normalize(k);
  	convolve(k);
 }
 
public void gauss15(){
	float k[][] = {
	{  1.9045144E-7f,   9.671922E-7f,   3.8253193E-6f,   1.1782813E-5f,   2.8265502E-5f,   5.2806907E-5f,   7.6833596E-5f,   8.7063876E-5f,   7.6833596E-5f,   5.2806907E-5f,   2.8265502E-5f,   1.1782813E-5f,   3.8253193E-6f,   9.671922E-7f, 1.9045144E-7f},
	{  9.671922E-7f,   4.9118075E-6f,   1.9426576E-5f,   5.9838065E-5f,   1.4354405E-4f,   2.681756E-4f,   3.901932E-4f,   4.4214682E-4f,   3.901932E-4f,   2.681756E-4f,   1.4354405E-4f,   5.9838065E-5f,   1.9426576E-5f,   4.9118075E-6f, 9.671922E-7f},
	{  3.8253193E-6f,   1.9426576E-5f,   7.6833596E-5f,   2.3666414E-4f,   5.677278E-4f,   0.0010606551f,   0.001543244f,   0.0017487246f,   0.001543244f,   0.0010606551f,   5.677278E-4f,   2.3666414E-4f,   7.6833596E-5f,   1.9426576E-5f, 3.8253193E-6f},
	{  1.1782813E-5f,   5.9838065E-5f,   2.3666414E-4f,   7.2897685E-4f,   0.0017487246f,   0.0032670477f,   0.0047535263f,   0.0053864513f,   0.0047535263f,   0.0032670477f,   0.0017487246f,   7.2897685E-4f,   2.3666414E-4f,   5.9838065E-5f, 1.1782813E-5f},
	{  2.8265502E-5f,   1.4354405E-4f,   5.677278E-4f,   0.0017487246f,   0.004194972f,   0.00783724f,   0.011403117f,   0.012921424f,   0.011403117f,   0.00783724f,   0.004194972f,   0.0017487246f,   5.677278E-4f,   1.4354405E-4f, 2.8265502E-5f},
	{  5.2806907E-5f,   2.681756E-4f,   0.0010606551f,   0.0032670477f,   0.00783724f,   0.014641892f,   0.021303825f,   0.024140399f,   0.021303825f,   0.014641892f,   0.00783724f,   0.0032670477f,   0.0010606551f,   2.681756E-4f, 5.2806907E-5f},
	{  7.6833596E-5f,   3.901932E-4f,   0.001543244f,   0.0047535263f,   0.011403117f,   0.021303825f,   0.030996885f,   0.03512407f,   0.030996885f,   0.021303825f,   0.011403117f,   0.0047535263f,   0.001543244f,   3.901932E-4f, 7.6833596E-5f},
	{  8.7063876E-5f,   4.4214682E-4f,   0.0017487246f,   0.0053864513f,   0.012921424f,   0.024140399f,   0.03512407f,   0.039800785f,   0.03512407f,   0.024140399f,   0.012921424f,   0.0053864513f,   0.0017487246f,   4.4214682E-4f, 8.7063876E-5f},
	{  7.6833596E-5f,   3.901932E-4f,   0.001543244f,   0.0047535263f,   0.011403117f,   0.021303825f,   0.030996885f,   0.03512407f,   0.030996885f,   0.021303825f,   0.011403117f,   0.0047535263f,   0.001543244f,   3.901932E-4f, 7.6833596E-5f},
	{  5.2806907E-5f,   2.681756E-4f,   0.0010606551f,   0.0032670477f,   0.00783724f,   0.014641892f,   0.021303825f,   0.024140399f,   0.021303825f,   0.014641892f,   0.00783724f,   0.0032670477f,   0.0010606551f,   2.681756E-4f, 5.2806907E-5f},
	{  2.8265502E-5f,   1.4354405E-4f,   5.677278E-4f,   0.0017487246f,   0.004194972f,   0.00783724f,   0.011403117f,   0.012921424f,   0.011403117f,   0.00783724f,   0.004194972f,   0.0017487246f,   5.677278E-4f,   1.4354405E-4f, 2.8265502E-5f},
	{  1.1782813E-5f,   5.9838065E-5f,   2.3666414E-4f,   7.2897685E-4f,   0.0017487246f,   0.0032670477f,   0.0047535263f,   0.0053864513f,   0.0047535263f,   0.0032670477f,   0.0017487246f,   7.2897685E-4f,   2.3666414E-4f,   5.9838065E-5f, 1.1782813E-5f},
	{  3.8253193E-6f,   1.9426576E-5f,   7.6833596E-5f,   2.3666414E-4f,   5.677278E-4f,   0.0010606551f,   0.001543244f,   0.0017487246f,   0.001543244f,   0.0010606551f,   5.677278E-4f,   2.3666414E-4f,   7.6833596E-5f,   1.9426576E-5f, 3.8253193E-6f},
	{  9.671922E-7f,   4.9118075E-6f,   1.9426576E-5f,   5.9838065E-5f,   1.4354405E-4f,   2.681756E-4f,   3.901932E-4f,   4.4214682E-4f,   3.901932E-4f,   2.681756E-4f,   1.4354405E-4f,   5.9838065E-5f,   1.9426576E-5f,   4.9118075E-6f, 9.671922E-7f},
	{  1.9045144E-7f,   9.671922E-7f,   3.8253193E-6f,   1.1782813E-5f,   2.8265502E-5f,   5.2806907E-5f,   7.6833596E-5f,   8.7063876E-5f,   7.6833596E-5f,   5.2806907E-5f,   2.8265502E-5f,   1.1782813E-5f,   3.8253193E-6f,   9.671922E-7f, 1.9045144E-7f}};
//sum=0.9999999983459134
	convolve(k);
}

public void gauss31() {
	double sigma = 2.0;
	float k[][] = getGaussKernel(31,31,sigma);
	convolve(k);
}

public int numberOfNonZeros(short k[][]) {
    int umax = k.length;    
    int vmax = k[0].length;
    int sum = 0;
    
    for (int x=0; x< umax; x++)
      for (int y=0; y <vmax; y++)
      	if (k[x][y] != 0) sum++;
    return sum;
}

public short getMax(short a[]) {
	short max = -255;
	for (int i =0; i < a.length;i++) 
		if (a[i] > max)
			max = a[i];
	return max;
}
public short getMin(short a[]) {
	short min = 255;
	for (int i =0; i < a.length;i++) 
		if (a[i] < min)
			min = a[i];
	return min;
}

public static void quickSort(int a[]) {
	quickSort(a,0,a.length-1);
}
private static void quickSort(int a[], int lo0, int hi0) {
   // Based on the QuickSort method by 
   // James Gosling from Sun's SortDemo applet
   
      int lo = lo0;
      int hi = hi0;
      int mid, t;

      if ( hi0 > lo0) {
         mid = a[ ( lo0 + hi0 ) / 2 ];
         while( lo <= hi ) {
            while( ( lo < hi0 ) && ( a[lo] < mid ) )
               ++lo;
            while( ( hi > lo0 ) && ( a[hi] > mid ) )
               --hi;
            if( lo <= hi ) {
		      t = a[lo]; 
		      a[lo] = a[hi];
		      a[hi] = t;
               ++lo;
               --hi;
            }
         }
         if( lo0 < hi )
            quickSort( a, lo0, hi );
         if( lo < hi0 )
            quickSort( a, lo, hi0 );

      }
}

private static void swap(int x[],int a, int b) {
	int t = x[a];
	x[a] = x[b];
	x[b] = t;
}

 public void copyRedToGreenAndBlue() {
 	g = new short[width][height];
 	b = new short[width][height];
    for (int x=0; x < width; x++) 
	 for (int y=0; y < height; y++) {
      	g[x][y] = r[x][y];
      	b[x][y] = r[x][y];
	}
 }

public void medianSquare3x3() {
 short k[][] = {
	{1, 1, 1},
	{1, 1, 1},
	{1, 1, 1}
	};
	median(k);
}
public void medianSquare5x5(){
	short k[][] = {
	{  1, 1, 1, 1, 1},
	{  1, 1, 1, 1, 1},
	{  1, 1, 1, 1, 1},
	{  1, 1, 1, 1, 1},
	{  1, 1, 1, 1, 1}};
	median(k);
}
public void medianOctagon5x5() {
	short k[][] = {
	{  0, 1, 1, 1, 0},
	{  1, 1, 1, 1, 1},
	{  1, 1, 1, 1, 1},
	{  1, 1, 1, 1, 1},
	{  0, 1, 1, 1, 0}};
	median(k);
}
public void medianDiamond7x7(){
	short k[][] = {
	{ 0, 0, 0, 1, 0, 0, 0},
	{ 0, 0, 1, 1, 1, 0, 0},
	{ 0, 1, 1, 1, 1, 1, 0},
	{ 1, 1, 1, 1, 1, 1, 1},
	{ 0, 1, 1, 1, 1, 1, 0},
	{ 0, 0, 1, 1, 1, 0, 0},
	{ 0, 0, 0, 1, 0, 0, 0}};
	median(k);
}
public void medianCross7x7(){
	short k[][] = {
	{ 0, 0, 0, 1, 0, 0, 0},
	{ 0, 0, 0, 1, 0, 0, 0},
	{ 0, 0, 0, 1, 0, 0, 0},
	{ 1, 1, 1, 1, 1, 1, 1},
	{ 0, 0, 0, 1, 0, 0, 0},
	{ 0, 0, 0, 1, 0, 0, 0},
	{ 0, 0, 0, 1, 0, 0, 0}};
	median(k);
}


public static void printMedian(short k[][], String name) {
	//printMaple(k);
	//  System.out.println(
	//	"\npublic void "+name+"(){\n"
	//	+"\tfloat k[][] = {");
	  int w = k.length;
	  int h = k[0].length;
		
	  for (int y=0; y< h; y++) {
	  	System.out.print("\t{");
	  	for (int x=0; x < w-1; x++) 
	  		System.out.print(k[x][y]+", ");
	   String s = k[w-1][y]+"}";
	   if(y < h-1)  s	= s +","; 
	   else s = s + "};"; 	
	   System.out.println(s);
	}
	
	String s="\n\tmedian(k);\n}";	
  	System.out.println(s);

}
public void medianSquare7x7(){
	short k[][] = {
	{ 1, 1, 1, 1, 1, 1, 1},
	{ 1, 1, 1, 1, 1, 1, 1},
	{ 1, 1, 1, 1, 1, 1, 1},
	{ 1, 1, 1, 1, 1, 1, 1},
	{ 1, 1, 1, 1, 1, 1, 1},
	{ 1, 1, 1, 1, 1, 1, 1},
	{ 1, 1, 1, 1, 1, 1, 1}};
	median(k);
}

public void medianCross3x3() {
 short k[][] = {
	{0, 1, 0},
	{1, 1, 1},
	{0, 1, 0}
	};
	median(k);
}

 public void median (short k[] []) {
 	printMedian(k,"color median");
 	Timer t = new Timer();
 	t.start();
    r = median(r,k);
    g = median(g,k);
    b = median(b,k);
    t.print("Median filter time");
  	short2Image();
 }
 
 public void medianBottom(
 	short f[][], short k[][], short h[][]) {
 	int windowLength = 0;
    int window[];
    int uc = 0;    
    int vc = 0;
    System.out.println("k="+k.length);
    uc = k.length /2;
    vc = k[0].length/2;
    windowLength = numberOfNonZeros(k);
    window = new int[windowLength];

     //median bottom
    for (int x=0; x < width-1; x++)  
    	for(int y=0; y < vc; y++) {
    	int loc = 0;
		for(int v = -vc; v <= vc; v++) 
			for(int u = -uc; u <= uc; u++) 
	  		  if (k[ u+uc][v+vc] !=0) 
	  			window[loc++]=f[cx(x-u)][cy(y-v)];
		h[x][y] = (short)median(window);
	}
}
 public void medianLeft(
 	short f[][], short k[][], short h[][]) {
 	int uc = k.length/2;    
    int vc = k[0].length/2;

 	int windowLength = numberOfNonZeros(k);
    int window[] = new int[windowLength];
     //median bottom
     //median left
    for (int x=0; x < uc; x++) 
      for(int y=vc; y < height - vc; y++) {
      int loc = 0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	  		  if (k[ u+uc][v+vc] !=0) 
	  			window[loc++]=f[cx(x-u)][cy(y-v)];
		h[x][y] = (short)median(window);
	}
}

 public void medianRightAndTop(
 	short f[][], short k[][], short h[][]) {
 	int uc = k.length/2;    
    int vc = k[0].length/2;

 	int windowLength = numberOfNonZeros(k);
    int window[] = new int[windowLength];
    //median right
        for (int x=width-uc; x < width -1; x++)
        	for(int y=vc; y < height - vc; y++) { 
        	int loc = 0;
				for(int v = -vc; v <= vc; v++) 
	  				for(int u = -uc; u <= uc; u++) 
	  		  			if (k[ u+uc][v+vc] !=0) 
	  						window[loc++]=f[cx(x-u)][cy(y-v)];
		h[x][y] = (short)median(window);
	}

    //median top
    for (int x=0; x < width-1; x++) {
    	for(int y=height-vc; y < height-1; y++) {
    	int loc = 0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	  		  if (k[ u+uc][v+vc] !=0) 
	  			window[loc++]=f[cx(x-u)][cy(y-v)];
		h[x][y] = (short)median(window);
		}
	}
}
 
 // median, optimze the edges	
  public short[][] median(short f[][], short k[][]) {
    short h[][]=medianNoEdge(f,k);
    medianBottom(f,k,h);
    medianLeft(f,k,h);
    medianRightAndTop(f,k,h);
    return h;
  }
  
  public static double mean(int a[]) {
  	double sum = 0;
  	for (int i =0; i < a.length;i++)
  		 sum += a[i];
  	return sum/ a.length;
  }
  
  public static double variance(int a[]) {
  	double xBar = mean(a);
  	double sum = 0;
  	double dx = 0;
  	for (int i=0; i < a.length; i++) {
  		dx = a[i] - xBar;
  		sum += dx*dx;
  	}
  	return sum/a.length; 		
  }
  
  public static double coefficientOfVariation(int a[]) {
  	double aBar = mean(a);
  	double aBar2 = aBar * aBar;
  	return Math.sqrt(variance(a)/aBar2);
  }
   
  public short[][] medianNoEdge(short f[][], short k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;
    short h[][]=new short[width][height];

    int windowLength = numberOfNonZeros(k);
    int window[] = new int[windowLength];
    	    
    for(int x = uc; x < width-uc; x++) {
      for(int y = vc; y < height-vc; y++) {
		int loc = 0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	  		  if (k[ u+uc][v+vc] !=0) 
	  			window[loc++]=f[x-u ][y-v];
		h[x][y] = (short)median(window);
		//f[x][y] = (short)median(window);
		//the above should not have been here, it is replacing values, mid calculation
      }

    }
    return h; 
  }
  
public static short median(Vector v) {
	Collections.sort(v);
   
   return 
    	 ((Cshort)v.elementAt(v.size()/2)).getValue();   	
}

public void testMedian() {
	int a[] = {1,2,3,5,4,3,2,5,6,7};
	System.out.println("The median ="+median(a)); 
}
public static void testVariance() {
	int a[] = {1,2,3,5,4,3,2,5,6,7};
	System.out.println("The variance ="+variance(a)); 
}
public static void testCoefficientOfVariation() {
	int a[] = {0,85,87,90,100};
	System.out.println(
		"coefficientOfVariation({0,85,87,90,100}) ="
		+coefficientOfVariation(a)); 
	int b[] = {95,85,87,90,100};
	System.out.println(
		"The coefficientOfVariation({95,85,87,90,100}) ="
		+coefficientOfVariation(b));
}

public static void testOutlier() {
	int a[] = {0,85,87,90,100};
	int b[] = {95,85,87,90,100};
	System.out.println(
		"dog ate my homework ={0,85,87,90,100}"
		+ outlierHere(a));
	System.out.println(
		"dog ate my homework ={95,85,87,90,100}"
		+ outlierHere(b));
}

public static boolean outlierHere(int a[]) {
	return ( coefficientOfVariation(a) > .1);
}

public static void main(String args[]) {
	testOutlier();
}

public int median(int a[]) {
    int mid = a.length/2-1;
	if (computeOutlier) {
		if (! outlierHere(a)) {
			return a[mid];
		}
	}
	numberOfOutliers++;
    quickSort(a);

    if ((a.length & 1) == 1)
    	return a[mid];
    return (int)((a[mid]+ a[mid+1]+0.5)/2);  	
}
// The sloooww way to do a median filter.
// This one uses fancy design patterns,
// including the template method.
// What are these CS people thinking?
// 18 seconds to do a 128x128 grayscale median
// filter with quicksort using MRJ 2.0
// on a powermac 8100/100.
// Gosh!
// Simple quicksort based median filter
// works in 1/2 second for the same image
// yes...36 times faster!
  public short[][] medianSlow(short f[][], short k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;
    short h[][]=new short[width][height];
    Cshort cs = new Cshort(0);
    Vector window = new Vector();
    int windowLength = numberOfNonZeros(k);
    for (int i=0; i < windowLength;i++)
    	window.addElement(new Cshort(0));
    	    
    for(int x = uc; x < width-uc; x++) {
      for(int y = vc; y < height-vc; y++) {
		int loc = 0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	  		  if (k[ u+uc][v+vc] !=0.0f) {
	  			cs = (Cshort)window.elementAt(loc++);
	  			cs.setValue(
	  				(short) (f[x-u ][y-v]));
	  			}
		h[x][y] = median(window);
      }

    }
    return h;
  }
  
  public void convolve(float a[][]) {
  	//printMaple(a);
  	super.convolve(a);
  }
  
  static void printMaple(short a[][]) {
  	printMaple(shortToFloat(a));  			
  }
  
  public static void printMaple(float a[][]) {
  //linalg[matrix](3,3,[1/9,1/9,1/9,1/9,1/9,1/9,1/9,1/9,1/9]);
  int w = a.length;
  int h = a[0].length;
  System.out.println("evalf(linalg[matrix]("+w
  	+","+h+",[");
  for(int i=0; i < w; i++)
   for (int j=0; j < h; j++) {
     System.out.print(a[i][j]);
     if (i*j == (w-1)*(h-1)) break;
     System.out.print(",");
    }
   System.out.println("]));");
  }
}