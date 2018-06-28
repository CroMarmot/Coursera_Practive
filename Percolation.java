import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private enum Ntype {n_OPEN, n_FULL, n_CLOSE} // air water stock

    private final int perHead;
    private final int perBott;
    private final int sz;
    private final int der[][] = {
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0},
    };
    private int opencnt = 0;
    private boolean isPercolates;
    private Ntype[][] pInner;
    private int[] ufGroup;

    public int numberOfOpenSites() {
        return opencnt;
    }

    private int findfa(int v) {
        if (v != ufGroup[v]) {
            ufGroup[v] = findfa(ufGroup[v]);
        }
        return ufGroup[v];
    }

    private void connect(int v1, int v2) {
        int f1 = findfa(v1);
        int f2 = findfa(v2);
        if (f1 == f2) return;
        if ((f1 == perHead && f2 >= perBott) || (f1 >= perBott && f2 == perHead))
            isPercolates = true;
        // if (isPercolates) return;

        if (f1 == perHead) {
            ufGroup[f2] = f1;
        } else if (f2 == perHead) {
            ufGroup[f1] = f2;
        } else if (f1 >= perBott) {
            ufGroup[f2] = f1;
        } else {
            ufGroup[f1] = f2;
        }
    }

    private int ij2index(int i, int j) {
        return i * (sz + 2) + j;
    }

    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        isPercolates = false;
        sz = n;
        pInner = new Ntype[sz + 2][sz + 2];
        ufGroup = new int[(sz + 2) * (sz + 2)];
        perHead = ij2index(0, 1);
        perBott = ij2index(sz + 1, 1);

        for (int i = 0; i <= sz + 1; i++) {
            for (int j = 0; j <= sz + 1; j++) {
                pInner[i][j] = Ntype.n_CLOSE;
                ufGroup[ij2index(i, j)] = ij2index(i, j);
            }
        }
        // init HEAD and bottom
        for (int i = 1; i <= sz; i++) {
            pInner[0][i] = Ntype.n_OPEN;
            pInner[sz + 1][i] = Ntype.n_OPEN;
        }
        ufGroup[ij2index(0, 1)] = perHead;
        // ufGroup[ij2index(sz + 1, 1)] = perBott;
        // connect
        for (int i = 1; i < sz; i++) {
            connect(ij2index(0, i), ij2index(0, i + 1));
            // connect(ij2index(sz + 1, i), ij2index(sz + 1, i + 1));
        }
    }

    public boolean isFull(int r, int c) {
        if (r <= 0 || c <= 0 || r > sz || c > sz)
            throw new java.lang.IllegalArgumentException();
        if (findfa(ufGroup[ij2index(r, c)]) == perHead)
            pInner[r][c] = Ntype.n_FULL;
        return pInner[r][c] == Ntype.n_FULL;
    }

    public boolean isOpen(int r, int c) {
        if (r <= 0 || c <= 0 || r > sz || c > sz)
            throw new java.lang.IllegalArgumentException();
        return pInner[r][c] != Ntype.n_CLOSE;
    }

    public void open(int i, int j) {
        if (i <= 0 || j <= 0 || i > sz || j > sz)
            throw new java.lang.IllegalArgumentException();
        if (pInner[i][j] != Ntype.n_CLOSE) return;
        opencnt++;
        pInner[i][j] = Ntype.n_OPEN;
        for (int[] derxy : der) {
            int newi = i + derxy[0];
            int newj = j + derxy[1];
            if (pInner[newi][newj] == Ntype.n_CLOSE) {
                continue;
            }
            connect(ij2index(i, j), ij2index(newi, newj));
        }
    }

    public boolean percolates() {
        return isPercolates;
    }
}
