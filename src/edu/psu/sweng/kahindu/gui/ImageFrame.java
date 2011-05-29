package edu.psu.sweng.kahindu.gui;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.io.AdditiveTransformer;
import edu.psu.sweng.kahindu.io.GrayTransformer;
import edu.psu.sweng.kahindu.io.NegateTransformer;
import edu.psu.sweng.kahindu.io.PowerTransformer;

public class ImageFrame extends JFrame {

	private final JFrame frame = new JFrame("Kahindu Refactor - Team 2");
	private final ImageComponent component;

	private KahinduImage image;

	public ImageFrame(final KahinduImage image) {
		super("Kahindu Refactor - Team 2");
		this.image = image;
		this.component = new ImageComponent(image);
		this.getContentPane().add(component);
		this.setJMenuBar(this.getMenu());
		this.pack();
	}

	private JMenuBar getMenu() {
		JMenuBar menuBar = new JMenuBar();

		TransformMenuBuilder builder = new TransformMenuBuilder(component);
		builder.addTransformer("Negate", new NegateTransformer(), KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		builder.addTransformer("Gray", new GrayTransformer());
		builder.addTransformer("Add 10", new AdditiveTransformer(10));
		builder.addTransformer("Brighten", new PowerTransformer(0.9));
		builder.addTransformer("Darken", new PowerTransformer(1.5));

		menuBar.add(builder.build());

		return menuBar;
	}

}
