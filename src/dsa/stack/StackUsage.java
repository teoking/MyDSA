package dsa.stack;

/**
 * Created by teoking on 17-12-21.
 */
public class StackUsage {

    static char digit[] = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F'
    };

    // recursive version
    public static void convert1(Stack<Character> s, int n, int base) {
        if (0 < n) {
            s.push(digit[n % base]);
            convert1(s, n/base, base);
        }
    }

    // traverse version
    public static void convert2(Stack<Character> s, int n, int base) {
        while (n > 0) {
            int remainder = n % base;
            s.push(digit[remainder]);
            n /= base;
        }
    }

    public static String flattenStack(Stack<Character> s) {
        String result = "";
        while (s.size() > 0) {
            result = result + s.pop();
        }
        return result;
    }

    public static boolean paren(String exp, int lo, int hi) {
        Stack<Character> s = new Stack<>();
        char[] arr = exp.toCharArray();
        for (int i = lo; i <= hi; i++) {
            switch (arr[i]) {
                case '(':
                case '[':
                case '{':
                    s.push(arr[i]);
                    break;
                case ')':
                    if (s.empty() || '(' != s.pop())
                        return false;
                    break;
                case ']':
                    if (s.empty() || '[' != s.pop())
                        return false;
                    break;
                case '}':
                    if (s.empty() || '{' != s.pop())
                        return false;
                    break;
            }
        }
        return s.empty();
    }

    // 求值算法
    // RPN（逆波兰表达式）求值


}
