package gui;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;

public class ColorFrame extends MartelliFrame {

	String noFloatMsg = "Make a float image first, use rgbtoxxx";
	Menu colorMenu = new Menu("Color");
	Menu triStimulusMenu = new Menu("TriStimulus");
	Menu quantizationMenu = new Menu("Quantization");
	Menu medianCutMenu = new Menu("Median Cut");
	Menu linearCutMenu = new Menu("linear Cut");
	Menu wuMenu = new Menu("Wu");
	Menu rgbMenu = new Menu("RGBto...");
	
	MenuItem linearCut21_mi = 
		addMenuItem(linearCutMenu,"cut 21 bits->3 bit image");
	MenuItem linearCut18_mi = 
		addMenuItem(linearCutMenu,"cut 18 bits->6 bit image");
	MenuItem linearCut15_mi = 
		addMenuItem(linearCutMenu,"cut 15 bits->9 bit image");
	MenuItem linearCut12_mi = 
		addMenuItem(linearCutMenu,"cut 12 bits->12 bit image");
	MenuItem linearCut9_mi = 
		addMenuItem(linearCutMenu,"cut 9 bits->15 bit image");
	MenuItem linearCut6_mi = 
		addMenuItem(linearCutMenu,"cut 6 bits->18 bit image");
	MenuItem linearCut3_mi = 
		addMenuItem(linearCutMenu,"cut 3 bits->21 bit image");

	MenuItem medianCut256_mi = 
		addMenuItem(medianCutMenu,"to 256");
	MenuItem medianCut128_mi = 
		addMenuItem(medianCutMenu,"to 128");
	MenuItem medianCut64_mi = 
		addMenuItem(medianCutMenu,"to 64");
	MenuItem medianCut32_mi = 
		addMenuItem(medianCutMenu,"to 32");
	MenuItem medianCut16_mi = 
		addMenuItem(medianCutMenu,"to 16");
	MenuItem medianCut8_mi = 
		addMenuItem(medianCutMenu,"to 8");
	MenuItem medianCut4_mi = 
		addMenuItem(medianCutMenu,"to 4");
		
	MenuItem wu128_mi = 
		addMenuItem(wuMenu,"to 128");
	MenuItem wu64_mi = 
		addMenuItem(wuMenu,"to 64");
	MenuItem wu32_mi = 
		addMenuItem(wuMenu,"to 32");
	MenuItem wu16_mi = 
		addMenuItem(wuMenu,"to 16");
	MenuItem wu8_mi = 
		addMenuItem(wuMenu,"to 8");
	MenuItem wu4_mi = 
		addMenuItem(wuMenu,"to 4");

	MenuItem printColors_mi = 
		addMenuItem(quantizationMenu,"printColors");
	MenuItem printNumberOfColors_mi = 
		addMenuItem(quantizationMenu,"print number of colors");
   	MenuItem printSNR_mi =
		addMenuItem(fileMenu, "printSNR relative to child");
	MenuItem copyToFloatPlane_mi =
		addMenuItem(quantizationMenu, "copy to floatPlane");


	MenuItem subSampleChroma4To1_mi = 
		addMenuItem(triStimulusMenu,"subSampleChroma4To1");
	MenuItem subSampleChroma2To1_mi = 
		addMenuItem(triStimulusMenu,"subSampleChroma2To1");
	MenuItem rgb2yuv_mi = 
		addMenuItem(rgbMenu,"rgb2yuv");
	MenuItem yuv2rgb_mi = 
		addMenuItem(triStimulusMenu,"yuv2rgb");
	MenuItem rgb2iyq_mi = 
		addMenuItem(rgbMenu,"rgb2iyq");
	MenuItem iyq2rgb_mi = 
		addMenuItem(triStimulusMenu,"iyq2rgb");
	MenuItem rgb2hsb_mi = 
		addMenuItem(rgbMenu,"rgb2hsb");
	MenuItem hsb2rgb_mi = 
		addMenuItem(triStimulusMenu,"hsb2rgb");
	//MenuItem rgb2hls_mi = 
	//	addMenuItem(rgbMenu,"rgb2hls");
	//MenuItem hls2rgb_mi = 
	//	addMenuItem(triStimulusMenu,"hls2rgb ");
	MenuItem rgb2xyzd65_mi = 
		addMenuItem(rgbMenu,"rgb SMPTE-C 2xyzd65");
	MenuItem xyzd652rgb_mi = 
		addMenuItem(triStimulusMenu,"xyzd652 rgb SMPTE-C");
	MenuItem rgb2Ccir601_2cbcr_mi = 
		addMenuItem(rgbMenu,"rgb NTSC 2Ccir601_2cbcr");
	MenuItem ccir601_2cbcr2rgb_mi = 
		addMenuItem(triStimulusMenu,"Ccir601_2cbcr2 rgb NTSC");
	
