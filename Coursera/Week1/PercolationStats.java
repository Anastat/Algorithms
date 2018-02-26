import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    final private double[] thresholdsArr;
    private Percolation percolation;
    private double trials;
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n<=0 || trials<=0) throw new java.lang.IllegalArgumentException();
        this.trials = trials;

        this.thresholdsArr = new double[trials];
        for (int i=0; i<trials; i++) {
            this.percolation = new Percolation(n);
            while (!percolation.percolates())  {
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                percolation.open(row, col);
            }
            thresholdsArr[i] = (double) percolation.numberOfOpenSites()/(n*n);
        }

    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholdsArr);
    }

    // sample standard deviation of percolation threshold
    public double stddev()  {
        return StdStats.stddev(thresholdsArr);
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean()-(1.96*stddev()/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean()+(1.96*stddev()/Math.sqrt(trials));
    }

    public static void main(String[] args) {

        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats stat = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stat.mean());
        System.out.println("stddev                  = " + stat.stddev());
        System.out.println("95% confidence interval = " + stat.confidenceLo() + ", " + stat.confidenceHi());

    }    // test client (described below)
}
