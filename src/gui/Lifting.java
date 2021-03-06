package gui;

public class Lifting {	

	//standard decomposition	
	public static void forwardHaar(short in[][]) {
		for (int x=0; x < in.length; x++) 
			forwardHaar(in[x],in.length);
		in = transpose(in);
		for (int x=0; x < in.length; x++) 
			forwardHaar(in[x],in.length);
		in = transpose(in);		
	}
	//standard reconstruction
	public static void backwardHaar(short in[][]) {
		for (int x=0; x < in.length; x++) 
			backwardHaar(in[x]);
		in = transpose(in);
		for (int x=0; x < in.length; x++) 
			backwardHaar(in[x]);
		in = transpose(in);
	}
	
	//nonstandard decomposition is lossy
	//Find out why!
	public static void forwardHaarNSD(short in[][]) {
		int width = in.length;
		int height = in[0].length;
		short temp[] = new short [width];
		for (int i = 0; i < width; i++) 
			forwardHaar(in[i],in.length);
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) 
				temp[i] = in[i][j];
			forwardHaar(temp,temp.length);
			for (int i = 0; i < width; i++)
				in[i][j]=temp[i];
		}
	}


	//nonstandard reconstruction
	public static void backwardHaarNSR(short in[][]) {
		int width = in.length;
		int height = in[0].length;
		short out[] = new short [width];
		for (int i = 0; i < width; i++)
			backwardHaar(in[i]);
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) 
				out[i] = in[i][j];
			backwardHaar(out);
			for (int i = 0; i < width; i++)
				in[i][j]=out[i];
		}
	}

	public static void forwardHaar(short in[],int n){
		int i;
		if (n<2) return;
	   	int nOn2=n/2;
		short s[]=new short[nOn2];
		short d[]=new short[nOn2];
		for (i=0;i<nOn2;i++) {
			s[i]=in[2*i];
			d[i]=in[2*i+1];
		}
		for (i=0;i<s.length;i++) {
			d[i]=(short)(d[i]-s[i]);
			s[i]=(short)(s[i]+d[i]/2);
		}
	 	for (i=0;i<s.length;i++) {
			in[i]=s[i];
			in[i+nOn2]=d[i];
		}
		forwardHaar(in,nOn2);
	}
	public static void backwardHaar(short in[]){
		int n=in.length;
		if (n<2) return;
		int nOn2=n/2;
		short s[]=new short[nOn2];
		for (int i=0; i<nOn2; i++)
			s[i]=in[i];
		backwardHaar(s);
		nOn2=in.length/2;
		short d[]=new short[nOn2];
		for (int i=0;i<d.length;i++)
			d[i]=in[i+nOn2];
		for (int i=0;i<s.length;i++)
			s[i]=(short)(s[i]-d[i]/2);
		for (int i=0;i<s.length;i++)
			d[i]=(short)(d[i]+s[i]);
		for (int i=0;i<s.length;i++){
			in[2*i]=s[i];
			in[2*i+1]=d[i];
		}
	}
	
	 // Transpose matrix input.
  private static short[][] transpose(short in[][] ) {
  	int width = in.length;
  	int height = in[0].length;
    short[][] output = new short[height][width];

    for(int j=0; j<width; j++)
       for(int i=0; i<height; i++)
          output[j][i] = in[i][j];

      return output;
  } // End of function transpose().



	public static void print(short in[]) {
    	for (int i=0;i<in.length;i++)
			System.out.print(in[i]+" ");
		System.out.println();
	}
	public static void print(short in[][]) {
		for (int x=0;x < in.length; x++) {
		  for (int y=0; y < in[0].length; y++)
		  	System.out.print(in[x][y]+" ");
		 System.out.println();
		}
	}
	
	
	public static void main(String[] args){
		
		short input[][]= {
			{1  ,  2,  2,  2},
			{251,  0,254,254},
			{251,254,254,254},
			{251,254,254,254}};
		
		System.out.println("Input:");
		print(input);
		
		// since forwardHaar uses recursion, so we can see the procedure of coding
		forwardHaar(input);
		System.out.println("Transmission:");
		print(input);
		backwardHaar(input);
		System.out.println("Reconstruction:");
		print(input);
	}
	
	public static void main1d(String[] args){
		
		short input[]={1,2,251,254,255,0,128,64,0};
		
		System.out.println("Input:");
		print(input);
		
		// since forwardHaar uses recursion, so we can see the procedure of coding
		forwardHaar(input,input.length);
		System.out.println("Transmission:");
		print(input);
		backwardHaar(input);
		System.out.println("Reconstruction:");
		print(input);
	}

}