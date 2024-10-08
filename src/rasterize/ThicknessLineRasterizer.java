package rasterize;

import model.Line;
import view.Panel;  // Nezapomeň na import třídy Panel

import java.awt.image.BufferedImage;

public class ThicknessLineRasterizer extends LineRasterizer {
    private final int thickness = 8; // Výchozí tloušťka čáry
    private final Panel panel; // Přidáme proměnnou pro Panel

    public ThicknessLineRasterizer(BufferedImage raster, Panel panel) {
        super(raster);
        this.panel = panel; // Uložení instance Panel
    }

    public ThicknessLineRasterizer(BufferedImage raster, int color, Panel panel) {
        super(raster, color);
        this.panel = panel; // Uložení instance Panel
    }

    @Override
    public void drawLine(Line line) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

        float k = (x1 == x2) ? 0 : (y2 - y1) / (float) (x2 - x1); // Výpočet směrnice k
        float q = y1 - k * x1;

        // Řídící osa je x
        if (Math.abs(k) <= 1) {
            if (x1 > x2) {  // Ošetření pro čáry zleva doprava
                int tempX = x1;
                int tempY = y1;
                x1 = x2;
                y1 = y2;
                x2 = tempX;
                y2 = tempY;
            }
            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                drawThickPoint(x, Math.round(y), thickness);
            }
        }
        // Řídící osa je y
        else {
            if (y1 > y2) {  // Ošetření pro čáry shora dolů
                int tempX = x1;
                int tempY = y1;
                x1 = x2;
                y1 = y2;
                x2 = tempX;
                y2 = tempY;
            }
            for (int y = y1; y <= y2; y++) {
                float x = (y - q) / k;
                drawThickPoint(Math.round(x), y, thickness);
            }
        }
    }

    // Vykreslí tlustý bod (čtvercová oblast kolem daného bodu)
    private void drawThickPoint(int x, int y, int thickness) {
        int halfThickness = thickness / 2;
        for (int dx = -halfThickness; dx <= halfThickness; dx++) {
            for (int dy = -halfThickness; dy <= halfThickness; dy++) {
                int drawX = x + dx;
                int drawY = y + dy;
                // Použijeme metodu inWindow z instance panelu
                if (panel.inWindow(drawX, drawY)) {
                    raster.setRGB(drawX, drawY, color);
                }
            }
        }
    }
}


