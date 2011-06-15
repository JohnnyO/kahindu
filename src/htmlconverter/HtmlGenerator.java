package htmlconverter;
import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HtmlGenerator extends Frame implements ActionListener {

	JavaStream js;
	DataOutputStream dos;
	FileControlPanel fcp;
	ColorControlPanel ccp;
	TargetControlPanel tcp;
	ButtonControlPanel bcp;
	Label statusBar;
	String fileName, pathName;
	String extention;
	MenuItem miOpen, miSave, miConvertAll, miQuit;
	
	boolean miOpenBuf, miSaveBuf, miConvertAllBuf, miQuitBuf;
	
	static String MI_OPEN       = "Open...";
	static String MI_SAVE       = "Save";
	static String MI_CONVERTALL = "Convert All";
	static String MI_QUIT       = "Quit";
	
	static String EXT_JAVA 	    = ".java";
	static String EXT_C         = ".c";
	static String EXT_CPP       = ".cpp";
	
	static String DIR_OUTPUT    = "Html";	// the folder name for output
	
// constructor
				
	public HtmlGenerator(String title) {
		super(title);
		MenuBar mb = new MenuBar();
		Menu m = new Menu("File");
		miOpen = new MenuItem(MI_OPEN);
		miSave = new MenuItem(MI_SAVE);
		miConvertAll = new MenuItem(MI_CONVERTALL);
		miQuit = new MenuItem(MI_QUIT);
		m.add(miOpen);
		m.add(miSave);
		m.add(miConvertAll);
		m.addSeparator();
		m.add(miQuit);
		mb.add(m);
		// now add the actionListener
		miOpen.addActionListener(this);
		miSave.addActionListener(this);
		miConvertAll.addActionListener(this);
		miQuit.addActionListener(this);
		miOpen.setEnabled(true);
		miSave.setEnabled(false);
		miConvertAll.setEnabled(false);
		miQuit.setEnabled(true);
		setMenuBar(mb);
		init();
	}
	
// public methods
	
	public String getExtention() {
		if (tcp.c.getSelectedItem().equals("Java"))
			return EXT_JAVA;
		else if (tcp.c.getSelectedItem().equals("C"))
			return EXT_C;
		else
			return EXT_CPP;
	}
	

	public void actionPerformed(ActionEvent evt) {
		int index;
		DataOutputStream indexFile;
		Object arg = evt.getActionCommand();
		Object target = evt.getSource();
		//System.out.println("Event="+evt);
		
		String label = (String)arg;
		if (target instanceof MenuItem || 
			target instanceof Button) {
			if (label.equals(MI_OPEN) && miOpen.isEnabled()) {
				setEventMask(true);
				statusMsg("Open is selected.");
				FileDialog fd = new FileDialog(this, "");
				fd.show();
				fileName = fd.getFile();
				pathName = fd.getDirectory();
      			fd.dispose();
      			
				if (fileName != null && pathName != null) {
					index = fileName.indexOf(".");
					setEventMask(false);
					if (index >= 0 && fileName.substring(index).equals(getExtention())) {
						fileName = fileName.substring(0, index);
						fcp.setText(fileName);
						// deprecated DL 4/26/98
						//miSave.enable();
						//miConvertAll.enable();
						miSave.setEnabled(true);
						miConvertAll.setEnabled(true);
					} else {
						fileName = "noname";
						pathName = "";
					}
				} else {
					setEventMask(false);
				}
			} else if (label.equals(MI_SAVE) && miSave.isEnabled()) {
				setEventMask(true);
				statusMsg("Save is selected.");
				File newDir = new File(pathName + DIR_OUTPUT);
				newDir.mkdir();
				convert(MI_SAVE);
				statusMsg("Converting is done.");
				setEventMask(false);
			} else if (label.equals(MI_CONVERTALL) && 
						miConvertAll.isEnabled()) {
				setEventMask(true);
				statusMsg("Convert All is selected.");
				try {
					File dir = new File(pathName);
					File newDir = new File(pathName + DIR_OUTPUT);
					newDir.mkdir();
					indexFile = new DataOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(
								pathName + DIR_OUTPUT + "/index.html")));
					indexFile.writeBytes("<HTML>\n");
					indexFile.writeBytes("<TITLE>Index</TITLE>\n");
					indexFile.writeBytes("<H3>Index</H3>\n");
					indexFile.writeBytes("<HR>\n");
					indexFile.writeBytes("<OL>\n");
					String[] files;
					if (getExtention().equals(EXT_JAVA))
						files = dir.list(new WildFilter(".java"));
					else if (getExtention().equals(EXT_C))
						files = dir.list(new WildFilter(".c"));
					else
						files = dir.list(new WildFilter(".cpp"));
					for (int i = 0; i < files.length; i++) {
						indexFile.writeBytes("<LI><A HREF=\"" + 
							files[i].substring(0, files[i].indexOf(".")) + ".html\">" +
							files[i] + "</A>\n");
						statusMsg("Converting " + files[i]);
						index = files[i].indexOf(".");
						if (index >= 0 && files[i].substring(index).equals(getExtention())) {
							fileName = files[i].substring(0, index);
							fcp.setText(fileName);
							convert(MI_CONVERTALL);
						}
					}
					indexFile.writeBytes("</OL>");
					indexFile.writeBytes("<HR>");
					indexFile.writeBytes("<H6><A HREF=\"http://lyon.bridgeport.edu\">This document was generated automatically by Kahindu.</A></H6>\n");
					indexFile.writeBytes("</HTML>\n");
					indexFile.close();
					statusMsg("Converting is done.");
				} catch (IOException e) {
					System.out.println("error is occured.");
				}
				setEventMask(false);

      		} else if (label.equals(MI_QUIT) && miQuit.isEnabled()) {
				setEventMask(true);
				statusMsg("Quit is selected.");
      			dispose();
				setEventMask(false);
      		}
		}
		// return true;

	}
	
	public static void main(String[] args) {
		HtmlGenerator window = new HtmlGenerator("HtmlGenerator");
		// deprecated DL 4/26/98
		// window.resize(275, 250);
		window.setSize(350, 350);
		window.show();
	} 

