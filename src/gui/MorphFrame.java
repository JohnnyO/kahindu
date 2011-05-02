package gui;
import java.awt.*;import java.awt.event.*;

public class MorphFrame extends EdgeFrame  {

	
	Menu morphMenu = new Menu("Morph");
	Menu morphEdgeMenu = new Menu("Edge");
	Menu dilateMenu = new Menu("Dilate");
	Menu erodeMenu = new Menu("Erode");
	Menu openMorphMenu = new Menu("Open");
	Menu closeMorphMenu = new Menu("Close");
	Menu insideContourMenu = new Menu("Inside Contour");
	Menu outsideContourMenu = new Menu("Outside Contour");
	Menu middleContourMenu = new Menu("Middle Contour");
	Menu serraMenu = new Menu("Serra Transform (experimental)");
	Menu morphColorMenu = new Menu("Color (experimental)");
	
	MenuItem dilateh_mi = addMenuItem(dilateMenu,"h");
	MenuItem dilatev_mi = addMenuItem(dilateMenu,"v");
	MenuItem dilateSquare_mi = addMenuItem(dilateMenu,"square");
	MenuItem dilateCross_mi = addMenuItem(dilateMenu,"cross");

	MenuItem erodeh_mi = addMenuItem(erodeMenu,"h");
	MenuItem erodeh5_mi = addMenuItem(erodeMenu,"hx5");
	MenuItem erodev_mi = addMenuItem(erodeMenu,"v");
	MenuItem erodeSquare_mi = addMenuItem(erodeMenu,"square");
	MenuItem erodeCross_mi = addMenuItem(erodeMenu,"cross");

	MenuItem openh_mi = addMenuItem(openMorphMenu,"h");
	MenuItem openv_mi = addMenuItem(openMorphMenu,"v");
	MenuItem openSquare_mi = addMenuItem(openMorphMenu,"square");
	MenuItem openCross_mi = addMenuItem(openMorphMenu,"cross");

	MenuItem closeh_mi = addMenuItem(closeMorphMenu,"h");
	MenuItem closev_mi = addMenuItem(closeMorphMenu,"v");
	MenuItem closeSquare_mi = addMenuItem(closeMorphMenu,"square");
	MenuItem closeCross_mi = addMenuItem(closeMorphMenu,"cross");

	MenuItem insideContourh_mi = addMenuItem(insideContourMenu,"h");
	MenuItem insideContourv_mi = addMenuItem(insideContourMenu,"v");
	MenuItem insideContourSquare_mi = addMenuItem(insideContourMenu,"square");
	MenuItem insideContourCross_mi = addMenuItem(insideContourMenu,"cross");

	MenuItem outsideContourh_mi = addMenuItem(outsideContourMenu,"h");
	MenuItem outsideContourv_mi = addMenuItem(outsideContourMenu,"v");
	MenuItem outsideContourSquare_mi = addMenuItem(outsideContourMenu,"square");
	MenuItem outsideContourCross_mi = 
		addMenuItem(outsideContourMenu,"[x]cross");

	MenuItem middleContourh_mi = addMenuItem(middleContourMenu,"h");
	MenuItem middleContourv_mi = addMenuItem(middleContourMenu,"v");
	MenuItem middleContourSquare_mi = addMenuItem(middleContourMenu,"square");
	MenuItem middleContourCross_mi = addMenuItem(middleContourMenu,"cross");

	MenuItem serrah_mi = addMenuItem(serraMenu,"h");
	MenuItem serrav_mi = addMenuItem(serraMenu,"v");
	MenuItem serraSquare_mi = addMenuItem(serraMenu,"square");
	MenuItem serraCross_mi = addMenuItem(serraMenu,"cross");
	
	MenuItem skeleton_mi = addMenuItem(morphEdgeMenu,"[s]keleton");
	MenuItem thin_mi = addMenuItem(morphEdgeMenu,"[t]hin");
	

	MenuItem hatMorph_mi = addMenuItem(morphEdgeMenu,"hat Morph");
	
