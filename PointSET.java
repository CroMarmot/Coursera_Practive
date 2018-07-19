import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> point2DS = new SET<>();

    // construct an empty set of points
    public PointSET() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return point2DS.size() == 0;
    }

    // number of points in the set
    public int size() {
        return point2DS.size();
    }

    // add the point to the set (if it is not already in the set){}
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        point2DS.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return point2DS.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D pe : point2DS) {
            pe.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary){}
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        ArrayList<Point2D> point2DArrayList = new ArrayList<>();
        for (Point2D point2D : point2DS) {
            if (rect.contains(point2D))
                point2DArrayList.add(point2D);
        }
        return point2DArrayList;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Point2D pret = null;
        for (Point2D point2D : point2DS) {
            if (pret == null || pret.distanceSquaredTo(p) < point2D.distanceSquaredTo(p))
                pret = point2D;
        }
        return pret;
    }

    // unit testing of the methods (optional){}
    public static void main(String[] args) {
    }

}
