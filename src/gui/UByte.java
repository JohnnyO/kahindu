package gui;

// and unsigned 8 bit byte in java
public final class UByte {
	private UByte() {};
	
	// strip sign, unsigned int
	public static int ui(byte b) {
		if (b < 0) {
			return 256+b;
		}
		return b;
	}
	
	// unsigned short
	public static short us(byte b) {
		if (b < 0) {
			return (short) (256+b);
		}
		return (short)b;
	}
	
	public static void main(String args[]) {
		byte b = -2;
		System.out.println("Ubyte.ss("+b+")="+UByte.us(b));
		b = 127;
		System.out.println("Ubyte.ss("+b+")="+UByte.us(b));
		b = (byte)128;
		System.out.println("Ubyte.ss("+b+")="+UByte.us(b));
		
	}
}