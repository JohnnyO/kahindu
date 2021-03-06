package gui;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;

public class WaveletFrame extends FFTFrame {

	Menu waveletMenu = new Menu("Wavelet");
	MenuItem forwardHaar_mi = addMenuItem(waveletMenu,"Forward Haar");
	MenuItem backwardHaar_mi = addMenuItem(waveletMenu,"Backward Haar");
	MenuItem liftingForwardHaar_mi = addMenuItem(waveletMenu,"LiftingForward Haar");
	MenuItem liftingBackwardHaar_mi = addMenuItem(waveletMenu,"LiftingBackward Haar");
	MenuItem demo1d_mi = addMenuItem(waveletMenu,"demo 1d");
	MenuItem demo2d_mi = addMenuItem(waveletMenu,"demo 2d");

	MenuItem haarCompress_mi = addMenuItem(waveletMenu,"[E-c]haarCompress");
	MenuItem stripimage_mi = addMenuItem(waveletMenu,"stripimage");

	MenuItem ulawEncode_mi = addMenuItem(waveletMenu,"ulaw encode");
	MenuItem ulawDecode_mi = addMenuItem(waveletMenu,"ulaw decode");
	MenuItem clip_mi = addMenuItem(waveletMenu,"clip");
	MenuItem clearQuad1_mi = addMenuItem(waveletMenu,"clear quad1");
	MenuItem clearQuad2_mi = addMenuItem(waveletMenu,"clear quad2");
	MenuItem clearQuad3_mi = addMenuItem(waveletMenu,"clear quad3");
	MenuItem clearLowerHalf_mi = addMenuItem(waveletMenu,"clear lower half");
	MenuItem clearLower34_mi = addMenuItem(waveletMenu,"clear lower 3/4");

