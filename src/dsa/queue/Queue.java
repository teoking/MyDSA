package dsa.queue;

import dsa.list.ListNode;
import dsa.list.MyLinkedList;

/**
 * Created by teoking on 17-12-25.
 */
public class Queue<T> extends MyLinkedList<T> {

    public void enqueue(T e) {
        insertAsLast(e);
    }

    public T dequeue() {
        return remove(first());
    }

    public ListNode<T> front() {
        return first();
    }
}
