package dsa.vec

import dsa.common.ElemComparable
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by teoking on 17-12-14.
 */
class MySortedVectorTest {

    class ElemComparableImpl : ElemComparable<Integer> {

        override fun compare(e1: Integer, e2: Integer): Int {
            return e1.toInt() - e2.toInt()
        }
    }

    private val comp : ElemComparable<Integer> = ElemComparableImpl()
    private val list : MySortedVector<Integer> = MySortedVector<Integer>()

    @Test
    fun `test disordered`() {
        list.insert(Integer( 0))
        list.insert(Integer(1))
        list.insert(Integer(2))
        list.insert(Integer(3))
        list.insert(Integer(4))

        assertTrue(list.disordered(comp) == 0)

        list.insert(Integer(3))
        assertTrue(list.disordered(comp) > 0)
    }

    @Test
    fun `test uniquify`() {
        list.insert(Integer(0))
        list.insert(Integer(1))
        list.insert(Integer(2))
        list.insert(Integer(4))
        list.insert(Integer(4))
        list.insert(Integer(5))
        list.insert(Integer(5))
        list.insert(Integer(6))

        assertTrue(list.uniquify(comp) == 2)
    }

    @Test
    fun `test search`() {
        list.insert(Integer(1))
        list.insert(Integer(3))
        list.insert(Integer(6))
        list.insert(Integer(7))
        list.insert(Integer(8))
        list.insert(Integer(14))
        list.insert(Integer(17))
        list.insert(Integer(17))
        list.insert(Integer(17))

        var searchMethod : SortedVector.Searchable<Integer> = MySortedVector.LinearSearch<Integer>()
        assertTrue(list.search(Integer(6), 0, list.size() - 1, comp, searchMethod) == 2)
        assertTrue(list.search(Integer(14), 0, list.size() - 1, comp, searchMethod) == 5)

        searchMethod = MySortedVector.BinarySearchA()
        assertTrue(list.search(Integer(6), 0, list.size() - 1, comp, searchMethod) == 2)
        assertTrue(list.search(Integer(14), 0, list.size() - 1, comp, searchMethod) == 5)

        searchMethod = MySortedVector.FibSearch()
        assertTrue(list.search(Integer(6), 0, list.size() - 1, comp, searchMethod) == 2)
        assertTrue(list.search(Integer(14), 0, list.size() - 1, comp, searchMethod) == 5)

        searchMethod = MySortedVector.BinarySearchB()
        assertTrue(list.search(Integer(6), 0, list.size() - 1, comp, searchMethod) == 2)
        assertTrue(list.search(Integer(14), 0, list.size() - 1, comp, searchMethod) == 5)

        searchMethod = MySortedVector.BinarySearchC()
        assertTrue(list.search(Integer(6), 0, list.size() - 1, comp, searchMethod) == 2)
        assertTrue(list.search(Integer(14), 0, list.size() - 1, comp, searchMethod) == 5)
        assertTrue(list.search(Integer(17), 0, list.size() - 1, comp, searchMethod) == 7)

        searchMethod = MySortedVector.InterpolationSearch()
        assertTrue(list.search(Integer(6), 0, list.size() - 1, comp, searchMethod) == 2)
        assertTrue(list.search(Integer(14), 0, list.size() - 1, comp, searchMethod) == 5)
        assertTrue(list.search(Integer(17), 0, list.size() - 1, comp, searchMethod) == 8)
    }

    @Test
    fun `test bubble sort`() {
        list.insert(Integer(3))
        list.insert(Integer(2))
        list.insert(Integer(5))
        list.insert(Integer(1))
        list.insert(Integer(66))
        list.insert(Integer(2))
        list.insert(Integer(9))
        list.insert(Integer(10))
        list.insert(Integer(17))

        val sortMethod : SortedVector.Sortable<Integer> = MySortedVector.BubbleSort<Integer>()
        sortMethod.sort(list, 0, list.size, comp)

        assertTrue(list.get(0).toInt() == 1)
        assertTrue(list.get(list.size() - 1).toInt() == 66)
    }

    @Test
    fun `test merge sort`() {
        list.insert(Integer(6))
        list.insert(Integer(3))
        list.insert(Integer(2))
        list.insert(Integer(7))
        list.insert(Integer(1))
        list.insert(Integer(5))
        list.insert(Integer(8))
        list.insert(Integer(4))

        val sortMethod : SortedVector.Sortable<Integer> = MySortedVector.MergeSort<Integer>()
        sortMethod.sort(list, 0, list.size, comp)

        assertTrue(list.get(0).toInt() == 1)
        assertTrue(list.get(list.size() - 1).toInt() == 8)

        list.remove(0, list.size() - 1)
        list.insert(Integer(3))
        list.insert(Integer(2))
        list.insert(Integer(5))
        list.insert(Integer(1))
        list.insert(Integer(66))
        list.insert(Integer(2))
        list.insert(Integer(9))
        list.insert(Integer(10))
        list.insert(Integer(17))

        sortMethod.sort(list, 0, list.size, comp)

        assertTrue(list.get(0).toInt() == 1)
        assertTrue(list.get(list.size() - 1).toInt() == 66)
    }
}