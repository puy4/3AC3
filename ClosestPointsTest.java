import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClosestPointsTest {

    static boolean testSmallDatasetAccuracy() {
        Random random = new Random();
        List<Point> dataset = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataset.add(new Point(random.nextDouble() * 100, random.nextDouble() * 100));
        }
        Point[] dataArray = dataset.toArray(new Point[dataset.size()]);
        Point[] closest = ClosestPoints.closestPoints(dataArray);
        Point[] closestBruteForce = ClosestPoints.bruteForce(dataArray);
        double epsilon = 1e-12;
        return (Math.abs((closest[0].distanceTo(closest[1])) - (closestBruteForce[0]
                .distanceTo(closestBruteForce[1]))) < epsilon) ? true : false;
    }

    static boolean testLargeDatasetAccuracy() {
        Random random = new Random();
        List<Point> largeDataset = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeDataset.add(new Point(random.nextDouble() * 100, random.nextDouble() * 100));
        }
        Point[] dataArray = largeDataset.toArray(new Point[largeDataset.size()]);
        Point[] closest = ClosestPoints.closestPoints(dataArray);
        Point[] closestBruteForce = ClosestPoints.bruteForce(dataArray);
        double epsilon = 1e-12;
        return (Math.abs((closest[0].distanceTo(closest[1])) - (closestBruteForce[0]
                .distanceTo(closestBruteForce[1]))) < epsilon) ? true : false;
    }

    /**
     * compexityTestDP and compexityTestBF measure running times for the dp
     * algorithm and the brute force algorithm, and verifies that
     * the DP algorithm is in O(nLogN) and the brute force algorithm is in O(n^2) by
     * testing with different sizes of n and comparing their ratios.
     */
    static void compexityTestDP() {
        System.out.println("DP complexity");
        Random random = new Random();

        int[] sizes = { 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 55000, 60000, 65000,
                70000 };
        System.out.println("Input size | Runtime (ns) | n log n | Ratio");
        for (int size : sizes) {
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                points.add(new Point(random.nextDouble() * 100, random.nextDouble() * 100));
            }
            Point[] pointsArray = points.toArray(new Point[0]);

            long totalDuration = 0;
            int runs = 20;
            for (int run = 0; run < runs; run++) {
                long startTime = System.nanoTime();
                Point[] closest = ClosestPoints.closestPoints(pointsArray);
                long endTime = System.nanoTime();
                totalDuration += (endTime - startTime);
            }
            long avgDuration = totalDuration / runs;

            double nLogN = size * Math.log(size);
            double ratio = (double) avgDuration / nLogN;

            System.out.printf("%10d | %12d | %6.0f | %6.2f%n", size, avgDuration, nLogN, ratio);
        }
    }

    static void compexityTestBF() {
        System.out.println("BF complexity");
        Random random = new Random();

        int[] sizes = { 100, 1000, 5000, 10000, 12500, 15000, 17500, 20000, 22500, 25000 };
        System.out.println("Input size | Runtime (ns) | n log n | Ratio");
        for (int size : sizes) {
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                points.add(new Point(random.nextDouble() * 100, random.nextDouble() * 100));
            }
            Point[] pointsArray = points.toArray(new Point[0]);

            long totalDuration = 0;
            int runs = 10;
            for (int run = 0; run < runs; run++) {
                long startTime = System.nanoTime();
                Point[] closest = ClosestPoints.bruteForce(pointsArray);
                long endTime = System.nanoTime();
                totalDuration += (endTime - startTime);
            }
            long avgDuration = totalDuration / runs;

            double nSquare = size * size;
            double ratio = (double) avgDuration / nSquare;

            System.out.printf("%10d | %12d | %6.0f | %6.2f%n", size, avgDuration, nSquare, ratio);
        }
    }

    public static void main(String[] args) {

        Point[] identicals = { new Point(1, 1), new Point(1, 1), new Point(1, 1) };
        Point[] closestIdenticals = ClosestPoints.closestPoints(identicals);
        System.out.println(closestIdenticals[0].toString() + "," + closestIdenticals[1].toString() + ","
                + "Distance: " + (closestIdenticals[0].distanceTo(closestIdenticals[1])));

        Point[] singlePoint = { new Point(1, 1) };
        Point[] closestiSinglePoint = ClosestPoints.closestPoints(singlePoint);
        System.out.println(closestiSinglePoint[0].toString());

        Point[] twoPoints = { new Point(1, 1), new Point(2, 2) };
        Point[] closestiTwoPoints = ClosestPoints.closestPoints(twoPoints);
        System.out.println(closestiTwoPoints[0].toString() + "," + closestiTwoPoints[1].toString() + ","
                + "Distance: " + (closestiTwoPoints[0].distanceTo(closestiTwoPoints[1])));

        Point[] zeroPoints = { new Point(0, 0), new Point(1, 1), new Point(2, 2) };
        Point[] negativePoints = { new Point(-1, -1), new Point(-2, -2), new Point(1, 1) };
        Point[] closestZero = ClosestPoints.closestPoints(zeroPoints);
        Point[] closestNegative = ClosestPoints.closestPoints(negativePoints);
        System.out.println(closestZero[0].toString() + "," + closestZero[1].toString() + ","
                + "Distance: " + (closestZero[0].distanceTo(closestZero[1])));
        System.out.println(closestNegative[0].toString() + "," + closestNegative[1].toString() + ","
                + "Distance: " + (closestNegative[0].distanceTo(closestNegative[1])));

        int i = 100;
        boolean isAccurate = true;
        while (i > 0) {
            if (!testSmallDatasetAccuracy()) {
                isAccurate = false;
                break;
            }
            if (!testLargeDatasetAccuracy()) {
                isAccurate = false;
                break;
            }
            i--;
        }
        System.out.println(isAccurate);
        compexityTestDP();
        compexityTestBF();
    }
}
