package edu.psu.sweng.kahindu.gui;

import java.io.File;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.AdvancedImageReader;
import edu.psu.sweng.kahindu.image.io.AdvancedImageWriter;
import edu.psu.sweng.kahindu.image.io.ByteArrayImageReader;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.DefaultImageWriter;
import edu.psu.sweng.kahindu.transform.AdditiveTransformer;
import edu.psu.sweng.kahindu.transform.ConvolutionTransformation;
import edu.psu.sweng.kahindu.transform.ExponentialNonAdaptiveHistogram;
import edu.psu.sweng.kahindu.transform.GrayTransformer;
import edu.psu.sweng.kahindu.transform.LegacyTransform;
import edu.psu.sweng.kahindu.transform.MedianFilter;
import edu.psu.sweng.kahindu.transform.NegateTransformer;
import edu.psu.sweng.kahindu.transform.PowerTransformer;
import edu.psu.sweng.kahindu.transform.RaleighNonAdaptiveHistogram;
import edu.psu.sweng.kahindu.transform.SaltAndPepperTransformation;
import edu.psu.sweng.kahindu.transform.UniformNonAdaptiveHistogram;
import gui.NumImage;


import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.*;

public class ImageFrame extends JFrame {
	private static final long serialVersionUID = 3848669250991405715L;

	private final ImageComponent component;

	private KahinduImage image;

	public ImageFrame() {
		super("Kahindu Refactor - Team 2");
		File defaultImage = new File("gifs/baboon.GIF");
		try {
			this.image = new ByteArrayImageReader(NumImage.gray).read(defaultImage);
			// this.image = new GIFReader(new File("gifs/baboon.GIF")).read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.component = new ImageComponent(image);
		this.getContentPane().add(component);
		this.setJMenuBar(this.getMenu());
		this.pack();
	}

	private JMenuBar getMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getFileMenu());
		menuBar.add(getFilterMenu());
		menuBar.add(getSpatialFilterMenu());

		return menuBar;
	}

	private JMenu getSpatialFilterMenu() {

		JMenuBuilder builder = new JMenuBuilder("Spatial", component);

		JMenuBuilder lowPassBuilder = new JMenuBuilder("Low-Pass", component);
		lowPassBuilder.add("Average", new ConvolutionTransformation(LOW_PASS_AVERAGE));
		lowPassBuilder.add("Low-Pass 1", new ConvolutionTransformation(LOW_PASS_ONE));
		lowPassBuilder.add("Low-Pass 2", new ConvolutionTransformation(LOW_PASS_TWO));
		lowPassBuilder.add("Low-Pass 3", new ConvolutionTransformation(LOW_PASS_THREE));

		JMenuBuilder medianBuilder = new JMenuBuilder("Median", component);
		medianBuilder.add("Cross   (3x3)", new MedianFilter(3, MedianFilter.CROSS));
		medianBuilder.add("Square  (3x3)", new MedianFilter(3, MedianFilter.SQUARE));
		medianBuilder.add("Octagon (5x5)", new MedianFilter(5, MedianFilter.OCTAGON));
		medianBuilder.add("Square  (5x5)", new MedianFilter(5, MedianFilter.SQUARE));
		medianBuilder.add("Diamond (7x7)", new MedianFilter(7, MedianFilter.DIAMOND));
		medianBuilder.add("Cross   (7x7)", new MedianFilter(7, MedianFilter.CROSS));

		medianBuilder.add("Salt & Pepper (100)", new SaltAndPepperTransformation(100));
		medianBuilder.add("Salt & Pepper (1000)", new SaltAndPepperTransformation(1000));
		medianBuilder.add("Salt & Pepper (2000)", new SaltAndPepperTransformation(2000));
		medianBuilder.add("Salt & Pepper (4000)", new SaltAndPepperTransformation(4000));
		
		JMenuBuilder highPassBuilder = new JMenuBuilder("High-Pass", component);
		highPassBuilder.add("High Pass 1", new ConvolutionTransformation(HIGH_PASS_ONE));
		highPassBuilder.add("High Pass 2", new ConvolutionTransformation(HIGH_PASS_TWO));
		highPassBuilder.add("High Pass 3", new ConvolutionTransformation(HIGH_PASS_THREE));
		highPassBuilder.add("High Pass 4",new ConvolutionTransformation(HIGH_PASS_FOUR));
		highPassBuilder.add("High Pass 5", new ConvolutionTransformation(HIGH_PASS_FIVE));
		highPassBuilder.add("Shadow Mask (hp6)",new ConvolutionTransformation(SHADOW_MASK));
		

		

		builder.addItem(lowPassBuilder);
		builder.addItem(medianBuilder);
		builder.addItem(highPassBuilder);
		return builder.build();
	}

	private JMenu getFileMenu() {
		JMenuBuilder file = new JMenuBuilder("File", component);

		JMenuBuilder openBuilder = new JMenuBuilder("Open", component);
		openBuilder.add("Load GIF", new DefaultImageReader());
		openBuilder.add("Load JPG", new DefaultImageReader());
		openBuilder.add("Load PNG", new DefaultImageReader());
		openBuilder.add("Load PPM", new AdvancedImageReader());

		JMenuBuilder saveBuilder = new JMenuBuilder("Save", component);
		saveBuilder.add("Save GIF", new DefaultImageWriter("gif"));
		saveBuilder.add("Save JPG", new DefaultImageWriter("jpg"));
		saveBuilder.add("Save PNG", new DefaultImageWriter("png"));
		saveBuilder.add("Save PPM", new DefaultImageWriter("PNM"));

		file.addItem(openBuilder);
		file.addItem(saveBuilder);

		return file.build();
	}

	private JMenu getFilterMenu() {
		JMenuBuilder builder = new JMenuBuilder("Filters", component);
		builder.add("Negate", new NegateTransformer());
		builder.add("Gray", new GrayTransformer());
		builder.add("Add 10", new AdditiveTransformer(10));
		builder.add("Brighten", new PowerTransformer(0.9));
		builder.add("Darken", new PowerTransformer(1.5));

		JMenuBuilder histograms = new JMenuBuilder("Histograms", component);
		histograms.add("Show Histograms", new LegacyTransform("histogram"));
		
		histograms.add("Uniform (Non-Adaptive)", new UniformNonAdaptiveHistogram());
		histograms.add("Exponential (Non-Adaptive)", new ExponentialNonAdaptiveHistogram());
		histograms.add("Raleigh (Non-Adaptive)", new RaleighNonAdaptiveHistogram());

		builder.addItem(histograms);
		return builder.build();
	}

}