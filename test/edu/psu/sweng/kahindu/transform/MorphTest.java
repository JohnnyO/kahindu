package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.DefaultImageReader;
import edu.psu.sweng.kahindu.image.io.ImageReader;
import edu.psu.sweng.kahindu.matrix.MatrixDictionary;
import edu.psu.sweng.kahindu.transform.TestingUtils;

import gui.TopFrame;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class MorphTest {
    private TopFrame topFrame;
    private KahinduImage kahinduImage;

    @Before
    public void setUp() throws IOException {
        topFrame = new TopFrame("");
        topFrame.openGif("gifs/baboon.gif");

        ImageReader reader = new DefaultImageReader();
        kahinduImage = reader.read(new File("gifs/baboon.gif"));

        topFrame.gray();
    }

    @Test
    public void testErodeKH() {
        topFrame.erode(TopFrame.kh);

        Transformer<KahinduImage> t = new ErodeTransformer(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }

    @Test
    public void testErodeKV() {
        topFrame.erode(TopFrame.kv);

        Transformer<KahinduImage> t = new ErodeTransformer(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }

    @Test
    public void testErodeKSquare() {
        topFrame.erode(TopFrame.kSquare);

        Transformer<KahinduImage> t = new ErodeTransformer(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));

    }

    @Test
    public void testErodeKCross() {
        topFrame.erode(TopFrame.kCross);

        Transformer<KahinduImage> t = new ErodeTransformer(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testDilateKH() {
        topFrame.dilate(TopFrame.kh);

        Transformer<KahinduImage> t = new DilateTransformer(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testDilateKV() {
        topFrame.dilate(TopFrame.kv);

        Transformer<KahinduImage> t = new DilateTransformer(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testDilateKSquare() {
        topFrame.dilate(TopFrame.kSquare);

        Transformer<KahinduImage> t = new DilateTransformer(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testDilateKCross() {
        topFrame.dilate(TopFrame.kCross);

        Transformer<KahinduImage> t = new DilateTransformer(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testOpenKH() {
        topFrame.open(TopFrame.kh);

        Transformer<KahinduImage> t = new OpenTransformer(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOpenKV() {
        topFrame.open(TopFrame.kv);

        Transformer<KahinduImage> t = new OpenTransformer(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOpenKSquare() {
        topFrame.open(TopFrame.kSquare);

        Transformer<KahinduImage> t = new OpenTransformer(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOpenKCross() {
        topFrame.open(TopFrame.kCross);

        Transformer<KahinduImage> t = new OpenTransformer(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testCloseH() {
        topFrame.close(TopFrame.kh);

        Transformer<KahinduImage> t = new CloseTransformer(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testCloseV() {
        topFrame.close(TopFrame.kv);

        Transformer<KahinduImage> t = new CloseTransformer(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testCloseKSquare() {
        topFrame.close(TopFrame.kSquare);

        Transformer<KahinduImage> t = new CloseTransformer(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testCloseKCross() {
        topFrame.close(TopFrame.kCross);

        Transformer<KahinduImage> t = new CloseTransformer(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }


    @Test
    public void testInnerH() {
        topFrame.insideContour(TopFrame.kh);
        Transformer<KahinduImage> t = new InnerContourTransformer(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testInnerV() {
        topFrame.insideContour(TopFrame.kv);
        Transformer<KahinduImage> t = new InnerContourTransformer(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testInnerKSquare() {
        topFrame.insideContour(TopFrame.kSquare);
        Transformer<KahinduImage> t = new InnerContourTransformer(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testInnerKCross() {
        topFrame.insideContour(TopFrame.kCross);
        Transformer<KahinduImage> t = new InnerContourTransformer(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOuterH() {
        topFrame.outsideContour(TopFrame.kh);
        Transformer<KahinduImage> t = new OuterContourTransformer(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOuterV() {
        topFrame.outsideContour(TopFrame.kv);
        Transformer<KahinduImage> t = new OuterContourTransformer(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testOuterKSquare() {
        topFrame.outsideContour(TopFrame.kSquare);
        Transformer<KahinduImage> t = new OuterContourTransformer(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    @Test
    public void testOuterKCross() {
        topFrame.outsideContour(TopFrame.kCross);
        Transformer<KahinduImage> t = new OuterContourTransformer(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }

    
    @Test
    public void testMiddleKH() {
        topFrame.middleContour(TopFrame.kh);
        Transformer<KahinduImage> t = new MiddleContourTransformer(MatrixDictionary.KH);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testMiddleKV() {
        topFrame.middleContour(TopFrame.kv);
        Transformer<KahinduImage> t = new MiddleContourTransformer(MatrixDictionary.KV);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testMiddleKSquare() {
        topFrame.middleContour(TopFrame.kSquare);
        Transformer<KahinduImage> t = new MiddleContourTransformer(MatrixDictionary.KSQUARE);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
    
    @Test
    public void testMiddleKCross() {
        topFrame.middleContour(TopFrame.kCross);
        Transformer<KahinduImage> t = new MiddleContourTransformer(MatrixDictionary.KCROSS);
        TestingUtils.compareImages(topFrame, t.transform(kahinduImage));
    }
















}
