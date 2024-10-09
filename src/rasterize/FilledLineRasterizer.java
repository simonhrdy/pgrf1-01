package rasterize;

import model.Line;
import view.Panel;

import java.awt.image.BufferedImage;

public class FilledLineRasterizer extends LineRasterizer {
    private final Panel panel;

    public FilledLineRasterizer(BufferedImage raster, Panel panel) {
        super(raster);
        this.panel = panel;
    }

    public FilledLineRasterizer(BufferedImage raster, int color, Panel panel) {
        super(raster, color);
        this.panel = panel;
    }

    @Override
    public void drawLine(Line line, boolean isShift) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

        if (isShift) {
            // Výpočet rozdílů mezi souřadnicemi
            float dx = Math.abs(x2 - x1);
            float dy = Math.abs(y2 - y1);
            float threshold = 30; // Nastavíme práh pro rozhodování

            // Pokud je rozdíl v y menší než práh, úsečka je horizontální
            if (dy < threshold) {
                y2 = y1;
            }
            // Pokud je rozdíl v x menší než práh, úsečka je vertikální
            else if (dx < threshold) {
                x2 = x1;
            }
            // Pokud je rozdíl v obou osách větší, úsečka bude diagonální (45°)
            else {
                if (dx > dy) {
                    y2 = y1 + (int) Math.signum(y2 - y1) * (int) dx;
                } else {
                    x2 = x1 + (int) Math.signum(x2 - x1) * (int) dy;
                }
            }
        }

        // Pokud je x1 větší než x2, prohodíme body úsečky (abychom vždy kreslili zleva doprava)
        if (x1 > x2) {
            int tempX = x1;
            int tempY = y1;
            x1 = x2;
            y1 = y2;
            x2 = tempX;
            y2 = tempY;
        }

        // Vypočítáme směrnici k a průsečík y -> q
        float k = (x1 == x2) ? 0 : (y2 - y1) / (float) (x2 - x1); // Ošetření pro vertikální úsečky (x1 == x2)
        float q = y1 - k * x1;

        // Vertikální úsečka
        if (x1 == x2) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                if (panel.inWindow(x1, y)) {
                    raster.setRGB(x1, y, color);
                }
            }
        }
        // Pokud směrnice (k) je v intervalu [-1, 1], úsečka není strmá a řídící osa je x
        else if (Math.abs(k) <= 1) {
            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                if (panel.inWindow(x, Math.round(y))) {
                    raster.setRGB(x, Math.round(y), color);
                }
            }
        }
        // Pokud je směrnice k větší než 1 nebo menší než -1, je úsečka strmá, řídící osa bude y
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
                if (panel.inWindow(Math.round(x), y)) {
                    raster.setRGB(Math.round(x), y, color);
                }
            }
        }
    }

}
