package dsa.stack

import org.junit.Test
import org.junit.Assert.*

/**
 * Created by teoking on 17-12-21.
 */
class NumberConvertTest {

    @Test
    fun `test recursive convert`() {
        val s = Stack<Char>()
        StackUsage.convert1(s, 1024, 2)
        var str = StackUsage.flattenStack(s)
        assertEquals(str, "10000000000")

        StackUsage.convert1(s, 1024, 16)
        str = StackUsage.flattenStack(s)
        assertEquals(str, "400")
    }

    @Test
    fun `test traverse convert`() {
        val s = Stack<Char>()
        StackUsage.convert2(s, 1024, 2)
        var str = StackUsage.flattenStack(s)
        assertEquals(str, "10000000000")

        StackUsage.convert2(s, 1024, 16)
        str = StackUsage.flattenStack(s)
        assertEquals(str, "400")
    }

    @Test
    fun `test paren`() {
        var s = "((())())"
        assertTrue(StackUsage.paren(s, 0, s.length - 1))

        s = "[]{((()){())}"
        assertFalse(StackUsage.paren(s, 0, s.length - 1))
    }
}