	private ColorHash ch = null;
	
	private FloatPlane fp = null;
	
	public void actionPerformed(ActionEvent e) {				
		if (match(e, printSNR_mi)) {
    		printSNR();
			return;
		}
		if (match(e, copyToFloatPlane_mi)) {
    		copyToFloatPlane();
			return;
		}
		if (match(e, subSampleChroma4To1_mi)) {
    		subSampleChroma4To1();
			return;
		}
		if (match(e, subSampleChroma2To1_mi)) {
    		subSampleChroma2To1();
			return;
		}
		if (match(e, rgb2iyq_mi)) {
    		rgb2iyq();
			return;
		}
		if (match(e, iyq2rgb_mi)) {
    		iyq2rgb();
			return;    		
		}
		if (match(e, rgb2Ccir601_2cbcr_mi)) {
    		rgb2Ccir601_2cbcr();
			return;
		}
		if (match(e, ccir601_2cbcr2rgb_mi)) {
    		ccir601_2cbcr2rgb();
			return;    		
		}
		if (match(e, rgb2xyzd65_mi)) {
    		rgb2xyzd65();
			return;
		}
		if (match(e, xyzd652rgb_mi)) {
    		xyzd652rgb();
			return;
		}
		if (match(e, rgb2hsb_mi)) {
    		rgb2hsb();
			return;
		}
		if (match(e, hsb2rgb_mi)) {
    		hsb2rgb();
			return;
		}
		//if (match(e, rgb2hls_mi)) {
    	//	rgb2hls();
		//	return;
		//}
		//if (match(e, hls2rgb_mi)) {
    	//	hls2rgb();
		//	return;
		//}
		if (match(e, rgb2yuv_mi)) {
    		rgb2yuv();
			return;
		}
		if (match(e, yuv2rgb_mi)) {
    		yuv2rgb();
			return;
		}
		if (match(e, medianCut256_mi)) {
    		medianCut(256);
			return;
		}
		if (match(e, medianCut128_mi)) {
    		medianCut(128);
			return;
		}
		if (match(e, medianCut64_mi)) {
    		medianCut(64);
			return;
		}
		if (match(e, medianCut32_mi)) {
    		medianCut(32);
			return;
		}
		if (match(e, medianCut16_mi)) {
    		medianCut(16);
			return;
		}
		if (match(e, medianCut8_mi)) {
    		medianCut(8);
			return;
		}
		if (match(e, medianCut4_mi)) {
    		medianCut(4);
			return;
		}	
		if (match(e, wu128_mi)) {
    		wu(128);
			return;
		}
		if (match(e, wu64_mi)) {
    		wu(64);
			return;
		}
		if (match(e, wu32_mi)) {
    		wu(32);
			return;
		}
		if (match(e, wu16_mi)) {
    		wu(16);
			return;
		}
		if (match(e, wu8_mi)) {
    		wu(8);
			return;
		}
		if (match(e, wu4_mi)) {
    		wu(4);
			return;
		}
		if (match(e, linearCut21_mi)) {
    		linearCut(7,7,7);
			return;
		}
		if (match(e, linearCut18_mi)) {
    		linearCut(6,6,6);
			return;
		}
		if (match(e, linearCut15_mi)) {
    		linearCut(5,5,5);
			return;
		}
		if (match(e, linearCut12_mi)) {
    		linearCut(4,4,4);
			return;
		}
		if (match(e, linearCut9_mi)) {
    		linearCut(3,3,3);
			return;
		}
		if (match(e, linearCut6_mi)) {
    		linearCut(2,2,2);
			return;
		}
		if (match(e, linearCut3_mi)) {
    		linearCut(1,1,1);
			return;
		}
		
		if (match(e,printColors_mi)) {
			printColors();
			return;
		}
		if (match(e,printNumberOfColors_mi)) {
			printNumberOfColors();
			return;
		}
		super.actionPerformed(e);
	}
	ColorFrame(String title) {
		super(title);
		initMenu();		
	}
	public void printSNR() {
		System.out.println("SNR, in dB = "+getSNRinDb());
		System.out.println("There are "+width*height+" pixels");
	}
	
	// Compute (13.9)
	public double getSNRinDb() {
		if (child == null) {
			System.out.println("Make child first!");
			return 0;
		}
		// Math.log is a natural log
		// but want a common log.
		return 10 * Math.log(getSNR())/Math.log(10);
	}
	
	public float getSNR() {
		return getTotalSignalPower()/getTotalNoisePower();		
	}
	
