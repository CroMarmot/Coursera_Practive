import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int n;
    private int[][] datas;
    private int der[][] = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    private int hammingRes = 0;
    private int manhattanRes = 0;
    private int zeroPos = 0;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j){}

    private int ij2index(int i, int j) {
        return i * n + j;
    }

    private int hammingdis(int v1, int v2) {
        return v1 != v2 ? 1 : 0;
    }

    private int manhattandis(int v1, int v2) {
        return Math.abs(v1 / n - v2 / n) + Math.abs(v1 % n - v2 % n);
    }

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.IllegalArgumentException();
        n = blocks.length;
        if (n < 2 || n >= 128)
            throw new java.lang.IllegalArgumentException();
        boolean[] vischeck = new boolean[n * n];
        for (int i = 0; i < n * n; i++)
            vischeck[i] = false;
        for (int i = 0; i < n; i++) {
            if (blocks[i].length != n)
                throw new java.lang.IllegalArgumentException();
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] < 0 || blocks[i][j] >= n * n)
                    throw new java.lang.IllegalArgumentException();
                if (vischeck[blocks[i][j]])
                    throw new java.lang.IllegalArgumentException();
                vischeck[blocks[i][j]] = true;
                if (blocks[i][j] == 0)
                    zeroPos = ij2index(i, j);
            }
        }
        datas = blocks;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (datas[i][j] != 0) {
                    hammingRes += hammingdis(ij2index(i, j), datas[i][j] - 1);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (datas[i][j] != 0) {
                    manhattanRes += manhattandis(ij2index(i, j), datas[i][j] - 1);
                }
            }
        }
    }

    private Board moveI(int derindex) {
        int[][] newboard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newboard[i][j] = datas[i][j];
            }
        }
        int zeroi = zeroPos / n;
        int zeroj = zeroPos % n;
        int mov2i = zeroi + der[derindex][0];
        int mov2j = zeroj + der[derindex][1];
        newboard[zeroi][zeroj] = newboard[mov2i][mov2j];
        newboard[mov2i][mov2j] = 0;

        return new Board(newboard);
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingRes;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanRes;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] tboard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tboard[i][j] = datas[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (tboard[i][j] * tboard[i][j + 1] != 0) {
                    int tmp = tboard[i][j];
                    tboard[i][j] = tboard[i][j + 1];
                    tboard[i][j + 1] = tmp;
                    return new Board(tboard);
                }
            }
        }
        return new Board(tboard);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (!y.getClass().getName().equals("Board"))
            return false;
        Board by = (Board) y;
        if (n != by.n)
            return false;
        if (hammingRes != by.hammingRes || manhattanRes != by.manhattanRes)
            return false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (datas[i][j] != by.datas[i][j])
                    return false;
        return true;
    }

    private boolean boundCheck(int i) {
        return i >= 0 && i < n;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> ret = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int newi = zeroPos / n + der[i][0];
            int newj = zeroPos % n + der[i][1];
            if (boundCheck(newi) && boundCheck(newj)) {
                ret.add(moveI(i));
            }
        }
        return ret;
    }

    // string representation of this board (in the output format specified below){}
    public String toString() {
        String ret = n + "\n";
        for (int i = 0; i < n; i++) {
            ret += datas[i][0];
            for (int j = 1; j < n; j++) {
                ret += " " + datas[i][j];
            }
            ret += "\n";
        }
        return ret;
    }

    // unit tests (not graded){}
    public static void main(String[] args) {
        int[][] dtest = {
                {5, 8, 7},
                {1, 4, 6},
                {3, 0, 2},
        };
        // int[][] dtest = {
        //         {1, 2},
        //         {3, 0},
        // };
        Board b = new Board(dtest);
        StdOut.println(b);
        StdOut.println(b.manhattan());
        Iterable<Board> bnei = b.neighbors();
        for (Board ni : bnei) {
            StdOut.println(ni);
        }
    }
}
