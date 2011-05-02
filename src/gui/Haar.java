package gui;

public class Haar  {

	public static void main(String args[]) {
		int data[][] = 
			{
				{9,7,5,3},
				{3,5,7,9},
				{2,4,6,8},
				{4,6,8,10}
			};
		int in[] = {9,7,5,3,9,7,5,3};
		fhw(in,in.length);
		println(in);
		
	}
  public static void fhw(int in[], int size) {
    int tmp[] = new int[size];

    for (int i=0; i<size; i+=2) {
      tmp[i/2] = (in[i]+in[i+1])/2;	
      tmp[size/2+i/2] = (in[i]-in[i+1])/2; 
    }

    for (int i=0; i<size; i++) 
      in[i] = tmp[i];
    show(in, size, true); 
  }
  
  public static void show(int in[], int size, boolean colon) {

    for (int i=0; i<size; i++) {
      if ((size/2 == i) && colon)
	System.out.print(", ");
      System.out.print(in[i] + "\t");
    }
    System.out.println("");
  }

	public static void fhw2(int in[]) {
		int g = in.length;
		while (g > 2) {
			println(in);
			decompositionStep(in,g);
			g = g / 2;
		}
	}

	public static void decompositionStep(int a[], int g){
		println("g="+g);
		println("g/2="+g/2);
		int c[] = new int[a.length];
		for (int i =1; i < g/2; i++) {
			c[i] = (a[2*i-1]+a[2*i])/2;
			c[g/2+i] = (a[2*i-1]-a[2*i])/2;
		}
		for (int i=0; i < c.length; i++) 
			a[i]=c[i];
	}
	public static void println(int a[]) {
		print(a);
		System.out.println();
	}
	public static void println(String s) {
		System.out.println(s);
	}
	public static void print(String s) {
		System.out.print(s);
	}
	public static void print(int a[]) {
		for (int i =0; i < a.length; i++) 
			System.out.print(a[i]+" ");
	}

}