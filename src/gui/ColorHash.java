package gui;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class ColorHash extends Hashtable {

    static Color colors[] = {
       new Color(24,10,38),
       new Color(255,128,100),
       new Color(233,100,200),
       new Color(24,10,38),
       new Color(233,100,200),
       new Color(24,10,38),
       new Color(233,100,200),
       new Color(24,10,38),
       new Color(233,100,200),
       new Color(24,10,38),
       new Color(10,10,10)

    };
    
    public void addShortArrays(short r[][], short g[][], short b[][]) {
    	for (int x=0; x < r.length; x++) {
    		for (int y=0; y < r[0].length;y++) {
    			try {
    				Color c = new Color(r[x][y],g[x][y],b[x][y]);
    				put(c,c);
    				}
    			catch (Exception e) {
    				Color c = new Color(clip(r[x][y]),clip(g[x][y]),clip(b[x][y]));
    				put(c,c);
    			}
    		}
    	}
    }
    
    private int clip(int i) {
    	if (i > 255) return 255;
    	if (i < 0) return 0;
    	return i;
    }
    public int countColors() {
        int numberOfColors = 0;
        for (Enumeration e = this.elements(); 
        	e.hasMoreElements(); 
        	e.nextElement()) {
        	numberOfColors++;
        }
       return numberOfColors;
  }
  
  public void printColors() {
	Vector v = makeVector();
    for (int i =0; i < v.size(); i++) 
        	System.out.println("Vec:"+i+" "+v.elementAt(i));
  }
 
 public Vector makeVector() {
      Vector v = new Vector();
         for (Enumeration e = elements(); e.hasMoreElements(); ) {
        	v.addElement(new Pixel((Color)e.nextElement()));
        }
       return v;
 }


    public static void main(String args[]) {
   	 Color c = new Color(255,128,100);
        ColorHash ch = new ColorHash();
        Object o;
        for (int i = 0; i < colors.length; i++) {
            Color p = colors[i];
            ch.put(p, p);
        }
        System.out.println(
        	"ColorHash contains :"
        	+ch.countColors());
        ch.printColors();
        
        // sort the colors here!
        Vector vb = new Vector();
        Vector v = ch.makeVector();
        Collections.sort(v);
        Sort.sort(v, vb, 0, v.size(), true);

        for (int i =0; i < v.size(); i++) {
        	System.out.println("Sorted Vec:"+i+" "+v.elementAt(i));
        }
   }  
       
 }




