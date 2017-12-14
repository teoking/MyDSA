package dsa.fib;

/**
 * Created by teoking on 17-12-14.
 */
public class Fibonacci {

    private int f;
    private int g;

    public Fibonacci(int n) {
        f = 1;
        g = 0;
        while (g < n)
            next();
    }

    public int get() {
        return g;
    }

    int next() {
        g += f;
        f = g - f;
        return g;
    }

    public int prev() {
        if (g == 1) return g;

        f = g - f;
        g -= f;
        return g;
    }
}
