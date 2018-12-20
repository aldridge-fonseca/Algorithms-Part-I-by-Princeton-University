import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    final private double[] trial_data;
    final private int trials, n;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid parameters");
        } // invalid parameters
        // init number of trials ,trial_data array and number of sites
        this.trials = trials;
        trial_data = new double[this.trials];
        this.n = n;

        // performing trials number of experiments on n*nm grid
        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n); //init a new percolation class
            int opensites = 0;

            do {
                int row = random(n);
                int col = random(n);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    opensites++;
                }
            } while (!p.percolates());
            trial_data[t] = (double) opensites / ((double) n * n);
            ;
//            System.out.println(trial_data[t]);
//            System.out.println(trial_data[t]+","+p.numberOfOpenSites()+","+this.n*this.n);

        }
    }

    private int random(int size) {
        return StdRandom.uniform(1, size + 1);
    }

    public double mean() {
        return StdStats.mean(trial_data);
    }

    public double stddev() {
        return StdStats.stddev(trial_data);
    }

    public double confidenceLo() {
        return mean() - confidence();
    }

    private double confidence() {
        return (1.96 * stddev() / Math.sqrt(trial_data.length));
    }

    public double confidenceHi() {
        return mean() + confidence();
    }


    public static void main(String[] args) {
        // test client (described below)
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.format("mean                     = %f\n", test.mean());
        System.out.format("stddev                   = %f\n", test.stddev());
        System.out.format("95%% confidence interval = [%f, %f]\n", test.confidenceLo(), test.confidenceHi());

    }
}
