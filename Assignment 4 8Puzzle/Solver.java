
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private class Change implements Comparable<Change> {
        private Change previous;
        private Board board;
        private int totalNumberOfChanges = 0;

        public Change(Board board) {
            this.board = board;
        }

        public Change(Board board, Change previous) {
            this.board = board;
            this.previous = previous;
            this.totalNumberOfChanges = previous.totalNumberOfChanges + 1;
        }

        public int compareTo(Change change) {
            int manhattan1 = this.board.manhattan();
            int manhattan2 = this.board.manhattan();
            int numChanges1 = this.totalNumberOfChanges;
            int numChanges2 = change.totalNumberOfChanges;
            int priority = manhattan1 + numChanges1 - manhattan2 - numChanges2;
            return priority;
        }
    }

    private Change lastChange;

    public Solver(Board initial) {
        MinPQ<Change> moves = new MinPQ<>();
        MinPQ<Change> twinMoves = new MinPQ<Change>();
        moves.insert(new Change(initial));
        twinMoves.insert(new Change(initial.twin()));

        while (true) {
            lastChange = solve(moves);
            if (lastChange != null || solve(twinMoves) != null) return;
        }
    }

    private Change solve(MinPQ<Change> moves) {
        if (moves.isEmpty()) return null;
        Change bestChange = moves.delMin();
        if (bestChange.board.isGoal()) return bestChange; // got solution
        for (Board neighbor : bestChange.board.neighbors()) {
            if (bestChange.previous == null || !neighbor.equals(bestChange.previous.board)) {
                moves.insert(new Change(neighbor, bestChange));
            }
        }
        return null;
    }

    public boolean isSolvable() {
        return (lastChange != null);
    }

    public int moves() {
        return isSolvable() ? lastChange.totalNumberOfChanges : -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> moves = new Stack<Board>();
        while (lastChange != null) {
            moves.push(lastChange.board);
            lastChange = lastChange.previous;
        }

        return moves;
    }
}