package dsa.list;

import dsa.common.ElemComparable;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by teoking on 17-12-18.
 */
public interface MyList<T> {

    void init();
    int clear();
    void copyNodes(ListNode<T> position, int n);
    // Sort methods
    // void merge

    // Merge sort n nodes begin with p.
    // void mergeSort

    // Selection sort n nodes begin at p.
    void selectionSort(ListNode<T> pos, int n, ElemComparable<T> comp);

    // Insertion sort n nodes begin at p.
    void insertionSort(ListNode<T> pos, int n, ElemComparable<T> comp);

    // Merge sort n nodes begin at p.
    void mergeSort(ListNode<T> pos, int n, ElemComparable<T> comp);

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
     * @param comp
     * @return
     */
    ListNode<T> find(T e, ElemComparable<T> comp);

    ListNode<T> search(T e, ElemComparable<T> comp);
    ListNode<T> search(T e, int n, ListNode<T> p, ElemComparable<T> comp);

    /**
     * Select the largest one after p.
     * @param p
     * @param n
     * @return
     */
    ListNode<T> selectMax(ListNode<T> p, int n, ElemComparable<T> comp);

    // Insertion & Remove
    ListNode<T> insertAsFirst(T e);
    ListNode<T> insertAsLast(T e);
    ListNode<T> insertA(ListNode<T> p, T e);    // insert as successor
    ListNode<T> insertB(ListNode<T> p, T e);    // insert as predecessor
    T remove(ListNode<T> p);
    int deduplicated(ElemComparable<T> comp);
    int uniquify();
    void reverse();

    // Traverse
    //void tranverse();
}
