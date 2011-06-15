package gui;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
public class StreamSniffer {
	private BufferedInputStream bis;
	private byte header[] = new byte[6];
	private int numberActuallyRead = 0;
	
	public StreamSniffer(InputStream is) {
		bis = new BufferedInputStream(is);
		init();
		sniff();
	}
	
	public BufferedInputStream getStream() {
		return bis;
	}
	
	// Sniff and cover your tracks!
	private void sniff() {
		if (! bis.markSupported()) {
			System.out.println(
				"StreamSniffer needs"+
				" a markable stream");
			return;
		}
		bis.mark(header.length);
		
		try {
			numberActuallyRead 
				= bis.read(header);
			bis.reset();
		}
		catch (IOException e) {
			System.out.println(e);
			numberActuallyRead = -1;
		}
		
	}
	public void printHeader() {
		System.out.println("In hex...");
		for (int i=0; i < header.length; i++) 
		 Byte.printToHex(header[i]);	
		System.out.println("\nin base 8...");
		for (int i=0; i < header.length; i++) 
		 Byte.printToOctal(header[i]);
		System.out.println("\n in ASCII");
		System.out.println(new String(header));
		System.out.println("if (match("
			+ Byte.toString(header[0]) +","
			+ Byte.toString(header[1]) +","
			+ Byte.toString(header[2]) +","
			+ Byte.toString(header[3]) +"))");	
		 
	}
	
	public String toString() {
		int id = classifyStream();
		String s = getStringForId(id);
		if (id == TYPENOTFOUND)
			printHeader();		
		return s;
			
	}
	
	public boolean match(
		char c0, char c1) {
		byte b[] = header;
		return
			(b[0] == c0) &&
			(b[1] == c1);
	}
	public boolean match(
		char c0, char c1, 
		char c2, char c3) {
		byte b[] = header;
		return 
			(b[0] == (c0&0xFF)) &&
			(b[1] == (c1&0xFF)) &&
			(b[2] == (c2&0xFF)) &&
			(b[3] == (c3&0xFF));		
	}
	public boolean match(
		int c0, int c1, 
		int c2, int c3) {
		byte b[] = header;
		return 
			((b[0]&0xFF) == (c0&0xFF)) &&
			((b[1]&0xFF) == (c1&0xFF)) &&
			((b[2]&0xFF) == (c2&0xFF)) &&
			((b[3]&0xFF) == (c3&0xFF));		
	}
	public boolean match(
		int c0, int c1) {
		byte b[] = header;
		return 
			((b[0]&0xFF) == (c0&0xFF)) &&
			((b[1]&0xFF) == (c1&0xFF));		
	}
	

	
	
