package dsa.list;

import dsa.common.ElemComparable;

public class MyLinkedList<T> extends MyAbstractList<T> {

    public MyLinkedList() {
        super();
    }

    @Override
    public void selectionSort(ListNode<T> p, int n, ElemComparable<T> comp) {

    }

    @Override
    public void insertionSort(ListNode<T> p, int n, ElemComparable<T> comp) {
        for (int r = 0; r < n; r++) {
            ListNode<T> found = search(p.data, r, p, comp);
            insertA(found, p.data);
            p = p.succ;
            remove(p.pred);
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
        System.out.println("before p=" + p + " n=" + n);
        System.out.println("p is header =" + (p == header));
        System.out.println("p.pred=" + p.pred);
        while (0 <= n--) {
            if (p.pred == header || comp.compare(((p = p.pred).data), e) <= 0) {
                break;
            }
        }
        System.out.println("after p=" + p + " n=" + n);
        return p;
    }

    @Override
    public ListNode<T> selectMax(ListNode<T> p, int n, ElemComparable<T> comp) {
        return null;
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
