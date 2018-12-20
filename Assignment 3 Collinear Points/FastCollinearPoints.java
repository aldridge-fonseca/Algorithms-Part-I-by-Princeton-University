//import java.util.*;
//public class FastCollinearPoints {
//    private List<LineSegment> segments;
//    private HashMap<Double,List<Point>> foundSegments = new HashMap<>(); // a hash map that maps slope to list of points
//    public FastCollinearPoints(Point[] points){
//        // finds all line segments containing 4 or more points
//        checkRepeatedPoints(points);
//        if (points == null) throw new IllegalArgumentException("Argument null");
//        Point[] pointsCopy = Arrays.copyOf(points,points.length);
//       for(Point p : points) {// for every p in the pointsCopy
//           Arrays.sort(pointsCopy,p.slopeOrder()); // sort array as per the slope order with respect to p
//           double curSlope =0,prevSlope = Double.NEGATIVE_INFINITY;
//           ArrayList<Point> slopePoints= new ArrayList<Point>();
//           for (int i = 1; i <pointsCopy.length ; i++) { //for every point except the first
//               Point q = pointsCopy[i];
//               curSlope = p.slopeTo(q);
//               if (curSlope == prevSlope) {
//                   slopePoints.add(q);
//               } else {
//                   if (slopePoints.size() >= 3) { // if the number of points with equal slope is more than or equal to 3
//                       slopePoints.add(p);
//                       checkSegment(prevSlope, slopePoints);
//                   }
//                   slopePoints.clear(); // if less than 3 then we clear the slope points and add the current point q
//                   slopePoints.add(q);
//               }
//               prevSlope = curSlope;
//           }
//           if (slopePoints.size() >= 3) { // if the number of points with equal slope is more than or equal to 3
//               slopePoints.add(p);
//               checkSegment(curSlope, slopePoints);
//           }
//
//           }
//
//       }
//
//
//    private void checkSegment(Double slope,List<Point> slopePoints) {
//        List<Point> storedPoints = foundSegments.get(slope);
//        Collections.sort(slopePoints);
//        Point slopeStartPoint = slopePoints.get(0); // intial point
//        Point slopeEndPoint = slopePoints.get(storedPoints.size()-1); // final point
//        // if there is no segment added
//        if (storedPoints==null){
//            storedPoints = new ArrayList<Point>();
//            storedPoints.add(slopeEndPoint); // updates storedPoints
//            foundSegments.put(slope,storedPoints); // creates new segment in found segment against slope
//            segments.add(new LineSegment(slopeStartPoint,slopeEndPoint)); // adds segment to segments
//        }
//        else{ // check every point if it is equal to slopeEndpoint then do not add else we need to add the point
//            for (Point storedPoint : storedPoints)
//            if(storedPoint==slopeEndPoint) return ;
//            // if point is not present then
//            storedPoints.add(slopeEndPoint); // updates storedPoints
//            segments.add(new LineSegment(slopeStartPoint,slopeEndPoint)); // adds segment to segments
//        }
//    }
//
//    public int numberOfSegments() {
//        // the number of line segments
//        return segments.size();
//    }
//
//    public LineSegment[] segments() {
//        // the line segments
//        return segments.toArray(new LineSegment[segments.size()]); // creates complete copy of object
//    }
//    private void checkRepeatedPoints(Point[] points) {
//        for (int i = 0; i < points.length - 1; i++) {
//            for (int j = i + 1; j < points.length; j++) {
//                if (points[i].compareTo(points[j]) == 0)//same point
//                    throw new IllegalArgumentException("Repeated points");
//            }
//        }
//    }
//}


import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        checkRepeatedandNullPoints(points);
        ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>(); // a hash map that maps slope to list of points
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        for (Point p : points) {// for every p in the pointsCopy
            Arrays.sort(pointsCopy, p.slopeOrder()); // sort array as per the slope order with respect to p
            double curSlope = p.slopeTo(pointsCopy[0]);
            int count = 1;
            int i; // start checking leaving first
            for (i = 1; i < pointsCopy.length; i++) { //for every point except the first
                Point q = pointsCopy[i];
                if (curSlope == p.slopeTo(q)) // if the point q is same slope and current slope then increment count
                {
                    count++;
                    continue;
                } else {
                    if (count >= 3) { // if the number of points with equal slope is more than or equal to 3
                        Arrays.sort(pointsCopy, i - count, i); // sort the array till i, count -i is the index from which the points with same slope starts
                        if (p.compareTo(pointsCopy[i - count]) < 0)
                            foundSegments.add(new LineSegment(p, pointsCopy[i - 1])); // add the line segment p -> q to the found segment array
                    }
                    count = 1; // if the slope changes then reset the count
                    curSlope = p.slopeTo(q); // new current slope is slope of p -> q
                }
            }
            // for the last set of same slope after coming out of loop
            if (count >= 3) { // if the number of points with equal slope is more than or equal to 3
                Arrays.sort(pointsCopy, i - count, i); // sort the array till i, count -i is the index from which the points with same slope starts
                if (p.compareTo(pointsCopy[i - count]) < 0)
                    foundSegments.add(new LineSegment(p, pointsCopy[i - 1])); // add the line segment p -> q to the found segment array
            }
        }

        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
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

}


