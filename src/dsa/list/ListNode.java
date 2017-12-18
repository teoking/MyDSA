package dsa.list;

/**
 * Created by teoking on 17-12-18.
 */
// ADT
public abstract class ListNode<T> {

    T data;     // data
    ListNode<T> pred;   // predecessor
    ListNode<T> succ;   // successor

    public ListNode() {}
    public ListNode(T e, ListNode pred, ListNode succ) {
        this.data = e;
        this.pred = pred;
        this.succ = succ;
    }

    abstract ListNode<T> insertAsPred(T e);
    abstract  ListNode<T> insertAsSucc(T e);
}
