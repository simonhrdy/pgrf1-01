package controller;

import model.Line;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.FilledLineRasterizer;
import rasterize.PolygonRasterizer;
import rasterize.ThicknessLineRasterizer;
import view.Panel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller2D {
    private final Panel panel;
    private PolygonRasterizer polygonRasterizer;
    private LineRasterizer lineRasterizer;
    private Point startPoint;
    private Point endPoint;
    private boolean isShift = false;
    private boolean isPolygon = false;
    private boolean isEditMode = false;
    private Polygon polygon;

    public Controller2D(Panel panel) {
        this.panel = panel;
        panel.getRaster().setPixel(panel.getX(),panel.getY(), 0x0000ff);
        panel.repaint();

        lineRasterizer = new FilledLineRasterizer(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);
        polygon = new Polygon();

        initListener();
        panel.repaint();
    }

    private void initListener(){
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    startPoint = new Point(e.getX(), e.getY());
                    if(isEditMode){
                        showDialog("Edit mod, pust E pro přidávání nových bodu");
                    }
                    if(isPolygon && !isEditMode){
                        polygon.addPoint(startPoint);
                        panel.clear();
                        polygonRasterizer.rasterize(polygon);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if(isEditMode){
                        startPoint = new Point(e.getX(), e.getY());
                        Point closestPoint = polygon.getClosestPoint(startPoint);

                        if(closestPoint != null){
                            closestPoint.setX(startPoint.getX());
                            closestPoint.setY(startPoint.getY());
                        }
                        System.out.println(closestPoint);

                        panel.repaint();
                        //return;
                        panel.clear();
                        polygonRasterizer.rasterize(polygon);
                    }

                }
                panel.repaint();
            }
        });


        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = new Point(e.getX(), e.getY());
                if(e.getButton() != MouseEvent.BUTTON3){
                    panel.clear();
                }

                if(isPolygon && !isEditMode){
                    polygonRasterizer.setLineRasterizer(new FilledLineRasterizer(panel.getRaster()));

                    if (polygon.getSize() <= 2) {
                        polygon.addPoint(endPoint);
                    } else {
                        polygon.deleteLastElement();
                        polygon.addPoint(endPoint);
                    }
                    polygonRasterizer.rasterize(polygon);

                } else if (!isPolygon) {
                    if (isShift) {
                        float dy = Math.abs(endPoint.getY() - startPoint.getY());
                        float dx = Math.abs(endPoint.getX() - startPoint.getX());

                        if (dy < 20) {
                            endPoint.y = startPoint.y;
                        } else if (dx < 20) { // Zarovnání svisle
                            endPoint.x = startPoint.x;
                        } else {    //Řeší diagonálu
                            if (endPoint.y < startPoint.y) {
                                endPoint.y -= Math.round(dx - dy);
                            } else {
                                endPoint.y += Math.round(dx - dy);
                            }
                        }
                    }
                    Line line = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
                    lineRasterizer.drawLine(line);
                }
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
                                lineRasterizer = new FilledLineRasterizer(panel.getRaster());
                                showDialog("Režim: úsečka");
                                break;
                            case KeyEvent.VK_W:
                                clear();
                                lineRasterizer = new ThicknessLineRasterizer(panel.getRaster());
                                showDialog("Režim: tlustá úsečka");
                                break;
                            case KeyEvent.VK_C:
                                clear();
                                showDialog("Grid byl vymazán");
                                break;
                            case KeyEvent.VK_P:
                                if(polygon.getSize() < 1){
                                    clear();
                                }
                                showDialog("Režim: polygon");
                                isPolygon = true;
                                polygonRasterizer = new PolygonRasterizer(lineRasterizer);
                                break;
                            case KeyEvent.VK_E:
                                if(isPolygon){
                                    isEditMode = true;
                                }
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
                        } else if (e.getKeyCode() == KeyEvent.VK_E){
                            isEditMode = false;
                        }
                    }
                }
        );
    }

    private void clear(){
        panel.clear();
        panel.repaint();
        isPolygon = false;
        isEditMode = false;
        startPoint = null;
        endPoint = null;
        polygon.clearList();
    }

    public void showDialog(String message) {
        JOptionPane.showMessageDialog(panel, message, "Informace", JOptionPane.INFORMATION_MESSAGE);
    }

}
