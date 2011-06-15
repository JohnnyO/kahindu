package edu.psu.sweng.kahindu.image;

import java.awt.Color;

public abstract class DefaultImageDecorator implements KahinduImage {

    private final KahinduImage source;

    public DefaultImageDecorator(final KahinduImage source) {
        this.source = source;
    }

    @Override
    public int getWidth() {
        return source.getWidth();
    }

    @Override
    public int getHeight() {
        return source.getHeight();
    }

    @Override
    public abstract Color getColor(int x, int y);

}