	MenuItem stats_mi = addMenuItem(fileMenu,"compute stats");
	
	
	public void actionPerformed(ActionEvent e) {
		String args[] = {""};

		if (match(e, clearLowerHalf_mi)) {
			clearLowerHalf();
			return;
		}
		if (match(e, clearLower34_mi)) {
			clearLower34();
			return;
		}
		if (match(e, clearQuad3_mi)) {
			clearQuad3();
			return;
		}
		if (match(e, clearQuad1_mi)) {
			clearQuad1();
			return;
		}
		if (match(e, clearQuad2_mi)) {
			clearQuad2();
			return;
		}
		if (match(e, stripimage_mi)) {
			stripimage();
			return;
		}
		if (match(e, haarCompress_mi)) {
			haarCompress();
			return;
		}
		if (match(e, clip_mi)) {
			clip();
			return;
		}
		if (match(e, ulawDecode_mi)) {
			ulawDecode();
			return;
		}
		if (match(e, ulawEncode_mi)) {
			ulawEncode();
			return;
		}
		if (match(e, stats_mi)) {
			stats();
			return;
		}
		
		if (match(e, liftingForwardHaar_mi)) {
			liftingForwardHaar();
			return;
		}
		if (match(e, liftingBackwardHaar_mi)) {
			liftingBackwardHaar();
			return;
		}
		if (match(e, backwardHaar_mi)) {
			backwardHaar();
			return;
		}
		if (match(e, forwardHaar_mi)) {
			forwardHaar();
			return;
		}

		if (match(e, demo2d_mi)) {
			demo2d();
			return;
		}
		if (match(e, demo1d_mi)) {
			demo1d();
			return;
		}
		super.actionPerformed(e);
	}
	public void demo2d() {
	  	short x[][] =
  				{
				{9,7,5,3},
				{3,5,7,9},
				{2,4,6,8},
				{4,6,8,10}
			};
		print(x);
		forwardHaar(x);
		print(x);
	}
	public void demo1d() {
	  	short x[][] =
  				{
				{9,7,5,3},
				{3,5,7,9},
				{2,4,6,8},
				{4,6,8,10}
			};
		print(x);
		for (int i=0; i < x.length; i++)	
			forwardHaar2(x[i]);
		print(x);
	}
  public void print(short in[][]) {
     for (int i=0; i<in.length; i++) {
      for (int j=0; j<in[0].length; j++)
        System.out.print(in[i][j] + "\t");
      System.out.println("");
    }
    System.out.println("-------------------");
  }
	public void forwardHaar() {
		fh(r);
		fh(g);
		fh(b);
		short2Image();
	}
	public void liftingForwardHaar() {
		Lifting.forwardHaar(r);
		Lifting.forwardHaar(g);
		Lifting.forwardHaar(b);
		short2Image();
	}
	public void liftingBackwardHaar() {
		Lifting.backwardHaar(r);
		Lifting.backwardHaar(g);
		Lifting.backwardHaar(b);
		short2Image();
	}
	public void fh(short in[][]) {
		forwardHaar(in);
	}
	public void backwardHaar() {
		backwardHaar(r);
		backwardHaar(g);
		backwardHaar(b);
		clip();
	}
	private static void forwardHaar(short in[][]) {
		int width = in.length;
		int height = in[0].length;
		short temp[] = new short [width];
		for (int i = 0; i < width; i++) 
			forwardHaar2(in[i]);
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) 
				temp[i] = in[i][j];
			forwardHaar2(temp);
			for (int i = 0; i < width; i++)
				in[i][j]=temp[i];
		}
	}



	private void backwardHaar(short in[][]) {
		int width = in.length;
		int height = in[0].length;
		short out[] = new short [width];
		for (int i = 0; i < width; i++)
			backwardHaar2(in[i]);
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) 
				out[i] = in[i][j];
			backwardHaar2(out);
			for (int i = 0; i < width; i++)
				in[i][j]=out[i];
		}
	}

	private static void forwardHaar2(short in[]) {
		int n = in.length;
		int nOn2 = n / 2;
		if (n < 2) return;
		for (int i = 0; i < n; i+=2) {
			in[i+1] -= in[i]; 
			in[i] += in[i+1]/2; 
		}
		short averages[] = new short[n/2];
		for (int i = nOn2 - 1 ; i >=0; i--) {
			averages[i]=in[2*i];
			in[i + nOn2] = in[2*i+1];
		}
		forwardHaar2(averages);
		for (int i = 0; i < nOn2; i++) 
			in[i] = averages[i];
	}

	private void backwardHaar2(short in[]) {
		int n = in.length;
		if (n < 2) return; 
		int nOn2 = n / 2;

		short averages[] = new short[nOn2];
		for (int i = 0; i < nOn2; i++) 
			averages[i] = in[i];
		backwardHaar2(averages);
		for (int i = 0 ; i < nOn2; i++) {
			in[2*i]=averages[i];
			in[2*i + 1] = in[i+nOn2];
		}
		for (int i = 0; i < n; i+=2) {
			in[i] -= in[i+1]/2; 
			in[i+1] += in[i]; 
		}
	}

	public int[][] short2Int(short s[][]) {
		int a[][] = new int[width][height];
		for (int x=0; x < width; x++)
			for (int y=0; y < height; y++)
				a[x][y] = s[x][y];
		return a;
	}
	public short[][] int2Short(int s[][]) {
		short a[][] = new short[width][height];
		for (int x=0; x < width; x++)
			for (int y=0; y < height; y++)
				a[x][y] = (short)s[x][y];
		return a;
	}
	
	WaveletFrame(String title) {
		super(title);
		xformMenu.add(waveletMenu);
	}


  /**
  	print statistics on the image
  */
 public void stats() {
   int max[] = {-10000,-1000,-1000};
   int min[] = { 10000, 1000, 1000};
   double average[] = new double[3];
 	for (int x=0; x < width; x ++) 
 		for (int y=0; y < height; y++) {
 			if (r[x][y] > max[0]) max[0] = r[x][y];
 			if (g[x][y] > max[1]) max[1] = r[x][y];
 			if (b[x][y] > max[2]) max[2] = r[x][y];
 			if (r[x][y] < min[0]) min[0] = r[x][y];
 			if (g[x][y] < min[1]) min[1] = r[x][y];
 			if (b[x][y] < min[2]) min[2] = r[x][y];
 			average[0]+=r[x][y];
 			average[1]+=g[x][y];
 			average[2]+=b[x][y];
 		}
 	int n = width*height;
 	average[0] = average[0]/n;
 	average[1] = average[1]/n;
 	average[2] = average[2]/n;
 	System.out.println("------ Statistics -----");
 	System.out.println("\tR\tG\tB\t");
 	System.out.println("min:"+min[0]+"\t"+min[1]+"\t"+min[2]);
 	System.out.println("max:"+max[0]+"\t"+max[1]+"\t"+max[2]);
 	System.out.println("avg:"+average[0]+"\t"+average[1]+"\t"+average[2]);
 }
 public static void main(String args[]) {
  	int x[][] =
  				{
				{9,7,5,3},
				{3,5,7,9},
				{2,4,6,8},
				{4,6,8,10}
			};
	WaveletFrame wf =new WaveletFrame("wavelet frame");
	wf.show();
  }
  
  public void ulawEncode() {
   for (int x=0; x < width; x++) {
   	 	r[x] = UlawCodec.encode(r[x]);
  	 	g[x] = UlawCodec.encode(g[x]);
  	 	b[x] = UlawCodec.encode(b[x]);
   }
   //add(128);
   short2Image();
  }
  public void ulawDecode() {
   for (int x=0; x < width; x++) {
   	 	r[x] = UlawCodec.decode(r[x]);
  	 	g[x] = UlawCodec.decode(g[x]);
  	 	b[x] = UlawCodec.decode(b[x]);
   }
   //add(-128);
   short2Image();
  }
  

  short eps = 0;
  public void haarCompress() {
  	forwardHaar();
  	stripimage();
  	backwardHaar();
   }
  public void stripimage() {
  	eps += 5;
  	System.out.println("Haar compress factor="+eps);
  	for (int x=0; x < width; x++) 
  		for (int y=0; y < height; y++) {
  			r[x][y]=strip(r[x][y],eps);
  			g[x][y]=strip(g[x][y],eps);
  			b[x][y]=strip(b[x][y],eps);
  		}
   }

  public void clearQuad1() {
  	clearQuad(width/2,height/2,width,height);
  	short2Image();
  }
  public void clearQuad2() {
  	clearQuad(width/2,0,width,height);
  	clearQuad(0,height/2,width,height);
  	short2Image();
  }
  public void clearQuad3() {
  	clearQuad(width/4,0,width,height);
  	clearQuad(0,height/4,width,height);
  	short2Image();
  }
  public void clearLowerHalf() {
  	clearQuad(0,height/2,width,height);
  	short2Image();
  }
  public void clearLower34() {
  	clearQuad(0,height/4,width,height);
  	short2Image();
  }
  
  public void clearQuad(int x1,int y1,int x2,int y2) {
 	for (int x=x1; x < x2; x++) 
  		for (int y=y1; y < y2; y++) {
  			r[x][y]=0;
  			g[x][y]=0;
  			b[x][y]=0;
  		}
  }
  public short strip(short i,short eps) {
  	if (Math.abs(i) < eps) return 0;
  	return i;
  }

  public void clip() {
  	for (int x=0; x < width; x++) 
  		for (int y=0; y < height; y++) {
  			r[x][y]=clip(r[x][y]);
  			g[x][y]=clip(g[x][y]);
  			b[x][y]=clip(b[x][y]);
  		}
  	short2Image();
  }
  private short clip(short i) {
	if (i < 0 )	return 0;
	if (i > 255) return 255;
	return i;
  }

}

class UlawCodec  {
	public static double mu = 255.0;
	public static double vmax = 255;
	public static double offset = vmax/2+2;
	private static double factor = 22;
	private static double muOnVmax = mu/vmax;

	public static short [] encode(short a[]){
		for (int i=0; i<a.length;i++)
			a[i] = encode(a[i]);
		return a; 
	}
	public static short decode(short x) {
		double a =  (x - offset)/factor;
		a = Math.exp(a) - 1;
		a = a / muOnVmax;
		return (short)a;
	}
	public static short encode(short x) {
		return
			(short) (offset+sign(x)*factor*Math.log(1+Math.abs(x)*muOnVmax));
	}
	public static short sign(short x) {
		if (x < 0) return -1;
		return 1;
	}

	public static short[] decode(short a[] ) {
		for (int i=0; i<a.length;i++)
			a[i] = decode(a[i]);
		return a; 
	}
	


}