package dsa.list;

/**
 * Created by teoking on 17-12-18.
 */
public interface MyList<T> {

    void init();
    int clear();
    void copyNodes(ListNode targetNode, int copiedNodesNumber);
    // Sort methods
    // void merge

    // Merge sort n nodes begin with p.
    // void mergeSort

    // Selection sort n nodes begin wih p.
    void selectionSort(ListNode pos, int n);

    // Insertion sort n nodes begin wih p.
    void insertionSort(ListNode pos, int n);

    int size();
    boolean empty();
    T get(int i);
    ListNode<T> first();
    ListNode<T> last();
    boolean valid(ListNode p);
    int disordered();

    /**
     * Search e in disordered {@MyList}
     * @param e
     * @return
     */
    ListNode<T> find(T e);

    ListNode<T> search(T e);

    /**
     * Select the largest one after p.
     * @param p
     * @param n
     * @return
     */
    ListNode<T> selectMax(ListNode<T> p, int n);

    // Insertion & Remove
    ListNode<T> insertAsFirst(T e);
    ListNode<T> insertAsLast(T e);
    ListNode<T> insertA(ListNode<T> p, T e);    // insert as successor
    ListNode<T> insertB(ListNode<T> p, T e);    // insert as predecessor
    int deduplicated();
    int uniquify();
    void reverse();

    // Traverse
    //void tranverse();
}
