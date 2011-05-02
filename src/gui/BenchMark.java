package gui;
public class BenchMark {
	TopFrame tf = null;
	public void run(TopFrame _tf) {
		System.out.println("Bench marks");
		tf = _tf;
		doFplaneMark();
	}
	public void doFplaneMark() {
		Timer t = new Timer();
		int N = 5;
		System.out.println(
		   "Running fplane mark,malloc 100MB.."+
		   "convolve "+N+" times with\n"+
		   "new Fplane(512,512)\n"+
		   "tf.lp2()\n"+
		   "tf.hp3()\n"+
		   "tf.zedSquare()\n"+
		   "tf.laplacian5()\n"+
		   "tf.fftR2()\n"+
		   "tf.ifftR2()\n"+
		   "tf.revert()\n");
		t.start();
		for (int i=0; i < N; i++) {
		   new Fplane(512,512);
		   tf.lp2();
		   tf.hp3();
		   tf.zedSquare();
		   tf.laplacian5();
		   tf.fftR2();
		   tf.ifftR2();
		   tf.sobel3();
		   tf.thresh();
		   tf.skeleton();
		   tf.revert();
		}
		t.stop();
		System.out.println("Total benchmarkTime");
		t.report();
	}
class Fplane {
	public float r[][];
	public float g[][];
	public float b[][];
	Fplane(int w, int h) {
		r = new float[w][h];
		g = new float[w][h];
		b = new float[w][h];
	}
}
 class Timer {
	private long base_time;
	private long elapsed_time;

	private long UNIT = 1000;


	public void start() {
		base_time = System.currentTimeMillis();
	}


	public void stop() {
		elapsed_time = 
			(System.currentTimeMillis() - base_time);
	}

	public float elapsed() {
		return ((float) elapsed_time) / UNIT;
	}

	public void report() {
		float elapsed_seconds = elapsed();
		System.out.println(
			"Time " + elapsed_seconds + " sec");
	}

}

}




