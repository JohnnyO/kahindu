package edu.psu.sweng.kahindu.gui;

import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.GUASSIAN15;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.GUASSIAN3;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.GUASSIAN31;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.GUASSIAN7;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.HIGH_PASS_FIVE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.HIGH_PASS_FOUR;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.HIGH_PASS_ONE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.HIGH_PASS_THREE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.HIGH_PASS_TWO;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.KCROSS;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.KH;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.KSQUARE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.KV;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.LOW_PASS_AVERAGE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.LOW_PASS_ONE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.LOW_PASS_THREE;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.LOW_PASS_TWO;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.MEAN3;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.MEAN9;
import static edu.psu.sweng.kahindu.matrix.MatrixDictionary.SHADOW_MASK;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.AdvancedImageReader;
import edu.psu.sweng.kahindu.image.io.AdvancedImageWriter;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.DefaultImageWriter;
import edu.psu.sweng.kahindu.transform.AdditiveTransformer;
import edu.psu.sweng.kahindu.transform.CloseTransformer;
import edu.psu.sweng.kahindu.transform.ConvolutionTransformer;
import edu.psu.sweng.kahindu.transform.DilateTransformer;
import edu.psu.sweng.kahindu.transform.ENAHTransformer;
import edu.psu.sweng.kahindu.transform.ErodeTransformer;
import edu.psu.sweng.kahindu.transform.GrayTransformer;
import edu.psu.sweng.kahindu.transform.InnerContourTransformer;
import edu.psu.sweng.kahindu.transform.LegacyTransformer;
import edu.psu.sweng.kahindu.transform.MedianFilterTransformer;
import edu.psu.sweng.kahindu.transform.MiddleContourTransformer;
import edu.psu.sweng.kahindu.transform.NegateTransformer;
import edu.psu.sweng.kahindu.transform.OpenTransformer;
import edu.psu.sweng.kahindu.transform.OuterContourTransformer;
import edu.psu.sweng.kahindu.transform.PowerTransformer;
import edu.psu.sweng.kahindu.transform.RNAHTransformer;
import edu.psu.sweng.kahindu.transform.RobertsTransformer;
import edu.psu.sweng.kahindu.transform.SaltAndPepperTransformer;
import edu.psu.sweng.kahindu.transform.UNAHTransformer;

public class ImageFrame extends JFrame {
    private static final long serialVersionUID = 3848669250991405715L;

    private final ImageComponent component;

    private KahinduImage image;

