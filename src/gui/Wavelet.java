/****************************************************************
 *                   CS410X  - JAVA Programming                 *
 *                                                              *
 *                        Assignment #4                         *
 *                                                              *
 *                      Haar - Wavelet 2D CODEC                 *
 *                                                              *
 *      Programmer: Matanya Elchanani, SID:0280923              *
 *      Date: Nov. 1st 1997                                     *
 *                                                              *
 *      File: Wavelet.java                                      *
 *                                                              *
 ****************************************************************
 This program is a 2D form of the previous homework assignment.
 I have used the same code (I think it is quite nifty to expend
 on a previous work) with minor changes:
 1. The prime matrix filler now works on a 2D matrix.
 2. The multilevel Forward and Inverse Haar-Wavelet methods were
    revised so they iterate through a 2D array, applying the
    transform to each row at a time.
 3. An overloaded 2D show method was added to be able to present
    the whole 2D array at the beginning.
 
 The rest of this remark is taken from the previous code:
    
 This program will fill an array with prime numbers. It will then
 Apply the Haar-Wavelet algorithm to the data and extract averages
 and difference coefficients repeatedly, until all data has been
 converted, and a single "father of all data" value is left as the
 first member of the array. The rest of the array will contain
 growing sets of difference coefficients.
 
 The above array is then operated over with an inverse Haar-Wavelet
 algorithm, which uses the "father of all data" and the difference
 coefficients to gradually reconstruct the the original data.
 
 Author's remark: Instead of using a set of matrices to hold the
 resulting coefficients (as the assignment suggested), I have chosen
 to use an overwrite of the original data in a vector. This is
 possible because for N data points, N/2 averages and N/2
 coefficients are written, so we can safely overwrite the old N data 
 points. This method is much more efficient from a memory usage
 point, and keeps the data in a single dimension orientation (which
 is recommended as this is a single dimension CODEC).
 
 As can be seen from the results, the usage of integers introduces
 rounding errors which eventually cause "noise" in the reconstructed
 data. Using floats to hold the averages and coefficients will solve
 the problem on the expence of wasting memory for holding floats.
*/

package gui;
 
public class Wavelet {
  public static void main(String args[]) {
  	int x[][] =
  				{
				{9,7,5,3},
				{3,5,7,9},
				{2,4,6,8},
				{4,6,8,10}
			};
	demo(x);
	int y[][] =
  				{
				{1,2,3,4},
				{4,3,2,1},
				{1,2,3,4},
				{4,3,2,1}
			};
	demo (y);

  }
   public static void demo(int x[][]) {
	show(x,x.length);
	System.out.println("Running Forward Haar Wavelet on the matrix:");
    fhw(x);
    //x=transpose(x);
    //fhw(x);
    //System.out.println("Here is what you transmit");
    //show(x);
    System.out.println("Running Inverse Haar Wavelet on the matrix:");
    ihw(x);
    //x=transpose(x);
    //ihw(x);
    
  }
  public static void demo2d(int x[][]) {
	show(x,x.length);
	System.out.println("Running Forward Haar Wavelet on the matrix:");
    fhw(x);
    x=transpose(x);
    fhw(x);
    System.out.println("Here is what you transmit");
    show(x);
    System.out.println("Running Inverse Haar Wavelet on the matrix:");
    ihw(x);
    x=transpose(x);
    ihw(x);  
  }
  static boolean printing = true;
  public static void fihw2d(int x[][]) {
  	printing = false;
    fhw(x);
    x=transpose(x);
    fhw(x);
    System.out.println("Here is what you transmit");
    show(x);
    ihw(x);
    x=transpose(x);
    ihw(x);  
  }
  public static void fhw(int x[][]) {
  	fhw(x, x.length, log2(x.length));
  }
  
  public static void ihw(int x[][]) {
    ihw(x, x.length, log2(x.length));
  }
 
  public static int log2(int n) {
  	return (int) (Math.log(n)/Math.log(2));
  }

  public static void main2(String args[]) {


/* Create the 2D data array and fill it with prime numbers */
    int x[][] = new int[8][8];
	fillprime(x);

/* Show our original data (resolution level 1) */
	System.out.println("The original Data:");
	show(x,8);

/* Now run the multilevel forward haar wavelet transform on the
   data, replacing the original array members with their averages
   and difference coefficients */
	System.out.println("Running Forward Haar Wavelet on the matrix:");
    fhw(x, 8, 3);
    
/* Now run the multilevel inverse haar wavelet transform on the
   data, replacing the averages and difference coefficients with
   the reconstructed data */
	System.out.println("Running Inverse Haar Wavelet on the matrix:");
    ihw(x, 8, 3);
	System.out.println("That's all folks.");
  }


