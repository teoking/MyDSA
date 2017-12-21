package dsa.stack.queen;

import dsa.common.ElemComparable;
import dsa.stack.Stack;

/**
 * Created by teoking on 17-12-21.
 */
public class PlaceQueen {

    static class Queen {
        int x, y;   // 棋盘上的位置
        Queen(int xx, int yy) {
            x = xx;
            y = yy;
        }

        Queen(Queen q) {
            x = q.x;
            y = q.y;
        }

        public boolean equals(Queen q) {
            return (x == q.x)
                    || (y == q.y)
                    || (x + y == q.x + q.y)
                    || (x - y == q.x - q.y);
        }

        @Override
        public String toString() {
            return String.format("queen(x=%d, y=%d)", x, y);
        }
    }

    static ElemComparable<Queen> sComp = new ElemComparable<Queen>() {
        @Override
        public int compare(Queen q1, Queen q2) {
            return q1.equals(q2) ? 0 : -1;
        }
    };

    int nCheck = 0;
    int nSolu = 0;

    void placeQueens(Stack<Queen> solu, int N) {
        Queen q = new Queen(0, 0);  // start from (0,0)
        do {
            if (N <= solu.size() || N <= q.y) {
                q = solu.pop();
                q.y++;
            } else {
                while (q.y < N && 0 <= solu.find(q, sComp)) {
                    q.y++;
                    nCheck++;
                }
                if (N > q.y) {
                    solu.push(new Queen(q));
                    if (N <= solu.size()) {
                        nSolu++;
                        displayProgress(solu, N);
                    }
                    q.x++;
                    q.y = 0;
                }
            }
            //displayProgress(solu, N);
        } while (0 < q.x || q.y < N);
    }

    void displayRow(Queen q, int N) {
        System.out.print(String.format("%2d: ", q.x));
        int i = 0;
        while (i++ < q.y)
            System.out.print("[]");
        System.out.print("█");
        while (i++ < N)
            System.out.print("[]");
        System.out.println(String.format("%2d", q.y));
    }

    void displayProgress(Stack<Queen> s, int N) {
        for (int i = 0; i < s.size(); i++) {
            displayRow(s.get(i), N);
        }
        if (N <= s.size()) {
            System.out.println(String.format("%d solutions found after %d check(s)", nSolu, nCheck));
        }
    }

    public static void main(String[] args) {
        PlaceQueen qp = new PlaceQueen();
        Stack<Queen> solu = new Stack<>();
        qp.placeQueens(solu, 12);

        while (!solu.empty()) {
            System.out.println(solu.pop());
        }
    }

}
