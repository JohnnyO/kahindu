package gui;
public class LoopInterchange {
	int jmax = 700;
	int imax = 700;
	int i, j;
	
	float x[][] = new float [imax][jmax];
	float y[] = new float [imax*jmax];
	
	public void test() {
		double t1,t2,t3,speedup;
		
		t1 = testLoop1();
		System.out.println("loop1:"+t1+" seconds");
		t2 = testLoop2();
		System.out.println("loop2:"+t2+" seconds");
		t3 = testLoop3();
		System.out.println("loop3:"+t3+" seconds");	
	}
	
	double testLoop3() {
		Timer t = new Timer();
		t.start();
		for (j=0; j < jmax; j++)
			for (i=0; i < imax; i++)
				x[i][j] *= 2;
		t.stop();
		System.out.println("first index faster");
		return t.getTime();
	}
	double testLoop1() {
		Timer t = new Timer();
		t.start();

		for (i=0; i < imax; i++)
			for (j=0; j < jmax; j++)
				y[j+i*imax] *= 2;
		t.stop();
		System.out.println("multiply access");
		return t.getTime();
	}
	
	double testLoop2() {
		Timer t = new Timer();
		t.start();
		for (i=0; i < imax; i++)
			for (j=0; j < jmax; j++)
				x[i][j] *= 2;
		t.stop();
		System.out.println("second index faster");
		return t.getTime();
	}
	
	public static void main(String args[]) {
		LoopInterchange li = new LoopInterchange();
		for (int i = 0; i < 10; i++)
			li.test();
	}
}