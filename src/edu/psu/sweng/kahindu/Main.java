package edu.psu.sweng.kahindu;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import edu.psu.sweng.kahindu.gui.ImageFrame;
import edu.psu.sweng.kahindu.image.AWTImageAdapter;
import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.GIFReader;

public class Main {
	public static void main(String [] args) throws IOException {
		
		ImageFrame iFrame = new ImageFrame();
		iFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iFrame.setVisible(true);
		
		
	}

}
