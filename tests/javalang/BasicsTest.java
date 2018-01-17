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

    @Test
    public void testReferencePass() {
        Bar bar = new Bar();
        Foo foo = getFooFromBar(bar);
        foo = new Foo("222");

        System.out.println("testReferencePass: " + bar.foo);

        bar.foo = new Foo("111");
        foo = getFooFromBar(bar);
        System.out.println("testReferencePass: " + foo.str);
    }

    Foo getFooFromBar(Bar bar) {
        return bar.foo;
    }

    class Foo {
        String str;
        Foo(String s) {
            str = s;
        }
    }

    class Bar {
        Foo foo;
    }
}
