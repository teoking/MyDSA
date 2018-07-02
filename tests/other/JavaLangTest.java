package other;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class JavaLangTest {

    @Test
    public void testHashMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        System.out.println(map.get(1));
    }

    @Test
    public void testStringSort() {
        String str = "abcd";
        char[] chars = str.toCharArray();

        shuffleArray(0, chars);
    }

    private void shuffleArray(int i, char[] chars) {
        if (i == chars.length) {
            return;
        }

        for (int j = 0; j < chars.length; j++) {
            char[] arr = Arrays.copyOf(chars, chars.length);
            char c = arr[i];
            arr[i] = arr[j];
            arr[j] = c;

            System.out.println(new String(arr));
        }

        shuffleArray(i + 1, chars);
    }
}
