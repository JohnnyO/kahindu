package edu.psu.sweng.kahindu.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

public class AWTImageAdapter implements KahinduImage
{

    private BufferedImage bufferedImage;

    public AWTImageAdapter(BufferedImage image)
    {
        this.bufferedImage = image;
    }

    public AWTImageAdapter(KahinduImage kImage)
    {
        Image image = getImage(kImage);
        bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bufferedImage.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();

        // this.bufferedImage = getImage(image);
    }
    
    public AWTImageAdapter(RenderedImage img)
    {
        if (img instanceof BufferedImage)
        {
            this.bufferedImage = (BufferedImage) img;
        }
        
        ColorModel cm = img.getColorModel();
        int width = img.getWidth();
        int height = img.getHeight();
        WritableRaster raster = cm
                .createCompatibleWritableRaster(width, height);
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        Hashtable<String, Object> properties = new Hashtable<String, Object>();
        String[] keys = img.getPropertyNames();
        if (keys != null)
        {
            for (int i = 0; i < keys.length; i++)
            {
                properties.put(keys[i], img.getProperty(keys[i]));
            }
        }
        BufferedImage result = new BufferedImage(cm, raster,
                isAlphaPremultiplied, properties);
        img.copyData(raster);
        
        this.bufferedImage = result;
    }
    
    public BufferedImage getBufferedImage()
    {
        return this.bufferedImage;
    }

    public Image getImage(KahinduImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        int[] pixels = new int[width * height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
            {
                pixels[x + y * width] = image.getColor(x, y).getRGB();
            }
        MemoryImageSource source = new MemoryImageSource(image.getWidth(),
                image.getHeight(), pixels, 0, image.getWidth());

        return Toolkit.getDefaultToolkit().createImage(source);
    }
    
    @Override
    public int getWidth()
    {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight()
    {
        return bufferedImage.getHeight();
    }

    @Override
    public Color getColor(int x, int y)
    {
        return new Color(bufferedImage.getRGB(x, y));
    }

}