// private method
	
	private void setModifier(JavaStream js) {
		js.mainText.setColorForMainText(ccp.ccf0.c0.getSelectedItem());
		js.comments.setColor(ccp.ccf1.c0.getSelectedItem());
		js.comments.setStyle(ccp.ccf1.c1.getSelectedItem());
		js.strings.setColor(ccp.ccf2.c0.getSelectedItem());
		js.strings.setStyle(ccp.ccf2.c1.getSelectedItem());
		js.keywords.setColor(ccp.ccf3.c0.getSelectedItem());
		js.keywords.setStyle(ccp.ccf3.c1.getSelectedItem());
		if (tcp.c.getSelectedItem().equals("Java"))
			js.reservedWords = js.javaReservedWords;
		else if (tcp.c.getSelectedItem().equals("C"))
			js.reservedWords = js.cReservedWords;
		else
			js.reservedWords = js.cplusplusReservedWords;
	}
		
	private void init() {		
		fcp = new FileControlPanel();
		tcp = new TargetControlPanel();
		ccp = new ColorControlPanel(this);
		bcp = new ButtonControlPanel(this);
		statusBar = new Label("Please open a " + getExtention() + "file            ");
		setBackground(Color.white);
												
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(tcp);						// Add a target control panel
		add(fcp);						// Add a file control panel
		add(ccp);						// Add a color control panel
		add(bcp);						// Add a button control panel
		add(statusBar);					// Add a status bat
			
	}
	private void writeHtml(DataOutputStream dos,JavaStream js) 
			throws IOException {
			dos.writeBytes("<HTML>\n");
			dos.writeBytes("<TITLE>" + fcp.fcf1.getText() + "</TITLE>\n");
			dos.writeBytes(js.mainText.pref + "\n");
			dos.writeBytes("<H3>" + fcp.fcf1.getText() + "</H3>\n");
			dos.writeBytes("<HR><PRE>\n");
			js.convertToHtml();
			dos.writeBytes(js.mainText.postf + "</PRE></BODY>\n");
			dos.writeBytes("<HR>\n");
	}	
	private void convert(String convertMode) {
		try {
			dos = new DataOutputStream(
				new BufferedOutputStream(
					new FileOutputStream(pathName + DIR_OUTPUT 
							+ "/" + fcp.fcf2.getText())));
			js = new JavaStream(
				new DataInputStream(
					new FileInputStream(pathName + fcp.fcf1.getText())
					), dos);
			setModifier(js);
			writeHtml(dos,js);
			if (convertMode.equals(MI_CONVERTALL)){
				dos.writeBytes("<A HREF=\"index.html\">[Back to Index]</A>\n");
			}
			String programName = "This document was generated automatically"+
				" by Kahindu.</A></H6>\nKahindu";
			String homePage = "www.DocJava.com/\">";
			String beginHeader = "<H6><A HREF=\"http://";
			String bottomMessage =
				beginHeader+homePage+programName;
			dos.writeBytes(bottomMessage);
			dos.writeBytes("</HTML>\n");
			js.close();
			dos.close();
		} catch(FileNotFoundException e) {
		} catch(IOException e) {
		}
	}
	
	protected void setEventMask(boolean cond) {
		if (cond) {
			miOpenBuf = miOpen.isEnabled();
			miSaveBuf = miSave.isEnabled();
			miConvertAllBuf = miConvertAll.isEnabled();
			miQuitBuf = miQuit.isEnabled();
			miOpen.setEnabled(false);
			miSave.setEnabled(false);
			miConvertAll.setEnabled(false);
			miQuit.setEnabled(false);
		} else {
			miOpen.setEnabled(miOpenBuf);
			miSave.setEnabled(miSaveBuf);
			miConvertAll.setEnabled(miConvertAllBuf);
			miQuit.setEnabled(miQuitBuf);
		}
	}
	
	protected void statusMsg(String msgText) {
		statusBar.setText(msgText);
	}
	
	
}

