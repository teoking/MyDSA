package dsa.vec

import org.junit.Assert.*
import org.junit.Test
import java.util.ArrayList

/**
 * Created by teoking on 17-12-14.
 */
class MyArrayListTest {

    @Test
    fun `create empty MyArrayList`() {
        var list = MyArrayList<Integer>()
        assert(list.size() == 0)

        list = MyArrayList<Integer>(10)
        assertTrue(list.size() == 0)
    }

    @Test
    fun `test insertion`() {
        val list = MyArrayList<Integer>()
        list.insert(Integer(0))
        list.insert(Integer(1))

        assertTrue(list.get(0).toInt() == 0)
        assertTrue(list.get(1).toInt() == 1)

        var exception: IndexOutOfBoundsException? = null
        try {
            list.insert(10, Integer(9))
        } catch (e: IndexOutOfBoundsException) {
            exception = e
        }
        assertTrue(exception != null)

        list.insert(2, Integer(2))
        assertTrue(list.get(2).toInt() == 2)

        assertTrue(list.size() == 3)
    }

    @Test
    fun `test remove`() {
        val list = MyArrayList<Integer>()
        list.insert(Integer(0))
        list.insert(Integer(1))
        list.insert(Integer(2))
        list.insert(Integer(3))

        val e = list.remove(2)

        assertTrue(e.toInt() == 2)
        assertTrue(list.size() == 3)
    }

}