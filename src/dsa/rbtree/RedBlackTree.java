package dsa.rbtree;

import dsa.bst.BinarySearchTree;
import dsa.common.ElemComparable;
import dsa.tree.BinaryTreeNode;
import dsa.tree.BinaryTreeNode.RBColor;

import static dsa.tree.BinaryTreeNode.RBColor.RB_BLACK;
import static dsa.tree.BinaryTreeNode.RBColor.RB_RED;

/**
 * Created by teoking on 18-1-10.
 */
public class RedBlackTree<T> extends BinarySearchTree<T> {

    // BST::search()等其余接口可直接沿用

    /*RedBlack高度更新条件*/
    static boolean blackHeightUpdated(BinaryTreeNode x) {
        return (OPTS.stature(x.getLC()) == OPTS.stature(x.getRC()))
                && (x.getHeight() == (OPTS.isRed(x) ? OPTS.stature(x.getLC()) : OPTS.stature(x.getLC()) + 1));
    }

    public RedBlackTree(ElemComparable<BinaryTreeNode<T>> comp) {
        super(comp);
    }


    //双红修正
    /******************************************************************************************
     * RedBlack双红调整算法：解决节点x与其父均为红色的问题。分为两大类情况：
     *    RR-1：2次颜色翻转，2次黑高度更新，1~2次旋转，不再递归
     *    RR-2：3次颜色翻转，3次黑高度更新，0次旋转，需要递归
     ******************************************************************************************/
    protected void solveDoubleRed(BinaryTreeNode<T> x) { //x当前必为红
        if (OPTS.isRoot(x)) {
            //若已（递归）转至树根，则将其转黑，整树黑高度也随之递增
            root.setColor(RB_BLACK);
            root.setHeight(root.getHeight() + 1);
            return;
        }
        //否则，x的父亲p必存在

        BinaryTreeNode<T> p = x.getParent();
        //若p为黑，则可终止调整。否则
        if (OPTS.isBlack(p))
            return;

        //既然p为红，则x的祖父必存在，且必为黑色
        BinaryTreeNode<T> g = p.getParent();
        //以下，视x叔父u的颜色分别处理
        BinaryTreeNode<T> u = OPTS.uncle(x);
        if (OPTS.isBlack(u)) {
            //u为黑色（含NULL）时 //*DSA*/printf("  case RR-1:\n");

            //若x与p同侧（即zIg-zIg或zAg-zAg），则
            if (OPTS.isLChild(x) == OPTS.isLChild(p))
                //p由红转黑，x保持红
                p.setColor(RB_BLACK);
            else
                //若x与p异侧（即zIg-zAg或zAg-zIg），则
                //x由红转黑，p保持红
                x.setColor(RB_BLACK);

            //g必定由黑转红
            g.setColor(RB_RED);

            ///// 以上虽保证总共两次染色，但因增加了判断而得不偿失
            ///// 在旋转后将根置黑、孩子置红，虽需三次染色但效率更高

            //曾祖父（great-grand parent）
            BinaryTreeNode<T> gg = g.getParent();

            //调整后的子树根节点
            BinaryTreeNode<T> r = OPTS.replaceInParent(this, g, rotateAt(x));

            //与原曾祖父联接
            r.setParent(gg);
        } else { //若u为红色 //*DSA*/printf("  case RR-2:\n");
            //p由红转黑
            p.setColor(RB_BLACK);
            p.setHeight(p.getHeight() + 1); // p.height++;
            //u由红转黑
            u.setColor(RB_BLACK);
            u.setHeight(u.getHeight() + 1);
            //g若非根，则转红
            if (!OPTS.isRoot(g))
                g.setColor(RB_RED);

            //继续调整g（类似于尾递归，可优化为迭代形式）
            solveDoubleRed(g);
        }
    }

