package dsa.list;

import dsa.common.ElemComparable;

public class MyLinkedList<T> extends MyAbstractList<T> {

    public MyLinkedList() {
        super();
    }

    // Sorting a number of n elements begin at p.
    // O(n^2) : selectMax cost O(n) whatever in the best or worst case.
    @Override
    public void selectionSort(ListNode<T> p, int n, ElemComparable<T> comp) { // valid(p) && rank(p) + n <= size
        ListNode<T> head = p.pred;
        ListNode<T> tail = p;
        for (int i = 0; i < n; i++) {
            tail = tail.succ;   // (head, tail) is to be sorted.
        }
        while (1 < n) {     // sort between 2 elements at least
            ListNode<T> max = selectMax(head.succ, n, comp);      // find the max one
            insertB(tail, remove(max));     // move it to the end of the disordered section
            tail = tail.pred;
            n--;
        }
    }

    // O(n^2)
    @Override
    public void insertionSort(ListNode<T> p, int n, ElemComparable<T> comp) {
        for (int r = 0; r < n; r++) {
            ListNode<T> found = search(p.data, r, p, comp);
            insertA(found, p.data);
            p = p.succ;
            remove(p.pred);
        }
    }

    // O(nlogn)
    @Override
    public void mergeSort(ListNode<T> p, int n, ElemComparable<T> comp) {
        if (n < 2) return;
        int m = n >> 1;     // center as the boundary
        ListNode<T> q = p;
        for (int i = 0; i < m; i++) {
            q = q.succ;     // divide the list to two parts.
        }
        mergeSort(p, m, comp);      // sort the first part
        mergeSort(q, n - m, comp);      // sort the last part.
        merge(p, m, this, q, n - m, comp);    // merge
    }

    // merge the n elements begin at p in this list, with the m elements begin at q in the L.
    private void merge(ListNode<T> p, int n, MyList<T> L, ListNode<T> q, int m, ElemComparable<T> comp) {
        // assert: this.valid(p) && rank(p) + n <= size && this.sorted(p, n)
        //          L.valid(q) && rank(q) + m <= L.size && L.sorted(q, m)
        // notice: possible case in kind of merge sort, this == L && rank(p) + n == rank(q)
        ListNode<T> pp = p.pred;        // save p.pred
        while(0 < m) {      //
            if ((0 < n) && (comp.compare(p.data, q.data) <= 0)) {
                if (q == (p = p.succ)) {
                    break;
                }
                n--;
            } else {
                insertB(p, L.remove((q = q.succ).pred));
                m--;
            }
            p = pp.succ;    // the new start point after merging.
        }
    }

    @Override
    public T get(int i) {
        ListNode<T> p = first();
        while (0 < i--)
            p = p.succ;
        return p.data;
    }

    @Override
    public int disordered() {
        // TODO to be implemented
        return 0;
    }

    // O(n)
    @Override
    public ListNode<T> find(T e, ElemComparable<T> comp) {
        return find(e, size, trailer, comp);
    }

    @Override
    public ListNode<T> search(T e, ElemComparable<T> comp) {
        return search(e, size, trailer, comp);
    }

    @Override
    public ListNode<T> search(T e, int n, ListNode<T> p, ElemComparable<T> comp) {
        // assert: 0 <= n <= rank of p < size
        while (0 <= n--) {
            p = p.pred;
            if (p.data == null || comp.compare(p.data, e) <= 0) {
                break;
            }
        }
        return p;
    }

    // Select the max one in the number of n elements begin with p.
    // O(n)
    @Override
    public ListNode<T> selectMax(ListNode<T> p, int n, ElemComparable<T> comp) {
        ListNode<T> max = p;    // the max one is p temporarily
        for (ListNode<T> cur = p; 1 < n; n--) {
            if (comp.compare((cur = cur.succ).data, max.data) > 0) {    // current is larger than the max one
                max = cur;  // update the max
            }
        }
        return max;
    }

    // O(n^2)
    @Override
    public int deduplicated(ElemComparable<T> comp) {
        if (size < 2) return 0;
        int oldSize = size;
        ListNode<T> p = header;
        int r = 0;
        while (trailer != (p = p.succ)) {
            ListNode<T> q = find(p.data, r, p, comp);
            if (q != null) {
                remove(q);
            } else {
                r++;
            }
        }
        return oldSize - size;
    }

    // O(n)
    @Override
    public int uniquify() {
        if (size < 2)
            return 0;
        int oldSize = size;
        ListNode<T> p = first();
        ListNode<T> q;
        while (trailer != (q = p.succ)) {
            if (p.data != q.data) {
                p = q;
            } else {
                remove(q);
            }
        }
        return oldSize - size;
    }

    @Override
    public void reverse() {

    }

    private ListNode<T> find(T data, int r, ListNode<T> p, ElemComparable<T> comp) {
        while (0 > r--) {
            if (p.pred != null && comp.compare(data, (p = p.pred).data) == 0) {
                return p;
            }
        }
        return null;
    }

}
