package dsa.vec;

import dsa.fib.Fibonacci;

/**
 * Created by teoking on 17-12-14.
 */
public class MySortedVector<E> extends MyVector<E> implements SortedVector<E> {

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

    // linear search, disorder or ordered, O(n)
    @Override
    public int find(E e, ElemComparable comp) {
        for (int i = 0; i < size; i++) {
            if (comp.compare(e, get(i)) == 0)
                return i;
        }
        return -1;
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

    ////////////////////////////////// Search class //////////////////////////////////////////

    // O(n)
    static class LinearSearch<E> implements Searchable<E> {

        @Override
        public int search(Vector<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
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
        public int search(Vector<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
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
        public int search(Vector<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
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
        public int search(Vector<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
            while (1 < hi - lo) {
                int mi = (lo + hi) >> 1;    // middle index

                if (comp.compare(e, list.get(mi)) < 0) hi = mi;
                else lo = mi;
            }
            return (comp.compare(e, list.get(lo)) == 0) ? lo : -1;
        }
    }

    // O(logn)
    static class BinarySearchC<E> implements Searchable<E> {

        @Override
        public int search(Vector<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
            while (lo < hi) {
                int mi = (lo + hi) >> 1;
                int c = comp.compare(e, list.get(mi));
                if (c < 0)
                    hi = mi;    // forward to [lo, mi)
                else
                    lo = mi + 1;    // forward to (mi, hi)
            }
            return --lo;
        }
    }

    // O(loglogn)
    static class InterpolationSearch<E> implements Searchable<E> {

        @Override
        public int search(Vector<E> list, E e, int lo, int hi, ElemComparable<E> comp) {
            int mi;
            int c;
            if (hi >= list.size()) hi = list.size() - 1;

            while (comp.compare(list.get(lo), list.get(hi)) != 0 // A[hi] != A[lo] && A[lo] <= e <= A[hi]
                    && comp.compare(list.get(lo), e) <= 0
                    && comp.compare(list.get(hi), e) >= 0) {
                // mi = lo + (hi - lo) * (e - A[lo]) / (A[hi] - A[lo])
                mi = lo + (hi - lo) * (comp.compare(e, list.get(lo))) / (comp.compare(list.get(hi), list.get(lo)));
                c = comp.compare(list.get(mi), e);
                if (c < 0) {
                    lo = mi + 1;
                } else if (c > 0) {
                    hi = mi - 1;
                } else
                    return mi;
            }

            if (comp.compare(e, list.get(lo)) == 0) {
                return lo;
            } else
                return -1;
        }
    }

    ////////////////////////////////// Sort class //////////////////////////////////////////

    // O(n^2)
    static class BubbleSort<E> implements Sortable<E> {

        @Override
        public void sort(Vector<E> list, int lo, int hi, ElemComparable<E> comp) {
            while (!bubble(list, lo, hi, comp))
                ;
        }

        boolean bubble(Vector<E> list, int lo, int hi, ElemComparable<E> comp) {
            boolean sorted = true;  // is already sorted?
            while (++lo < hi) {
                if (comp.compare(list.get(lo - 1), list.get(lo)) > 0) {
                    sorted = false; // not sorted
                    list.swap(lo - 1, lo);
                }
            }
            return sorted;
        }
    }

    // O(nlogn)
    static class MergeSort<E> implements Sortable<E> {

        @Override
        public void sort(Vector<E> list, int lo, int hi, ElemComparable<E> comp) {
            if (hi - lo < 2) return;
            int mi = (lo + hi) / 2;     // mi as the center
            sort(list, lo, mi, comp);   // [lo, mi)
            sort(list, mi, hi, comp);   // [mi, hi)
            merge(list, lo, mi, hi, comp);
        }

        // O(n)
        private void merge(Vector<E> list, int lo, int mi, int hi, ElemComparable<E> comp) {
            E[] A = ((MyVector<E>) list).elem;
            int lb = mi - lo;
            E[] B = (E[]) new Object[lb];   // prev sub array: B[0, lb) = elem[lo, mi)
            for (int i = 0; i < lb; B[i] = list.get(lo + i++))  // copy elem[lo, mi)
                ;

            int lc = hi - mi;
            // notice: last sub array is elem[mi, hi), that is vector C in the textbook
            // The following codes add the smaller one of B[j] and C[k] to the end of A.
            for (int i = lo, j = 0, k = 0; (j < lb) || (k < lc);) {
                if ((j < lb) && (!(k < lc) || (comp.compare(B[j], A[mi + k]) <= 0)))
                    A[i++] = B[j++];
                if ((k < lc) && (!(j < lb) || (comp.compare(A[mi + k], B[j]) < 0)))
                    A[i++] = A[mi + k++];
            }
        }

    }

}
