package dsa.vec;

/**
 * Created by teoking on 17-12-13.
 */
public interface MyList<E> {

    int size();

    E get(int r);

    int insert(int r, E e);

    int insert(E e);

    E remove(int r);

    void traverse();
}
