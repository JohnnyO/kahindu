package gui;
import java.awt.*;import java.awt.event.*;
import java.lang.*;
import java.util.*;
public class ProgressFrame extends Frame {
        long startTime = System.currentTimeMillis();
        private static final long UNIT = 1000;
        long elapsedTime = 0;
        float timeLeft = 0;
        float total = 0;
        int   n = 0;
        String[] s=new String[4];
        double db;

      public  ProgressFrame(String title) {
                super(title);
                setSize(200,200);
               
        }

        public void actionPerformed(ActionEvent e) {
           if ( e.getID() == WindowEvent.WINDOW_CLOSING ) {
              setVisible(false);
              dispose();
              return;
           } 
        }                                             
          
        public static String f2(double f) {
                int i = (int) f;
                double frac = f - i;
                int trunFrac = (int) (frac * 100);
                return i+"."+trunFrac;
        }
        
        public void setRatioComplete(double d, String id) {
                db = d;
                s[0] = id;
                elapsedTime = System.currentTimeMillis()
                        - startTime;
                timeLeft = (float)((1/d-1) * elapsedTime);
   
                s[1] = "Elapsed Time: " + f2(elapsedTime/UNIT) + " Seconds";
                s[2] = 
                	"Estimated time left: " 
                	+ f2(timeLeft/UNIT) 
                	+ " Seconds";
                s[3] = "Percent done:"
                		+ (int)(d*100)+"%";                                       
        		repaint();
        }

   public void paint(Graphics g) {

        Font  f = new Font("Times", Font.PLAIN, 10);
        FontMetrics fm = g.getFontMetrics(f);

        g.setFont(f);

        Dimension d = getSize();

        int sideMargin = 30;
        int topMargin = 40;
        int stringxMargin = 15;
        int markMargin = 5;

        int w = d.width-sideMargin*2;
        int h = d.height/4;

        setBackground(Color.white);
        g.setColor(Color.black);

        g.clearRect(0, 0, d.width, d.height);
        g.drawRect(sideMargin, topMargin, w, h);
                 
		g.drawString(s[0], sideMargin, topMargin+h+20);
    	g.drawString(s[1], sideMargin, topMargin+h+40);
    	g.drawString(s[2], sideMargin, topMargin+h+60);
    
    	g.fillRect(sideMargin, topMargin, (int)(w*db), h);
                  
    }
    
  public static void main(String args[]) {  
  	ProgressFrame pb = 
  		new ProgressFrame("test prog");
  	pb.show();
  	for (double d=0; d < 1; d = d + 0.1) {
  		pb.setRatioComplete(d,
    		"Percent done:"+(int)(d*100)+"%");
    		try {
    			Thread.sleep(500);
        	}
        	catch (Exception e) {};
   	}
  }

}