package dsa.tree

import dsa.common.ElemComparable
import org.junit.Test
import org.junit.Assert.*

/**
 * Created by teoking on 17-12-28.
 */
class BinaryTreeTranversalTest {

    val preorderStr = "i->d->c->a->b->h->f->e->g->l->k->j->n->m->p->o->"
    val postorderStr = "b->a->c->e->g->f->h->d->j->k->m->o->p->n->l->i->"
    val inorderStr = "a->b->c->d->e->f->g->h->i->j->k->l->m->n->o->p->"
    val levelorderStr = "i->d->l->c->h->k->n->a->f->j->m->p->b->e->g->o->"

    class Visitor : NodeVisitor<Char> {

        var sb = StringBuilder()

        override fun visit(data: Char) {
            sb.append(String.format("%c->", data))
        }

        fun getResultString() : String {
            return sb.toString()
        }

        fun clearResultString() {
            sb = StringBuilder()
        }
    }

    class CharComp : ElemComparable<BinaryTreeNode<Char>> {
        override fun compare(e1: BinaryTreeNode<Char>, e2: BinaryTreeNode<Char>): Int {
            return e1.data.toInt() - e2.data.toInt()
        }
    }

    private val visitor : NodeVisitor<Char> = Visitor()
    private val vRef : Visitor = visitor as Visitor
    private val comp : ElemComparable<BinaryTreeNode<Char>> = CharComp()

    @Test
    fun `run traversals`() {
        val tree = createTestTree()

        val trav = BinaryTreeTraversal<Char>()

        ////// Pre order //////
        print("pre order: ")
        trav.travPreorder_R(tree.root, visitor)
        assertEquals(preorderStr, vRef.getResultString())
        println(vRef.getResultString())
        vRef.clearResultString()

        trav.travPreorder_I1(tree.root, visitor)
        assertEquals(preorderStr, vRef.getResultString())
        vRef.clearResultString()

        trav.travPreorder_I2(tree.root, visitor)
        assertEquals(preorderStr, vRef.getResultString())
        vRef.clearResultString()

        ////// Post order //////
        print("post order: ")
        trav.travPostorder_R(tree.root, visitor)
        assertEquals(postorderStr, vRef.getResultString())
        println(vRef.getResultString())
        vRef.clearResultString()

        trav.travPostorder_I(tree.root, visitor)
        assertEquals(postorderStr, vRef.getResultString())
        vRef.clearResultString()

        ////// In order //////
        print("in order: ")
        trav.travInorder_R(tree.root, visitor)
        assertEquals(inorderStr, vRef.getResultString())
        println(vRef.getResultString())
        vRef.clearResultString()

        trav.travInorder_I1(tree.root, visitor)
        assertEquals(inorderStr, vRef.getResultString())
        vRef.clearResultString()

        trav.travInorder_I2(tree.root, visitor)
        assertEquals(inorderStr, vRef.getResultString())
        vRef.clearResultString()

        trav.travInorder_I3(tree.root, visitor)
        assertEquals(inorderStr, vRef.getResultString())
        vRef.clearResultString()

        ////// level order //////
        print("level order: ")
        trav.travLevel(tree.root, visitor)
        assertEquals(levelorderStr, vRef.getResultString())
        println(vRef.getResultString())
        vRef.clearResultString()
    }


    /* Tree:
                            i
                          /    \
                         d       l
                       /   \    /  \
                      c     h   k   n
                     /     /   /   / \
                    a     f   j   m   p
                     \   / \         /
                      b  e  g       o
     */
    private fun createTestTree() : BinaryTree<Char> {
        val tree = BinaryTree<Char>(comp)
        var i = tree.insertAsRoot('i')
        val d = tree.insertAsLC(i, 'd')
        val l = tree.insertAsRC(i, 'l')

        val c = tree.insertAsLC(d, 'c')
        val h = tree.insertAsRC(d, 'h')
        val k = tree.insertAsLC(l, 'k')
        val n = tree.insertAsRC(l, 'n')

        val a = tree.insertAsLC(c, 'a')
        val f = tree.insertAsLC(h, 'f')
        val j = tree.insertAsLC(k, 'j')
        val m = tree.insertAsLC(n, 'm')
        val p = tree.insertAsRC(n, 'p')

        val b = tree.insertAsRC(a, 'b')
        val e = tree.insertAsLC(f, 'e')
        val g = tree.insertAsRC(f, 'g')
        val o = tree.insertAsLC(p, 'o')

        return tree
    }
}