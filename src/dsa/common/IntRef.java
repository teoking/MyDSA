package dsa.common;

/**
 * Created by teoking on 17-12-13.
 */
public class IntRef {
    int i;

    public IntRef() {
        i = 0;
    }

    public IntRef(int n) {
        i = n;
    }

    public void set(int n) {
        i = n;
    }

    public int get() {
        return i;
    }
}
