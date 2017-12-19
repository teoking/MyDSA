package dsa.list;

import java.io.IOException;
import java.io.OutputStream;

public abstract class MyAbstractList<T> implements MyList<T> {

    protected int size;
    protected ListNode<T> header;
    protected ListNode<T> trailer;

    public MyAbstractList() {
        init();
    }

    @Override
    public void init() {
        header = new ListNode<>();
        trailer = new ListNode<>();
        header.succ = trailer;
        header.pred = null;
        trailer.succ = null;
        trailer.pred = header;
        size = 0;
    }

    @Override
    public void copyNodes(ListNode<T> p, int n) {
        init();
        while (n-- > 0) {
            insertAsLast(p.data);
            p = p.succ;
        }
    }

    // O(n)
    @Override
    public int clear() {
        int oldSize = size;
        while (0 < size)
            remove(header.succ);
        return oldSize;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        return size <= 0;
    }

    @Override
    public ListNode<T> first() {
        return header.succ;
    }

    @Override
    public ListNode<T> last() {
        return trailer.pred;
    }

    @Override
    public boolean valid(ListNode p) {
        return p != null && (trailer != p) && (header != p);
    }

    @Override
    public ListNode<T> insertAsFirst(T e) {
        size++;
        return header.insertAsSucc(e);
    }

    @Override
    public ListNode<T> insertAsLast(T e) {
        size++;
        return trailer.insertAsPred(e);
    }

    @Override
    public ListNode<T> insertA(ListNode<T> p, T e) {
        size++;
        return p.insertAsSucc(e);
    }

    @Override
    public ListNode<T> insertB(ListNode<T> p, T e) {
        size++;
        return p.insertAsPred(e);
    }

    @Override
    public T remove(ListNode<T> p) {
        T e = p.data;
        p.pred.succ = p.succ;
        p.succ.pred = p.pred;
        size--;
        return e;
    }
}
