package dsa.tree;

import dsa.common.ElemComparable;

/**
 * Created by teoking on 17-12-27.
 */
public interface BinaryTreeNode<T> {

    // Node color.
    enum RBColor {
        RB_RED,
        RB_BLACK
    }

    // OPTS

    T getData();
    BinaryTreeNode<T> getParent();
    void setParent(BinaryTreeNode<T> x);
    BinaryTreeNode<T> getLC();
    void setLC(BinaryTreeNode<T> x);
    BinaryTreeNode<T> getRC();
    void setRC(BinaryTreeNode<T> x);

    // the counts of children of current node, the size of it's subtree
    int size();

    int getHeight();
    void setHeight(int height);

    void release();

    /** Insert as the left child of the node. */
    BinaryTreeNode<T> insertAsLC(T e);

    /** Insert as the right child of the node. */
    BinaryTreeNode<T> insertAsRC(T e);

    /** Successor of the node. */
    BinaryTreeNode<T> succ();

    /** The node is less than bn. */
    boolean isLessThan(BinaryTreeNode<T> bn, ElemComparable<BinaryTreeNode<T>> comp);

    /** True if bn is equal with the node. */
    boolean isEqualWith(BinaryTreeNode<T> bn, ElemComparable<BinaryTreeNode<T>> comp);

    /** True if the node is great than bn. */
    boolean isGreatThan(BinaryTreeNode<T> bn, ElemComparable<BinaryTreeNode<T>> comp);

}
