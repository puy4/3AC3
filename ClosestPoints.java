
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClosestPoints {

    public static Point[] closestPoints(Point[] points) {
        Point[] pointsSortedByX = Arrays.copyOf(points, points.length);
        Point[] pointsSortedByY = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortedByX, (p1, p2) -> Double.compare(p1.getX(), p2.getX()));
        Arrays.sort(pointsSortedByY, (p1, p2) -> Double.compare(p1.getY(), p2.getY()));

        Point[] closest = findingClosestPoints(pointsSortedByX, pointsSortedByY);
        return closest;
    }

    static Point[] findingClosestPoints(Point[] pointsByX, Point[] pointsByY) {
        int n = pointsByX.length;
        if (n <= 3) {
            return bruteForce(pointsByX); // Base case
        }

        int mid = n / 2;
        Point midPoint = pointsByX[mid];
        Point[] leftX = Arrays.copyOfRange(pointsByX, 0, mid);// y sorted points on left of vertical line
        Point[] rightX = Arrays.copyOfRange(pointsByX, mid, n);

        List<Point> leftY = new ArrayList<>();
        List<Point> rightY = new ArrayList<>();
        for (Point p : pointsByY) {
            if (p.getX() < midPoint.getX()) {
                leftY.add(p);
            } else {
                rightY.add(p);
            }
        }

        Point[] closestLeft = findingClosestPoints(leftX, leftY.toArray(new Point[0]));
        Point[] closestRight = findingClosestPoints(rightX, rightY.toArray(new Point[0]));

        Point[] minP = minDist(closestLeft, closestRight);
        double d = minP[0].distanceTo(minP[1]);

        ArrayList<Point> slab = new ArrayList<>();
        for (Point p : pointsByY) {
            if (Math.abs(p.getX() - midPoint.getX()) < d) {
                slab.add(p);
            }
        }
        int slabSize = slab.size();

        for (int i = 0; i < slabSize; i++) {
            for (int j = i + 1; j < Math.min(i + 8, slabSize) && slab.get(j).getY() - slab.get(i).getY() < d; ++j) {
                double distSlab = slab.get(i).distanceTo(slab.get(j));
                if (distSlab < d) {
                    d = distSlab;
                    minP[0] = slab.get(i);
                    minP[1] = slab.get(j);
                }
            }
        }

        return minP;

    }

    static Point[] minDist(Point[] l, Point[] r) {
        double distL = l[0].distanceTo(l[1]);
        double distR = r[0].distanceTo(r[1]);
        return (distL > distR) ? r : l;
    }

    static Point[] bruteForce(Point[] points) {
        double minDistance = Double.MAX_VALUE;
        int pointsLength = points.length;
        Point[] closestPair = new Point[2];
        if (pointsLength == 0) {
            return null;
        }
        if (pointsLength == 1) {
            return points;
        }
        if (pointsLength == 2) {
            return points;
        }
        for (int i = 0; i < pointsLength; i++) {
            for (int j = i + 1; j < pointsLength; j++) {
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPair[0] = points[i];
                    closestPair[1] = points[j];
                }
            }
        }
        return closestPair;
    }
}
