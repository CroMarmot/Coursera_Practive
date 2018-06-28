import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double anss[];
    private final int T;
    private static double CONFIDENCE_95 = 1.96;

    public PercolationStats(int n, int trials) {     // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        T = trials;
        anss = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(n);
            int[] arrint = StdRandom.permutation(n * n);
            for (int j = 0; j < n * n; j++) {
                int ri = arrint[j] / n + 1;
                int rj = arrint[j] % n + 1;
                percolation.open(ri, rj);
                if (percolation.percolates()) {
                    anss[i] = (j / (double) (n * n));
                    break;
                }
            }
        }
    }

    public double mean() {                          // sample mean of percolation threshold
        return StdStats.mean(anss);
        // double ret = 0;
        // for (int i = 0; i < T; i++) {
        //    ret += anss[i] / T;
        // }
        // return ret;
    }

    public double stddev() {                        // sample standard deviation of percolation threshold
        return StdStats.stddev(anss);
        // double meanv = mean();
        // double ret = 0;
        // if (T == 1)
        //     return 0;
        // for (int i = 0; i < T; i++) {
        //     ret += (anss.get(i) - meanv) * (anss.get(i) - meanv) / (T - 1);
        // }
        // return Math.sqrt(ret);
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        double meanv = mean();
        double s = stddev();
        return meanv - CONFIDENCE_95 * s / (Math.sqrt(T));
    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        double meanv = mean();
        double s = stddev();
        return meanv + CONFIDENCE_95 * s / (Math.sqrt(T));
    }

    public static void main(String[] args) {        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);


        PercolationStats percolationStats = new PercolationStats(n, trails);

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");
    }
}