    /******************************************************************************************
     * RedBlack双黑调整算法：解决节点x与被其替代的节点均为黑色的问题
     * 分为三大类共四种情况：
     *    BB-1 ：2次颜色翻转，2次黑高度更新，1~2次旋转，不再递归
     *    BB-2R：2次颜色翻转，2次黑高度更新，0次旋转，不再递归
     *    BB-2B：1次颜色翻转，1次黑高度更新，0次旋转，需要递归
     *    BB-3 ：2次颜色翻转，2次黑高度更新，1次旋转，转为BB-1或BB2R
     ******************************************************************************************/
    //双黑修正
    protected void solveDoubleBlack(BinaryTreeNode<T> r) {
        //r的父亲
        BinaryTreeNode<T> p = r != null ? r.getParent() : hot;
        if (p == null)
            return;
        //r的兄弟
        BinaryTreeNode<T> s = (r == p.getLC()) ? p.getRC() : p.getLC();
        //兄弟s为黑
        if (OPTS.isBlack(s)) {
            //s的红孩子（若左、右孩子皆红，左者优先；皆黑时为NULL）
            BinaryTreeNode<T> t = null;
            if (OPTS.isRed(s.getRC()))
                t = s.getRC();  //右子
            if (OPTS.isRed(s.getLC()))
                t = s.getLC();  //左子
            //黑s有红孩子：BB-1
            if (t != null) {
                //*DSA*/printf("  case BB-1: Child ("); print(s->lc); printf(") of BLACK sibling ("); print(s); printf(") is RED\n");
                //备份原子树根节点p颜色，并对t及其父亲、祖父
                RBColor oldColor = p.getColor();
                // 以下，通过旋转重平衡，并将新子树的左、右孩子染黑
                BinaryTreeNode<T> b = OPTS.replaceInParent(this, p, rotateAt(t));

                //左子
                if (OPTS.hasLChild(b)) {
                    b.getLC().setColor(RB_BLACK);
                    updateHeight(b.getLC());
                }
                //右子
                if (OPTS.hasRChild(b)) {
                    b.getRC().setColor(RB_BLACK);
                    updateHeight(b.getRC());
                }
                //新子树根节点继承原根节点的颜色
                b.setColor(oldColor);
                updateHeight(b);
                //*DSA*/printBinTree(b, 0, 0);
            } else { //黑s无红孩子
                //s转红
                s.setColor(RB_RED);
                s.setHeight(s.getHeight() - 1);

                if (OPTS.isRed(p)) {
                    //BB-2R
                    //*DSA*/printf("  case BB-2R: Both children ("); print(s->lc); printf(") and ("); print(s->rc); printf(") of BLACK sibling ("); print(s); printf(") are BLACK, and parent ("); print(p); printf(") is RED\n"); //s孩子均黑，p红
                    //p转黑，但黑高度不变
                    p.setColor(RB_BLACK);
                    //*DSA*/printBinTree(p, 0, 0);
                } else {
                    //BB-2B
                    //*DSA*/printf("  case BB-2R: Both children ("); print(s->lc); printf(") and ("); print(s->rc); printf(") of BLACK sibling ("); print(s); printf(") are BLACK, and parent ("); print(p); printf(") is BLACK\n"); //s孩子均黑，p黑
                    //p保持黑，但黑高度下降
                    p.setHeight(p.getHeight() - 1);
                    //*DSA*/printBinTree(p, 0, 0);
                    //递归上溯
                    solveDoubleBlack(p);
                }
            }
        } else {
            //兄弟s为红：BB-3
            //*DSA*/printf("  case BB-3: sibling ("); print(s); printf(" is RED\n"); //s红（双子俱黑）
            //s转黑，p转红
            s.setColor(RB_BLACK);
            p.setColor(RB_RED);
            //取t与其父s同侧
            BinaryTreeNode<T> t = OPTS.isLChild(s) ? s.getLC() : s.getRC();
            //对t及其父亲、祖父做平衡调整
            hot = p;
            OPTS.replaceInParent(this, p, rotateAt(t));
            //*DSA*/printBinTree<T>(s, 0, 0);
            solveDoubleBlack(r);  //继续修正r处双黑——此时的p已转红，故后续只能是BB-1或BB-2R
        }
    }

    /**
     * Update height of x.
     * @param x
     * @return
     */
    protected int updateHeight(BinaryTreeNode<T> x) {
        //孩子一般黑高度相等，除非出现双黑
        x.setHeight(Math.max(OPTS.stature(x.getLC()), OPTS.stature(x.getRC())));
        /*DSA*/// 红黑树中各节点左、右孩子的黑高度通常相等
        /*DSA*/// 这里之所以取更大值，是便于在删除节点后的平衡调整过程中，正确更新被删除节点父亲的黑高度
        /*DSA*/// 否则，rotateAt()会根据被删除节点的替代者（高度小一）设置父节点的黑高度
        if (OPTS.isBlack(x)) {
            //若当前节点为黑，则计入黑深度
            x.setHeight(x.getHeight() + 1);
        }
        return x.getHeight();
    } //因统一定义stature(NULL) = -1，故height比黑高度少一，好在不致影响到各种算法中的比较判断

    @Override
    public BinaryTreeNode<T> insert(T e) {
        BinaryTreeNode<T> x = search(e);
        //确认目标不存在（留意对_hot的设置）
        if (x != null) {
            return x;
        }

        //创建红节点x：以_hot为父，黑高度-1
        x = newNode(e, hot, null, null, -1, 0, RB_RED);
        size++;
        solveDoubleRed(x);
        //经双红修正后，即可返回
        return x != null ? x : hot.getParent();
    } //无论e是否存在于原树中，返回时总有x->data == e

    @Override
    public boolean remove(T e) {
        return super.remove(e);
    }
}
