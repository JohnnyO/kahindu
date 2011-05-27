package edu.psu.sweng.kahindu;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.psu.sweng.kahindu.gui.ImageFrame;
import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import edu.psu.sweng.kahindu.io.NegateTransformer;

public class Main {
	public static void main(String [] args) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new File("gifs/baboon.GIF"));
		KahinduImage kImage = new AWTImageAdapter(bufferedImage);
		
		ImageFrame iFrame = new ImageFrame(kImage);
		iFrame.setVisible(true);
		
		
	}

}
