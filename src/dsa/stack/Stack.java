package dsa.stack;

import dsa.vec.MySortedVector;
import dsa.vec.MyVector;

/**
 * Created by teoking on 17-12-21.
 */
public class Stack<T> extends MySortedVector<T> {

    public void push(T e) {
        insert(size(), e);
    }

    public T pop() {
        return remove(size() - 1);
    }

    public T top() {
        return get(size() - 1);
    }

    public boolean empty() {
        return size() == 0;
    }
}
