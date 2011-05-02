package gui;

public class TransformTable {
	private short lut[];
	
	public TransformTable(int size) {
		lut = new short[size];
	}
	
	public short[] getLut() {
		return lut;
	}
	
	public void randomize() {
		for (int i=0; i < lut.length; i++)
			lut[i] = (short)(Math.random() * 255);
	}
	
	public void clip() {
		for (int i=0; i < lut.length; i++) {
			if (lut[i] > 255) lut[i] = 255;
			if (lut[i] < 0) lut[i] = 0;
		}
	}
	
	public void setLut(short _lut[]) {
		lut = _lut;
	}
	
	public void print() {
		for (int i=0; i < lut.length; i++) {
			System.out.println(i+"\t"+lut[i]);
		}
	}
}