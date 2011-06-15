package idx;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Application extends Frame
	 implements Runnable, KeyListener, WindowListener, 
	 MouseListener, MouseMotionListener
{
	// Create new tread
	Thread idx_Thread = null;
	boolean initialized=false;

	idx3d demo;

	float oldx,oldy;

	float xrot=64;
	float yrot=32;
	float zrot=17;
	float dx=0;
	float dy=0;
	float dz=0;

	int mode;
	
	boolean autorot=true;

	public static void main(String args[]) {
		image3D();
	}
	
	public static void image3D() {
		Application f = new Application();
		f.setSize(200,200);
		f.setVisible(true);
		f.initialize();
		f.start();
		for (int i=1;i<=f.demo.objects;i++)
		{
			f.demo.object[i].mode=6;
		}
		
	}
	
	public void addScan(float radius[][]){
     int scancolor=demo.getIntColor(60,255,20);
     demo.addObject(mode,scancolor);
     int obj=demo.objects;
     float twoPi=2f* (float)Math.PI;
     float x1, y1, z1;
     int numberOfImages=radius[0].length;
     int h = radius.length;
     float deltaTheta = (float)(twoPi/(numberOfImages-1f));
     double deltaY = 3d/h;
     for (int i=0;i <numberOfImages;i ++)
        for (int y=0; y <h;y++){
          if (radius[y][i]>0){
	        x1 = (float)(radius[y][i] * Math.sin((float)(i*deltaTheta)));
            y1=-1.5f+(float)(y*deltaY);
            z1 = (float)(radius[y][i] * Math.cos((float)(i*deltaTheta)));
          }   
          else{
	        x1 = 0;
    	    y1=-1.5f+(float)(y*deltaY);
            z1 = 0;
          }
          demo.addNode(obj,x1,y1,z1);
        }
      demo.generateScanObject(obj, h, numberOfImages-1);
      demo.shiftObject(obj,(float)-1.2,(float)-0.5,(float)1.3);
      demo.scaleObject(obj,(float)0.5);
      demo.object[obj].texture=obj;
   }
	
public void initialize(Image i,float r[][]) {
	addListeners();
	demo=new idx3d(getSize().width,getSize().height);
	int mode = 1;
	demo.addTexture(i);
	addScan(r);
	addLights();
	demo.reflectivity=200;
	demo.setStatic();
	initialized=true;
}
	public static void image3D(Image img,short s[][]) {
		Application f = new Application();
		f.setSize(200,200);
		f.setVisible(true);
		f.initialize(img,s);
		f.start();
		for (int i=1;i<=f.demo.objects;i++)
		{
			f.demo.object[i].mode=6;
		}
		
	}


public static void image3D(Image img,float r[][]) {
	Application f = new Application();
	f.setSize(400,400);
	f.setVisible(true);
	f.initialize(img,r);
	f.start();
	for (int i=1;i<=f.demo.objects;i++){
		f.demo.object[i].mode=6;
	}
}
	public static Application stepImage(Image img,short s[][]) {
		Application f = new Application();
		f.setSize(200,200);
		f.setVisible(true);
		f.initialize(img,s);
		for (int i=1;i<=f.demo.objects;i++) {
			f.demo.object[i].mode=6;
		}
		return f;
	}
	
	private static void waitForImage(Component component,
                                           Image image) {
  MediaTracker tracker = new MediaTracker(component);        
  try {
  	tracker.addImage(image, 0);
    tracker.waitForID(0);
    if (!tracker.checkID(0)) 
         System.out.println("Load failure!");
  }
  catch(InterruptedException e) {  }
}
	
	public void windowClosing(WindowEvent e) {
		dispose();
	}
	public void windowClosed(WindowEvent e) {};
	public void windowDeiconified(WindowEvent e) {};
	public void windowIconified(WindowEvent e) {};
	public void windowActivated(WindowEvent e) {};
	public void windowDeactivated(WindowEvent e) {};
	public void windowOpened(WindowEvent e) {};
 	public void keyPressed(KeyEvent e) {};
 	public void keyReleased (KeyEvent e) {};

	public void addListeners() {
		addWindowListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	public void initialize() {
		addListeners();
		demo=new idx3d(getSize().width,getSize().height);
		int mode = 1;
		demo.addTexture(NumImage.getImage());
		addImageField();
		addLights();
		demo.reflectivity=200;
		demo.setStatic();
		demo.rotateWorld((float)90,(float)0,0);
		initialized=true;
	}

	public void initialize(Image i,short s[][]) {
		addListeners();
		demo=new idx3d(getSize().width,getSize().height);
		int mode = 1;
		demo.addTexture(i);
		addImageField(s);
		addLights();
		demo.reflectivity=200;
		demo.setStatic();
		demo.rotateWorld((float)90,(float)0,0);
		initialized=true;
	}
	Image getFileImage(String fileName) {
		Image i = Toolkit.getDefaultToolkit().getImage(
		fileName);
		waitForImage(this,i);
		return i;
	}

	public void paint(Graphics gx)
	{
		gx.setColor(Color.black);
		gx.fillRect(0,0,this.getSize().width,this.getSize().height);
		gx.setColor(new Color(0,255,0));
		gx.drawString("idx3d II.beta DEMO APPLET",10,20);
		gx.drawLine(10,26,160,26);
		gx.drawString("©1998 by Peter Walser",10,42);
		gx.drawString("Visit my JavaPage:",10,56);
		gx.drawString("www2.active.ch/~proxima/java",10,72);
		gx.drawString("Please wait while initializing...",10,92);

	}

	public void start()
	{
		if (idx_Thread == null)
		{
			idx_Thread = new Thread(this);
			idx_Thread.start();
		}
	}
	
	public void stop()
	{
		if (idx_Thread != null)
		{
			// now deprecated!
			// idx_Thread.stop();
			idx_Thread = null;
		}
	}

	public void run()
	{
		while(true)
		{
			repaint();
			try
			{
				idx_Thread.sleep(20);
			}
			catch (InterruptedException e)
			{
				System.out.println("idx://interrupted");
			}
		}
	}
	public void update(Graphics g) {
		if (initialized) {
			if (autorot) {
				demo.rotateWorld(3,-5,2);
			}
			demo.rotateObject(1,0,0,5);
			g.drawImage(demo.renderScene(),0,0,this);
		}
		else {
			paint(g);
			initialize();
		}
	}		

	void addImageField() {
		int fieldres=20;
		int fieldcolor=demo.getIntColor(255,96,0);
		float map[][]=new float[fieldres][fieldres];
		for (int i=2;i<fieldres;i++) {
			for (int j=2;j<fieldres;j++) {
				int x=NumImage.gray.length*i/fieldres;
				int y=NumImage.gray[0].length*j/fieldres;
				map[i][j]=(float)(NumImage.gray[x][y]/255.0);
			}
		}
		demo.generateField(map,fieldres,fieldres,mode,fieldcolor);
		demo.object[1].texture=1;
		demo.rotateObject(1,0,(float)180,(float)180);
		demo.shiftObject(1,0,(float)0.5,0);
	}
	void addImageField(short s[][]) {
		int fieldres=40;
		int fieldcolor=demo.getIntColor(255,96,0);
		float map[][]=new float[fieldres][fieldres];
		for (int i=1;i<fieldres-1;i++) {
			for (int j=1;j<fieldres-1;j++) {
				int x=s.length*i/fieldres;
				int y=s[0].length*j/fieldres;
				map[i][j]=(float)(s[x][y]/255.0);
			}
		}
		demo.generateField(map,fieldres,fieldres,mode,fieldcolor);
		demo.object[1].texture=1;
		demo.rotateObject(1,0,(float)180,(float)180);
		demo.shiftObject(1,0,(float)0.5,0);
	}
	void addField()
	// Adds a 3d field to the scene
	{
		int fieldres=10;
		int fieldcolor=demo.getIntColor(255,96,0);
		float map[][]=new float[fieldres][fieldres];
		for (int i=0;i<fieldres;i++)
		{
			for (int j=0;j<fieldres;j++)
			{
				float x=(float)i/(float)fieldres*2-1;
				float y=(float)j/(float)fieldres*2-1;
				//map[i][j]=x*x*x*y-y*y*y*x;
				//map[i][j]=(float)(Math.cos(x*y*8)/10-Math.tan(x*y/2)/2);
				map[i][j]=x*x+y*y-(float)0.5;
				//map[i][j]=(float)(Math.sin(x*y*8)/6-0.2);
			}
		}
		demo.generateField(map,fieldres,fieldres,mode,fieldcolor);
		demo.object[1].texture=1;
		demo.rotateObject(1,0,(float)180,(float)180);
		demo.shiftObject(1,0,(float)0.5,0);
	}


	void addLights()
	{
		demo.ambient=48;
		demo.setPhong(64);
		demo.addLight(new idx3d_vector((float)0,(float)0,(float)-1),1,164);
		demo.addLight(new idx3d_vector((float)2,(float)-4,(float)-1),1,144);
	}

	 public void keyTyped (KeyEvent e) {
		mode=(mode+1)%8;
		for (int i=1;i<=demo.objects;i++) 
			demo.object[i].mode=mode;
	 }

	
	public void mousePressed(MouseEvent evt) {
		autorot=false;
		oldx=evt.getX();
		oldy=evt.getY();
	}

	public void mouseReleased(MouseEvent evt) {
		autorot=true;
		oldx=evt.getX();
		oldy=evt.getY();
	}
	
	public void mouseDragged(MouseEvent evt) {
		int x= evt.getX();
		int y= evt.getY();

		demo.rotateWorld((oldy-y),(oldx-x),0);
		dx=(dx+oldx-x+360)%360;
		dy=(dy+oldy-y+360)%360;
		//showStatus("dx="+dx+" dy="+dy);
		oldx=x;
		oldy=y;
	}
	public void mouseMoved(MouseEvent evt) {};
	public void mouseClicked(MouseEvent evt) {};
	public void mouseEntered(MouseEvent evt) {};
	public void mouseExited(MouseEvent evt) {};
}
