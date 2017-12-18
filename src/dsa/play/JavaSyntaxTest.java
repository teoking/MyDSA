package dsa.play;

import dsa.common.IntRef;

/**
 * Created by teoking on 17-12-13.
 */
public class JavaSyntaxTest {

    static void changePrimitive(IntRef i) {
        i.set(i.get() + 1);
    }

    public static void main(String[] args) {

        IntRef i = new IntRef(0);
        changePrimitive(i);
        changePrimitive(i);
        System.out.println(i.get());

        int a = 0;
        while (a++ < 1) {
            System.out.println(a);
            break;
        }
        System.out.println(a);

        int k = 1;
        System.out.println((1 + k++));
        System.out.println(k);
    }

}
