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

    public Point getClosestPoint(Point p) {
        if (points.isEmpty()) {
            return null;
        }

        Point closestPoint = points.getFirst();
        double minDistance = distance(p, closestPoint);

        for (Point point : points) {
            double distance = distance(p, point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = point;
            }
        }

        return closestPoint;
    }

    private double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

}
