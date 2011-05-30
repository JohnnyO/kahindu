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

		JMenuBuilder builder = new JMenuBuilder("Transforms");

		TransformMenuItemBuilder negate = new TransformMenuItemBuilder(new NegateTransformer(), component);
		negate.setName("Negate").setShortcutKey(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		builder.addMenuItemBuilder(negate);

		TransformMenuItemBuilder gray = new TransformMenuItemBuilder(new GrayTransformer(), component);
		gray.setName("Gray");
		builder.addMenuItemBuilder(gray);

		TransformMenuItemBuilder add10 = new TransformMenuItemBuilder(new AdditiveTransformer(10), component);
		add10.setName("Add 10");
		builder.addMenuItemBuilder(add10);

		TransformMenuItemBuilder brighten = new TransformMenuItemBuilder(new PowerTransformer(0.9), component);
		brighten.setName("Brighten");
		builder.addMenuItemBuilder(brighten);

		TransformMenuItemBuilder darken = new TransformMenuItemBuilder(new PowerTransformer(1.5), component);
		darken.setName("Darken");
		builder.addMenuItemBuilder(darken);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(builder.getMenu());
		return menuBar;
	}

}
