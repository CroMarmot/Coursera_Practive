import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.PriorityQueue;
import java.util.Queue;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private final Point2D p;
        private RectHV rect;
        private Node l;
        private Node r;

        public Node(Point2D point2D) {
            this.p = point2D;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        root = insert(root, new Node(p), true, 0, 0, 1, 1);
    }

    private Node insert(Node n, Node inputNode, boolean vertical, double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            size++;
            inputNode.rect = new RectHV(xmin, ymin, xmax, ymax);
            return inputNode;
        } else if (n.p.equals(inputNode.p)) {
            return n;
        }
        if (vertical) {
            if (inputNode.p.x() < n.p.x()) {
                n.l = insert(n.l, inputNode, !vertical, xmin, ymin, n.p.x(), ymax);
            } else {
                n.r = insert(n.r, inputNode, !vertical, n.p.x(), ymin, xmax, ymax);
            }
        } else {
            if (inputNode.p.y() < n.p.y()) {
                n.l = insert(n.l, inputNode, !vertical, xmin, ymin, xmax, n.p.y());
            } else {
                n.r = insert(n.r, inputNode, !vertical, xmin, n.p.y(), xmax, ymax);
            }
        }
        return n;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p, true);
    }

    private boolean contains(Node n, Point2D p, boolean vertical) {
        if (n == null) {
            return false;
        } else if (n.p.equals(p)) {
            return true;
        }
        if (vertical) {
            if (p.x() < n.p.x()) {
                return contains(n.l, p, !vertical);
            } else {
                return contains(n.r, p, !vertical);
            }
        } else {
            if (p.y() < n.p.y()) {
                return contains(n.l, p, !vertical);
            } else {
                return contains(n.r, p, !vertical);
            }
        }
    }

    public void draw() {
        if (root == null)
            return;
        drawHelper(root, true);
    }

    private void drawHelper(Node n, boolean vertical) {
        if (n == null) return;
        drawNode(n);

        drawSplittingLine(n, vertical);

        drawHelper(n.l, !vertical);
        drawHelper(n.r, !vertical);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Queue<Point2D> res = new PriorityQueue<>();
        if (root == null)
            return res;
        rangeHelper(rect, root, res);
        return res;
    }

    private void rangeHelper(RectHV rect, Node n, Queue<Point2D> res) {
        if (n == null)
            return;
        if (!rect.intersects(n.rect))
            return;
        if (rect.contains(n.p))
            res.add(n.p);
        rangeHelper(rect, n.l, res);
        rangeHelper(rect, n.r, res);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
            return null;

        return nearestHelper(root, p, root.p);
    }

    private Point2D nearestHelper(Node n, Point2D p, Point2D min) {
        if (n == null)
            return min;
        if (p.distanceSquaredTo(min) < n.rect.distanceSquaredTo(p))
            return min;

        // update min based on current node
        if (n.p.distanceSquaredTo(p) < min.distanceSquaredTo(p))
            min = n.p;

        if (n.l == null) {
            return nearestHelper(n.r, p, min);
        } else if (n.r == null) {
            return nearestHelper(n.l, p, min);
        } else if (n.l.rect.distanceSquaredTo(p) < n.r.rect.distanceSquaredTo(p)) {
            min = nearestHelper(n.l, p, min);
            return nearestHelper(n.r, p, min);
        } else {
            min = nearestHelper(n.r, p, min);
            return nearestHelper(n.l, p, min);
        }
    }

    private void drawSplittingLine(Node n, boolean vertical) {
        StdDraw.setPenRadius();
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            Point2D up = new Point2D(n.p.x(), n.rect.ymax());
            Point2D down = new Point2D(n.p.x(), n.rect.ymin());
            up.drawTo(down);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D left = new Point2D(n.rect.xmin(), n.p.y());
            Point2D right = new Point2D(n.rect.xmax(), n.p.y());
            left.drawTo(right);
        }
    }

    private void drawNode(Node n) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();
    }
}
