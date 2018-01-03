package javalang;

import org.junit.Test;

/**
 * Created by teoking on 18-1-3.
 */
public class BasicsTest {

    @Test
    public void testWhileLoop() {
        int n = 10;
        int i = 3;
        int j = i;
        do {
            System.out.println(j);
        } while (i != (j = (++j % n)));
    }
}