	MenuItem colorDilateErode_mi = addMenuItem(morphColorMenu,"[E-c]olor Dilate-Erode");
	MenuItem colorDilate_mi = addMenuItem(morphColorMenu,"[E-d]color Dilate");
	MenuItem colorErode_mi = addMenuItem(morphColorMenu,"[E-e]color Erode");
	MenuItem colorOpen_mi = addMenuItem(morphColorMenu,"color open");
	MenuItem colorClose_mi = addMenuItem(morphColorMenu,"color close");
	MenuItem colorPyramid_mi = addMenuItem(morphColorMenu,"[p]yramid");
	
	public void actionPerformed(ActionEvent e) {
	   	if (match(e,thin_mi)) {		
	    		thin();
	    		return;
	    }

	   	if (match(e,colorPyramid_mi)) {		
	    		colorPyramid(kSquare);
	    		return;
	    }
	   	if (match(e,colorOpen_mi)) {		
	    		colorOpen(kSquare);
	    		return;
	    }
	   	if (match(e,colorClose_mi)) {		
	    		colorClose(kSquare);
	    		return;
	    }
	   	if (match(e,serraSquare_mi)) {		
	    		serra(kSquare);
	    		return;
	    }

	   	if (match(e,serrah_mi)) {		
	    		serra(kh);
	    		return;
	    }

	   	if (match(e,serrav_mi)) {		
	    		serra(kv);
	    		return;
	    }

	   	if (match(e,serraCross_mi)) {		
	    		serra(kCross);
	    		return;
	    }

	   	if (match(e,colorErode_mi)) {		
	    		colorErode(kSquare);
	    		return;
	    }
	   	if (match(e,colorDilate_mi)) {		
	    		colorDilate(kSquare);
	    		return;
	    }
	   	if (match(e,colorDilateErode_mi)) {		
	    		colorDilateErode(kSquare);
	    		return;
	    }
	   	if (match(e,skeleton_mi)) {		
	    		skeleton();
	    		return;
	    }
		if (match(e,hatMorph_mi)) {		
	    		hat13v2();
	    		thresh();
	    		insideContour(kh);
	    	return;
	    }

		if (match(e,middleContourh_mi)) {
	    		middleContour(kh);
	    		return;
	    }
		if (match(e,middleContourv_mi)) {
	    		middleContour(kv);
	    		return;
	    }
		if (match(e,middleContourSquare_mi)) {
	    		middleContour(kSquare);
	    		return;
	    }
		if (match(e,middleContourCross_mi)) {
	    		middleContour(kCross);
	    		return;
	    }
	    		if (match(e,insideContourh_mi)) {
	    		insideContour(kh);
	    		return;
	    }
		if (match(e,insideContourv_mi)) {
	    		insideContour(kv);
	    		return;
	    }
		if (match(e,insideContourSquare_mi)) {
	    		insideContour(kSquare);
	    		return;
	    }
		if (match(e,insideContourCross_mi)) {
	    		insideContour(kCross);
	    		return;
	    }
	    
	    if (match(e,outsideContourh_mi)) {
	    		outsideContour(kh);
	    		return;
	    }
		if (match(e,outsideContourv_mi)) {
	    		outsideContour(kv);
	    		return;
	    }
		if (match(e,outsideContourSquare_mi)) {
	    		outsideContour(kSquare);
	    		return;
	    }
		if (match(e,outsideContourCross_mi)) {
	    		outsideContour(kCross);
	    		return;
	    }
		if (match(e,openh_mi)) {
	    		open(kh);
	    		return;
	    }
		if (match(e,openv_mi)) {
	    		open(kv);
	    		return;
	    }
		if (match(e,openSquare_mi)) {
	    		open(kSquare);
	    		return;
	    }
		if (match(e,openCross_mi)) {
	    		open(kCross);
	    		return;
	    }
		if (match(e,closeh_mi)) {
	    		close(kh);
	    		return;
	    }
		if (match(e,closev_mi)) {
	    		close(kv);
	    		return;
	    }
		if (match(e,closeSquare_mi)) {
	    		close(kSquare);
	    		return;
	    }
		if (match(e,closeCross_mi)) {
	    		close(kCross);
	    		return;
	    }
		if (match(e,erodeCross_mi)) {
	    		erode(kCross);
	    		return;
	    }
		if (match(e, erodeSquare_mi)) {
	    		erode(kSquare);
	    		return;
	    }

		if (match(e, erodeh5_mi)) {
	    		erode(kh);
	    		erode(kh);
	    		erode(kh);
	    		erode(kh);
	    		erode(kh);
	    	return;
	    }
		if (match(e, erodeh_mi)) {
	    		erode(kh);
	    	return;
	    }
		if (match(e, erodev_mi)) {
	    		erode(kv);
	    		return;
	    }
		if (match(e,dilateCross_mi)) {
	    		dilate(kCross);
	    		return;
	    }
		if (match(e, dilateSquare_mi)) {
	    		dilate(kSquare);
	    		return;
	    }
		if (match(e, dilateh_mi)) {
	    		dilate(kh);
	    		return;
	    }
		if (match(e, dilatev_mi)) {
	    		dilate(kv);
	    		return;
	    }
		super.actionPerformed(e);  

	}
	
