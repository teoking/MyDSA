package dsa.vec;

import dsa.fib.Fibonacci;

/**
 * Created by teoking on 17-12-14.
 */
public class MySortedArrayList<E> extends MyArrayList<E> implements MySortedList<E> {

    @Override
    public int disordered(ElemComparable comp) {
        int n = 0;
        for (int i = 1; i < size; i++) {
            if (comp.compare(elem[i - 1], elem[i]) > 0) {
                n++;
            }
        }
        return n;
    }

    @Override
    public void sort(int lo, int hi) {

    }

    @Override
    public int find(E e) {
        return 0;
    }

    @Override
    public void deduplicate() {

    }

    @Override
    public int search(E e, int lo, int hi, ElemComparable comp, Searchable searchMethod) {
        return searchMethod.search(this, e, lo, hi, comp);
    }

    // O(n) version
    @Override
    public int uniquify(ElemComparable comp) {
        int i = 0, j = 0;
        while (++j < size) {
            if (comp.compare(elem[i], elem[j]) != 0) {
                elem[++i] = elem[j];
            }
        }
        size = ++i;
        shrink();
        return j - i;
    }

    // O(n)
    static class LinearSearch<E> implements Searchable<E> {

        @Override
        public int search(MyList<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
            while (lo < hi) {
                if (comp.compare(e, list.get(lo)) == 0) {
                    return lo;
                }
                lo++;
            }
            return -1;
        }
    }

    // O(logn)
    // Average search length: O(1.44 * logn)
    static class BinarySearchA<E> implements Searchable<E> {

        @Override
        public int search(MyList<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
            while(lo < hi) {
                int mi = (lo + hi) >> 1; // middle index
                int c = comp.compare(e, list.get(mi));
                if (c < 0) {    // e < list.get(mi)
                    hi = mi;    // forward to [lo, mi)
                } else if (c > 0) {
                    lo = mi + 1;    // backward to (mi, hi)
                } else {
                    return mi;  // shot at mi!
                }
            }

            return -1; // search failed
        }
    }

    // O(logn), a little faster than BinarySearchA. (see the textbook)
    // Average search length: O(1.50 * logn)
    static class FibSearch<E> implements Searchable<E> {

        @Override
        public int search(MyList<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
            Fibonacci fib = new Fibonacci(hi - lo);
            while (lo < hi) {
                while (hi - lo < fib.get())
                    fib.prev();
                int mi = lo + fib.get() - 1;
                int c = comp.compare(e, list.get(mi));
                if (c < 0)
                    hi = mi;    // forward to [lo, mi)
                else if (c > 0)
                    lo = mi + 1;    // backward to (mi, hi)
                else
                    return mi;  // shot!
            }
            return -1;  // failed
        }
    }

    // O(logn)
    static class BinarySearchB<E> implements Searchable<E> {

        @Override
        public int search(MyList<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
            while (1 < hi - lo) {
                int mi = (lo + hi) >> 1;    // middle index

                if (comp.compare(e, list.get(mi)) < 0) hi = mi;
                else lo = mi;
            }
            return (comp.compare(e, list.get(lo)) == 0) ? lo : -1;
        }
    }

    static class BinarySearchC<E> implements Searchable<E> {

        @Override
        public int search(MyList<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
            while (lo < hi) {
                int mi = (lo + hi) >> 1;
                int c = comp.compare(e, list.get(mi));
                if (c < 0)
                    hi = mi;
                else
                    lo = mi + 1;
            }
            return --lo;
        }
    }
}
