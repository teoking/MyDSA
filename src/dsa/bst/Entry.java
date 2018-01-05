package dsa.bst;

import dsa.common.ElemComparable;

/**
 * Created by teoking on 18-1-5.
 */
public class Entry<K, V> {
    K key;
    V value;
    ElemComparable<K> comp;

    Entry(K k, V v, ElemComparable<K> comp) {
        key = k;
        value = v;
        this.comp = comp;
    }

    Entry(Entry<K, V> e) {
        key = e.key;
        value = e.value;
        comp = e.comp;
    }

    boolean isLessThan(Entry<K, V> e) {
        return comp.compare(key, e.key) < 0;
    }

    boolean isBiggerThan(Entry<K, V> e) {
        return comp.compare(key, e.key) > 0;
    }

    boolean isEqual(Entry<K, V> e) {
        return comp.compare(key, e.key) == 0;
    }

    boolean isNotEqual(Entry<K, V> e) {
        return comp.compare(key, e.key) != 0;
    }
}
