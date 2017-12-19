package dsa.list;

/**
 * Created by teoking on 17-12-18.
 */
// ADT
public class ListNode<T> {

    T data;     // data
    ListNode<T> pred;   // predecessor
    ListNode<T> succ;   // successor

    public ListNode() {}
    public ListNode(T e, ListNode pred, ListNode succ) {
        this.data = e;
        this.pred = pred;
        this.succ = succ;
    }

    public ListNode<T> insertAsPred(T e){
        ListNode<T> x = new ListNode<>(e, pred, this);
        pred.succ = x;
        pred = x;
        return x;
    }
    public ListNode<T> insertAsSucc(T e){
        ListNode<T> x = new ListNode<>(e, this, succ);
        succ.pred = x;
        succ = x;
        return x;
    }

}