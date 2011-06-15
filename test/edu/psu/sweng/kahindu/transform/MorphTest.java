package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.GIFReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import edu.psu.sweng.kahindu.matrix.MatrixDictionary;
import gui.TopFrame;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MorphTest {
    private TopFrame topFrame;
    private KahinduImage kahinduImage;

    @Before
    public void setUp() throws IOException {
        topFrame = new TopFrame("");
        topFrame.openGif("gifs/baboon.gif");

        ImageReader reader = new GIFReader();
        kahinduImage = reader.read(new File("gifs/baboon.gif"));

        topFrame.gray();
    }

    @Test
    public void testErodeKH() {
        topFrame.erode(TopFrame.kh);

        Transformer<KahinduImage> t = new ErodeTransform(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }

    @Test
    public void testErodeKV() {
        topFrame.erode(TopFrame.kv);

        Transformer<KahinduImage> t = new ErodeTransform(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }

    @Test
    public void testErodeKSquare() {
        topFrame.erode(TopFrame.kSquare);

        Transformer<KahinduImage> t = new ErodeTransform(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }

    @Test
    public void testErodeKCross() {
        topFrame.erode(TopFrame.kCross);

        Transformer<KahinduImage> t = new ErodeTransform(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testDilateKH() {
        topFrame.dilate(TopFrame.kh);

        Transformer<KahinduImage> t = new DilateTransform(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testDilateKV() {
        topFrame.dilate(TopFrame.kv);

        Transformer<KahinduImage> t = new DilateTransform(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testDilateKSquare() {
        topFrame.dilate(TopFrame.kSquare);

        Transformer<KahinduImage> t = new DilateTransform(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testDilateKCross() {
        topFrame.dilate(TopFrame.kCross);

        Transformer<KahinduImage> t = new DilateTransform(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testOpenKH() {
        topFrame.open(TopFrame.kh);

        Transformer<KahinduImage> t = new OpenTransform(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOpenKV() {
        topFrame.open(TopFrame.kv);

        Transformer<KahinduImage> t = new OpenTransform(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOpenKSquare() {
        topFrame.open(TopFrame.kSquare);

        Transformer<KahinduImage> t = new OpenTransform(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOpenKCross() {
        topFrame.open(TopFrame.kCross);

        Transformer<KahinduImage> t = new OpenTransform(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testCloseH() {
        topFrame.close(TopFrame.kh);

        Transformer<KahinduImage> t = new CloseTransform(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testCloseV() {
        topFrame.close(TopFrame.kv);

        Transformer<KahinduImage> t = new CloseTransform(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testCloseKSquare() {
        topFrame.close(TopFrame.kSquare);

        Transformer<KahinduImage> t = new CloseTransform(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testCloseKCross() {
        topFrame.close(TopFrame.kCross);

        Transformer<KahinduImage> t = new CloseTransform(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }


    @Test
    public void testInnerH() {
        topFrame.insideContour(TopFrame.kh);
        Transformer<KahinduImage> t = new InnerContour(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testInnerV() {
        topFrame.insideContour(TopFrame.kv);
        Transformer<KahinduImage> t = new InnerContour(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testInnerKSquare() {
        topFrame.insideContour(TopFrame.kSquare);
        Transformer<KahinduImage> t = new InnerContour(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testInnerKCross() {
        topFrame.insideContour(TopFrame.kCross);
        Transformer<KahinduImage> t = new InnerContour(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOuterH() {
        topFrame.outsideContour(TopFrame.kh);
        Transformer<KahinduImage> t = new OuterContour(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOuterV() {
        topFrame.outsideContour(TopFrame.kv);
        Transformer<KahinduImage> t = new OuterContour(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOuterKSquare() {
        topFrame.outsideContour(TopFrame.kSquare);
        Transformer<KahinduImage> t = new OuterContour(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testOuterKCross() {
        topFrame.outsideContour(TopFrame.kCross);
        Transformer<KahinduImage> t = new OuterContour(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    
    @Test
    public void testMiddleKH() {
        topFrame.middleContour(TopFrame.kh);
        Transformer<KahinduImage> t = new MiddleContour(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testMiddleKV() {
        topFrame.middleContour(TopFrame.kv);
        Transformer<KahinduImage> t = new MiddleContour(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testMiddleKSquare() {
        topFrame.middleContour(TopFrame.kSquare);
        Transformer<KahinduImage> t = new MiddleContour(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testMiddleKCross() {
        topFrame.middleContour(TopFrame.kCross);
        Transformer<KahinduImage> t = new MiddleContour(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
















}