	public void colorPyramid(float k[][]) {
		r = dilategs(erodegs(r,k),k);
		g = dilategs(erodegs(g,k),k);
		b = dilategs(erodegs(b,k),k);
		r = erodegs(dilategs(r,k),k);
		g = erodegs(dilategs(g,k),k);
		b = erodegs(dilategs(b,k),k);
		resample(2);
		short2Image();
	}
	public void resample2(int ratio) {
	       child = new MorphFrame("MorphFrame");
 		child.width = width/2;
 		child.height = height/2;
 		child.r = resampleArray(r,2);
 		child.g = resampleArray(g,2);
 		child.b = resampleArray(b,2);
 	}
	public void resample(int ratio) {
 		width = width/2;
 		height = height/2;
 		r = resampleArray(r,2);
 		g = resampleArray(g,2);
 		b = resampleArray(b,2);
 	}
 	
 	public short [][] resampleArray(short a[][], int ratio) {
 	int w = a.length;
 	int h = a[0].length;
 	int nw = w / ratio;
 	int nh = h / ratio;
 	short c[][] = new short[nw][nh];
 	for (int i=0; i < w; i++)
 		for (int j=0; j < h; j++)
 			c[i/ratio][j/ratio] = a[i][j];
 	  return c;	
 	 }

	public void colorOpen(float k[][]) {
		r = dilategs(erodegs(r,k),k);
		g = dilategs(erodegs(g,k),k);
		b = dilategs(erodegs(b,k),k);
		short2Image();
	}
	
	public void colorClose(float k[][]) {
		r = erodegs(dilategs(r,k),k);
		g = erodegs(dilategs(g,k),k);
		b = erodegs(dilategs(b,k),k);
		short2Image();
	}
	public void open(float k[][]) {
		r = dilate(erode(r,k),k);
		copyRedToGreenAndBlue();
		short2Image();
	}
	public void close(float k[][]) {
		r = erode(dilate(r,k),k);
		copyRedToGreenAndBlue();
		short2Image();
	}
	public void serra(float k[][]) {
		r = erode(r,k);
		g = erode(complement(g),kSquare);
		r = intersect(r,g);
		copyRedToGreenAndBlue();
		short2Image();
	}
	public short [][] intersect(short a[][], short b[][]) {
		int w = a.length;
		int h =  a[0].length;
		short c[][] = new short[w][h];
		for (int x=0; x < w; x++)
			for (int y=0; y < h; y++)
				if (a[x][y] == b[x][y])
					c[x][y] = a[x][y];
		return c;
	}
	
	public short [][] complement(short s[][]) {
		int w = s.length;
		int h = s[0].length;
		short sComp[][] = new short[w][h];
		for (int x=0; x < w; x++)
			for (int y=0; y < h; y++)
				sComp[x][y] = (short)(255 - s[x][y]);
		return sComp;
	}

public static void main(String args[]) {
	MorphFrame ef = new MorphFrame("MorphFrame");
	ef.show();
}

	
    public void dilate(float k[][]) {
        r = dilate(g,k);
        copyRedToGreenAndBlue();
        short2Image();
    }
    public void erode(float k[][]) {
        r = erode(g,k);
        copyRedToGreenAndBlue();
        short2Image();
    }
    
