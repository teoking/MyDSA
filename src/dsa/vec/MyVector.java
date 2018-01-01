package dsa.vec;

/**
 * Created by teoking on 17-12-14.
 */
public class MyVector<E> implements Vector<E> {

    private static final int DEFAULT_CAPACITY = 10;

    public int size;
    public int capacity;
    public E[] elem;

    public MyVector() {
        capacity = DEFAULT_CAPACITY;
        elem = (E[]) new Object[capacity];
        size = 0;
    }

    public MyVector(int n) {
        elem = (E[]) new Object[capacity = n];
        size = 0;
    }

    public MyVector(int n, int size, E defaultValue) {
        elem = (E[]) new Object[capacity = n];
        this.size = 0;
        while (this.size < size) {
            elem[this.size++] = defaultValue;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int r) {
        return elem[r];
    }

    @Override
    public int insert(int r, E e) {
        expand();
        if (r > size) {
            throw new IndexOutOfBoundsException("Index :" + r + ", Size: " + size);
        }

        for (int i = size; i > r; i--) {
            elem[i] = elem[i - 1];
        }
        elem[r] = e;
        size++;
        return r;
    }

    @Override
    public int insert(E e) {
        return insert(size, e);
    }

    public int remove(int lo, int hi) {
        if (lo == hi) return 0;

        while (hi < size) {
            elem[lo++] = elem[hi++];
        }
        size = lo;
        shrink();
        return hi -lo;
    }

    @Override
    public E remove(int r) {
        if (r > size) {
            throw new IndexOutOfBoundsException("Index :" + r + ", Size: " + size);
        }
        E e = elem[r];
        remove(r, r + 1);
        return e;
    }

    @Override
    public void traverse() {

    }

    @Override
    public void swap(int l, int r) {
        E temp = elem[l];
        elem[l] = elem[r];
        elem[r] = temp;
    }

    // protected methods

    protected void expand() {
        if (size < capacity) return;
        if (capacity < DEFAULT_CAPACITY) capacity = DEFAULT_CAPACITY;

        int tempCapacity = capacity * 2;
        E[] tempElements = (E[]) new Object[tempCapacity];
        System.arraycopy(elem, 0, tempElements, 0, size);
        elem = tempElements;
        capacity = tempCapacity;
    }

    protected void shrink() {
        if (capacity < DEFAULT_CAPACITY << 1) return;
        if (size << 2 > capacity) return;
        // Under 25% of capacity
        E[] oldElem = elem;
        elem = (E[]) new Object[capacity >>= 1];
        for (int i = 0; i < size; i++) {
            elem[i] = oldElem[i];
        }
    }
}
