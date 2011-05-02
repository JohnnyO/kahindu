package gui;
import java.util.*;


public abstract class Sort {
    private Sort() {
    }
    
    public static void main(String args[]) {
    	Vector v = new Vector();
    	int a[] = {23,34,45,23,12,324,45,65,34,23,1,1,324};
    	for (int i=0; i<a.length; i++) 
    		v.addElement(new Cint(a[i]));
    	    sort(v,new Vector(),0, v.size(), true);
    	
    }
    
    public static void printVec(String s, Vector v) {
       System.out.println("-----> Printing Vector:"+s);
        for (int i =0; i < v.size(); i++) {
        	System.out.println("Vec:"+i+" "+v.elementAt(i));
        }

      }
      
     public static void sort(Vector va) {
     	Vector v = new Vector();
     	sort(va,v,0,va.size(),true);
     }
        
 
     public static void sort(Vector va, Vector vb, int begin,
        int count, boolean ascending) {

        if (count <= 1)
            return;
            quickSort(va, vb, begin, begin + count - 1, ascending);
    }


     private static void quickSort(Vector va, Vector vb,
                                  int left, int right, boolean ascending) {
        int i, j;
        Comparable pivot;
        Object tmp;

        if (va.size() <= 1)
            return;

        i = left;
        j = right;

        // choose middle key as pivot
        pivot = (Comparable)
        	va.elementAt((left + right) / 2);

        do {
            if (ascending) {
                while (i < right && pivot.isGreater(
                	(Comparable)va.elementAt(i)))
                    i++;

                while (j > left && pivot.isLess(
                	(Comparable)va.elementAt(j)))
                    j--;
            } else {
                while (i < right && pivot.isLess(
                	(Comparable)va.elementAt(i)))
                    i++;

                while (j > left && pivot.isGreater(
                	(Comparable)va.elementAt(j)))
                    j--;
            }

            if (i < j) {
               swap(va, i, j);
               swap(vb, i, j);
            }

            if (i <= j) {
                i++;
                j--;
            }
        } while (i <= j);

       // printVec(" Left="+left+"to j="+j+
		//	" Right="+i+ "to "+ right, va);


        if (left < j)
            quickSort(va, vb, left, j, ascending);

        if (i < right)
            quickSort(va, vb, i, right, ascending);
    }
    
    public static void swap(Vector v, int i, int j) {
      if (v == null) return;
      if ((v.size() < i) || (v.size() < j)) return;
    	Object tmp = v.elementAt(i);
    	v.setElementAt(v.elementAt(j),i);
    	v.setElementAt(tmp, j);
    }
    

}
