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

    @Test
    fun `test tSort`() {
        // Create a graph.
        // Elements:
        // 0 1 2 3 4 5
        // F E D C B A
        val A = 'A'
        val B = 'B'
        val C = 'C'
        val D = 'D'
        val E = 'E'
        val F = 'F'
        val map = hashMapOf(
                F to 0,
                E to 1,
                D to 2,
                C to 3,
                B to 4,
                A to 5
        )
        val gm = GraphMatrix<Char, Int>()
        val insertEdge = fun(e1:Char, e2:Char) {
            gm.insert(1, map[e1]!!, map[e2]!!, Int.MAX_VALUE)
        }

        // insert vertices
        gm.insert(F)
        gm.insert(E)
        gm.insert(D)
        gm.insert(C)
        gm.insert(B)
        gm.insert(A)

        // insert edges
        insertEdge(A, C)
        insertEdge(A, D)
        insertEdge(B, C)
        insertEdge(C, D)
        insertEdge(C, E)
        insertEdge(C, F)
        insertEdge(E, F)

        val stack = gm.tSort(map[A]!!)
        print("TSort sorted: ")
        stack.elem.forEach { c ->
            run {
                if (c != null)
                    print(c + " ")
            }
        }
        assertFalse(stack.empty())
        assertEquals(map.size, stack.size)

        val expected = "BACEFD".toCharArray()
        for (c in expected) {
            assertEquals(c, stack.pop())
        }
    }

    @Test
    fun `test bcc`(){
        // Create a graph.
        // Elements:
        // 0 1 2 3 4 5 6 7 8 9
        // A B C D E F G H I J
        val A = 'A'
        val B = 'B'
        val C = 'C'
        val D = 'D'
        val E = 'E'
        val F = 'F'
        val G = 'G'
        val H = 'H'
        val I = 'I'
        val J = 'J'
        val map = hashMapOf(
                A to 0,
                B to 1,
                C to 2,
                D to 3,
                E to 4,
                F to 5,
                G to 6,
                H to 7,
                I to 8,
                J to 9
        )
        val gm = GraphMatrix<Char, Int>()
        val insertEdge = fun(e1:Char, e2:Char) {
            gm.insert(1, map[e1]!!, map[e2]!!, Int.MAX_VALUE)
        }

        // insert vertices
        gm.insert(A)
        gm.insert(B)
        gm.insert(C)
        gm.insert(D)
        gm.insert(E)
        gm.insert(F)
        gm.insert(G)
        gm.insert(H)
        gm.insert(I)
        gm.insert(J)

        // insert edges
        insertEdge(A, B)
        insertEdge(A, H)
        insertEdge(A, I)
        insertEdge(A, J)

        insertEdge(B, A)
        insertEdge(B, C)

        insertEdge(C, B)
        insertEdge(C, D)
        insertEdge(C, H)

        insertEdge(D, C)
        insertEdge(D, E)
        insertEdge(D, G)

        insertEdge(E, D)
        insertEdge(E, G)

        insertEdge(F, G)

        insertEdge(G, D)
        insertEdge(G, E)
        insertEdge(G, F)

        insertEdge(H, A)
        insertEdge(H, C)

        insertEdge(I, A)
        insertEdge(I, J)

        insertEdge(J, A)
        insertEdge(J, I)

        gm.bcc(map[A]!!)
    }
}