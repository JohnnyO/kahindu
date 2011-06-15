package vs;

import java.io.PrintStream;

public class vsTimer {
	private long base_time;
	private long elapsed_time;

	private static final long UNIT = 1000;

	public vsTimer() {
		clear();
	}

	public void mark() {
		base_time = System.currentTimeMillis();
	}

	public void clear() {
		elapsed_time = 0;
	}

	public void record() {
		elapsed_time += (System.currentTimeMillis() - base_time);
	}

	public float elapsed() {
		return ((float) elapsed_time) / UNIT;
	}

	public void report(PrintStream pstream) {
		float elapsed_seconds = elapsed();
		pstream.println("Time " + elapsed_seconds + " sec");
	}
	public void report() {
		report(System.out);
	}

}