  /**
   * Print an array (or part of it) given the size to print.
   * This is an overloaded method for showing up a whole 2D
   * array.
   */
  public static void show(int in[][]) {
  	show(in, in[0].length);
  }
  public static void show(int in[][],int size) {

    for (int i=0; i<in.length; i++) {
      for (int j=0; j<size; j++)
        System.out.print(in[i][j] + "\t");
      System.out.println("");
    }
    System.out.println("-------------------");
  }

  /**
   * Print an array (or part of it) given the size to print.
   * If colon is true, a coma will be printed half way between
   * the members (separating the avereges from the coefficients).
   */
  public static void show(int in[], int size, boolean colon) {
  	if (!printing) return;

    for (int i=0; i<size; i++) {
      if ((size/2 == i) && colon)
	System.out.print(", ");
      System.out.print(in[i] + "\t");
    }
    System.out.println("");
  }


  /**
   * Single level Forward Haar Wavelet transform.
   */
  public static void fhw(int in[], int size) {
    int tmp[] = new int[size];

    for (int i=0; i<size; i+=2) {
      tmp[i/2] = (in[i]+in[i+1])/2;	//compute the average
      tmp[size/2+i/2] = (in[i]-in[i+1])/2; // compute the coeff.
    }

    for (int i=0; i<size; i++) //put data back to the array
      in[i] = tmp[i];
    show(in, size, true); // and show it
  }


  /**
   * Multilevel 2D forward haar wavelet transform.
   * This is an overloaded "fhw" method for applying forward
   * haar wavelet "level" times on the matrix, each time with
   * half the range, and doing that on each row in the 2D matrix.
   */
  public static void fhw(int in[][], int size, int level) {
	int range = size;
	for (int j=0; j<level; j++) {
      for (int i=0; i<size; i++) {
        fhw(in[i], range);  // Apply fhw to each row
      }
      System.out.println("-------------------");
	  range /= 2;  // next resolution level
	}
  }

  
  /**
   * Single level Inverse Haar Wavelet transform.
   */
  public static void ihw(int in[], int size) {
    int tmp[] = new int[size];
    
    for (int i=0; i<size; i+=2) {  // reconstruct two members
      tmp[i] = in[i/2]+in[size/2+i/2];
      tmp[i+1] = in[i/2]-in[size/2+i/2];
    }
    
    for (int i=0; i<size; i++)  // put it in the array
      in[i] = tmp[i];
	show(in, size, false);  // and show the results
  }
  

  /**
   * Multilevel 2D inverse haar wavelet transform.
   * This is an overloaded "ihw" method for applying inverse
   * haar wavelet "level" times on the matrix, each time with
   * double the range.
   */

  public static void ihw(int in[][], int size, int level) {
    int range = size >> (level-1);
	for (int j=0; j<level; j++) {
      for (int i=0; i<size; i++) {
        ihw(in[i], range);  // Apply ihw on each row
      }
      if (printing)
         System.out.println("-------------------");
	  range *= 2;  // Next resolution level
	}
  }



  /**
   * Fill a 2D matrix with prime numbers.
   * 
   */
  public static void fillprime(int in[][]) {
	int primenum = 1;
	boolean itsprime;
	
	for(int i=0;i<in.length;i++) {  // fill the whole matrix
      for (int j=0; j<in[i].length; j++)
	  do {
	    if (itsprime=isprime(primenum))
		  in[i][j]=primenum;  // we have a prime, store it.
		primenum++;
	  } while (!itsprime);  // loop as long as it's not a prime
	}
  }


  /**
   * Check if a number is prime.
   * 
   */
  public static boolean isprime(int x) {
	boolean prime = true;  // assume it is
	int div;
		
	div=x/2;  // check only half the range
	while((div>1) && prime) {
		prime = ((x % div) != 0);
		div--;
	}
	return(prime);
  }
  
  public static int[][] transpose(int a[][]) {
    int b[][] = new int[a[0].length][a.length];
    for(int x=0; x<a.length; x++)
       for(int y=0; y<a[0].length; y++) 
          b[y][x] = a[x][y];
    return b;
	}
}