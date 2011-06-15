package edu.psu.sweng.kahindu.gui;

import java.io.File;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.xml.transform.Transformer;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.AdvancedImageReader;
import edu.psu.sweng.kahindu.image.io.AdvancedImageWriter;
import edu.psu.sweng.kahindu.image.io.ByteArrayImageReader;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.DefaultImageWriter;
import edu.psu.sweng.kahindu.matrix.Matrix;
import edu.psu.sweng.kahindu.matrix.MatrixDictionary;
import edu.psu.sweng.kahindu.transform.AdditiveTransformer;
import edu.psu.sweng.kahindu.transform.CloseTransform;
import edu.psu.sweng.kahindu.transform.ConvolutionTransformation;
import edu.psu.sweng.kahindu.transform.DilateTransform;
import edu.psu.sweng.kahindu.transform.ErodeTransform;
import edu.psu.sweng.kahindu.transform.ExponentialNonAdaptiveHistogram;
import edu.psu.sweng.kahindu.transform.GrayTransformer;
import edu.psu.sweng.kahindu.transform.InnerContour;
import edu.psu.sweng.kahindu.transform.LegacyTransform;
import edu.psu.sweng.kahindu.transform.MedianFilter;
import edu.psu.sweng.kahindu.transform.MiddleContour;
import edu.psu.sweng.kahindu.transform.NegateTransformer;
import edu.psu.sweng.kahindu.transform.OpenTransform;
import edu.psu.sweng.kahindu.transform.OuterContour;
import edu.psu.sweng.kahindu.transform.PowerTransformer;
import edu.psu.sweng.kahindu.transform.RaleighNonAdaptiveHistogram;
import edu.psu.sweng.kahindu.transform.RobertsTransform;
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
        lowPassBuilder.add("Mean 3", new ConvolutionTransformation(MEAN3));
        lowPassBuilder.add("Mean 9", new ConvolutionTransformation(MEAN9));
        lowPassBuilder.add("Guassian 3x3", new ConvolutionTransformation(GUASSIAN3));
        lowPassBuilder.add("Guassian 7x7", new ConvolutionTransformation(GUASSIAN7));
        lowPassBuilder.add("Guassian 15x15", new ConvolutionTransformation(GUASSIAN15));
        lowPassBuilder.add("Guassian 31x31", new ConvolutionTransformation(GUASSIAN31));

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
        highPassBuilder.add("High Pass 4", new ConvolutionTransformation(HIGH_PASS_FOUR));
        highPassBuilder.add("High Pass 5", new ConvolutionTransformation(HIGH_PASS_FIVE));
        highPassBuilder.add("Shadow Mask (hp6)", new ConvolutionTransformation(SHADOW_MASK));

        JMenuBuilder edgeMenu = new JMenuBuilder("Edge", component);
        edgeMenu.add("Roberts 2", new RobertsTransform());
        edgeMenu.add("Median Square (2x2)", new MedianFilter(2, MedianFilter.SQUARE));
        edgeMenu.add("Median Line (2x1)", new MedianFilter(3, MedianFilter.VERTICAL_LINE));
        edgeMenu.add("Median Line (1x2)", new MedianFilter(3, MedianFilter.HORIZONTAL_LINE));

        JMenuBuilder morphMenu = new JMenuBuilder("Morph", component);

        JMenuBuilder erodeMenu = new JMenuBuilder("Erode", component);
        erodeMenu.add("H", new ErodeTransform(KH));
        erodeMenu.add("V", new ErodeTransform(KV));
        erodeMenu.add("Square", new ErodeTransform(KSQUARE));
        erodeMenu.add("Cross", new ErodeTransform(KCROSS));
        morphMenu.addItem(erodeMenu);

        JMenuBuilder dilateMenu = new JMenuBuilder("Dilate", component);
        dilateMenu.add("H", new DilateTransform(KH));
        dilateMenu.add("V", new DilateTransform(KV));
        dilateMenu.add("Square", new DilateTransform(KSQUARE));
        dilateMenu.add("Cross", new DilateTransform(KCROSS));
        morphMenu.addItem(dilateMenu);

        JMenuBuilder openMenu = new JMenuBuilder("Open", component);
        openMenu.add("H", new OpenTransform(KH));
        openMenu.add("V", new OpenTransform(KV));
        openMenu.add("Square", new OpenTransform(KSQUARE));
        openMenu.add("Cross", new OpenTransform(KCROSS));
        morphMenu.addItem(openMenu);
        
        
        JMenuBuilder closeMenu = new JMenuBuilder("Close", component);
        closeMenu.add("H", new CloseTransform(KH));
        closeMenu.add("V", new CloseTransform(KV));
        closeMenu.add("Square", new CloseTransform(KSQUARE));
        closeMenu.add("Cross", new CloseTransform(KCROSS));
        morphMenu.addItem(closeMenu);
        
        JMenuBuilder innerMenu = new JMenuBuilder("Inner Contour", component);
        innerMenu.add("H", new InnerContour(KH));
        innerMenu.add("V", new InnerContour(KV));
        innerMenu.add("Square", new InnerContour(KSQUARE));
        innerMenu.add("Cross", new InnerContour(KCROSS));
        morphMenu.addItem(innerMenu);
        
        JMenuBuilder outerMenu = new JMenuBuilder("Outer Contour", component);
        outerMenu.add("H", new OuterContour(KH));
        outerMenu.add("V", new OuterContour(KV));
        outerMenu.add("Square", new OuterContour(KSQUARE));
        outerMenu.add("Cross", new OuterContour(KCROSS));
        morphMenu.addItem(outerMenu);
        
        JMenuBuilder middleMenu = new JMenuBuilder("Middle Contour", component);
        middleMenu.add("H", new MiddleContour(KH));
        middleMenu.add("V", new MiddleContour(KV));
        middleMenu.add("Square", new MiddleContour(KSQUARE));
        middleMenu.add("Cross", new MiddleContour(KCROSS));
        morphMenu.addItem(middleMenu);




        

        builder.addItem(lowPassBuilder);
        builder.addItem(medianBuilder);
        builder.addItem(highPassBuilder);
        builder.addItem(edgeMenu);
        builder.addItem(morphMenu);

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