    public void colorDilateErode(float k[][]) {
    	for (int i=0; i < 5; i++) 
    		colorDilate(k);
    	for (int i=0; i < 5; i++) 
    		colorErode(k);   	
    }
    public void colorDilate(float k[][]) {
    	r =	dilategs(r,k);
    	g =	dilategs(g,k);
    	b =	dilategs(b,k);
       short2Image();    	
    }
    public void colorErode(float k[][]) {
    	r =	erodegs(r,k);
    	g =	erodegs(g,k);
    	b =	erodegs(b,k);
       short2Image();    	
    }

	
  public void insideContour(float k[][]) {
  	r = subtract(g, erode(g,k));
  	copyRedToGreenAndBlue();
  	short2Image();
  }
  public void outsideContour(float k[][]) {
  	r = subtract( dilate(g,k),g);
  	copyRedToGreenAndBlue();
  	short2Image();
  }
  public void middleContour(float k[][]) {
  	r = subtract( 
  		dilate(g,k),
  		erode(g,k));
  	copyRedToGreenAndBlue();
  	short2Image();
  }
  public void clip(short a[][], short min, short max ) { 
  int w = a.length;
  int h = a[0].length;
  for (int x=0; x < w; x++) 
  	for (int y=0; y < h; y++) {
  		if (a[x][y] < min) a[x][y] = min;
  		if (a[x][y] > max) a[x][y] = max;
  	} 	
  }
  
  public short[][] subtract(
  	short a[][], short b[][]){
  
  int w = a.length;
  int h = a[0].length;
  
  short c[][]=new short[w][h];
  for (int x=0; x < w; x++) 
  	for (int y=0; y < h; y++) {
		int s = a[x][y] - b[x][y];
  		c[x][y] = (short)Math.abs(s);
  	}
   return c;
  }
  
public void thin() {
  	skeletonRedPassSuen(true) ;
  	skeletonRedPassSuen(false) ;
   	copyRedToGreenAndBlue();
  	short2Image();
}
  public void skeleton() {
  	Timer t = new Timer();
  	t.start();
  	while (
  		skeletonRedPassSuen(true)&& 
  		skeletonRedPassSuen(false)) { 
   	}
   	copyRedToGreenAndBlue();
  	short2Image();
  	t.print("skeletonTime=");

  } 
  // p7 p0 p1
  // p6 --- p2
  // p5 p4 p3
  public boolean skeletonRedPassSuen(
  	boolean firstPass) {
    boolean p[]=new boolean[8];
    short c = 0;
    for(int x = 1; x < width-1; x++) {
      for(int y = 1; y < height-1; y++) {
      	g[x][y] = 0; // use g for 1st pass
      	if (r[x][y] == 0) continue;
    	p[0] = r[x][y+1]  != 0;
      	p[1] = r[x+1][y+1]!= 0;
      	p[2] = r[x+1][y]  != 0;
      	p[3] = r[x+1][y-1]!= 0;
      	p[4] = r[x][y-1]  != 0;
      	p[5] = r[x-1][y-1]!= 0;
      	p[6] = r[x-1][y]  != 0;
      	p[7] = r[x-1][y+1]!= 0;
      	int n = numberOfNeighbors(p);
      	if ((n < 2) || (n > 6)) continue;
      	if (numberOf01Transitions(p) != 1) continue;

      	if (firstPass) { 
      		if ((p[0] && p[2] && p[4]) )
      			continue;
      		if ((p[2] && p[4] && p[6]))
      			continue;
      		g[x][y] = 255;
      		c++;
      		}
      	else {
      	    if ((p[0] && p[2] && p[6])) 
      			continue;
      		if ((p[0] && p[4] && p[6]))
      			continue;
      		g[x][y] = 255;
      		c++;
     			
      	}
      }
    }
    //System.out.println("c="+c);
    if (c == 0) return false;
    deleteFlagedPoints();
    return true;
   }

	public void deleteFlagedPoints() {
	 for(int x = 1; x < width-1; x++) 
      for(int y = 1; y < height-1; y++) 
		    if (g[x][y] != 0) 
		    	r[x][y] = 0;
    }



  public short[][] erode(short f[][], float k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;

    short h[][]=new short[width][height];
    short sum = 0;
    
    for(int x = uc; x < width-uc; x++) {
      for(int y = vc; y < height-vc; y++) {    
		sum = 255;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	  			if (k[u+uc][v+vc] == 1) 
	  			 if (f[x-u][y-v] < sum)
	  			   sum = f[x-u][y-v]; 
		h[x][y] = sum;
      }
    }
    return h;
  }
  
