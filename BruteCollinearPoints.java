import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lines = new ArrayList<>();

    private boolean doubleEqual(double v1, double v2) {
        return v1 == v2 || Math.abs(v1 - v2) < 1 / (double) (32768000);
    }

    private void checkPs(Point[] ps) {
        Arrays.sort(ps);
        // all in one line
        double[] slopeCmp = new double[3];
        slopeCmp[0] = ps[0].slopeTo(ps[1]);
        slopeCmp[1] = ps[0].slopeTo(ps[2]);
        slopeCmp[2] = ps[0].slopeTo(ps[3]);
        if (doubleEqual(slopeCmp[0], slopeCmp[1]) && doubleEqual(slopeCmp[0], slopeCmp[2])) {
            lines.add(new LineSegment(ps[0], ps[3]));
            // StdOut.println(ps[0] + "->" + ps[3]);
        }
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.IllegalArgumentException();
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
        if (points.length < 4)
            return;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {
                        Point[] putinps = new Point[4];
                        putinps[0] = points[i];
                        putinps[1] = points[j];
                        putinps[2] = points[k];
                        putinps[3] = points[m];
                        checkPs(putinps);
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lines.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        In in = new In("collinear/input8.txt");

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }
}
