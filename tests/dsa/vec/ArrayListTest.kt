package dsa.vec

import org.junit.Assert.*
import org.junit.Test
import java.util.ArrayList

/**
 * Created by teoking on 17-12-14.
 */
class ArrayListTest {

    @Test
    fun `create empty ArrayList`() {
        var list = ArrayList<Integer>()
        assertTrue(list.size == 0)

        list = ArrayList<Integer>(7)
        assertTrue(list.size == 0)
    }

    @Test
    fun `test insertion`() {
        val list = ArrayList<Integer>()
        list.add(5, Integer(1))
    }

    @Test
    fun `test remove`() {
        val list = ArrayList<Integer>()
        list.add(Integer(0))
        list.add(Integer(1))
        list.add(Integer(2))
        list.add(Integer(3))
        list.add(Integer(4))

        list.removeAt(7);
    }

}