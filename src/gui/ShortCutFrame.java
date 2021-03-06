package gui;
import java.awt.AWTEvent;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

 public class  ShortCutFrame extends ClosableFrame 
 	implements KeyListener, ActionListener {
 private int place = 1;
 private String s = null;
 private String ps = null;
	
 public ShortCutFrame(String title) {
		super(title);
		addKeyListener(this);
 }
 
 public String getPS() {
 	return ps;
 }

 public boolean match(AWTEvent e, MenuItem mi) {
	if (e.getSource() == mi) return true;

	if (e.getSource() instanceof MenuItem) return false;
	if (ps == null) return false;
	if ( mi.getLabel().startsWith(ps)) return true;
	return false;
 }

 public void keyPressed(KeyEvent e) {
 	int charValue = e.getKeyChar();
 	ps = petriMap(charValue);
 	actionPerformed(new ActionEvent(e,
 		ActionEvent.ACTION_PERFORMED,charValue+""));
 }
 public void keyReleased (KeyEvent e) {
 }

 public static String getShortCut(KeyEvent e) {
 	int modifiers = e.getModifiers();
        StringBuffer buf = new StringBuffer();
         if ((modifiers & InputEvent.CTRL_MASK) != 0) {
            buf.append("^");
            buf.append("+");
        }
       if ((modifiers & InputEvent.META_MASK) != 0) {
            buf.append("Meta");
            buf.append("+");
        }
        if ((modifiers & InputEvent.ALT_MASK) != 0) {
            buf.append("@");
            buf.append("+");
        }
        return buf.toString();
    }

 public void actionPerformed(ActionEvent e){
 }
 public String mapModifiers(KeyEvent e) {
 	String modString =
 		KeyEvent.getKeyModifiersText(
 			e.getModifiers());
 	String newMods = "";
 	if (modString.indexOf("Ctrl")!=-1) newMods +="^-";
 	if (modString.indexOf("Command")!=-1) newMods += "A-";
 	if (modString.indexOf("Option")!=-1) newMods += "C-";
 	return newMods;
 	//Command+Ctrl+Option
 }

 public void keyTyped (KeyEvent e) {
 // System.out.println(mapModifiers(e));//+KeyEvent.getKeyModifiersText(
 // 	e.getModifiers()));
/*System.out.println(
"KeyPressed, consumed: "+e.isConsumed()
+"\n src is: "+e.getSource()+" id: "+e.getID()
+",\n code: "+e.getKeyCode()
+"\n("+e.getKeyText(e.getKeyCode())+"), char: "
+(int)e.getKeyChar()+",\n modifiers: "
+e.getKeyModifiersText(e.getModifiers()));
 	System.out.println(e);
 	int key = e.getKeyChar();
 	System.out.println("keycode="+key+ 
 		" hex="+ Integer.toString(key,16));
 	int mods = e.getModifiers();
 	System.out.println("mods="+mods+ 
 		" hex="+ Integer.toString(mods,16)
 		+" keyText="+e.getKeyText(key));
 	*/
}
public MenuItem addMenuItem(Menu aMenu, String itemName) {
	MenuItem mi = new MenuItem(itemName);
	aMenu.add(mi);
	mi.addActionListener(this);
	return(mi);
}
	
 private boolean isEsc(int c) {
	return ( c == 27);
 }
 private boolean isTab(int c) {
	return (c == '\t');
 }

 private boolean isMod(int c) {
	return (isEsc(c) || isTab(c));
 }
	
 private String petriMap(int c) {
	String s ="[" + (char) c + "]";
	switch (place) {
		case 1: 
			if (isEsc(c)) {
				// t1
				place=2; 
				return null; 
			}
			if (isTab(c)) {
				// t2
				place=4; 
				return null;
			}
			return s;
		case 2:
			if (isTab(c)) {
				// t3
				place = 3; 
				return null;
			}
			if (isEsc(c))
				return null;
			// t4
			place = 1; 
			ps = "[E-"+(char)c+"]";
			return (ps);
		case 3:
			if (isTab(c))
				return null;
			if (isEsc(c)) {
				// t5
				place = 1; 
				return null;
			}
			// t6
			place = 1; 
			ps = "[E-T-"+(char)c+"]";
			return (ps);
		case 4:
			if (isEsc(c)) {
				// t7
				place = 1; 
				return null;
			}
			if (isTab(c)) return null;
			// t8
			place = 1; 
			ps = "[T-"+(char)c+"]";
			return (ps);						
		}
		ps = s;
		return ps;   
	}
	
}
