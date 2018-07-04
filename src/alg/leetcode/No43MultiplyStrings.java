package alg.leetcode;

public class No43MultiplyStrings {

    // Emulate two numbers multiply by hand.
    // time: O(n^2)
    // space: O(n)
    // runtime: 28ms
    private static String multiply(String num1, String num2) {
        char[] arr1 = num1.toCharArray();
        char[] arr2 = num2.toCharArray();
        reverse(arr1);
        reverse(arr2);
        int[] arr = new int[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                int multi = (arr1[i] - '0') * (arr2[j] - '0');
                arr[i + j + 1] += (arr[i + j] + multi) / 10;
                arr[i + j] = (arr[i + j] + multi) % 10;
            }
        }

        reverse(arr);
        StringBuilder sb = new StringBuilder(arr.length);
        boolean findZero = true;
        for (int i = 0; i < arr.length; i++) {
            if (findZero && arr[i] != 0) {
                findZero = false;
            }

            if (!findZero) {
                sb.append(arr[i]);
            }
        }
        return "".equals(sb.toString())  ? "0" : sb.toString();
    }

    private static char[] reverse(char[] chars) {
        int i = 0, j = chars.length - 1;
        while (i <= j) {
            char tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;
            i++;
            j--;
        }

        return chars;
    }

    private static int[] reverse(int[] chars) {
        int i = 0, j = chars.length - 1;
        while (i <= j) {
            int tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;
            i++;
            j--;
        }

        return chars;
    }

    // The follow code has best performance:
    // from LeetCode.
    public String multiply2(String num1, String num2) {
        StringBuilder sb = new StringBuilder(num1);
        char[] arr1 = sb.reverse().toString().toCharArray();
        sb.setLength(0);
        sb.append(num2);
        char[] arr2 = sb.reverse().toString().toCharArray();
        int[] res = new int[arr1.length + arr2.length + 1];
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                res[i + j] += (arr1[i] - '0') * (arr2[j] - '0');
            }
        }
        sb.setLength(0);
        int carry = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] += carry;
            sb.append(res[i] % 10);
            carry = res[i] / 10;
        }
        int j = sb.length() - 1;
        while (j >= 0 && sb.charAt(j) == '0') {
            sb.deleteCharAt(j--);
        }
        String ans = sb.reverse().toString();
        return ans.length() == 0 ? new String("0") : ans;
    }

    public static void main(String[] args) {
        System.out.println(reverse("abcd".toCharArray()));
        System.out.println(reverse("abc".toCharArray()));

        System.out.println(multiply("456", "123"));
        System.out.println(multiply("0", "123"));
        System.out.println(multiply("1", "123"));
        System.out.println(multiply("3333333333", "123"));
    }
}
