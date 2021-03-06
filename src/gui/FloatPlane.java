package gui;

abstract public class FloatPlane {
	public float r[][];
	public float g[][];
	public float b[][];
	protected int width;
	protected int height;
	private ColorFrame parent;
	
	public float rBar, gBar, bBar;
	public float min, max;
	
public FloatPlane(ColorFrame _cf) {
	copyFloats(_cf);

}

public void zeroOut() {
	r = new float[width][height];
	g = new float[width][height];
	b = new float[width][height];
}



public void transpose() {
    float ro[][] = new float[r[0].length][r.length];
    float go[][] = new float[r[0].length][r.length];
    float bo[][] = new float[r[0].length][r.length];
    for(int x=0; x<r.length; x++)
       for(int y=0; y<r[0].length; y++) {
          ro[y][x] = r[x][y];
          go[y][x] = g[x][y];
          bo[y][x] = b[x][y];       
    }
	height = r.length;
	width = r[0].length;
	r = ro;
	g = go;
	b = bo;
}
	

public void copyFloats(ColorFrame _cf) {
	parent = _cf;
	width = parent.width;
	height = parent.height;
	r = new float[width][height];
	g = new float[width][height];
	b = new float[width][height];
	for (int x=0; x < width; x++) 
		for (int y=0; y < height; y++) {
			r[x][y] = parent.r[x][y];
			g[x][y] = parent.g[x][y];
			b[x][y] = parent.b[x][y];
		} 
}
 public void linearTransform() {
     computeStats();
    float Vmin = min;
    float Vmax = max;
    int Dmin = 0;
    int Dmax = 255;
    double deltaV = Vmax - Vmin;
    double deltaD = Dmax - Dmin;
    double c = deltaD/deltaV;
    double b = (Dmin * Vmax-Dmax*Vmin)/deltaV;
    linearTransform(c,b);
 }
 