	public float getTotalSignalPower() {
		float sum = 0;
		float signal = 0;
		for (int x = 0; x < width; x++) 
			for (int y=0; y < width; y++) {
				signal = r[x][y]+g[x][y]+b[x][y];
				sum = sum + signal*signal;
			}
		return sum;
	}
	public float getTotalNoisePower() {
		float sum = 0;
		float error = 0;
		for (int x = 0; x < width; x++) 
			for (int y=0; y < width; y++) {
				error = 
					r[x][y]+g[x][y]+b[x][y] 
					- (child.r[x][y] + child.g[x][y] + child.b[x][y]);
				sum = sum + error*error;
			}
		return sum;
	}
	public void copyToFloatPlane() {
		if (fp == null) {
			System.out.println("Make a float plane first!");
			return;
		}
		fp.copyFloats(this);
	}
	public void subSampleChroma4To1() {
		rgb2Ccir601_2cbcr();
		fp.subSampleChroma4To1();
		fp.toRgb();
		fp.linearTransform();
		fp.updateParent();
	}
	public void subSampleChroma2To1() {
		rgb2Ccir601_2cbcr();
		fp.subSampleChroma2To1();
		fp.toRgb();
		fp.linearTransform();
		fp.updateParent();
	}
	public void rgb2Ccir601_2cbcr() {
		fp = new Ccir601_2cbcr(this);
		fp.fromRgb();
		fp.updateParent();
	}
	public void ccir601_2cbcr2rgb() {
		if (fp == null) {
			System.out.println(noFloatMsg);
			return;
		}
		fp.toRgb();
		fp.updateParent();
	}
	public void rgb2xyzd65() {
		fp = new Xyzd65(this);
		fp.fromRgb();
		fp.updateParent(255);
	}
	public void xyzd652rgb() {
		if (fp == null) {
			System.out.println(noFloatMsg);
			return;
		}
		fp.toRgb();
		fp.updateParent();
	}

	public void rgb2iyq() {
		fp = new Yiq(this);
		fp.fromRgb();
		fp.updateParent();
	}
	public void iyq2rgb() {
		if (fp == null) {
			System.out.println(noFloatMsg);
			return;
		}
		fp.toRgb();
		fp.updateParent();
	}
	public void rgb2hsb() {
		fp = new Hsb(this);
		fp.fromRgb();
		fp.updateParent(255);
	}
	public void hsb2rgb() {
		if (fp == null) {
			System.out.println(noFloatMsg);
			return;
		}
		fp.toRgb();
		fp.updateParent();
	}
	public void rgb2yuv() {
		fp = new Yuv(this);
		fp.fromRgb();
		fp.updateParent();
	}
	public void yuv2rgb() {
		if (fp == null) {
			System.out.println(noFloatMsg);
			return;
		}
		fp.toRgb();
		fp.updateParent();
	}
	public void rgb2hls() {
		fp = new Hls(this);
		fp.fromRgb();
		fp.updateParent();
	}
	public void hls2rgb() {
		if (fp == null) {
			System.out.println(noFloatMsg);
			return;
		}
		fp.toRgb();
		fp.updateParent();
	}
	
	public void wu(int k) {
		Wu w=new Wu();
		w.wuQuantization(r,g,b,width,height,k);
		short2Image();
		repaint();
	}
	
	public void medianCut(int k) {
		MedianCut mc = new MedianCut(getPels(),width, height);
		setImage(mc.convert(k));
		repaint();
	}
	
	public void linearCut(int sr, int sg, int sb) {
		linearCut(r,sr);
		linearCut(g,sg);
		linearCut(b,sb);
		short2Image();
		repaint();
	}
	public void linearCut(short a[][], int numberOfBitsToCut) {
		int mask = 255 << numberOfBitsToCut;
		for (int x=0; x < width; x++)
			for (int y=0; y < height; y++) 
				a[x][y] = (short)(a[x][y] & mask);
	}

	public int [] getPels() {
 		int pels[] = new int[width*height];
 		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
		 		pels[ x + y * width] = 
		     		0xff000000 
		     		| (r[x][y] << 16) 
		     		| (g[x][y] << 8) 
		     		|  b[x][y];
			}
		return pels;
	}
	private void initMenu() {
		quantizationMenu.add(linearCutMenu);
		quantizationMenu.add(medianCutMenu);
		quantizationMenu.add(wuMenu);
		colorMenu.add(quantizationMenu);
		triStimulusMenu.add(rgbMenu);
		colorMenu.add(triStimulusMenu);
		SpatialFilterMenu.add(colorMenu);
	}
	
	public void printNumberOfColors() {
		System.out.println("Computing Colors");
		System.out.flush();
		System.out.println("There are "+
			computeNumberOfColors()+
			" colors in this image");
	}
	public int computeNumberOfColors() {
		ch = new ColorHash();
		ch.addShortArrays(r, g, b);
		return ch.countColors();
	}
	
	public void printColors() {
		if (ch == null) computeNumberOfColors();
		ch.printColors();
	}

}

