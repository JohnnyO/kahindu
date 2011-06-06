package edu.psu.sweng.kahindu.image;

import java.awt.Color;

public class RawImageAdapter implements KahinduImage
{
    private final short [][] red;
    private final short [][] green;
    private final short [][] blue;
    
    private final int width;
    private final int height;
    
    public RawImageAdapter(int width, int height, short [][] red, short [][] green, short [][] blue)
    {
        this.width = width;
        this.height = height;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    @Override
    public Color getColor(int x, int y)
    {
        return new Color(red[x][y], green[x][y], blue[x][y]);
    }
    
}
