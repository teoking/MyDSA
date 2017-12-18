package dsa.list

import org.junit.Test
import org.junit.Assert.*;

class MyLinkedListTest {

    @Test
    fun `test list init`() {
        var list = MyLinkedList<Integer>()
        assertEquals(list.size(), 0)
        assertTrue(list.empty())
    }
}