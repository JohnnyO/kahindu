package edu.psu.sweng.kahindu.gui;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.transform.ParameterizedTransformer;
import edu.psu.sweng.kahindu.transform.Transformer;

public class ParameterizedTransformMI extends AbstractMenuLeaf {

	private final ParameterizedTransformer transformer;
	private final ImageComponent target;

	public ParameterizedTransformMI(ParameterizedTransformer t, ImageComponent target) {
		this.transformer = t;
		this.target = target;
	}

	@Override
	public JMenuItem build() {
		Action a = new AbstractAction(this.name) {
			{
				this.putValue(ACCELERATOR_KEY, ParameterizedTransformMI.this.shortcutKey);
			}

			@Override
			public void actionPerformed(ActionEvent evt) {

				Map<String, Double> map = transformer.getDefaultParameters();
				DoubleLog prompt = new DoubleLog(map);
				Window ancestor = SwingUtilities.getWindowAncestor(target);

				int dialogChoice = JOptionPane.showConfirmDialog(ancestor, prompt, "Set Parameters",
						JOptionPane.OK_CANCEL_OPTION);
				if (dialogChoice == JOptionPane.OK_OPTION) {
					for (Map.Entry<String, Double> e : prompt.getParameters().entrySet()) {
						transformer.setParameter(e.getKey(), e.getValue());
					}
					KahinduImage source = target.getImage();
					KahinduImage result = transformer.transform(source);
					target.updateImage(result);
				}
			}
		};

		return new JMenuItem(a);
	}

	protected File openFileDialog(ImageComponent component) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(IMAGE_DIRECTORY_NAME));
		fc.showOpenDialog(component);
		return fc.getSelectedFile();
	}

	protected File saveFileDialog(ImageComponent component) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(IMAGE_DIRECTORY_NAME));
		fc.showSaveDialog(component);
		return fc.getSelectedFile();
	}

}
