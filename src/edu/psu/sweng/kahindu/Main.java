package edu.psu.sweng.kahindu;

import javax.swing.JFrame;

import edu.psu.sweng.kahindu.gui.ImageFrame;

/**
 * Entry point into the Kahindu application.
 * 
 * 
 */
public final class Main {

    /**
     * default private constructor. Should not be instantiated.
     */
    private Main() {
    }

    /**
     * Main method.
     * 
     * @param args
     *            not used
     */
    public static void main(final String[] args) {
        ImageFrame iFrame = new ImageFrame();
        iFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iFrame.setVisible(true);
    }

}