	public int classifyStream() {
		byte b[] = header;
		String s = new String(b);
		short leshort = 
			(short)(
				(b[0] << 8)|(b[1]));
		int belong =  
			(b[0] << 24) |
			(b[1] << 16) |
			(b[2] << 8) |
			(b[3]);
					

		if (s.startsWith("begin"))
			return UUENCODED;
		if (s.startsWith ("xbtoa"))
			return BTOAD;
		if (s.startsWith ("P1"))
			return PBM;
		if (s.startsWith ("P2"))
			return PGM;
		if (s.startsWith ("P3"))
			return PPM;
		if (s.startsWith ("P4"))
			return PBM_RAWBITS;
		if (s.startsWith ("P5"))
			return PGM_RAWBITS;
		if (s.startsWith ("P6"))
			return PPM_RAWBITS;
		if (s.startsWith ("yz"))
			return MGR_BITMAP;
		if (s.startsWith ("ILBM"))
			return IFF_ILBM;
		if ((b[0] == 131) &&
			(b[1] == 246) &&
			(b[2] == 152) &&
			(b[3] == 225))
			return SUNRASTER;
		if ((b[0] == 1) &&
			(b[1] == 332) ) 
			return SGI_IMAGE;
		if ((b[0] == 361) &&
			(b[1] == 0) &&
			(b[2] == 100) &&
			(b[3] == 273 ))
			return CMU_WINDOW_MANAGER_BITMAP;
		if ((b[0] == 131) &&
			(b[1] == 246) &&
			(b[2] == 152) &&
			(b[3] == 225) )
			return SUN;
		if ((b[0] == 115) &&
			(b[1] == 115))
			return TIFF_BIG_ENDIAN;
		if ((b[0] == 111) &&
			(b[1] == 111) )
			return TIFF_LITTLE_ENDIAN;
		if (match(73,73,42,0))
			return TIFF_LITTLE_ENDIAN;
		if (s.startsWith("GIF87a"))
			return GIF87a;
		if (s.startsWith("GIF89a"))
			return GIF89a;
		if ((b[0] == 131) &&
			(b[1] == 246) &&
			(b[2] == 152) &&
			(b[3] == 225))
			return SUNRASTER;
		if (leshort == 0xAF11)
			return FLI;
		if ((b[0] == 0) &&
			(b[1] == 0) &&
			(b[2] == 1) &&
			(b[3] == 263))
			return MPEG;
		if (s.startsWith(".snd")) 
			return SUN_NEXT_AUDIO;
		if (s.startsWith("MThd")) 
			return STANDARD_MIDI;
		if (s.startsWith("RIFF")) 
			return MICROSOFT_RIFF;
		if (s.startsWith("BZ")) 
			return BZIP;
		if (s.startsWith("FORM")) 
			return IFF_DATA;
		if (s.startsWith("IIN1")) 
			return NIFF_IMAGE;
		if (s.startsWith("BM")) 
			return PC_BITMAP;
		if (s.startsWith("%PDF-")) 
			return PDF_DOCUMENT;
		if (s.startsWith("%!")) 
			return POSTSCRIPT_DOCUMENT;
		if (s.startsWith("MOVI")) 
			return SILICON_GRAPHICS_MOVIE;
		if (s.startsWith("moov")) 
			return APPLE_QUICKTIME_MOVIE;
		if (s.startsWith("mdat")) 
			return APPLE_QUICKTIME_MOVIE;
		if (match('P','K')) 
			return ZIP_ARCHIVE;
		if (match(03,0235)) 
			return UNIX_COMPRESS;
		if (match(037,0213)) 
			return GZIP;
		if (match(037,036)) 
			return HUFFMAN;
		if (match((char)0x89,'P','N','G')) 
			return PNG_IMAGE;
		if (match(0xFF, 0xD8, 0xFF, 0xE0)) 
			return JPEG;
		if (match(0377,0330,0377,0356)) 
			return JPG;	
		if (match('8','B','P','S'))
			return PSHOP8;
		if (match(172,237,0,5))
			return ZIP;
		return TYPENOTFOUND;
	}

private void init() {
   add(TYPENOTFOUND,"TYPENOTFOUND");
   add(UUENCODED,"UUENCODED");
   add(BTOAD,"BTOAD");
   add(PBM,"PBM");
   add(PGM,"PGM");
   add(PPM,"PPM");
   add(PBM_RAWBITS,"PBM_RAWBITS");
   add(PGM_RAWBITS,"PGM_RAWBITS");
   add(PPM_RAWBITS,"PPM_RAWBITS");
   add(MGR_BITMAP,"MGR_BITMAP");
   add(GIF87a,"GIF87a");
   add(GIF89a,"GIF89a");
   add(IFF_ILBM,"IFF_ILBM");
   add(SUNRASTER,"SUNRASTER");
   add(SGI_IMAGE,"SGI_IMAGE");
   add(CMU_WINDOW_MANAGER_BITMAP,"CMU_WINDOW_MANAGER_BITMAP");
   add(SUN,"SUN");
   add(TIFF_BIG_ENDIAN,"TIFF_BIG_ENDIAN");
   add(TIFF_LITTLE_ENDIAN,"TIFF_LITTLE_ENDIAN");
   add(FLI,"FLI");
   add(MPEG,"MPEG");
   add(SUN_NEXT_AUDIO,"SUN_NEXT_AUDIO");
   add(STANDARD_MIDI,"STANDARD_MIDI");
   add(MICROSOFT_RIFF,"MICROSOFT_RIFF");
   add(BZIP,"BZIP");
   add(IFF_DATA,"IFF_DATA");
   add(NIFF_IMAGE,"NIFF_IMAGE");
   add(PC_BITMAP,"PC_BITMAP");
   add(PDF_DOCUMENT,"PDF_DOCUMENT");
   add(POSTSCRIPT_DOCUMENT,"POSTSCRIPT_DOCUMENT");
   add(SILICON_GRAPHICS_MOVIE,"SILICON_GRAPHICS_MOVIE");
   add(APPLE_QUICKTIME_MOVIE,"APPLE_QUICKTIME_MOVIE");
   add(ZIP_ARCHIVE,"ZIP_ARCHIVE");
   add(UNIX_COMPRESS,"UNIX_COMPRESS");
   add(GZIP,"GZIP");
   add(HUFFMAN,"HUFFMAN");
   add(PNG_IMAGE,"PNG_IMAGE");
   add(JPEG,"JPEG");
   add(JPG,"JPG");
   add(PSHOP8, "Photo Shop...8 bits per pel");
   add(ZIP, "Zip file...Wavelet encoded image sequence?");
}

protected void add(int i, String s) {
   h.put(new Integer(i), s);
}

public String getStringForId(int id) {
    return (String) h.get(new Integer(id));
}



public static final int TYPENOTFOUND = 0;
public static final int UUENCODED = 1; 
public static final int BTOAD =2; 
public static final int PBM =3; 
public static final int PGM =4; 
public static final int PPM =5; 
public static final int PBM_RAWBITS=6;
public static final int PGM_RAWBITS = 7; 
public static final int PPM_RAWBITS = 8; 
public static final int MGR_BITMAP =9;
public static final int GIF87a =10;
public static final int GIF89a =11;
public static final int IFF_ILBM =12;
public static final int SUNRASTER =13;
public static final int SGI_IMAGE =14;
public static final int CMU_WINDOW_MANAGER_BITMAP =15;
public static final int SUN =16;
public static final int TIFF_BIG_ENDIAN =17;
public static final int TIFF_LITTLE_ENDIAN =18;
public static final int FLI =19;
public static final int MPEG =20;
public static final int SUN_NEXT_AUDIO=21;
public static final int STANDARD_MIDI=22;
public static final int MICROSOFT_RIFF=23;
public static final int BZIP=24; 
public static final int IFF_DATA=25;
public static final int NIFF_IMAGE=26;
public static final int PC_BITMAP=27;
public static final int PDF_DOCUMENT=28;
public static final int POSTSCRIPT_DOCUMENT=29;
public static final int SILICON_GRAPHICS_MOVIE=30;
public static final int APPLE_QUICKTIME_MOVIE=31;
public static final int ZIP_ARCHIVE=32;
public static final int UNIX_COMPRESS=33;
public static final int GZIP=34;
public static final int HUFFMAN=35;
public static final int PNG_IMAGE=38;
public static final int JPEG=39;
public static final int JPG=40;	
public static final int PSHOP8=41;	
public static final int ZIP=42;	


protected Hashtable h = new Hashtable();
}


