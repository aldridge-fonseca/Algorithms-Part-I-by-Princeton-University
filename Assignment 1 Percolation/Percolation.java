import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    final private int num;
    private int num_open_sites;
    final private WeightedQuickUnionUF uf;
    private boolean[] blocked, connect_top, connect_bottom;
    private boolean ispecolated;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }// invalid parameter
        // create n*n grid. virtual sites at (0) and (n*n + 1)
        num = n;
        uf = new WeightedQuickUnionUF((n * n));
        // init blocked and full with (n*n + 2) locations
        blocked = new boolean[num * num];
        connect_top = new boolean[num * num];
        connect_bottom = new boolean[num * num];
        // both virtual sites are not blocked
        for (int i = 0; i < num * num; i++) {
            connect_top[i] = false;
            connect_bottom[i] = false;
            blocked[i] = true;

        }
        ispecolated = false;
        //init number of open sites to 0
        num_open_sites = 0;
    }

    private int get_id(int row, int col) {
        //checks if valid row and col if not throws a exception
        validate(row, col);
        return col + (row - 1) * num - 1;
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        validate(row, col);
        if (!isOpen(row, col)) {
            boolean top = false;
            boolean bottom = false;
            blocked[get_id(row, col)] = false; // site no longer blocked
            num_open_sites += 1;
            //create connections with neighbours
            if (row > 1 && isOpen(row - 1, col)) {
                if (connect_top[uf.find(get_id(row, col))] || connect_top[uf.find(get_id(row - 1, col))]) {
                    top = true;
                }
                if (connect_bottom[uf.find(get_id(row, col))] || connect_bottom[uf.find(get_id(row - 1, col))]) {
                    bottom = true;
                }
                uf.union(get_id(row, col), get_id(row - 1, col));

            }
            if (row < num && isOpen(row + 1, col)) {
                if (connect_top[uf.find(get_id(row, col))] || connect_top[uf.find(get_id(row + 1, col))]) {
                    top = true;
                }
                if (connect_bottom[uf.find(get_id(row, col))] || connect_bottom[uf.find(get_id(row + 1, col))]) {
                    bottom = true;
                }
                uf.union(get_id(row, col), get_id(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                if (connect_top[uf.find(get_id(row, col))] || connect_top[uf.find(get_id(row, col - 1))]) {
                    top = true;
                }
                if (connect_bottom[uf.find(get_id(row, col))] || connect_bottom[uf.find(get_id(row, col - 1))]) {
                    bottom = true;
                }
                uf.union(get_id(row, col), get_id(row, col - 1));
            }
            if (col < num && isOpen(row, col + 1)) {
                if (connect_top[uf.find(get_id(row, col))] || connect_top[uf.find(get_id(row, col + 1))]) {
                    top = true;
                }
                if (connect_bottom[uf.find(get_id(row, col))] || connect_bottom[uf.find(get_id(row, col + 1))]) {
                    bottom = true;
                }
                uf.union(get_id(row, col), get_id(row, col + 1));
            }
            // base conditions for top and bottom
            if (row == 1) {
                top = true;
            }
            if (row == num) {
                bottom = true;
            }
            // check conn with virtual sites
            connect_top[uf.find(get_id(row, col))] = top;
            connect_bottom[uf.find(get_id(row, col))] = bottom;
            if (top && bottom) ispecolated = true;

        }


    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        validate(row, col);
        return !blocked[get_id(row, col)];
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        validate(row, col);
        return connect_top[uf.find(get_id(row, col))];

    }

    private void validate(int row, int col) {
        if (row < 1 || row > num || col < 1 || col > num) throw new IllegalArgumentException("invalid parameters");
    }

    public int numberOfOpenSites() {
        // number of open sites\
        return num_open_sites;
    }

    public boolean percolates() {
        // does the system percolate?
        return ispecolated;
    }


}