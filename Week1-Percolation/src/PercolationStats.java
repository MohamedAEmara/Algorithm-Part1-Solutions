import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int N;
    private int T;
    private double p[];

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n < 1 || trials < 1)
        {
            throw new IllegalArgumentException("n and trials must be greater the zero");
        }
        N = n;
        T = trials;
        p = new double[trials];
    }

    // sample mean of Percolation2 threshold
    public double mean()
    {
        return StdStats.mean(p);
    }

    // sample standard deviation of Percolation2 threshold
    public double stddev()
    {
        return StdStats.stddev(p);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        double ret = 0;
        ret = mean() - (1.96 * stddev()) / Math.sqrt(T);
        return ret;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double ret = 0;
        ret = mean() + (1.96 * stddev()) / Math.sqrt(T);
        return ret;
    }

    // test client (see below)
    public static void main(String[] args)
    {
        int n, t;
//        n = Integer.parseInt(args[0]);
//        t = Integer.parseInt(args[1]);
        n = 2;
        t = 10000;
        PercolationStats percolationStats = new PercolationStats(n, t);

        for (int tt = 0; tt < t; tt ++)
        {
            Percolation percolation = new Percolation(n);

            // Until the system percolates, choose a random cell (i, j) and open it if  it's not already open.
            while (!percolation.percolates())
            {
                int i = StdRandom.uniformInt(1, n+1);
                int j = StdRandom.uniformInt(1, n+1);
                percolation.open(i, j);
            }
            double ratio = ((double)(percolation.numberOfOpenSites()) / (double)(n * n));

            // Calculate the Percolation2 thresholds and store it in p[i] as a fraction of sites open.
            percolationStats.p[tt] = ratio;
        }
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceHi() + ", " + percolationStats.confidenceLo() + "]");
    }
}
