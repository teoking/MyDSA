package dsa.tree;

/**
 * Created by teoking on 17-12-28.
 */
public interface NodeVisitor<T> {

    void visit(T data);
}
