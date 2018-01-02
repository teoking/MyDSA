package dsa.graph;

import dsa.vec.MyVector;

/**
 * Support set opt which is needed by {@link GraphMatrix}.
 * Created by teoking on 18-1-2.
 */
public class MyVector4Matrix<T> extends MyVector<T> {

    public MyVector4Matrix() {
        super();
    }

    public MyVector4Matrix(int n, int size, T defaultValue) {
        super(n, size, defaultValue);
    }

    // Set element at i to value e.
    public void set(int i, T e) {
        elem[i] = e;
    }
}
