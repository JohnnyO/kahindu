package gui;
import java.applet.Applet;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

public class Console 
	extends Applet implements ActionListener
{
	TextField textField;
    String newline;
    String title = "Kahindu by D. Lyon";
	TopFrame tf = new TopFrame(title);
	public void init() {
		tf.setVisible(true);
		textField = new TextField(80);
         //Add Components to the Applet. 
        GridBagLayout gridBag = new GridBagLayout();
        setLayout(gridBag);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        gridBag.setConstraints(textField, c);
        add(textField);

		textField.addActionListener(this);

        newline = System.getProperty("line.separator");
	}
	public static void main(String args[]) {
		Console c = new Console();
		Frame f = new Frame();
		f.add(c);
		c.setVisible(true);
		f.setVisible(true);
		c.init();
		
	}
	public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();

	try{
		//String line;		
		//DataInputStream dis = new DataInputStream(System.in); 			
  		//while ((line=dis.readLine())!= null) {
            //System.out.println(line);
         	StringTokenizer st = new StringTokenizer(text);
			//while (st.hasMoreTokens() ) {
         		String t1 = st.nextToken();
          		//System.out.println(t1);
            	if(t1.equalsIgnoreCase("help")){ //if first token is help
            		if(st.countTokens()==0){//case1: help
            		//System.out.println(st.countTokens());		
   						tf.printMethods();
   					}
     					
     				if(st.countTokens()== 1){//case2: help className
     				   //System.out.println("in case 2");
     				    String t2 = st.nextToken();
     				   // System.out.println(t2);
     				   	String test1="gui.";						
 						Class r= Class.forName(test1.concat(t2));
 						//System.out.println("r="+r);
 						tf.printMethods(r.getMethods());					
    				}//end if case2 
                }//end if
           		            		            		
            	else{  //if first token is not help
           			//if (st.countTokens()==0) {   //case3: classname.method
           				//System.out.println("classname.method");
           				
           				StringTokenizer st1 = new StringTokenizer(t1,".");
           				
           				System.out.println(t1);
           				String cntok = st1.nextToken();
           				//System.out.println(cntok);
           				String mntok = st1.nextToken();
           				//System.out.println(mntok);
           				Class r = Class.forName("gui."+cntok);
           				Class pArray[]=new Class[0];
           				int tokenNum = st.countTokens();
           				//Object pArray[] = new Object[tokenNum];
           				//for ( int i = 0;i < tokenNum; i++ ) {
           				//	pArray[i] = st.nextToken();
           				//}
           				/*if(st.countTokens()==0){
           					pArray[]=new Class[0];
           				}
           				if(st.countTokens()==1){
           					pArray[]=new Class[1];
           					pArray[0]=st.nextToken();
           					
           				}
           				if(st.countTokens()==2){
           					Class pArray[]=new Class[2];
           					//pArray[0]=(Class)st.nextToken();
           					//pArray[1]=(Class)st.nextToken();
           				}
           				if(st.countTokens()==3){
           					Class pArray[]=new Class[3];
           					//pArray[0]=(Class)st.nextToken();
           					//pArray[1]=(Class)st.nextToken();
           					//pArray[2]=(Class)st.nextToken();
           				}
           				*/
           				
           				Method m=r.getMethod(mntok,pArray);
           				
           				/*if (cntok.equalsIgnoreCase("AdaptiveLog")){
           					AdaptiveLog a = new AdaptiveLog(tf);
           					m.invoke(a,pArray);
           				}*/
           				if (cntok.equalsIgnoreCase("AffineFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("AppletFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("BeanFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("BeanTester")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("BooLog")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("BoundaryFrame")){
           					m.invoke(tf,pArray);
           				}
           				/*else if (cntok.equalsIgnoreCase("Byte")){
           					Byte a = new Byte(tf);
           					m.invoke(a,pArray);
           				}*/
           				else if (cntok.equalsIgnoreCase("Ccir601_2cbcr")){
           					Ccir601_2cbcr a = new Ccir601_2cbcr(tf);
           					m.invoke(a,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("ClosableFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("ColorFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("ColorGridFrame")){
           					m.invoke(tf,pArray);
           				}
           				/*else if (cntok.equalsIgnoreCase("ColorHash")){
           					ColorHash a = new ColorHash(tf);
           					m.invoke(a,pArray);
           				}*/
           				/*else if (cntok.equalsIgnoreCase("ComMenuItem")){
           					ComMenuItem a = new ComMenuItem();
           					m.invoke(a,pArray);
           				}*/
           				else if (cntok.equalsIgnoreCase("ConvolutionFrame")){
           					m.invoke(tf,pArray);
           				}    // Need to test
           				/*  Class CShort doesn't need to be included*/
           				/*  Class DoLog doesn't need to be included*/
           				/*  Class DoubleLog doesn't need to be included*/
           				else if (cntok.equalsIgnoreCase("DrawFrame")){
           					m.invoke(tf,pArray);
           				} 
           				else if (cntok.equalsIgnoreCase("DrawTest")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("EdgeFrame")){
           					m.invoke(tf,pArray);
           				}
           				/*  Class Edgel doesn't need to be included*/
           				else if (cntok.equalsIgnoreCase("EventTester")){
           					m.invoke(tf,pArray);
           				}
           				/*   Class ExpandoLog doesn't need to be included */
           				/*   Class ExponentialLog doesn't need to be included */
           				else if (cntok.equalsIgnoreCase("FFTFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("FFTImage")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("FFTRadix2")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("FileMenu")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("FilterFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("FloatPlane")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("GrabFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Graph")){
           					m.invoke(tf,pArray);
           				}
           				/*   Class GrayLog doesn't need to be included */
           				/*   Class Haar doesn't need to be included */
           				else if (cntok.equalsIgnoreCase("Histogram")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Hls")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Haar")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("IconFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("ImageFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Lifting")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("MartelliFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Mat3")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Mat4")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("MatLog")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("MedianCut")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("MessLog")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("MorphFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("NegateFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("OpenFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("PaintFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("ProgressFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Random")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("SaveFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("FrameOne")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("SnellWlx")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("SpatialFilterFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("TopFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("TransformTable")){
           					TransformTable a = new TransformTable(1);
           					m.invoke(a,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Yuv")){
           					Yuv a = new Yuv(tf);
           					m.invoke(a,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Yiq")){
           				 	Yiq a = new Yiq(tf);
           				 	m.invoke(a,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("Xyzd65")){
           					Xyzd65 a = new Xyzd65(tf);    
           					m.invoke(a,pArray);
	           			}		       					
           				else if (cntok.equalsIgnoreCase("Wu")){//not work
           					Wu a = new Wu();
           					m.invoke(a,pArray);
           				}
	           			else if (cntok.equalsIgnoreCase("XformFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("WaveletFrame")){
           					m.invoke(tf,pArray);
           				}
           				else if (cntok.equalsIgnoreCase("SpatialFilterFrame")){
           					m.invoke(tf,pArray);
           				}
           				
           				else if (cntok.equalsIgnoreCase("ShortCutFrame")){
              				m.invoke(tf,pArray);
           				}
           				
           				else if (cntok.equalsIgnoreCase("ColorFrame")){
           					m.invoke(tf,pArray);
           				}
           	
           		}	
         //	}//end of while
       //	}//end of while      			
    } //end of try
    
    catch(Exception e) {
       		System.out.println(e);
    }      
       // textField.selectAll();
    }//end of actionPerformed()

	
	public void paint( Graphics g ) {
		g.drawString( "Command line interface", 30, 30 );
	}

}
