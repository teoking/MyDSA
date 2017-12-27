package dsa.tree;

import dsa.common.ElemComparable;

import javax.xml.soap.Node;

/**
 * Created by teoking on 17-12-27.
 */
public class BinaryTree<T> {

    static class NodeImpl<T> implements BinaryTreeNode<T> {

        T data;
        BinaryTreeNode<T> parent;
        BinaryTreeNode<T> lc;
        BinaryTreeNode<T> rc;
        int height; // maintained by BinaryTree
        int npl; // Null Path Length (for Leftist Heaps, can be replace with height)

        RBColor color; // color for RB-tree.

        NodeImpl() {
            parent = null;
            lc = null;
            rc = null;
            height = 0;
            npl = 1;
            color = RBColor.RB_RED;
        }

        NodeImpl(T e, BinaryTreeNode<T> parent, BinaryTreeNode<T> lc, BinaryTreeNode<T>  rc) {
            this(e, parent, lc, rc, 0, 1, RBColor.RB_RED);
        }

        NodeImpl(T e, BinaryTreeNode<T> parent, BinaryTreeNode<T> lc, BinaryTreeNode<T>  rc, int height, int l, RBColor c) {
            this.data = e;
            this.parent = parent;
            this.lc = lc;
            this.rc = rc;
            height = 0;
            npl = 1;
            color = RBColor.RB_RED;
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public BinaryTreeNode<T> getParent() {
            return parent;
        }

        @Override
        public void setParent(BinaryTreeNode<T> x) {
            parent = x;
        }

        @Override
        public BinaryTreeNode<T> getLC() {
            return lc;
        }

        @Override
        public void setLC(BinaryTreeNode<T> x) {
            lc = x;
        }

        @Override
        public BinaryTreeNode<T> getRC() {
            return rc;
        }

        @Override
        public void setRC(BinaryTreeNode<T> x) {
            rc = x;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void setSize(int size) {

        }

        @Override
        public int getHeight() {
            return height;
        }

        // Called by the BinaryTree
        @Override
        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public void release() {
            parent = null;
            rc = null;
            lc = null;
            height = 0;
        }

        @Override
        public BinaryTreeNode<T> insertAsLC(T e) {
            return lc = new NodeImpl<T>(e, this, null, null);
        }

        @Override
        public BinaryTreeNode<T> insertAsRC(T e) {
            return rc = new NodeImpl<T>(e, this, null, null);
        }

        @Override
        public BinaryTreeNode<T> succ() {
            return null;
        }

        @Override
        public boolean isLessThan(BinaryTreeNode<T> bn, ElemComparable<BinaryTreeNode<T>> comp) {
            return comp.compare(this, bn) < 0;
        }

        @Override
        public boolean isEqualWith(BinaryTreeNode<T> bn, ElemComparable<BinaryTreeNode<T>> comp) {
            return comp.compare(this, bn) == 0;
        }

        @Override
        public boolean isGreatThan(BinaryTreeNode<T> bn, ElemComparable<BinaryTreeNode<T>> comp) {
            return comp.compare(this, bn) > 0;
        }
    }

    /* *********************************
     * BinaryTreeNode status OPTS *
     * *********************************/
    static class Opts {

        public int stature(BinaryTreeNode node) {
            return node != null ? node.getHeight() : -1;
        }

        public boolean isRoot(BinaryTreeNode x) {
            return x != null && x.getParent() == null;
        }

        public boolean isLChild(BinaryTreeNode x) {
            return !isRoot(x) && x == x.getParent().getLC();
        }

        public boolean isRChild(BinaryTreeNode x) {
            return !isRoot(x) && x == x.getParent().getRC();
        }

        public boolean hasParent(BinaryTreeNode x) {
            return !isRoot(x);
        }

        public boolean hasLChild(BinaryTreeNode x) {
            return x != null && x.getLC() != null;
        }

        public boolean hasRChild(BinaryTreeNode x) {
            return x != null && x.getRC() != null;
        }

        public boolean hasChild(BinaryTreeNode x) {
            return hasLChild(x) || hasRChild(x);
        }

        public boolean hasBothChild(BinaryTreeNode x) {
            return hasLChild(x) && hasRChild(x);
        }

        public boolean isLeaf(BinaryTreeNode x) {
            return !hasChild(x);
        }

        public BinaryTreeNode sibling(BinaryTreeNode x) {
            return isLChild(x) ? x.getParent().getRC() : x.getParent().getLC();
        }

        public BinaryTreeNode uncle(BinaryTreeNode x) {
            return isLChild(x.getParent()) ? x.getParent().getParent().getRC()
                    : x.getParent().getParent().getRC();
        }

        public BinaryTreeNode fromParentTo(BinaryTree tree, BinaryTreeNode x) {
            return isRoot(x) ? tree.getRoot() :
                    (isLChild(x) ? x.getParent().getLC()
                            : x.getParent().getRC());
        }

        public void derefFromParent(BinaryTree tree, BinaryTreeNode x) {
            if (!isRoot(x)) {
                if (isLChild(x)) {
                    x.getParent().setLC(null);
                } else {
                    x.getParent().setRC(null);
                }
            }
        }
    }

    static final Opts OPTS = new Opts();
    ElemComparable<BinaryTreeNode<T>> comp;
    int size;
    BinaryTreeNode<T> root;
    // update the height of x
    protected int updateHeight(BinaryTreeNode<T> x) {
        x.setHeight(1 + Math.max(OPTS.stature(x.getLC()), OPTS.stature(x.getRC())));
        return x.getHeight();
    }

    protected void updateHeightAbove(BinaryTreeNode<T> x) {
        while (x != null) {
            updateHeight(x);
            x = x.getParent();
        }
    }

    protected void release() {
        size = 0;
        root = null;
        comp = null;
    }

    // Public methods

    public BinaryTree(ElemComparable<BinaryTreeNode<T>> comp) {
        size = 0;
        root = null;
        this.comp = comp;
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return root == null;
    }

    public BinaryTreeNode<T> getRoot() {
        return root;
    }

    public BinaryTreeNode<T> insertAsRoot(T e) {
        size = 1;
        return root = new NodeImpl<>(e, null, null, null);
    }

    public BinaryTreeNode<T> insertAsLC(BinaryTreeNode<T> x, T e) {
        size++;
        x.insertAsLC(e);
        updateHeightAbove(x);
        return x.getLC();
    }

    public BinaryTreeNode<T> insertAsRC(BinaryTreeNode<T> x, T e) {
        size++;
        x.insertAsRC(e);
        updateHeightAbove(x);
        return x.getRC();
    }

    // 二叉树子树接入算法： 将S当做节点x的左子树接入，S本身置空
    public BinaryTreeNode<T> attachAsLC(BinaryTreeNode<T> x, BinaryTree<T> S) {
        // 接入
        x.setLC(S.root);
        if (x.getLC() != null) {
            x.getLC().setParent(x);
        }

        size += S.size;
        updateHeightAbove(x);   // 更新全树规模与x所有祖先的高度

        S.release();
        return x;   // 释放原树，返回接入位置
    }

    public BinaryTreeNode<T> attachAsRC(BinaryTreeNode<T> x, BinaryTree<T> S) {
        x.setRC(S.root);
        if (x.getRC() != null) {
            x.getRC().setParent(x);
        }

        size += S.size;
        updateHeightAbove(x);

        S.release();
        return x;
    }

    // 删除二叉树中位置x处的节点及后代，返回被删除节点的数值
    public int remove(BinaryTreeNode<T> x) {
        // 切断来自父节点的指针
        OPTS.derefFromParent(this, x);

        updateHeightAbove(x.getParent());   //更新祖先高度
        int n = removeAt(x);    // 删除子树x，更新规模，返回删除节点总数
        size -= n;
        return n;
    }

    // 删除二叉树中位置x处的节点及后代，返回被删除节点的数值
    private int removeAt(BinaryTreeNode<T> x) {
        if (x == null) {
            return 0;
        }

        int n = 1 + removeAt(x.getLC()) + removeAt(x.getRC()); // 递归释放左、右子树
        x.release();
        return n;
    }

    // 二叉树子树分离算法：将子树x从当前树中摘除，将其封装为一颗独立子树返回
    public BinaryTree<T> secede(BinaryTreeNode<T> x) {  // assert: x为二叉树中的合法位置
        // 切断来自父节点的指针
        OPTS.derefFromParent(this, x);

        // 更新原树中所有祖先的高度
        updateHeightAbove(x.getParent());

        // 新树以x为根
        BinaryTree<T> s = new BinaryTree<>(this.comp);
        s.root = x;
        x.setParent(null);

        // 更新规模，返回分离出来的子树
        s.size = x.size();
        size -= s.size;
        return s;
    }

}
