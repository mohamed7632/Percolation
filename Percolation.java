/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 ****************************************************************************
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int n;
    private int opensites;
    private WeightedQuickUnionUF obj;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("the size must be more than zero ");

        this.n = n;
        this.opensites = 0;
        obj = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[this.n][this.n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }

    private boolean exist(int row, int col) {
        return !(row - 1 < 0 || col - 1 < 0 || row > n || col > n);

    }

    // opens the site (row, col) if it is not open already

    public void open(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row > n || col > n)
            throw new IllegalArgumentException("the site is out of range");
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            opensites += 1;
        }

        int current = n * (row - 1) + col;
        int upper = n * (row - 2) + col;
        int lower = n * row + col;
        int left = n * (row - 1) + col - 1;
        int right = n * (row - 1) + col + 1;

        if (row == 1)
            obj.union(current, 0);

        if (row == n)
            obj.union(current, (n * n) + 1);

        if (exist(row - 1, col))
            if (isOpen(row - 1, col))
                obj.union(current, upper);

        if (exist(row + 1, col))
            if (isOpen(row + 1, col))
                obj.union(current, lower);


        if (exist(row, col - 1))
            if (isOpen(row, col - 1))
                obj.union(current, left);


        if (exist(row, col + 1))
            if (isOpen(row, col + 1))
                obj.union(current, right);


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row > n || col > n)
            throw new IllegalArgumentException("the site is out of range");
       
        return (grid[row - 1][col - 1]);

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row > n || col > n)
            throw new IllegalArgumentException("the site is out of range");
        int current = n * (row - 1) + col;
        return (obj.find(current) == obj.find(0));


    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return opensites;
    }

    // does the system percolate?
    public boolean percolates() {

        return (obj.find(0) == obj.find(n * n + 1));


    }

    // test client (optional)
    public static void main(String[] args) {
        // main is empty


    }

}