    public ImageFrame() {
        super("Kahindu Refactor - Team 2");
        File defaultImage = new File("gifs/baboon.GIF");
        try {
            this.image = new DefaultImageReader().read(defaultImage);
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
        lowPassBuilder.add("Average", new ConvolutionTransformer(LOW_PASS_AVERAGE));
        lowPassBuilder.add("Low-Pass 1", new ConvolutionTransformer(LOW_PASS_ONE));
        lowPassBuilder.add("Low-Pass 2", new ConvolutionTransformer(LOW_PASS_TWO));
        lowPassBuilder.add("Low-Pass 3", new ConvolutionTransformer(LOW_PASS_THREE));
        lowPassBuilder.add("Mean 3", new ConvolutionTransformer(MEAN3));
        lowPassBuilder.add("Mean 9", new ConvolutionTransformer(MEAN9));
        lowPassBuilder.add("Guassian 3x3", new ConvolutionTransformer(GUASSIAN3));
        lowPassBuilder.add("Guassian 7x7", new ConvolutionTransformer(GUASSIAN7));
        lowPassBuilder.add("Guassian 15x15", new ConvolutionTransformer(GUASSIAN15));
        lowPassBuilder.add("Guassian 31x31", new ConvolutionTransformer(GUASSIAN31));

        JMenuBuilder medianBuilder = new JMenuBuilder("Median", component);
        medianBuilder.add("Cross   (3x3)", new MedianFilterTransformer(3, MedianFilterTransformer.CROSS));
        medianBuilder.add("Square  (3x3)", new MedianFilterTransformer(3, MedianFilterTransformer.SQUARE));
        medianBuilder.add("Octagon (5x5)", new MedianFilterTransformer(5, MedianFilterTransformer.OCTAGON));
        medianBuilder.add("Square  (5x5)", new MedianFilterTransformer(5, MedianFilterTransformer.SQUARE));
        medianBuilder.add("Diamond (7x7)", new MedianFilterTransformer(7, MedianFilterTransformer.DIAMOND));
        medianBuilder.add("Cross   (7x7)", new MedianFilterTransformer(7, MedianFilterTransformer.CROSS));

        medianBuilder.add("Salt & Pepper (100)", new SaltAndPepperTransformer(100));
        medianBuilder.add("Salt & Pepper (1000)", new SaltAndPepperTransformer(1000));
        medianBuilder.add("Salt & Pepper (2000)", new SaltAndPepperTransformer(2000));
        medianBuilder.add("Salt & Pepper (4000)", new SaltAndPepperTransformer(4000));

        JMenuBuilder highPassBuilder = new JMenuBuilder("High-Pass", component);
        highPassBuilder.add("High Pass 1", new ConvolutionTransformer(HIGH_PASS_ONE));
        highPassBuilder.add("High Pass 2", new ConvolutionTransformer(HIGH_PASS_TWO));
        highPassBuilder.add("High Pass 3", new ConvolutionTransformer(HIGH_PASS_THREE));
        highPassBuilder.add("High Pass 4", new ConvolutionTransformer(HIGH_PASS_FOUR));
        highPassBuilder.add("High Pass 5", new ConvolutionTransformer(HIGH_PASS_FIVE));
        highPassBuilder.add("Shadow Mask (hp6)", new ConvolutionTransformer(SHADOW_MASK));

        JMenuBuilder edgeMenu = new JMenuBuilder("Edge", component);
        edgeMenu.add("Roberts 2", new RobertsTransformer());
        edgeMenu.add("Median Square (2x2)", new MedianFilterTransformer(2, MedianFilterTransformer.SQUARE));
        edgeMenu.add("Median Line (2x1)", new MedianFilterTransformer(3, MedianFilterTransformer.VERTICAL_LINE));
        edgeMenu.add("Median Line (1x2)", new MedianFilterTransformer(3, MedianFilterTransformer.HORIZONTAL_LINE));

        JMenuBuilder morphMenu = new JMenuBuilder("Morph", component);

        JMenuBuilder erodeMenu = new JMenuBuilder("Erode", component);
        erodeMenu.add("H", new ErodeTransformer(KH));
        erodeMenu.add("V", new ErodeTransformer(KV));
        erodeMenu.add("Square", new ErodeTransformer(KSQUARE));
        erodeMenu.add("Cross", new ErodeTransformer(KCROSS));
        morphMenu.addItem(erodeMenu);

        JMenuBuilder dilateMenu = new JMenuBuilder("Dilate", component);
        dilateMenu.add("H", new DilateTransformer(KH));
        dilateMenu.add("V", new DilateTransformer(KV));
        dilateMenu.add("Square", new DilateTransformer(KSQUARE));
        dilateMenu.add("Cross", new DilateTransformer(KCROSS));
        morphMenu.addItem(dilateMenu);

        JMenuBuilder openMenu = new JMenuBuilder("Open", component);
        openMenu.add("H", new OpenTransformer(KH));
        openMenu.add("V", new OpenTransformer(KV));
        openMenu.add("Square", new OpenTransformer(KSQUARE));
        openMenu.add("Cross", new OpenTransformer(KCROSS));
        morphMenu.addItem(openMenu);
        
        
        JMenuBuilder closeMenu = new JMenuBuilder("Close", component);
        closeMenu.add("H", new CloseTransformer(KH));
        closeMenu.add("V", new CloseTransformer(KV));
        closeMenu.add("Square", new CloseTransformer(KSQUARE));
        closeMenu.add("Cross", new CloseTransformer(KCROSS));
        morphMenu.addItem(closeMenu);
        
        JMenuBuilder innerMenu = new JMenuBuilder("Inner Contour", component);
        innerMenu.add("H", new InnerContourTransformer(KH));
        innerMenu.add("V", new InnerContourTransformer(KV));
        innerMenu.add("Square", new InnerContourTransformer(KSQUARE));
        innerMenu.add("Cross", new InnerContourTransformer(KCROSS));
        morphMenu.addItem(innerMenu);
        
        JMenuBuilder outerMenu = new JMenuBuilder("Outer Contour", component);
        outerMenu.add("H", new OuterContourTransformer(KH));
        outerMenu.add("V", new OuterContourTransformer(KV));
        outerMenu.add("Square", new OuterContourTransformer(KSQUARE));
        outerMenu.add("Cross", new OuterContourTransformer(KCROSS));
        morphMenu.addItem(outerMenu);
        
        JMenuBuilder middleMenu = new JMenuBuilder("Middle Contour", component);
        middleMenu.add("H", new MiddleContourTransformer(KH));
        middleMenu.add("V", new MiddleContourTransformer(KV));
        middleMenu.add("Square", new MiddleContourTransformer(KSQUARE));
        middleMenu.add("Cross", new MiddleContourTransformer(KCROSS));
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
        saveBuilder.add("Save PPM", new AdvancedImageWriter("PNM"));

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
        histograms.add("Show Histograms", new LegacyTransformer("histogram"));

        histograms.add("Uniform (Non-Adaptive)", new UNAHTransformer());
        histograms.add("Exponential (Non-Adaptive)", new ENAHTransformer());
        histograms.add("Raleigh (Non-Adaptive)", new RNAHTransformer());

        builder.addItem(histograms);
        return builder.build();
    }

}