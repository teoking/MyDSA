package dsa.vec;

/**
 * Created by teoking on 17-12-14.
 */
public interface MySortedList<E> {

    int disordered(ElemComparable comp);

    void sort(int lo, int hi);

    int find(E e, ElemComparable comp);

    void deduplicate();

    int search(E e, int lo, int hi, ElemComparable comp, Searchable searchMethod);

    int uniquify(ElemComparable comp);

    public interface Searchable<E> {
        int search(MyList<E> list, E e, int lo, int hi, ElemComparable<E> comp);
    }

    public interface Sortable<E> {
        void sort(MyList<E> list, int lo, int hi, ElemComparable<E> comp);
    }
}
