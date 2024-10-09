package model;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Point> points;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point p) {
        this.points.add(p);
    }

    public int getSize() {
        return this.points.size();
    }

    public ArrayList<Point> getArrayList() {
        return this.points;
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public void deleteLastElement() {
        this.points.removeLast();
    }

    public void clearList() {
        this.points.clear();
    }
}
