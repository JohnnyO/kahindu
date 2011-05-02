package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class MatLog extends 
	ClosableFrame  {	
     
 private int fieldSize;
 
 private static int colSpace = 5; 	 
 private static int rowSpace = 5; 	 
 
 private Panel inputPanel = new Panel();
  
 public MatLog(
    float f[][]) {
    	
    super("Kernal");
    initialize(f);
    pack();
    show();
}

 private void initialize(
 		float f[][]) {
 	
 	fieldSize = 3;
	int rowNum = f.length;
	int colNum = f[0].length;
    
	inputPanel.setLayout(new 
    	GridLayout(
    		rowNum,colNum,rowSpace,colSpace));
    for (int x = 0;x< rowNum; x++) 
     for (int y = 0;y< colNum; y++) 
        inputPanel.add(new Label(f[x][y]+" "));
    add("Center", inputPanel);
 	pack();
 	show();
 }
 
public static void main(String args[]) {
 float k[][] = {
	{ 0, -1,  0},
	{-1, 10, -1},
	{ 0, -1,  0}
	};
    	
    MatLog ml = new MatLog(k);
	}
}