 public void linearTransform(double c, double br) {
   for (int x = 0; x < width; x++) 
	for (int y = 0; y < height; y++) {
		r[x][y] = (float)(c * r[x][y] + br);
      	g[x][y] = (float)(c * g[x][y] + br);
      	b[x][y] = (float)(c * b[x][y] + br);
	}
 }
 public void computeStats() {
 	 min = Integer.MAX_VALUE;
 	 max = Integer.MIN_VALUE;
 	 rBar = 0;
 	 gBar = 0;
 	 bBar = 0;
 	 double N = width * height;
 	for (int x=0; x < width; x++) 
 		for (int y=0; y < height; y++) {
 			rBar += r[x][y];
 			gBar += g[x][y];
 			bBar += b[x][y];
 			min = Math.min(r[x][y],min);
 			min = Math.min(g[x][y],min);
 			min = Math.min(b[x][y],min);
 			max = Math.max(r[x][y],max);
 			max = Math.max(g[x][y],max);
 			max = Math.max(b[x][y],max);
 		}
 	rBar /= N;
 	gBar /= N;
 	bBar /= N;
 }
 

public abstract void toRgb();
public abstract void fromRgb();

public float getMin(float a[][]) {
	float min = Float.MAX_VALUE;
	for (int x=0; x < a.length; x++) 
		for (int y=0; y < a[0].length; y++) {
			if (min > a[x][y]) min = a[x][y];
		}
	return min;
}

public float min(float m1, float m2, float m3) {
	if ((m1 <= m2) && (m1 <= m3)) return m1;
	if ((m2 <= m1) && (m2 <= m3)) return m2;
	return m3;
}
public float max(float m1, float m2, float m3) {
	if ((m1 >= m2) && (m1 >= m3)) return m1;
	if ((m2 >= m1) && (m2 >= m3)) return m2;
	return m3;
}

public float getMin() {
	return min(getMin(r),getMin(g),getMin(b));
}
public float getMax() {
	return max(getMax(r),getMax(g),getMax(b));
}

public void printStatistics() {
	System.out.println("Max:"+getMax());
	System.out.println("Min:"+getMin());
}
// set a in [0,1]
public void normalize(float a[][]) {
	float min = getMin(a);
	if (min < 0) addArray(a,-min);
	if (min > 0) addArray(a,min);
	scaleArray(a,1/getMax(a));
}

// set r,g,b each in [0,1]
public void normalize() {
	float min = getMin();
	if (min < 0) min = -min;
	addArray(r,min);
	addArray(g,min);
	addArray(b,min);
	float s = 1/getMax();
	scaleArray(r,s);
	scaleArray(g,s);
	scaleArray(b,s);
}

public void scaleArray(float a[][], float s) {
	for (int x=0; x < width; x++) 
		for (int y=0; y < height; y++) 
			a[x][y] *= s;
}
public void powArray(float a[][], float s) {
	for (int x=0; x < width; x++) 
		for (int y=0; y < height; y++) 
			if (a[x][y] < 0)
				a[x][y] = (float)(-Math.pow(-a[x][y],s));
			else
				a[x][y] =(float) Math.pow(a[x][y],s);
}


public void pow(float s) {
	powArray(r,s);
	powArray(g,s);
	powArray(b,s);
}
public void scale(float s) {
	scaleArray(r,s);
	scaleArray(g,s);
	scaleArray(b,s);
}
public void addArray(float a[][], float s) {
	for (int x=0; x < width; x++) 
		for (int y=0; y < height; y++) 
			a[x][y] += s;
}


public float getMax(float a[][]) {
	float max = Float.MIN_VALUE;
	for (int x=0; x < a.length; x++) 
		for (int y=0; y < a[0].length; y++) {
			if (max < a[x][y]) max = a[x][y];
		}
	return max;
}

public void convertSpace(Mat3 m) {
float pel[];
   for (int x=0; x < width; x++) 
     for (int y=0; y < height; y++){
        pel = m.multiply(r[x][y],g[x][y],b[x][y]);

        r[x][y] = pel[0];
        g[x][y] = pel[1];
        b[x][y] = pel[2];
      }
}


public void subSampleChroma2To1() {
	b = oneDSubsampleTwoTo1(b);
	g = oneDSubsampleTwoTo1(g);
}
  public float[][] oneDSubsampleTwoTo1(float f[][]) {
  	int width = f.length;
  	int height = f[0].length;
    float h[][]=new float[width][height];
    double sum = 0;
    
    for(int y = 0; y < height; y++) {
      for(int x = 0; x < width-2; x=x+2) {
      	float a = (float)((f[x][y] + f[x+1][y]) / 2.0);
		h[x][y] = a;
		h[x+1][y] = a;
      }
    }
    return h;
  }
public void subSampleChroma4To1() {
	b = oneDSubsample4To1(b);
	g = oneDSubsample4To1(g);
}



  public float[][] oneDSubsample4To1(float f[][]) {
  	int width = f.length;
  	int height = f[0].length;
    float h[][]=new float[width][height];
    double sum = 0;  
    for(int y = 0; y < height-2; y=y+2) {
      for(int x = 0; x < width-2; x=x+2) {
      	float a = (f[x][y]+f[x+1][y]+f[x+1][y+1]+f[x][y+1])/4f;
		h[x][y] = a;
		h[x+1][y] = a;
		h[x][y+1] = a;
		h[x+1][y+1] = a;
      }
    }
    return h;
  }
public void updateParent(float scaleFactor) {
	parent.width = width;
	parent.height = height;
	parent.r = new short[width][height];
	parent.g = new short[width][height];
	parent.b = new short[width][height];
	for (int x=0; x < width; x++) 
		for (int y=0; y < height; y++) {
			parent.r[x][y] = (short)(scaleFactor*r[x][y]);
			parent.g[x][y] = (short)(scaleFactor*g[x][y]);
			parent.b[x][y] = (short)(scaleFactor*b[x][y]);
		} 
	parent.short2Image();
}
public void updateParent() {
	parent.width = width;
	parent.height = height;
	parent.r = new short[width][height];
	parent.g = new short[width][height];
	parent.b = new short[width][height];
	for (int x=0; x < width; x++) 
		for (int y=0; y < height; y++) {
			parent.r[x][y] = (short)r[x][y];
			parent.g[x][y] = (short)g[x][y];
			parent.b[x][y] = (short)b[x][y];
		} 
	parent.short2Image();
}
}