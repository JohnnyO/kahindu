package gui;
import java.awt.*;
import java.awt.event.*;

public class Graph extends Frame {

          Plot1 Data;
	  String [] xaxis;
	  String [] yaxis;
	  Rectangle r;
	  
	 
        	
     public static void main(String args[]) {
	  double limit=2*Math.PI;
	  double incr=10;
	  String xaxis[]={"X","1pi","2pi","3pi"};
  	  String yaxis[]={"Sin(x)","-1.0","0.0","1.0"};
   
          int n=(int)(1+limit/incr);
    	    Plot1 data=new Plot1(n,xaxis,yaxis);

	  for (double x=0;x<limit;x+=incr)
		  data.push(x,Math.sin(2*x),0); 

         Graph pf=new Graph("Eye Intensity",data);
         pf.setSize(400,400);
         pf.setVisible(true);

         }

      
	  public Graph(String mark,Plot1 data1) { 
	
          super(mark);
          Data=data1;
          xaxis=data1.getxA();
	  yaxis=data1.getyA();
	}
	
	
    public void update(Graphics g) {
          g.clearRect(0,0,r.width,r.height);
         paint(g);
    }	
    
    			
      public void paint(Graphics g) {
	     r=getBounds();

           
           int w=r.width;
           int h=r.height; 
           int leftMargin = 30;
           int tickMarkInterval = 10;
           int tickMarkWidth = 30;
           int middleY = h/2 -25;             
   
           setBackground(Color.white);

           g.drawLine(leftMargin,middleY,w,middleY);
    	   g.drawLine(leftMargin,0,leftMargin,h);

           for (int i=1; i < w; i+= tickMarkInterval)
	         g.drawLine(i,middleY,i,h/2-tickMarkWidth);
  
	   for (int j=1; j < h; j +=tickMarkInterval)
  	         g.drawLine(leftMargin,j,35,j);
         
     	   g.drawString(yaxis[0],35,h/20);
           g.drawString(yaxis[1],3,h/4*3-5);
	   g.drawString(yaxis[2],5,middleY);
	   g.drawString(yaxis[3],5,h/5-30);
	   g.drawString(xaxis[0],w/2,h/4*3+20);
	   g.drawString(xaxis[1],w/3-5,h/2-15);
	   g.drawString(xaxis[2],w/2+20,h/2-15);
    	   g.drawString(xaxis[3],w-80,h/2-15);
		   
		   
	    Point p1,p2;
         
	    p1=new Point(leftMargin,h/3+37);
	    double max=2*Math.PI;
             
	    for (int t=0;t<Data.num;t++) {
			    
	     int x1=(int)(Data.getx(t)*w/max+leftMargin);
	     int y1=(int)(h/3-(h/3)*Data.gety(t)+37);

	      p2=new Point(x1,y1);
	      g.drawLine(p1.x,p1.y,p2.x,p2.y);
	      p1=p2;
	    }
	}

	public void actionPerformed(ActionEvent e) {
		
	   if (e.getID()==WindowEvent.WINDOW_CLOSING) 
    		dispose();
     }
}

class Plot1
{
    int num;
    String x[];      
    String y[];

    double x_d[];
    double y_d[];
    double z_d[];
    int lct = 0;

    public Plot1(int nn, String[] xx, String[] yy)
    {
       num = nn;
       x=xx;
       y=yy;
       x_d=new double[num];
       y_d=new double[num];
       z_d=new double[num];
    }
    
   	public String [] getxA() { return x;}
	public String [] getyA() { return y;}
	public int getsize() { return num;}
	public double getx(int dd) { return x_d[dd];}
	public double gety(int dd) { return y_d[dd];}
	public double getz(int dd) { return z_d[dd];}

    public void push(double xxx, double yyy, double zzz)
    {
       x_d[lct] = xxx;
       y_d[lct] = yyy;
       z_d[lct] = zzz;
       lct++;
    }                   
}


                           
