package edu.psu.sweng.kahindu.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import edu.psu.sweng.kahindu.io.GrayTransformer;
import edu.psu.sweng.kahindu.io.NegateTransformer;


public class ImageFrame {
	
	private final JFrame frame = new JFrame("Kahindu Refactor - Team 2");
	private final ImageComponent component;

	private KahinduImage image;
	
	public ImageFrame(final KahinduImage image) {
		this.image = image;
		this.component = new ImageComponent(image);
		frame.getContentPane().add(component);
		frame.setJMenuBar(this.getMenu());
		frame.pack();
	}
	
	public void setVisible(final boolean flag) {
		frame.setVisible(flag);
	}
	
	private JMenuBar getMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Filters");
		menu.add(new JMenuItem(negateAction));
		menu.add(new JMenuItem(grayAction));

		menuBar.add(menu);
		return menuBar;
	}
	
	private Action negateAction = new AbstractAction("Negate") {

		@Override
		public void actionPerformed(ActionEvent e) {
			TransformedImage negative = new TransformedImage(ImageFrame.this.image, new NegateTransformer());
			ImageFrame.this.updateImage(negative);
		}
	};
	
	private Action grayAction = new AbstractAction("Gray") {

		@Override
		public void actionPerformed(ActionEvent e) {
			TransformedImage gray = new TransformedImage(ImageFrame.this.image, new GrayTransformer());
			ImageFrame.this.updateImage(gray);
		}
	};

	

	private void updateImage(KahinduImage input) {
		this.image = input;
		this.component.updateImage(input);
		
	}
	
	

}
