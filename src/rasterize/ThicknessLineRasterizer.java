package rasterize;

import model.Line;
import raster.Raster;

public class ThicknessLineRasterizer extends LineRasterizer {
    private final int thickness = 8;

    public ThicknessLineRasterizer(Raster raster) {
        super(raster);
    }

    public ThicknessLineRasterizer(Raster raster, int color) {
        super(raster, color);
    }

    @Override
    public void drawLine(Line line) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

        // Kontrola pro vertikální úsečku
        if (x1 == x2) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                if (raster.inRange(x1, y)) {
                    drawThickPoint(x1, y, thickness); // Vykreslení tlustého bodu pro vertikální úsečku
                }
            }
            // Vykreslení zaobleného konce na vertikální úsečce
            drawEndCap(x1, y1);
            drawEndCap(x2, y2);
            return; // Ukončíme funkci, protože jsme už vykreslili úsečku
        }

        // Výpočet směrnice k
        float k = (x1 == x2) ? 0 : (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;

        // Řídící osa je x
        if (Math.abs(k) <= 1) {
            if (x1 > x2) {
                int tempX = x1;
                int tempY = y1;
                x1 = x2;
                y1 = y2;
                x2 = tempX;
                y2 = tempY;
            }
            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                drawThickPoint(x, Math.round(y), thickness); // Tloušťka je konstantní
            }
        }
        // Řídící osa je y
        else {
            if (y1 > y2) {
                int tempX = x1;
                int tempY = y1;
                x1 = x2;
                y1 = y2;
                x2 = tempX;
                y2 = tempY;
            }
            for (int y = y1; y <= y2; y++) {
                float x = (y - q) / k;
                drawThickPoint(Math.round(x), y, thickness); // Tloušťka je konstantní
            }
        }

        // Vykreslení zaoblených konců
        drawEndCap(x1, y1);
        drawEndCap(x2, y2);
    }

    // Vykreslí tlustý bod
    private void drawThickPoint(int x, int y, int thickness) {
        int halfThickness = thickness / 2;
        for (int dx = -halfThickness; dx <= halfThickness; dx++) {
            for (int dy = -halfThickness; dy <= halfThickness; dy++) {
                if (Math.sqrt(dx * dx + dy * dy) <= halfThickness) { // Kruhový tvar
                    int drawX = x + dx;
                    int drawY = y + dy;
                    if (raster.inRange(drawX, drawY)) {
                        raster.setPixel(drawX, drawY, color);
                    }
                }
            }
        }
    }

    // Vykreslí zaoblené konce
    private void drawEndCap(int x, int y) {
        int radius = thickness / 2;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (Math.sqrt(dx * dx + dy * dy) <= radius) { // Kruhový tvar
                    int drawX = x + dx;
                    int drawY = y + dy;
                    if (raster.inRange(drawX, drawY)) {
                        raster.setPixel(drawX, drawY, color);
                    }
                }
            }
        }
    }



}


