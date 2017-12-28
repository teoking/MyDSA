package dsa.tree;

import dsa.queue.Queue;
import dsa.stack.Stack;

/**
 * Created by teoking on 17-12-28.
 */
public class BinaryTreeTraversal<T> {

    // R means Recursive

    public void travPreorder_R(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        if (x == null)
            return;

        vst.visit(x.getData());
        travPreorder_R(x.getLC(), vst);
        travPreorder_R(x.getRC(), vst);
    }

    public void travPostorder_R(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        if (x == null)
            return;

        travPostorder_R(x.getLC(), vst);
        travPostorder_R(x.getRC(), vst);
        vst.visit(x.getData());
    }

    public void travInorder_R(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        if (x == null)
            return;

        travInorder_R(x.getLC(), vst);
        vst.visit(x.getData());
        travInorder_R(x.getRC(), vst);
    }

    // I = Iterate

    public void travPreorder_I1(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        Stack<BinaryTreeNode<T>> stack = new Stack<>();

        // push root node
        if (x != null) {
            stack.push(x);
        }

        while (!stack.empty()) {
            x = stack.pop();
            vst.visit(x.getData());
            if (BinaryTree.OPTS.hasRChild(x)) {
                stack.push(x.getRC());
            }
            if (BinaryTree.OPTS.hasLChild(x)) {
                stack.push(x.getLC());
            }
        }
    }

    /////////////////////////////////////////////////////////////////////
    private void visitAlongLeftBranch_I2(BinaryTreeNode<T> x, NodeVisitor<T> vst,
                                      Stack<BinaryTreeNode<T>> stack) {
        while (x != null) {
            vst.visit(x.getData());
            if (x.getRC() != null) {
                stack.push(x.getRC());
            }
            x = x.getLC();  // 沿左分之深入一层
        }
    }

    public void travPreorder_I2(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        Stack<BinaryTreeNode<T>> stack = new Stack<>();
        while (true) {
            visitAlongLeftBranch_I2(x, vst, stack);
            if (stack.empty())
                break;
            x = stack.pop();
        }
    }

    /////////////////////////////////////////////////////////////////////
    //从当前节点出发，沿左分支不断深入，直至没有左分支的节点
    private void goAlongLeftBranch_in(BinaryTreeNode<T> x, Stack<BinaryTreeNode<T>> stack) {
        //当前节点入栈后随即向左侧分支深入，迭代直到无左孩子
        while (x != null) {
            stack.push(x);
            x = x.getLC();
        }
    }

    public void travInorder_I1(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        Stack<BinaryTreeNode<T>> stack = new Stack<>();
        while (true) {
            goAlongLeftBranch_in(x, stack);
            if (stack.empty())
                break;

            x = stack.pop();
            vst.visit(x.getData());
            x = x.getRC();
        }
    }

    /////////////////////////////////////////////////////////////////////
    public void travInorder_I2(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        Stack<BinaryTreeNode<T>> stack = new Stack<>();
        while (true) {
            if (x != null) {
                stack.push(x);
                x = x.getLC();
            } else if (!stack.empty()) {
                x = stack.pop();
                vst.visit(x.getData());
                x = x.getRC();
            } else
                break;
        }
    }

    // 不需要辅助栈，仅需要O(1)辅助空间
    public void travInorder_I3(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        boolean backtrack = false;
        while (true)
            if (!backtrack && BinaryTree.OPTS.hasLChild(x))
                x = x.getLC();
            else {
                vst.visit(x.getData());
                if (BinaryTree.OPTS.hasRChild(x)) {
                    x = x.getRC();
                    backtrack = false;
                } else {
                    if ((x = x.succ()) == null)
                        break;
                    backtrack = true;
                }
            }
    }

    /////////////////////////////////////////////////////////////////////
    void gotoHLVFL(Stack<BinaryTreeNode<T>> stack) {
        BinaryTreeNode<T> x;
        while ((x = stack.top()) != null) {
            if (BinaryTree.OPTS.hasLChild(x)) {
                if (BinaryTree.OPTS.hasRChild(x)) {
                    stack.push(x.getRC());
                }
                stack.push(x.getLC());
            } else {
                stack.push(x.getRC());
            }
        }
        stack.pop();
    }

    public void travPostorder_I(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        Stack<BinaryTreeNode<T>> stack = new Stack<>();
        if (x != null)
            stack.push(x);

        while (!stack.empty()) {
            if (stack.top() != x.getParent())
                gotoHLVFL(stack);
            x = stack.pop();
            vst.visit(x.getData());
        }
    }

    /////////////////////////////////////////////////////////////////////
    // level-order traversal: 广度优先遍历或深度优先遍历，先上后下，先左后右
    void travLevel(BinaryTreeNode<T> x, NodeVisitor<T> vst) {
        Queue<BinaryTreeNode<T>> queue = new Queue<>();
        queue.enqueue(x);
        while (!queue.empty()) {
            BinaryTreeNode<T> node = queue.dequeue();
            vst.visit(node.getData());
            if (BinaryTree.OPTS.hasLChild(node))
                queue.enqueue(node.getLC());
            if (BinaryTree.OPTS.hasRChild(node))
                queue.enqueue(node.getRC());

        }
    }
}
