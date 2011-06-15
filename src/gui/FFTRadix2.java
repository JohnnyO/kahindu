package gui;
import vs.ColorUtils;
import vs.ImageUtils;
import vs.vsFFT;

public class FFTRadix2 {
	
	FFTFrame parent;
	
   private ColorUtils CU = new ColorUtils();
   private ImageUtils iUtil = new ImageUtils();
   private vsFFT imgFFT = new vsFFT();
      
   private short [] imageData_R ;
   private short [] imageData_G ;
   private short [] imageData_B ;


FFTRadix2(FFTFrame _parent) {

	parent = _parent;
	initData();
}
 private void initData() {
 	imgFFT.DisplayLogPSD = true;
 	int w = parent.width;
 	int h = parent.height;
	// separated R,G,B values
	System.out.println("getting the 1-D arrays");
    get1DArraysFromParent();
    System.out.println("init complete");
 }
 
 public void get1DArraysFromParent() {
	if (parent == null) 
		System.out.println(
			"ER:get1DArraysFromParent, parent==null");
	if (parent.width != parent.height)
		System.out.println("ComplexRgb:fft Non-square image detected");
	int N = parent.width*parent.height;
	imageData_R = new short[N];
	imageData_G = new short[N];
	imageData_B = new short[N];
	
	for (int x = 0; x < parent.width; x++)
		for (int y = 0; y < parent.height; y++) {
		 imageData_R[ x + y * parent.width] = parent.r[x][y];
		 imageData_G[ x + y * parent.width] = parent.g[x][y];
		 imageData_B[ x + y * parent.width] = parent.b[x][y];
	}
}
vsFFT vsfft = new vsFFT();
public int [] fft() {
	vsfft.DisplayLogPSD = true;
	if (parent.width != parent.height)
		System.out.println("ComplexRgb:fft ER! Non-square image");
	return vsfft.forward2dFFT(
		imageData_R,
		imageData_G,
		imageData_B,
		parent.width,
		parent.height);
}

public void complexMult() {
	for (int x = 0; x < parent.width; x++)
		for (int y = 0; y < parent.height; y++) {
		 vsfft.cR_r[y][x] *= parent.r[x][y]/255.0;
		 vsfft.cR_i[y][x] *= parent.r[x][y]/255.0;
		 vsfft.cG_r[y][x] *= parent.g[x][y]/255.0;
		 vsfft.cG_i[y][x] *= parent.g[x][y]/255.0;
		 vsfft.cB_r[y][x] *= parent.b[x][y]/255.0;
		 vsfft.cB_i[y][x] *= parent.b[x][y]/255.0;
	}
}

public int [] ifft() {
	return vsfft.reverse2dFFT();
}


}







