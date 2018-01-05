package dsa.bst;

import dsa.common.ElemComparable;
import dsa.tree.BinaryTree;
import dsa.tree.BinaryTreeNode;

/**
 * Created by teoking on 18-1-5.
 */
public class BinarySearchTree<T> extends BinaryTree<T> {

    //“命中”节点的父亲
    BinaryTreeNode<T> hot;

    public BinarySearchTree(ElemComparable<BinaryTreeNode<T>> comp) {
        super(comp);
        hot = null;
    }

    /******************************************************************************************
     * 按照“3 + 4”结构联接3个节点及其四棵子树，返回重组之后的局部子树根节点位置（即b）
     * 子树根节点与上层节点之间的双向联接，均须由上层调用者完成
     * 可用于AVL和RedBlack的局部平衡调整
     ******************************************************************************************/
    //按照“3 + 4”结构，联接3个节点及四棵子树
    protected BinaryTreeNode<T> connect34(BinaryTreeNode<T> a,
                                          BinaryTreeNode<T> b,
                                          BinaryTreeNode<T> c,
                                          BinaryTreeNode<T> T0,
                                          BinaryTreeNode<T> T1,
                                          BinaryTreeNode<T> T2,
                                          BinaryTreeNode<T> T3) {
        //*DSA*/print(a); print(b); print(c); printf("\n");
        a.setLC(T0);
        if (T0 != null) T0.setParent(a);
        a.setRC(T1);
        if (T1 != null) T1.setParent(a);
        updateHeight(a);
        c.setLC(T2);
        if (T2 != null) T2.setParent(c);
        c.setRC(T3);
        if (T3 != null) T3.setParent(c);
        updateHeight(c);
        b.setLC(a);
        a.setParent(b);
        b.setRC(c);
        c.setParent(b);
        updateHeight(b);
        return b; //该子树新的根节点
    }

    /******************************************************************************************
     * BST节点旋转变换统一算法（3节点 + 4子树），返回调整之后局部子树根节点的位置
     * 注意：尽管子树根会正确指向上层节点（如果存在），但反向的联接须由上层函数完成
     ******************************************************************************************/
    protected BinaryTreeNode<T> rotateAt(BinaryTreeNode<T> v) {
        /*DSA*/
        if (v == null) {
            System.out.println("\nFail to rotate a null node\n");
            System.exit(-1);
        }
        BinaryTreeNode<T> p = v.getParent();
        BinaryTreeNode<T> g = p.getParent(); //视v、p和g相对位置分四种情况
        if (OPTS.isLChild(p)) /* zig */
            if (OPTS.isLChild(v)) { /* zig-zig */ //*DSA*/printf("\tzIg-zIg: ");
                p.setParent(g.getParent()); //向上联接
                return connect34(v, p, g, v.getLC(), v.getRC(), p.getRC(), g.getRC());
            } else { /* zig-zag */  //*DSA*/printf("\tzIg-zAg: ");
                v.setParent(g.getParent()); //向上联接
                return connect34(p, v, g, p.getLC(), v.getLC(), v.getRC(), g.getRC());
            }
        else  /* zag */
            if (OPTS.isRChild(v)) { /* zag-zag */ //*DSA*/printf("\tzAg-zAg: ");
                p.setParent(g.getParent()); //向上联接
                return connect34(g, p, v, g.getLC(), p.getLC(), v.getLC(), v.getRC());
            } else { /* zag-zig */  //*DSA*/printf("\tzAg-zIg: ");
                v.setParent(g.getParent()); //向上联接
                return connect34(g, v, p, g.getLC(), v.getLC(), v.getRC(), p.getRC());
            }
    }

    public BinaryTreeNode<T> search(T e) {
        BinaryTreeNode<T> node = newNode(e);
        return searchIn(getRoot(), node, hot);
    }

    private BinaryTreeNode<T> searchIn(BinaryTreeNode<T> v, BinaryTreeNode<T> e, BinaryTreeNode<T> hot) {
        if (v != null || e == v.getData())
            return v;
        hot = v;
        //返回时，返回值指向命中节点（或假想的通配哨兵），hot指向其父亲（退化时为初始值NULL）
        return searchIn(comp.compare(e, v) < 0 ? v.getLC() : v.getRC(), e, hot);
    }

    //将关键码e插入BST树中
    public BinaryTreeNode<T> insert(T e) {
        //确认目标不存在（留意对_hot的设置）
        BinaryTreeNode<T> x = search(e);
        if (x != null)
            return x;

        x = newNode(e, hot);
        //更新x及其历代祖先的高度
        updateHeightAbove(x);
        size++;
        //新插入的节点，必为叶子
        //无论e是否存在于原树中，返回时总有x->data == e
        return x;
    }

    //从BST树中删除关键码e
    public boolean remove(T e) {
        //确认目标存在（留意_hot的设置）
        BinaryTreeNode<T> x = search(e);
        if (x == null)
            return false;

        //实施删除
        removeAt(x, hot);
        size--;

        updateHeightAbove(hot);
        return true;
    }

    /******************************************************************************************
     * BST节点删除算法：删除位置x所指的节点（全局静态模板函数，适用于AVL、Splay、RedBlack等各种BST）
     * 目标x在此前经查找定位，并确认非NULL，故必删除成功；与searchIn不同，调用之前不必将hot置空
     * 返回值指向实际被删除节点的接替者，hot指向实际被删除节点的父亲——二者均有可能是NULL
     ******************************************************************************************/
    private BinaryTreeNode<T> removeAt(BinaryTreeNode<T> x, BinaryTreeNode<T> hot) {
        //实际被摘除的节点，初值同x
        BinaryTreeNode<T> w = x;
        //实际被删除节点的接替者
        BinaryTreeNode succ;

        //若*x的左子树为空，则可
        if (!OPTS.hasLChild(x)) {
            //直接将*x替换为其右子树
            succ = x = x.getRC();
        }
        //若右子树为空，则可
        else if (!OPTS.hasRChild(x)) {
            succ = x = x.getLC();
        }
        //若左右子树均存在，则选择x的直接后继作为实际被摘除节点，为此需要
        else {
            w = w.succ(); //（在右子树中）找到*x的直接后继*w
            //交换*x和*w的数据元素
            T tmp = x.getData();
            x.setData(w.getData());
            w.setData(tmp);

            BinaryTreeNode<T> u = w.getParent();
            //隔离节点*w
            if (u == x) {
                succ = w.getRC();
                u.setRC(w.getRC());
            } else {
                succ = w.getRC();
                u.setLC(w.getRC());
            }
        }
        //记录实际被删除节点的父亲
        hot = w.getParent();
        //并将被删除节点的接替者与hot相联
        if (succ != null) succ.setParent(hot);
        w.release();
        //释放被摘除节点，返回接替者
        return succ;
    }

}
