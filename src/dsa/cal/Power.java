package dsa.cal;

import dsa.Countable;

/**
 * Created by teoking on 17-12-13.
 */
public class Power extends Countable{

    // O(n)
    private static int pow1(int n) {
        int i = 1;
        while (0 < n--) {
            count++;
            i <<= 1;
        }
        return i;
    }

    private static int sqr(int n) {
        count++;
        return n * n;
    }
    // O(logn)
    private static int pow2(int n) {
        if (0 == n)
            return 1;
        return (n & 1) == 1 ? sqr(pow2(n >> 1)) << 1 : sqr(pow2(n >> 1));
    }

    public static void main(String[] args) {
        int n = 30;
        int i = pow1(n);
        System.out.println("pw1 count=" + count + " i=" + i);

        count = 0;
        i = pow2(n);
        System.out.println("pw1 count=" + count + " i=" + i);
    }
}
