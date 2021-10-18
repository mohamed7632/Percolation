/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static double confidence = 1.96;
    private double[] x;
    private double xbar;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("inputs must be bigger than  0");
        xbar = 0;
        x = new double[trials];
        for (int i = 0; i < trials; i++) {

            Percolation perco = new Percolation(n);
            while (!perco.percolates()) {

                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!perco.isOpen(row, col))
                    perco.open(row, col);
            }
            int numOfOpenSites = perco.numberOfOpenSites();
            xbar += (double) numOfOpenSites / (double) (n * n);
            x[i] = (double) numOfOpenSites / (double) (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return xbar / x.length;

    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return StdStats.stddev(x);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stdv = stddev();
        return mean - (confidence * stdv) / Math.sqrt(x.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        double mean = mean();
        double stdv = stddev();
        return mean + (confidence * stdv) / Math.sqrt(x.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n, t;
        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);
        PercolationStats obj = new PercolationStats(n, t);
        System.out.println("mean                    = " + obj.mean());
        System.out.println("stddev                  = " + obj.stddev());
        System.out.println(
                "95% confidence interval = [" + obj.confidenceLo() + ", " + obj.confidenceHi()
                        + "]");
    }


}
