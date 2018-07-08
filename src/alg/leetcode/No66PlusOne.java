package alg.leetcode;

import java.util.Arrays;

public class No66PlusOne {

    // time: O(n)
    // runtime: 0ms
    static class Solution {
        public int[] plusOne(int[] digits) {
            reverse(digits);

            int carry = 0;
            digits[0]++;
            for (int i = 0; i < digits.length; i++) {
                digits[i] += carry;
                carry = digits[i] / 10;
                digits[i] = digits[i] % 10;
            }

            reverse(digits);
            int[] result;
            if (carry > 0) {
                result = new int[digits.length + 1];
                result[0] = carry;
            } else {
                result = Arrays.copyOf(digits, digits.length);
            }

            return result;
        }

        private int[] reverse(int[] chars) {
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
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.plusOne(new int[]{9, 9, 9})));
        System.out.println(Arrays.toString(solution.plusOne(new int[]{9})));
        System.out.println(Arrays.toString(solution.plusOne(new int[]{1, 2, 3})));
    }
}
