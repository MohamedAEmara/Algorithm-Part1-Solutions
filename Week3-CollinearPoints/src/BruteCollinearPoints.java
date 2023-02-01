import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int segmentSize;
    private Point[] collinear;
    private int collinearSize;
    private Point[] pts;


    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();


        Point[] ptsNullCheck = points.clone();
        Arrays.sort(ptsNullCheck);
        for (int i = 0; i < ptsNullCheck.length - 1; i++) {
            if (ptsNullCheck[i] == null) throw new java.lang.NullPointerException();
            if (ptsNullCheck[i].compareTo(ptsNullCheck[i + 1]) == 0)
                throw new java.lang.IllegalArgumentException();
        }

        segments = new LineSegment[1];
        segmentSize = 0;

        pts = ptsNullCheck.clone();

        for (int p = 0; p < pts.length; p++) {
            Arrays.sort(pts, ptsNullCheck[p].slopeOrder());

            for (int j = 1; j < pts.length; j++) {

                collinear = new Point[4];
                collinearSize = 0;

                double slopeA = pts[0].slopeTo(pts[j]);
                enqueue(pts[0]);
                enqueue(pts[j]);


                while (++j < pts.length &&
                        slopeA == pts[0].slopeTo(pts[j])) {
                    enqueue(pts[j]);
                }
                j--; // Since we peeked ahead with j above, move j back.

                if (collinearSize >= 4) {


                    Point[] toAdd = new Point[collinearSize];
                    for (int k = 0; k < collinearSize; k++) {
                        toAdd[k] = collinear[k];
                    }
                    Arrays.sort(toAdd);


                    if (anyAbove(j)) {
                        enqueue(new LineSegment(toAdd[0],
                                toAdd[collinearSize - 1]));
                    }
                }
            }
        }
    }


    private boolean anyAbove(int j) {
        for (int k = 0; k < collinearSize - 1; k++)
            if (pts[0].compareTo(pts[j - k]) < 0)
                return false;
        return true;
    }

    private void enqueue(LineSegment l)
    {
        if (l == null) throw new java.lang.NullPointerException();
        if (segmentSize == segments.length)
            resize(2 * segments.length, segments);
        segments[segmentSize++] = l;
    }


    private void enqueue(Point p)
    {
        if (p == null) throw new java.lang.NullPointerException();
        if (collinearSize == collinear.length)
            resize(2 * collinear.length, collinear);
        collinear[collinearSize++] = p;
    }


    private void resize(int capacity, LineSegment[] l) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < segmentSize; i++) copy[i] = l[i];
        segments = copy;
    }


    private void resize(int capacity, Point[] p) {
        Point[] copy = new Point[capacity];
        for (int i = 0; i < collinearSize; i++) {
            copy[i] = p[i];
        }
        collinear = copy;
    }


    public int numberOfSegments() {
        return segmentSize;
    }


    public LineSegment[] segments() {
        LineSegment[] shrunk = new LineSegment[segmentSize];
        for (int i = 0; i < segmentSize; i++) {
            if(segments[i] == null)
                throw new java.lang.NullPointerException();
            shrunk[i] = segments[i];
        }
        return shrunk;
    }


}