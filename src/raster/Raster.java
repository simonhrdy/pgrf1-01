package raster;

public interface Raster {
    void setPixel(int x, int y, int value);
    int getPixel(int x, int y);
    int getWidth();
    int getHeight();
    void clear();
    boolean inRange(int x, int y);
}

