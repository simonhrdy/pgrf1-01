package controller;

import model.Line;
import model.Point;
import rasterize.LineRasterizer;
import rasterize.FilledLineRasterizer;
import rasterize.ThicknessLineRasterizer;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller2D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    private Point startPoint;
    private Point endPoint;
    private boolean isShift = false;

    public Controller2D(Panel panel) {
        this.panel = panel;
        panel.getRaster().setRGB(panel.getX(),panel.getY(), 0x0000ff);
        panel.repaint();

        lineRasterizer = new FilledLineRasterizer(panel.getRaster(), panel);

        initListener();
        panel.repaint();
    }

    private void initListener(){
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = new Point(e.getX(), e.getY());
                if (!panel.inWindow(startPoint.x, startPoint.y)) {
                    return;
                }
            }
        });


        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = new Point(e.getX(), e.getY());
                if (!panel.inWindow(endPoint.x, endPoint.y)) {
                    return;
                }
                panel.clear();
                Line line = new Line(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                lineRasterizer.drawLine(line,isShift);
                panel.repaint();
            }
        });


        panel.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch(e.getKeyCode()){
                            case KeyEvent.VK_S:
                                clear();
                                lineRasterizer = new FilledLineRasterizer(panel.getRaster(), panel);
                                break;
                            case KeyEvent.VK_W:
                                clear();
                                lineRasterizer = new ThicknessLineRasterizer(panel.getRaster(), panel);
                                break;
                            case KeyEvent.VK_C:
                                clear();
                                break;
                            case KeyEvent.VK_SHIFT:
                                isShift = true;
                                break;
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                            isShift = false;
                        }
                    }
                }
        );
    }

    private void clear(){
        panel.clear();
        panel.repaint();
    }

}
