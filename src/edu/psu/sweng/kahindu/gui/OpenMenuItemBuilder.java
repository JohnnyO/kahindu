package edu.psu.sweng.kahindu.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.io.ImageReader;

public class OpenMenuItemBuilder extends AbstractMenuItemBuilder
{
    private final ImageReader reader;
    private final ImageComponent target;

    /*
     * PPM Reader Image Reader Short GZ Reader
     * 
     * GIF Writer PPM Writer ImagerWriter Short GZ Writer
     */

    public OpenMenuItemBuilder(ImageReader reader, ImageComponent target)
    {
        this.reader = reader;
        this.target = target;
    }

    @Override
    public JMenuItem buildMenuItem()
    {
        Action a = new AbstractAction(this.name)
        {

            private static final long serialVersionUID = 7157033217054676920L;

            {
                this.putValue(ACCELERATOR_KEY,
                        OpenMenuItemBuilder.this.shortcutKey);
            }

            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    File file = getFileFromChooser(target);
                    // reader.setFile(new File("gifs/baboon.gif"));
                    if (file != null)
                    {
                        //reader.setFile(file);
                        KahinduImage result = reader.read(file);
                        target.updateImage(result);
                    }
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        };

        return new JMenuItem(a);

    }

}
