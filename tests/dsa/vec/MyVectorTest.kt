package dsa.vec

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by teoking on 17-12-14.
 */
class MyVectorTest {

    @Test
    fun `create empty MyArrayList`() {
        var list = MyVector<Integer>()
        assert(list.size() == 0)

        list = MyVector<Integer>(10)
        assertTrue(list.size() == 0)
    }

    @Test
    fun `test insertion`() {
        val list = MyVector<Integer>()
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
        val list = MyVector<Integer>()
        list.insert(Integer(0))
        list.insert(Integer(1))
        list.insert(Integer(2))
        list.insert(Integer(3))

        val e = list.remove(2)

        assertTrue(e.toInt() == 2)
        assertTrue(list.size() == 3)
    }

}