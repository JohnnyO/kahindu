package gui;
import java.awt.*;
import java.awt.event.*;

public class FileMenu extends ImageFrame implements ActionListener{
    MenuBar mb = new MenuBar();
    Menu FileMenu = new Menu("File");
    MenuItem print_mi = addMenuItem(FileMenu, "[E-p]rint");
    
    FileMenu(String title) {
    	super(title);
    	mb.add(FileMenu);
    	setMenuBar(mb);
    } 
   
    public void actionPerformed(ActionEvent e) {
    	if (match(e, print_mi)) {
    		print();
    		return;
    	}
    	super.actionPerformed(e);  

    }
 
    public void print() {
		Toolkit tk = Toolkit.getDefaultToolkit();
			PrintJob printJob = 
		    	tk.getPrintJob(
		    		this,
		    		"print me!",
		    		null);
			Graphics g = printJob.getGraphics();
			paint(g);
			printJob.end();
    }

}