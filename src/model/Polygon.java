package model;

import java.util.ArrayList;

public class Polygon {
    private final ArrayList<Point> points;

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
        if(getSize() > 0) {
            this.points.clear();
        }
    }

    public void setPoint(int index, Point point)
    {
        try {
            this.points.set(index, point);
        } catch (IndexOutOfBoundsException ex) {
            this.addPoint(point);
        }
    }
}
