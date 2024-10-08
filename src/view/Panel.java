package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends JPanel {

    private final BufferedImage raster;
    private int color;

    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));

        raster = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(raster, 0, 0, this);
    }

    public BufferedImage getRaster() {
        return raster;

    }

    public void clear() {
        Graphics g = raster.getGraphics();
        g.setColor(new Color(0x2f2f2f));
        g.clearRect(0, 0, raster.getWidth() - 1, raster.getHeight() - 1);
    }


    public boolean inWindow(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }
}
