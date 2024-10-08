package rasterize;

import model.Line;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRasterizerGraphics extends LineRasterizer{

    public LineRasterizerGraphics(BufferedImage raster) {
        super(raster);
    }

    public LineRasterizerGraphics(BufferedImage raster, int color) {
        super(raster, color);
    }

    @Override
    public void drawLine(Line line) {
        Graphics g = raster.getGraphics();
        g.setColor(new Color(color));
        g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }
}
