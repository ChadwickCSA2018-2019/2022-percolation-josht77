import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/******************************************************************************
 * Name: Kevin Wayne Login: wayne Precept: P01
 *
 * Partner Name: N/A Partner Login: N/A Partner Precept: N/A
 * 
 * Compilation: javac-algs4 Percolation.java Execution: java-algs4 Percolation
 * Dependencies: StdIn.java StdRandom.java WeightedQuickUnionUF.java
 *
 * Description: Modeling Percolation like a boss. woot. woot.
 ******************************************************************************/
public class PercolationStats {
	private static final double COEFF = 1.96;
	private double[] thresholds;
	private double mean = -1;
	private double stddev = -1;

	public PercolationStats(int n, int trials) {
		if (n < 1 || trials < 1) {
			throw new IllegalArgumentException();
		}

		thresholds = new double[trials];

		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);

			while (!percolation.percolates()) {
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;

				percolation.open(row, col);
			}

			thresholds[i] = percolation.numberOfOpenSites() / (double) (n * n);
		}
	}

	// done
	public double mean() {
		if (mean == -1) {
			mean = StdStats.mean(thresholds);
		}
		return mean;
	}

	// done
	public double stddev() {
		if (stddev == -1) {
			stddev = StdStats.stddev(thresholds);
		}
		return stddev;

	}

	// done
	public double confidenceLo() {
		return mean() - (COEFF * stddev()) / Math.sqrt(thresholds.length);
	}

	// done
	public double confidenceHi() {
		return mean() + (COEFF * stddev()) / Math.sqrt(thresholds.length);
	}

	public static void main(String[] args) {
		// test client (described at
		// http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)
		System.out.println(args[0] + " " + args[1]);
		int n = Integer.parseInt(args[0]); // convert the string argument to an integer
	}
}

// TODO: perform trials independent experiments on an n-by-n grid
// TODO: calculate sample mean of percolation threshold
// TODO: calculate sample standard deviation of percolation threshold
// TODO: return low  endpoint of 95% confidence interval
// TODO: return high endpoint of 95% confidence interval
// test client (described at http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)
