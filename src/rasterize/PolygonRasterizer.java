package rasterize;

import model.Line;
import model.Point;
import model.Polygon;

import java.awt.image.BufferedImage;

public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Polygon polygon) {
        for (int i = 0; i < polygon.getSize(); i++) {
            int indexA = i;
            int indexB = indexA + 1;

            Point point1 = polygon.getPoint(indexA);
            if (indexB == polygon.getSize()) {
                indexB = 0;
            }

            Point point2 = polygon.getPoint(indexB);

            this.lineRasterizer.drawLine(new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY()));
            if (polygon.getSize() == 2) {
                break;
            }
        }
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

}
