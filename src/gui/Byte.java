package gui;
public class Byte {
	public static void printToDecimal(byte b) {
		System.out.println(toInt(b));
	}
	
	public static int toInt(byte b) {
		return 
			((b >> 4) & 0xF)*16 +
			(b & 0xF);
	}
	
	public static String toString(byte b,int radix) {
		return 
			Integer.toString(toInt(b), radix);
	}
	
	public static String toString(byte b) {
		return toString(b,10);
	}
	public static void printToHex(byte b) {
		char hex[] = {
			'0','1','2','3','4',
			'5','6','7','8','9',
			'A','B','C','D','E','F'};
		System.out.print("0x"+
			hex[(b>>4) & 0x0f] +
			hex[ b & 0x0f]+" ");
	}
	public static void printToOctal(byte b) {
		char oct[] = {
			'0','1','2','3','4',
			'5','6','7'};
		System.out.print("0"+
			oct[(b>>6) & 03] +
			oct[(b>>3) & 07] +
			oct[ b & 07]+" ");
	}

	
}
