package gui;
import java.applet.Applet;
import java.awt.AWTEvent;
import java.awt.Choice;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;


public class NetImageSelector extends Applet  {
        Choice ch=new Choice();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Label lb=new Label("Select Image");
        Image image;
        URL url;
        String st=
        	"ftp://vinny.bridgeport.edu/home/ftp/pub/ipij/images/";
        
         
        
        public void init() {
		try {url=new URL(st+"baboon.JPEG");
                }
        catch (MalformedURLException e) {
                        e.printStackTrace();
         }
                
                image=
                	tk.getImage(url);

                //             
                ch.addItem("baboon.JPEG");
                ch.addItem("girl.JPEG");
                ch.addItem("lena.JPEG");
                ch.addItem("peppers.JPEG");
                          
                add(lb);
                add(ch);
        }       
                
        public void processEvent(AWTEvent e1)
        {
                if (e1.getSource() instanceof Choice)
                {
                        try
                        {
                                url=new URL(st+ch.getSelectedItem());
                        }
                        catch (MalformedURLException e)
                        {
                                e.printStackTrace();
                        }       
                        image=tk.getImage(url);
                        repaint();
                }
                return;
        }
        public void update(Graphics g) {
        	paint(g);
        }
        public void paint(Graphics g)
        {
                g.drawImage(image,10,40,this);
        }
}