    public short[][] erodegs(short f[][], float k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;

    short h[][]=new short[width][height];
    short min = 0;
    short sum = 0;
    
    for(int x = uc; x < width-uc; x++) {
      for(int y = vc; y < height-vc; y++) {    
		min = 255;
		sum = 0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	  		  if ( k[u+uc][v+vc] == 1) {
	  			sum = f[x-u][y-v] ;
	  			if ( sum < min) 
	  			 	min =  sum; 
	  		}
		h[x][y] = min;
      }
    }
    return h;
  }
  
      public short[][] dilategs(short f[][], float k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;

    short h[][]=new short[width][height];
    short max = 0;
    short sum = 0;
    
    for(int x = uc; x < width-uc; x++) {
      for(int y = vc; y < height-vc; y++) {    
		max = 0;
		sum = 0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	  		  if (k[u+uc][v+vc] == 1) {
	  			sum = f[x-u][y-v] ;
	  			if ( sum > max) 
	  			 	max =  sum; 
	  		}

		h[x][y] = max;
      }
    }
    return h;
  }


   public short[][] dilate(short f[][], float k[][]) {
    int uc = k.length/2;    
    int vc = k[0].length/2;

    short h[][]=new short[width][height];
    short sum;

    for(int x = uc; x < width-uc; x++) {
      for(int y = vc; y < height-vc; y++) {
	  	sum = 0;
		for(int v = -vc; v <= vc; v++) 
	  		for(int u = -uc; u <= uc; u++) 
	  			if (k[u+uc][v+vc] == 1) 
	  				if (f[x-u][y-v] > sum)
	  			   		sum = f[x-u][y-v]; 
		h[x][y] = sum;
      }
    }
    return h;
  }


MorphFrame(String title) {
		super(title);
		morphMenu.add(erodeMenu);
		morphMenu.add(dilateMenu);
	       morphMenu.add(openMorphMenu);
		morphMenu.add(closeMorphMenu);
		morphMenu.add(insideContourMenu);
		morphMenu.add(middleContourMenu);
		morphMenu.add(outsideContourMenu);
		morphMenu.add(morphEdgeMenu);	
		morphMenu.add(morphColorMenu);	
		morphMenu.add(serraMenu);	
		SpatialFilterMenu.add(morphMenu);

	}

 public static final 
 		float kv[][] = {
			{0,1,0},
			{0,1,0},
			{0,1,0}
		};
 public static final
 		float kh[][] = {
			{0,0,0},
			{1,1,1},
			{0,0,0}
		};
 public static final 
		float kCross[][] = {
			{0,1,0},
			{1,1,1},
			{0,1,0}
		};
 public static final 
		float kSquare[][] = {
			{1,1,1},
			{1,1,1},
			{1,1,1}
		};
 public static final 
		float kThinTop[][] = {
			{0,1,0},
			{1,1,1},
			{0,0,0}
		};
 public static final 
		float kThinBottom[][] = {
			{0,0,0},
			{1,1,1},
			{0,1,0}
		};
 public static final 
		float kOutline[][] = {
			{0,1,1,1,0},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{0,1,1,1,0}
		};

 public int numberOfNeighbors(boolean p[]) {
 	int n=0;
 	if (p[0]) n++;
 	if (p[1]) n++;
 	if (p[2]) n++;
 	if (p[3]) n++;
 	if (p[4]) n++;
 	if (p[5]) n++;
 	if (p[6]) n++;
 	if (p[7]) n++;
 	return n;
 }
 
 private int numberOf01Transitions(boolean p[]) {
 	int n=0;
 	if ((!p[0]) && p[1]) n++; 
 	if ((!p[1]) && p[2]) n++; 
 	if ((!p[2]) && p[3]) n++; 
 	if ((!p[3]) && p[4]) n++; 
 	if ((!p[4]) && p[5]) n++; 
 	if ((!p[5]) && p[6]) n++; 
 	if ((!p[6]) && p[7]) n++; 
 	if ((!p[7]) && p[0]) n++; 
 	return n;
}

}