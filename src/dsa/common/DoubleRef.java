package dsa.common;

/**
 * Created by teoking on 17-12-13.
 */
public class DoubleRef {
    double i;

    public DoubleRef() {
        i = 0;
    }

    public DoubleRef(double n) {
        i = n;
    }

    public void set(double n) {
        i = n;
    }

    public double get() {
        return i;
    }
}
