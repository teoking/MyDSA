package dsa.play;

import dsa.common.IntRef;

/**
 * Created by teoking on 17-12-13.
 */
public class JavaSyntaxTest {

    static void changePrimitive(IntRef i) {
        i.set(i.get() + 1);
    }

    static Obj start = new Obj("aaa");

    public static void main(String[] args) {
        sort(objs, start, objs.length);
    }

    static Obj[] objs = new Obj[] {
            start,
            new Obj("bbb"),
            new Obj("ccc"),
            new Obj("ddd")
    };

    static void sort(Obj[] objs, Obj start, int p) {
        if (p < 1)
            return;
        p--;
        System.out.println(start + " value=" + start.val);

        sort(objs, start, p);
        sortInner(objs, start, p);
    }

    static void sortInner(Obj[] objs, Obj start, int p) {
        start = objs[p];
    }

    static class Obj {
        String val;

        Obj(String a) {
            this.val = a;
        }
    }

}
