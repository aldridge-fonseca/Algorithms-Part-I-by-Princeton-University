
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;

public class Board {
    private int size;
    private int[] blocks;


    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)

        if (blocks == null || blocks.length != blocks[0].length) {
            throw new java.lang.IllegalArgumentException();
        }
        size = blocks.length;
        this.blocks = new int[size * size];

        for (int i = 0; i < blocks.length; i++) // converts from 2d to 1d array
            for (int j = 0; j < blocks.length; j++)
                this.blocks[i * blocks.length + j] = blocks[i][j];

    }

    public int dimension() {
        // board dimension n
        return size;
    }

    public int hamming() {
        // number of blocks out of place
        int hamming = 0;
        for (int i = 0; i < blocks.length; i++)
            if (blocks[i] != 0 && blocks[i] != i + 1)
                hamming++;
        return hamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattan = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0)
                continue;
            else {
                int pos = blocks[i] - 1; // get the position in 0 based state
                if (pos < 0)
                    pos = blocks.length - 1; // set to last
                // getrow,getcol at (i) gives current value whereas at (pos) gives goal value.
                // then we just have to calculate the abs of the row and cols respectively
                manhattan += calculateManhattan(getrow(i), getrow(pos), getcol(i), getcol(pos));
            }
        }
        return manhattan;

    }

    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < blocks.length; i++)
            if (blocks[i] != 0 && blocks[i] != i + 1)
                return false;
        return true;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[] blocktwin = blocks.clone();
        int random1 = StdRandom.uniform(0, blocktwin.length);
        int random2 = StdRandom.uniform(0, blocktwin.length);
        while (blocktwin[random1] == blocktwin[random2] || blocktwin[random1] == 0 || blocktwin[random2] == 0) {
            //blank is not a random element and both randoms are not equal
            random1 = StdRandom.uniform(0, blocktwin.length);
            random2 = StdRandom.uniform(0, blocktwin.length);
        }
        int temp = blocktwin[random1];
        blocktwin[random1] = blocktwin[random2];
        blocktwin[random2] = temp;

        return new Board(convertoBoard(blocktwin));

    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < blocks.length; i++)
            if (blocks[i] != that.blocks[i])
                return false;

        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        int[][] changes = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}}; // this is the 4 possible changes that can happen to the blank space
        Stack<Board> neighbors = new Stack<>(); // create new stack of neighbors

        for (int i = 0; i < blocks.length; i++) {
            if (neighbors.size() != 0) break;
            if (blocks[i] != 0) continue;
            int[] blocksCopy;
            for (int c = 0; c < changes.length; c++) {
                blocksCopy = blocks.clone();
                int row = getrow(c) + changes[c][0];
                int col = getcol(c) + changes[c][1];
                // if change is a valid one we can exchange the elements in blocks
                if (checkRowCol(row, col)) { // exchg element at i with element at new row,col
                    int temp = blocksCopy[i];
                    blocksCopy[i] = blocksCopy[row * size + col];
                    blocksCopy[row * size + col] = temp;
                    // push  the new board to the stack
                    neighbors.push(new Board(convertoBoard(blocksCopy)));
                }
            }
        }
        return neighbors;
    }


    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(size);
        s.append("\n");
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                s.append(" ");
                s.append(blocks[row * size + col]);
            }
            s.append("\n");
        }
        return s.toString();
    }

    private boolean checkRowCol(int row, int col) {
        if ((row >= 0 && row < size) && (col >= 0 && col < size))
            return true;
        return false;

    }

    private int[][] convertoBoard(int[] blocktwin) {
        int[][] block = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                block[i][j] = blocktwin[i * size + j];

        return block;
    }

    private int calculateManhattan(int row, int rowgoal, int col, int colgoal) {
        return Math.abs(row - rowgoal) + Math.abs(col - colgoal);
    }

    private int getcol(int col) {
        return col % size;
    }

    private int getrow(int row) {
        return row / size;
    }
}
