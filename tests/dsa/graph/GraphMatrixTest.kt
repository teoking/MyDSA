package dsa.graph

import org.junit.Test
import org.junit.Assert.*

class GraphMatrixTest {

    @Test
    fun `test vertex & edge opts`() {
        val gm = GraphMatrix<Int, Int>()
        gm.insert(5)    // 0
        gm.insert(3)    // 1
        gm.insert(9)    // 2
        gm.insert(7)    // 3

        // 类似乘法表的图
        gm.insert(15, 0, 1, Int.MAX_VALUE)  // (0, 1) = 5x3 = 15
        gm.insert(45, 0, 2, Int.MAX_VALUE)  // (0, 2) = 5x9 = 45
        gm.insert(49, 3, 3, Int.MAX_VALUE)  // (3, 3) = 7x7 = 49

        assertTrue(gm.exists(0, 1))
        assertTrue(gm.exists(0, 2))
        assertTrue(gm.exists(3, 3))
        assertFalse(gm.exists(3, 2))
        assertFalse(gm.exists(2, 1))

        assertEquals(2, gm.outDegree(0))
        assertEquals(0, gm.outDegree(2))
        assertEquals(1, gm.inDegree(1))
        assertEquals(1, gm.inDegree(2))

        gm.remove(2)
        assertTrue(gm.exists(0, 1))
        assertTrue(gm.exists(2, 2))
        assertEquals(49, gm.edge(2, 2))
    }

    @Test
    fun `test search`() {

    }
}