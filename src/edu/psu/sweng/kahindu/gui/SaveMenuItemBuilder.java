package edu.psu.sweng.kahindu.gui;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import edu.psu.sweng.kahindu.image.io.ImageWriter;

public class SaveMenuItemBuilder extends AbstractMenuItemBuilder
{
    private final ImageWriter writer;
    private final ImageComponent target;

    /*
     * GIF Writer
     */

    public SaveMenuItemBuilder(ImageWriter writer, ImageComponent target)
    {
        this.writer = writer;
        this.target = target;
    }

    @Override
    public JMenuItem buildMenuItem()
    {
        Action a = new AbstractAction(this.name)
        {

            private static final long serialVersionUID = 912556045328608649L;

            {
                this.putValue(ACCELERATOR_KEY,
                        SaveMenuItemBuilder.this.shortcutKey);
            }

            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    File file = saveFileDialog(target);
                    if (file != null)
                    {
                        writer.write(target.getImage(), file);
                    }
                }
                catch (Exception ioe)
                {
                    ioe.printStackTrace();
                }
            }
        };

        return new JMenuItem(a);

    }

}
