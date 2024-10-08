package rasterize;


import model.Line;

import java.awt.image.BufferedImage;

public abstract class LineRasterizer {
    protected final BufferedImage raster;
    protected int color;

    public LineRasterizer(BufferedImage raster) {
        this.raster = raster;
        this.color = 0xff0000;
    }

    public LineRasterizer(BufferedImage raster, int color) {
        this.raster = raster;
        this.color = color;
    }

    public void drawLine(Line line) {

    }
}
