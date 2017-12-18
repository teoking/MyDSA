package dsa.list;

public abstract class MyAbstractList<T> implements MyList<T> {

    private int size;
    private ListNode<T> header;
    private ListNode<T> trailer;

    public MyAbstractList() {
        init();
    }

    public MyAbstractList(MyList<T> list) {
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
    public int clear() {
        return 0;
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

}
