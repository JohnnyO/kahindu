/**
 * 
 */
package edu.psu.sweng.kahindu.image.io;

import java.awt.Color;
import java.awt.Dimension;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.psu.sweng.kahindu.image.KahinduImage;

public class PPMReader implements ImageReader
{
    public PPMReader()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.psu.sweng.kahindu.image.io.ImageReader#read()
     */
    @Override
    public KahinduImage read(File file) throws IOException
    {
        return getImage(file);
    }
    
    private KahinduImage getImage(File file) throws IOException
    {
        InputStream in = new FileInputStream(file);
        final Dimension d = readHeaderInfo(in);
        
        final short [][] r = new short[d.width][d.height];
        final short [][] g = new short[d.width][d.height];
        final short [][] b = new short[d.width][d.height];
        byte buf[] = new byte[d.width*d.height*3];
        int offset = 0;
        int count = buf.length;
        int n = 0;
        try
        {
            n = in.read(buf, offset, count);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        System.out.println("Read in " + n + " bytes");
        
        try
        {
            int j = 0;
            for (int col = 0; col < d.height; col++)
                for (int row = 0; row < d.width; row++)
                {
                    r[row][col] = unsignedShort(buf[j++]);
                    g[row][col] = unsignedShort(buf[j++]);
                    b[row][col] = unsignedShort(buf[j++]);
                }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return new KahinduImage()
        {

            @Override
            public int getWidth()
            {
                return d.width;
            }

            @Override
            public int getHeight()
            {
                return d.height;
            }

            @Override
            public Color getColor(int x, int y)
            {
                return new Color(r[x][y], g[x][y], b[x][y]);
            }

        };
    }

    private Dimension readHeaderInfo(InputStream in) throws IOException
    {
        char c1, c2;

        c1 = (char) readByte(in);
        c2 = (char) readByte(in);

        if (c1 != 'P')
        {
            throw new IOException("not a PPM file");
        }
        if (c2 != '6')
        {
            throw new IOException("not a PPM file");
        }
        
        int width = readInt(in);
        int height = readInt(in);
        System.out.println("ReadPPM: " + width + "x" + height);

        // Read maximum value of each color, and ignore it.
        // In PPM_RAW we know r,g,b use full range (0-255).
        int color = readInt(in);
        System.out.println("Color: " + color);
        
        return new Dimension(width, height);
    }

    private static int readInt(InputStream in) throws IOException
    {
        char c;
        int i;

        c = readNonwhiteChar(in);
        if ((c < '0') || (c > '9'))
        {
            throw new IOException(
                    "Invalid integer when reading PPM image file.");
        }

        i = 0;
        do
        {
            i = i * 10 + c - '0';
            c = readChar(in);
        } while ((c >= '0') && (c <= '9'));

        return (i);
    }

    private static char readNonwhiteChar(InputStream in) throws IOException
    {
        char c;

        do
        {
            c = readChar(in);
        } while ((c == ' ') || (c == '\t') || (c == '\n') || (c == '\r'));

        return c;
    }

    private static char readChar(InputStream in) throws IOException
    {
        char c;

        c = (char) readByte(in);
        if (c == '#')
        {
            do
            {
                c = (char) readByte(in);
            } while ((c != '\n') && (c != '\r'));
        }

        return (c);
    }

    private static int readByte(InputStream in) throws IOException
    {
        int b = in.read();

        // if end of file
        if (b == -1)
        {
            throw new EOFException();
        }
        return b;
    }
    
    public static short unsignedShort(byte b)
    {
        if (b < 0)
        {
            return (short) (256+b);
        }
        
        return (short)b;
    }

}
