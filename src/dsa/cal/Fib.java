package dsa.cal;

import dsa.Countable;
import dsa.common.DoubleRef;

/**
 * Created by teoking on 17-12-13.
 */
public class Fib extends Countable {

    // TC: O(2^n)  SC:
    private static double fib1(int n) {
        count++;
        return (2 > n) ? n : fib1(n - 1) + fib1(n - 2);
    }


    // TC: O(n)  SC: O(n)
    private static double fib2(int n) {
        DoubleRef df = new DoubleRef(0);
        return fib2i(n, df);
    }

    private static double fib2i(int n, DoubleRef prev) {
        count++;
        if (0 == n) {
            prev.set(1);
            return 0;
        } else {
            DoubleRef prevPrev = new DoubleRef();
            prev.set(fib2i(n - 1, prevPrev));
            return prevPrev.get() + prev.get();
        }
    }

    // TC: O(n)  SC: O(1)
    private static double fib3(int n) {
        int f = 1, g = 0;
        while (0 < n--) {
            count++;
            g += f;
            f = g -f;
        }
        return g;
    }

    public static void main(String[] args) {
        int n = 30;
        double i = fib1(n);
        System.out.println("fib1 count=" + count + " i=" + i);

        count = 0;
        i = fib2(n);
        System.out.println("fib2 count=" + count + " i=" + i);

        count = 0;
        i = fib3(n);
        System.out.println("fib3 count=" + count + " i=" + i);
    }
}
