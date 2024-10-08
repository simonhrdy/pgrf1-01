package controller;

import model.Line;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.LineRasterizerTrivial;
import rasterize.ThicknessLineRasterizer;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller2D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    private int startX, startY;
    private int endX, endY;

    public Controller2D(Panel panel) {
        this.panel = panel;
        panel.getRaster().setRGB(panel.getX(),panel.getY(), 0x0000ff);
        panel.repaint();

        lineRasterizer = new LineRasterizerTrivial(panel.getRaster(), panel);
        startX = panel.getWidth() / 2;
        startY = panel.getHeight() / 2;


        initListener();
        panel.repaint();
    }

    private void initListener(){
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();
                endX = e.getX();
                endY = e.getY();
                Line line = new Line(startX, startY, endX, endY);
                lineRasterizer.drawLine(line);
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
                                lineRasterizer = new LineRasterizerTrivial(panel.getRaster(), panel);
                                break;
                            case KeyEvent.VK_W:
                                clear();
                                lineRasterizer = new ThicknessLineRasterizer(panel.getRaster(), panel);
                                break;
                            case KeyEvent.VK_C:
                                clear();
                                break;
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
