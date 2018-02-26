import edu.princeton.cs.algs4.WeightedQuickUnionUF;




public class Percolation {
    final private int [][] model;
    private int count;
    private boolean openSites [][];
    final private WeightedQuickUnionUF sitesUnion;
    private int weightQuickUnionSize;
    // create n-by-n grid, with all sites blocked
    public Percolation(int n)  {
        if (n<=0) throw new java.lang.IllegalArgumentException("Index 0 or less");
        this.weightQuickUnionSize = n*n+2;
        this.count = 0;
        this.sitesUnion = new WeightedQuickUnionUF(weightQuickUnionSize);
        this.model = new int [n+1][n+1]; //create array for getting site index
        openSites = new boolean[n+1][n+1];//array with true value if site open and false if closed
        int index = 1;
        for (int i = 1; i < model.length; i++) {
            for (int j = 1; j < model.length; j++) {
                model[i][j] = index;
                index++;
            }
        }
    }
    // open site (row, col) if it is not open already
    public void open(int row, int col)  {
        if (isOpen(row, col)) return; //if site is open already, exit from method
        openSites[row][col] = true;
        count++;
        if (row == 1) {
            sitesUnion.union(0, model[row][col]); //connect first row site to virtual top site
        }
        if (row == openSites.length-1) sitesUnion.union(model[row][col], weightQuickUnionSize-1); //connect last row site to virtual bottom site

        if (isOpen(row-1, col)) sitesUnion.union(model[row-1][col], model[row][col]); //connect to up site if open
        if (isOpen(row+1, col)) sitesUnion.union(model[row+1][col], model[row][col]); //connect to down site if open
        if (isOpen(row, col+1)) sitesUnion.union(model[row][col+1], model[row][col]); //connect to right site
        if (isOpen(row, col-1)) sitesUnion.union(model[row][col-1], model[row][col]);//connect to left site
    }
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        try {
            return openSites[row][col];
        } catch (ArrayIndexOutOfBoundsException e) { //catch exception if Index is incorrect
            return false;
        }
    }
    // is site (row, col) full?
    // A full site is an open site that can be connected to an open site
    // in the top row via a chain of neighboring (left, right, up, down) open sites.
    public boolean isFull(int row, int col) {
        return sitesUnion.connected(0, model[row][col]);
    }

    // number of open sites
    public int numberOfOpenSites()   {
        return count;
    }
    // does the system percolate?
    //We say the system percolates if there is a full site in the bottom row.
    public boolean percolates()   {
        return sitesUnion.connected(0, weightQuickUnionSize-1);
    }

    public static void main(String[] args)  {
        Percolation percolation = new Percolation (6);

        percolation.open(1,6);
        percolation.open(2,6);
        percolation.open(3,6);
        percolation.open(4,6);
        percolation.open(5,6);
        percolation.open(5,5);
        percolation.open(4,4);
        percolation.open(3,4);
        percolation.open(2,4);
        percolation.open(2,3);
        percolation.open(2,2);
        percolation.open(2,1);
        percolation.open(3,1);
        percolation.open(4,1);
        percolation.open(5,1);
        percolation.open(5,2);
        percolation.open(6,2);
        percolation.open(5,4);
        System.out.println(percolation.percolates());
        System.out.println(percolation.count);

    } // test client (optional)
}
