package ktlang

import org.junit.Test
import java.util.HashMap

/**
 * Created by teoking on 18-1-3.
 */
class BasicsTest {

    @Test
    fun `test kotlin map`() {
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
        println(map[A])
        println("#### 1")
        for (c in map[F]!!..map[A]!!) {
            println(c)
        }

        println("#### 2")
        for (c in map[A]!! downTo map[F]!!) {
            println(c)
        }

        println("#### 3")
        for (c in map.keys) {
            println(c)
        }

        println("#### 4")
        for (c in map.keys.reversed()) {
            println(c)
        }
    }

    @Test
    fun `test java map`() {
        val A = 'A'
        val B = 'B'
        val C = 'C'
        val D = 'D'
        val E = 'E'
        val F = 'F'

        val map = HashMap<Char, Int>()
        map.put(F, 0)
        map.put(E, 1)
        map.put(D, 2)
        map.put(C, 3)
        map.put(B, 4)
        map.put(A, 5)

        for (i in map) {
            println(i)
        }
    }
}