package dsa.tree

import dsa.common.ElemComparable
import org.junit.Test
import org.junit.Assert.*

class BinaryTreeTest {

    class NodeComp : ElemComparable<BinaryTreeNode<Int>> {
        override fun compare(e1: BinaryTreeNode<Int>, e2: BinaryTreeNode<Int>): Int {
            return  e1.data.toInt() - e2.data.toInt()
        }

    }

    private val comp : NodeComp = NodeComp()

    @Test
    fun `test basic interface`() {
        val tree = BinaryTree<Int>(comp)

        assertTrue(tree.empty())

        val e10 = tree.insertAsRoot(10)
        assertFalse(tree.empty())
        /* Tree:
                10
               /  \
              5    6
             / \    \
            3   8    7
                    /
                   1
       */

        // test create
        val e5 = tree.insertAsLC(tree.root, 5)
        val e6 = tree.insertAsRC(tree.root, 6)
        assertEquals(3, tree.size)

        val e3 = tree.insertAsLC(e5, 3)
        val e8 = tree.insertAsRC(e5, 8)

        val e7 = tree.insertAsRC(e6, 7)
        val e1 = tree.insertAsLC(e7, 1)

        assertEquals(7, tree.size)
        assertEquals(3, tree.root.height)

        // test element comparision
        assertTrue(e1.isLessThan(e10, comp))
        assertTrue(e8.isGreatThan(e3, comp))
        assertFalse(e5.isEqualWith(e6, comp))

        // test OPTS
        val o = tree.opts
        assertEquals(e3.height, o.stature(e3))
        assertTrue(o.isRoot(e10))

        assertTrue(o.isLChild(e1))
        assertFalse(o.isRChild(e1))
        assertTrue(o.isRChild(e8))
        assertFalse(o.isLChild(e8))

        assertTrue(o.hasParent(e5))
        assertFalse(o.hasParent(e10))

        assertTrue(o.hasLChild(e5))
        assertFalse(o.hasLChild(e6))

        assertTrue(o.hasRChild(e6))
        assertFalse(o.hasRChild(e7))

        assertTrue(o.hasChild(e5))
        assertFalse(o.hasChild(e1))

        assertTrue(o.hasBothChild(e5))
        assertFalse(o.hasBothChild(e6))
        assertFalse(o.hasBothChild(e8))

        assertTrue(o.isLeaf(e3))
        assertFalse(o.isLeaf(e10))

        assertEquals(e3, o.sibling(e8))
        assertNotEquals(e8, o.sibling(e7))

        assertEquals(e6, o.uncle(e8))
        assertEquals(e6, o.uncle(e3))
        assertEquals(e5, o.uncle(e7))
        assertNull(o.uncle(e1))

        // test remove
        tree.remove(e7)
        assertNull(e7.parent)
        assertEquals(5, tree.size)
        assertEquals(2, tree.root.height)
    }

    @Test
    fun `test attach`() {
        val tree = BinaryTree<Int>(comp)
        assertTrue(tree.empty())

        val e10 = tree.insertAsRoot(10)
        assertFalse(tree.empty())
        /* Tree:
                10
               /  \
              5    6
             / \    \
            3   8    7
                    /
                   1
       */

        // test create
        val e5 = tree.insertAsLC(tree.root, 5)
        val e6 = tree.insertAsRC(tree.root, 6)

        val e3 = tree.insertAsLC(e5, 3)
        val e8 = tree.insertAsRC(e5, 8)

        val e7 = tree.insertAsRC(e6, 7)
        val e1 = tree.insertAsLC(e7, 1)


        /* Tree2:
                13
               /  \
              25   99
         */
        val tree2 = BinaryTree<Int>(comp)
        val e13 = tree2.insertAsRoot(13)
        val e25 = tree2.insertAsLC(e13, 25)
        val e99 = tree2.insertAsRC(e13, 99)

        assertEquals(e8, tree.attachAsLC(e8, tree2))
        assertEquals(4, e10.height)
        assertNull(tree2.root)
        assertEquals(e8, e13.parent)

        /* Tree3:
                111
               /   \
             222   333
         */
        val tree3 = BinaryTree<Int>(comp)
        val e111 = tree3.insertAsRoot(111)
        val e222 = tree3.insertAsLC(e111, 222)
        val e333 = tree3.insertAsRC(e111, 333)

        // failed
        assertNull(tree.attachAsRC(e13, tree3))

        assertEquals(e1, tree.attachAsRC(e1, tree3))
        assertNull(tree3.root)
        assertEquals(13, tree.size)
        assertEquals(e1, e111.parent)
        assertEquals(5, tree.root.height)
    }

    @Test
    fun `test secede`() {
        val tree = BinaryTree<Int>(comp)
        assertTrue(tree.empty())

        val e10 = tree.insertAsRoot(10)
        assertFalse(tree.empty())
        /* Tree:
                10
               /  \
              5    6
             / \    \
            3   8    7
                    /
                   1
       */

        // test create
        val e5 = tree.insertAsLC(tree.root, 5)
        val e6 = tree.insertAsRC(tree.root, 6)

        val e3 = tree.insertAsLC(e5, 3)
        val e8 = tree.insertAsRC(e5, 8)

        val e7 = tree.insertAsRC(e6, 7)
        val e1 = tree.insertAsLC(e7, 1)

        assertEquals(3, tree.root.height)

        val subtree = tree.secede(e6)
        assertNotNull(subtree)
        assertTrue(subtree.opts.isRoot(e6))
        assertEquals(2, tree.root.height)
        assertEquals(2, subtree.root.height)
        assertEquals(4, tree.size)
        assertEquals(3, subtree.size)
    }

}