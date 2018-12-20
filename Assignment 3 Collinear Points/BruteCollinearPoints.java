import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        checkRepeatedandNullPoints(points);
        ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>(); // will store all found lines
        Point[] pointCopy = Arrays.copyOf(points,points.length);
        Arrays.sort(pointCopy);
        for (int p = 0; p < pointCopy.length - 3; p++)
            for (int q = p + 1; q < pointCopy.length - 2; q++)
                for (int r = q + 1; r < pointCopy.length - 1; r++) {
                    if (pointCopy[p].slopeTo(pointCopy[q]) != pointCopy[p].slopeTo(pointCopy[r]))
                        continue; // to increase speed if 3 pointCopy are not collinear then no pointCopysCopy checking the 4 pointCopysCopy}
                    for (int s = r + 1; s < pointCopy.length; s++)
                        if (checkSlope(pointCopy[p], pointCopy[q], pointCopy[r], pointCopy[s]))
                            foundSegments.add(new LineSegment(pointCopy[p], pointCopy[s]));
                }
        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]); // stores final line segment
    }


    public int numberOfSegments() {
        // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() {
        // the line segments
        return segments.clone(); // creates complete copy of object
    }

        private void checkRepeatedandNullPoints(Point[] points) {
            if (points == null) throw new java.lang.IllegalArgumentException();
            for (int i = 0; i < points.length; i++) {
                if(points[i]==null){
                    throw new java.lang.IllegalArgumentException();
                }
            }
            for (int i = 0; i < points.length - 1; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    if (points[i].compareTo(points[j]) == 0)
                        throw new java.lang.IllegalArgumentException();

                }
            }
        }

    private boolean checkSlope(Point p, Point q, Point r, Point s) {
        return (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s));
    }
    public static void main(String[] args) {
//
//        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
    }
}


