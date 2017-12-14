package dsa.vec

import dsa.fib.Fibonacci
import org.junit.Test
import org.junit.Assert.*

/**
 * Created by teoking on 17-12-14.
 */
class FibonacciTest {

    @Test
    fun `test fib`() {
        val fib = Fibonacci(10)

        assertTrue(fib.get() == 13)
        assertTrue(fib.prev() == 8)
        assertTrue(fib.prev() == 5)
        assertTrue(fib.prev() == 3)
        assertTrue(fib.prev() == 2)
        assertTrue(fib.prev() == 1)
        assertTrue(fib.prev() == 1)

        // already reach the first element
        assertTrue(fib.prev() == 1)
    }
}