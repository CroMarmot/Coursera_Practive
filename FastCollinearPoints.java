import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final double EPS = 1 / (double) (32768000);
    private ArrayList<LineSegment> lines = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Point[] ps = Arrays.copyOf(points, points.length);
        int sz = ps.length;
        for (Point p1 : ps) {
            if (p1 == null) {
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(ps);
        for (int i = 0; i < sz - 1; i++) {
            if (ps[i].compareTo(ps[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        if (sz < 4) {
            return;
        }

        for (int p = 0; p < sz; ++p) {
            Point pcur = points[p];

            Arrays.sort(ps, pcur.slopeOrder());
            ArrayList<Point> curLine = new ArrayList<>();

            curLine.add(pcur); // ps[0] == pcur , slope of this == Double.NEGATIVE_INFINITY
            curLine.add(ps[1]);
            boolean fakeLine = pcur.compareTo(ps[1]) >= 0; // only find the line where pcur is the smallest
            double oldSlope = pcur.slopeTo(ps[1]);
            for (int q = 2; q < sz; q++) {
                double curSlope = pcur.slopeTo(ps[q]);
                if (curSlope == oldSlope || Math.abs(curSlope - oldSlope) < EPS) {
                    curLine.add(ps[q]);
                    if (pcur.compareTo(ps[q]) > 0) {
                        fakeLine = true;
                    }
                } else {
                    if (!fakeLine && curLine.size() > 3) {
                        addLine(curLine);
                    }
                    curLine.clear();
                    curLine.add(pcur);
                    curLine.add(ps[q]);
                    fakeLine = pcur.compareTo(ps[q]) >= 0;
                }
                oldSlope = curSlope;
            }
            if (!fakeLine && curLine.size() > 3)
                addLine(curLine);
        }
    }

    private void addLine(ArrayList<Point> lineSegPoints) {
        Point[] line = lineSegPoints.toArray(new Point[lineSegPoints.size()]);
        Arrays.sort(line);
        lines.add(new LineSegment(line[0], line[line.length - 1]));
    }

    // the number of line segments
    public int numberOfSegments() {
        return lines.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }

    public static void main(String[] args) {
        In in = new In("collinear/kw1260.txt");
        // In in = new In(args[0]);
        int n = in.readInt();
        StdOut.println("total " + n + " points");
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            StdOut.println("(" + x + "," + y + ")");
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
