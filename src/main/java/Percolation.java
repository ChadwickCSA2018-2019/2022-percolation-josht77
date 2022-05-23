import edu.princeton.cs.algs4.WeightedQuickUnionUF;

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
public class Percolation {

    private int top = 0;
    private int bottom = 0;
	private boolean[][] grid; // (openslots) true - open || false - closed
	private int numOfOpenSites; // gridSide
	private WeightedQuickUnionUF percolation; // backwash
	private WeightedQuickUnionUF isFullUF; // uf
	private int totalOpen;
	// creates n-by-n grid, with all sites initially blocked

	public Percolation(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Invalid argumenet. Should be greater than 0.");
		}
		numOfOpenSites = n;
		isFullUF = new WeightedQuickUnionUF(n * n + 1);
		percolation = new WeightedQuickUnionUF(n * n + 2);
		top = n * n;
		bottom = n * n + 1;
		grid = new boolean[n][n];
		totalOpen = 0;
	}

    public void open(int row, int col) {
        check(row, col);

        if (isOpen(row, col)) return;

        int index = index(row, col);

        // Top
        if (row == 1) {
            percolation.union(index, top);
            isFullUF.union(index, top);
        } else {
            int topRow = row - 1;
            unionIfOpen(topRow, col, index);
        }

        // Right
        if (col < numOfOpenSites) {
            int rightCol = col + 1;
            unionIfOpen(row, rightCol, index);
        }

        // Bottom
        if (row == numOfOpenSites) {
            percolation.union(index, bottom);
        } else {
            int bottomRow = row + 1;
            unionIfOpen(bottomRow, col, index);
        }

        // Left
        if (col > 1) {
            int leftCol = col - 1;
            unionIfOpen(row, leftCol, index);
        }

        grid[row - 1][col - 1] = true;
        totalOpen++;
    }

    public boolean isOpen(int row, int col) {
        check(row, col);
        return grid[row - 1][col - 1] == true;
	}

	// done
	public boolean isFull(int row, int col) {
        check(row, col);

        int index = index(row, col);
        return isFullUF.connected(index, top);
	}

	// done
	public int numberOfOpenSites() {
		return totalOpen;
	}

	// done
	public boolean percolates() {
        return percolation.connected(top, bottom);
	}

	//done
	public static void main(String[] args) {
		Percolation test = new Percolation(2);
		test.open(1, 1);
		test.open(2, 2);
		test.open(1, 2);
		System.out.println(test.percolates());
	}

	
    private void check(int row, int col) {
        if (row < 1 || row > numOfOpenSites || col < 1 || col > numOfOpenSites) {
            throw new IllegalArgumentException();
        }
    }

    private int index(int row, int col) {
        return numOfOpenSites * (row - 1) + (col - 1);
    }
    
    private void unionIfOpen(int row, int col, int indexToUnion) {
        if (isOpen(row, col)) {
            int index = index(row, col);
            percolation.union(index, indexToUnion);
            isFullUF.union(index, indexToUnion);
        }
    }
}

