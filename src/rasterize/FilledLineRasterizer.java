package rasterize;

import model.Line;
import raster.Raster;

public class FilledLineRasterizer extends LineRasterizer {

    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }

    public FilledLineRasterizer(Raster raster, int color) {
        super(raster, color);
    }

    @Override
    public void drawLine(Line line) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

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
                    raster.setPixel(x1, y, color);
            }
        }
        // Pokud směrnice (k) je v intervalu [-1, 1], úsečka není strmá a řídící osa je x
        else if (Math.abs(k) <= 1) {
            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                raster.setPixel(x, Math.round(y), color);
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
                    raster.setPixel(Math.round(x), y, color);
            }
        }
    }

}
