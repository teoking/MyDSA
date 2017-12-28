package dsa.queue

import org.junit.Test
import org.junit.Assert.*

/**
 * Created by teoking on 17-12-28.
 */
class QueueTest {

    @Test
    fun `test`() {
        val q = Queue<Int>()
        q.enqueue(1)
        q.enqueue(2)
        q.enqueue(3)
        q.enqueue(4)

        assertEquals(1, q.dequeue())
        assertEquals(2, q.dequeue())
        assertEquals(3, q.dequeue())
        assertEquals(4, q.dequeue())
        assertTrue(q.empty())
    }
}