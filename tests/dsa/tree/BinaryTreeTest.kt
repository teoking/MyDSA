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

        tree.insertAsRoot(10)
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

        // test CRUD
        val e5 = tree.insertAsLC(tree.root, 5)
        val e6 = tree.insertAsRC(tree.root, 6)
        assertEquals(3, tree.size)

        val e3 = tree.insertAsLC(e5, 3)
        val e8 = tree.insertAsRC(e5, 8)

        val e7 = tree.insertAsRC(e6, 7)
        val e1 = tree.insertAsLC(e7, 1)

        assertEquals(7, tree.size)
        assertEquals(3, tree.root.height)

        tree.remove(e7)
        assertNull(e7.parent)
        assertEquals(5, tree.size)
        assertEquals(2, tree.root.height)

        // test OPTS


        // test attach

        // test secede



    }
}