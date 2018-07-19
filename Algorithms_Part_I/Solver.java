import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    // priority queue save stat
    // hamming或者manhattan距离
    // 1。[ ] 去重
    // 2。[x] 预计算 距离
    // 3。[ ] tree结构 距离，移动次数，队列？
    // 4。[x] 判断不可达
    private boolean solvable = false;
    private ArrayList<Board> ans = new ArrayList<>();

    // private class TrieVisNode {
    //     TrieVisNode[] next;

    //     TrieVisNode(int n) {
    //         next = new TrieVisNode[n];
    //     }
    // }

    // private TrieVisNode trieVisHead;

    private class WrapBoard {
        int steps = 0;
        WrapBoard father = null;
        Board board;

        WrapBoard(Board b, WrapBoard fa, int step) {
            board = b;
            father = fa;
            steps = step;
        }
    }

    private MinPQ<WrapBoard> boardMinPQ = new MinPQ<>(
            new Comparator<WrapBoard>() {
                @Override
                public int compare(WrapBoard o1, WrapBoard o2) {
                    return (o1.steps + o1.board.manhattan()) - (o2.steps + o2.board.manhattan());
                }
            }
    );

    private boolean isvisited(WrapBoard fa, Board bo) {
        while (true) {
            if (fa.board.equals(bo)) return true;
            if (fa.father == null) break;
            ;
            fa = fa.father;
        }
        return false;
        // boolean ret = true;
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         if (th.next[bo.datas[i][j]] == null) {
        //             ret = false;
        //             th.next[bo.datas[i][j]] = new TrieVisNode(n * n);
        //         }
        //         th = th.next[bo.datas[i][j]];
        //     }
        // }
    }

    private void genAns(WrapBoard wb) {
        if (wb == null) return;
        genAns(wb.father);
        ans.add(wb.board);
    }

    // find a solution to the initial board (using the A* algorithm){}
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        int n = initial.dimension();
        // trieVisHead = new TrieVisNode(n * n);

        int[][] tmpBoardArr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmpBoardArr[i][j] = i * n + j + 1;
            }
        }
        tmpBoardArr[n - 1][n - 1] = 0;
        if (n == 2) {
            tmpBoardArr[0][1] = 3;
            tmpBoardArr[1][0] = 2;
        } else {
            tmpBoardArr[n - 1][n - 3] = (n - 1) * n + (n - 2) + 1;
            tmpBoardArr[n - 1][n - 2] = (n - 1) * n + (n - 3) + 1;
        }
        Board unsolveBoard = new Board(tmpBoardArr);

        boardMinPQ.insert(new WrapBoard(initial, null, 0));
        // isvisited(initial);

        if (initial.isGoal()) {
            ans.add(initial);
            solvable = true;
            return;
        }
        ;

        while (boardMinPQ.size() != 0) {
            WrapBoard head = boardMinPQ.delMin();

            Iterable<Board> boardsneiboard = head.board.neighbors();
            for (Board bi : boardsneiboard) {
                if (bi.equals(unsolveBoard)) {
                    return;
                }
                if (bi.isGoal()) {
                    genAns(new WrapBoard(bi, head, head.steps + 1));
                    solvable = true;
                    return;
                }
                if (!isvisited(head, bi)) {
                    boardMinPQ.insert(new WrapBoard(bi, head, head.steps + 1));
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return ans.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return ans.size() == 0 ? null : ans;
    }

    // solve a slider puzzle (given below){}
    public static void main(String[] args) {
        // int[][] start = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] start = {{5, 8, 7}, {1, 4, 6}, {3, 0, 2}};
        // int [][] start = {{1,3},{2,0}};
        Solver s = new Solver(new Board(start));
        System.out.println(s.solvable);
        System.out.println(s.moves());
        Iterable<Board> boardArrayList = s.solution();
        for (Board b : boardArrayList) {
            System.out.println(b);
        }
    }
}
