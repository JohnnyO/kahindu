package gui;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;


public class BeanTester extends BeanFrame {
		
	public static void main(String args[]) {
		BeanTester bt = new BeanTester("BeanTester");
	}
	public BeanTester(String title) {
		super(title);
		initMenuBar();
		setSize(300,300);
		show();
	}
	public void item1() {
		System.out.println("item1");
	}
	public void item2() {
		System.out.println("item2");
	}
	public void itemH1() {
		System.out.println("itemH1");
	}
	public void itemH2() {
		System.out.println("itemH2");
	}
	
	public void initMenuBar() {		
		Menu m1 = new Menu("Event Menu");
	 
		addMenuItem(m1,"[E-T-1]item1","item1");	 
		addMenuItem(m1,"[T-1]item2","item2");
	
		Menu hierarchicMenu = new Menu("Hierarchic Menu");
		addMenuItem(hierarchicMenu,"[E-h]itemH1","itemH1");
		addMenuItem(hierarchicMenu,"[h]itemH2","itemH2");

		MenuBar mb = new MenuBar();
		m1.add(hierarchicMenu);
		mb.add(m1);
		setMenuBar(mb);
	}
	public void paint(Graphics g) {
		g.drawString("bean tester",150,150);
	}
	
}

