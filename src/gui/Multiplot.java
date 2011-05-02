package gui;
import java.awt.*;import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class Multiplot {
       
        public static void main(String args[]) {

          double limit=2*Math.PI;
          double incr=0.01;

          int arr1=(int)(1+limit/incr);
          int arr2=4;
          double x ;
          int n;

          String xaxis[]={"0 1.0 2.0 3.0",
                          "0 pi/2 pi 3pi/2 2pi",
                          "0 1 2 3 4 5"};               
          String yaxis[]={"1.00 0.50 0.00 -0.5 -1.0",
                         "1.0  0.0  -1.0",
                         "A B C"};
          String title[]={"x","Cos(2x)","Cos2(pi+x)",
                           "Cos2(2pi+x)","Cos2(3pi+x)"};

          double data1[][] = new double [arr1][arr2];
 
          for (int m=0;m<arr2;m++) {
              for (x=0, n=0;x<limit;x+=incr,n++) 
                if (m==0)
                  data1[n][m] =x; //setup x-axis
                else 
                 data1[n][m]=Math.cos((m-1)*Math.PI+x);
          }
          Db data = new Db(arr1,arr2,data1,xaxis,yaxis,
                                title);
                   
          Newplot np = new Newplot(data);
          np.setSize(400,380);
          np.setVisible(true);
        }

}

class Newplot extends ClosableFrame {

        Db data;
        Newplot(Db d) {
                super("My New Graph");
                data = d;
        }

        public void paint(Graphics g) {

           Font f=new Font("TimesRoman",Font.PLAIN,12);
           g.setFont(f);

           Rectangle r=getBounds();
           FontMetrics gfm=g.getFontMetrics();
           int markMargin=5;
           int labHmg=gfm.getHeight();
           int xMargin=100;

           int w=r.width-2*xMargin;
           int h=r.height-5*labHmg;

           int area=h/(data.ar2-1)-labHmg;

           setBackground(Color.white);

           Point p[][]=new Point[data.ar2-1][2];
           int x[][]=new int[data.ar2-1][2];
           int y[][]=new int[data.ar2-1][2];
           double max=2*Math.PI;
                  
           int num1=0,sum;
           StringTokenizer st1,st2;
                           
           for (int i=0;i<data.ar2-1;i++) 
            {
              g.drawLine(xMargin,(1+i)*(area+labHmg),
                              w+xMargin,(1+i)*(area+labHmg));//Xaxis
              g.drawLine(xMargin,(1+i)*labHmg+i*area,xMargin,
                                      (1+i)*(area+labHmg)); //Yaxis
              g.drawString(data.t[0],w+xMargin+4*markMargin,
                                  (1+i)*(area+labHmg)+markMargin);//x
              g.drawString(data.t[i+1],labHmg,
                      (1+i)*labHmg+i*area+2*markMargin);     //f(x)
            }

           //y lab
           for (int i=0;i<data.ar2-1;i++)  {
             st1= new StringTokenizer(data.ylab[i]);
             while (st1.hasMoreTokens()) {
               st1.nextToken();
               ++num1;
             }
            sum=num1;
            num1=0;
            st2 = new StringTokenizer(data.ylab[i]);
            int labWmg=(int)(gfm.stringWidth(data.ylab[i])/sum);
            while (st2.hasMoreTokens())  {
              g.drawString(st2.nextToken(),xMargin-labWmg,
                         i*(area+labHmg)+2*labHmg+area/sum*num1);
                          
              for (int j=0;j<2*sum;j++) {     //y mark
                     if(j/2!=num1)
                         g.drawLine(xMargin,
                           i*(area+labHmg)+labHmg+area/(sum*2)*j,
                           xMargin+labWmg/5,
                           i*(area+labHmg)+labHmg+area/(sum*2)*j);
                     else  
                         g.drawLine(xMargin,
                           i*(area+labHmg)+labHmg+area/sum*num1,
                           xMargin+labWmg/3,
                           i*(area+labHmg)+labHmg+area/sum*num1);
                    }
                    ++num1;
                 }
                     sum=0;
                     num1=0;
               }
           
               //x lab
               for (int i=0;i<data.ar2-1;i++){
                   st1 = new StringTokenizer(data.xlab[i]);
                   while (st1.hasMoreTokens()) {
                       st1.nextToken ();
                       ++num1;
                   }
                   sum=num1;
                   num1=0;
                   st2 = new StringTokenizer(data.xlab[i]);
                   while (st2.hasMoreTokens()) {
                       g.drawString(st2.nextToken(),w/sum*num1+
                         xMargin-markMargin,
                            (1+i)*(area+labHmg)+2*markMargin);
                              
                       for (int j=0;j<sum*sum;j++){   //x mark
                       if (j/sum!=num1)
                          g.drawLine(w/(sum*sum)*j+xMargin,
                            (1+i)*(area+labHmg)-markMargin,
                               w/(sum*sum)*j+xMargin,
                                   (1+i)*(area+labHmg));
                       else 
                          g.drawLine(w/sum*num1+xMargin,
                            (1+i)*(area+labHmg)-2*markMargin,
                               w/sum*num1+xMargin,
                                   (1+i)*(area+labHmg));
                       }
                       ++num1;
                       }           
                       sum=0;
                       num1=0;
                   }
           
                   
                   for (int i=0;i<data.ar2-1;i++)
                     p[i][0]=new Point(xMargin,(i+1)*(area/2+2*labHmg));

                   for (int j=0;j<data.ar2-1;j++)
                   {
                         for (int i=0;i<data.ar1;i++) 
                         {
                           x[j][1]=(int)(data.dataxy[i][0]*w/max+xMargin);
                           y[j][1]=(int)((j*(area+labHmg))+area/2-area/2*
                              (data.dataxy[i][j+1])+labHmg);

                           p[j][1]=new Point(x[j][1],y[j][1]);
                           g.drawLine(p[j][0].x,
                               p[j][0].y,p[j][1].x,p[j][1].y);
                           p[j][0]=p[j][1];
                         }
                   }
         }
}
class Db {
	double limit =2*Math.PI;
    double incr=0.01;
    int ar1=(int)((1+limit)/incr);
    int ar2=4;
    
    double[][] dataxy=new double [ar1][ar2];
    String xlab[];
    String ylab[];
    String t[];
    
    public Db(int array1,int array2,
    	double data[][], String[] x, 
                String[] y ,String[] tt) {
                ar1=array1;
                ar2=array2;
                for(int i=0;i<ar1;i++) {
                   for (int j=0;j<ar2;j++)
                     dataxy[i][j] = data[i][j];
        }

        xlab=x;
        ylab=y;
        t=tt;
    }

public Db(Db data) {
	ar1=data.ar1;
    ar2=data.ar2;
    for(int i=0;i<ar1;i++)  {
      for (int j=0;j<ar2;j++)
        dataxy[i][j] = data.dataxy[i][j];
    }

    xlab=data.getX();
    ylab=data.getY();
    t=data.getT();
}
    public String [] getT() {return t;}
    public String [] getY() {return ylab;}
    public String [] getX() {return xlab;}
}
