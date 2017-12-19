package dsa.list

import dsa.common.ElemComparable
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class MyLinkedListTest {

    class ElemComparableImpl : ElemComparable<Integer> {

        override fun compare(e1: Integer?, e2: Integer?): Int {
            if (e1 == null && e2 == null) {
                return 0
            }
            if (e1 != null) {
                if (e2 == null) {
                    return Math.abs(e1.toInt())
                } else {
                    return e1.toInt() - e2.toInt()
                }
            } else {
                if (e2 == null) {
                    return 0
                } else {
                    return e2.toInt()
                }
            }
        }
    }

    private val comp : ElemComparable<Integer> = ElemComparableImpl()

    val list : MyLinkedList<Integer> = MyLinkedList()

    @Test
    fun `test list init`() {
        var list = MyLinkedList<Integer>()
        assertEquals(list.size(), 0)
        assertTrue(list.empty())
    }

    @Test
    fun `test insert & clear`() {
        var p0 = list.insertAsFirst(Integer(1))
        assertEquals(list.get(0).toInt(), 1)

        list.insertAsFirst(Integer(2))
        assertEquals(list.get(0).toInt(), 2)

        var p1 = list.insertAsLast(Integer(5))
        assertEquals(list.get(2).toInt(), 5)

        var p2 = list.insertA(p1, Integer(7))
        assertEquals(p1.succ.data, 7)

        list.insertB(p2, Integer(9))
        assertEquals(p2.pred.data, 9)

        assertTrue(list.first().pred == list.header)
        assertTrue(list.last().succ == list.trailer)

        list.remove(p0)
        assertEquals(list.get(0).toInt(), 2)

        list.clear()
        assertEquals(list.size(), 0)
    }

    @Test
    fun `test insertion sort`() {
        list.insertAsLast(Integer(7))
        list.insertAsLast(Integer(6))
        list.insertAsLast(Integer(5))
        list.insertAsLast(Integer(4))
        list.insertAsLast(Integer(3))
        list.insertAsLast(Integer(2))
        list.insertAsLast(Integer(1))

        list.insertionSort(list.first(), list.size(), comp)

        assertEquals(list.first().data.toInt(), 1)
        assertEquals(list.last().data.toInt(), 7)
    }

    @Test
    fun `test selection sort`() {
        list.insertAsLast(Integer(7))
        list.insertAsLast(Integer(6))
        list.insertAsLast(Integer(5))
        list.insertAsLast(Integer(4))
        list.insertAsLast(Integer(3))
        list.insertAsLast(Integer(2))
        list.insertAsLast(Integer(1))

        list.selectionSort(list.first(), list.size(), comp)

        assertEquals(list.first().data.toInt(), 1)
        assertEquals(list.last().data.toInt(), 7)
    }

    @Test
    fun `test merge sort`() {
        list.insertAsLast(Integer(7))
        list.insertAsLast(Integer(6))
        list.insertAsLast(Integer(5))
        list.insertAsLast(Integer(4))
        list.insertAsLast(Integer(3))
        list.insertAsLast(Integer(2))
        list.insertAsLast(Integer(1))

        list.mergeSort(list.first(), list.size(), comp)

        assertEquals(list.first().data.toInt(), 1)
        assertEquals(list.last().data.toInt(), 7)
    }

    private fun dumpList(list: MyLinkedList<*>) {
        var p = list.header.succ
        var sb = StringBuilder()
        while (p != list.trailer) {
            sb.append(p.data).append(" ")
            p = p.succ
        }
        println(sb.toString())
    }
}