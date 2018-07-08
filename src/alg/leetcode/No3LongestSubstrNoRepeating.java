package alg.leetcode;

import org.junit.Assert;

public class No3LongestSubstrNoRepeating {


    static final int NO_OF_CHARS = 256;

    // reference: https://www.geeksforgeeks.org/length-of-the-longest-substring-without-repeating-characters/
    // time:  O(n)
    // space: O(d)
    // runtime: 23ms
    static int lengthOfLongestSubstring(String str) {
        int n = str.length();
        if (n < 2) {
            return n;
        }
        // NRCS = Non-Repeating Character Substring

        // max length of the NRCS
        int maxLen = 1;
        // current length of the NRCS
        int curLen = 1;
        // previous index of the character.
        int prevIndex;

        // last indexes of already visited characters.
        int[] visited = new int[NO_OF_CHARS];
        // init elements of visited with -1.
        for (int i = 0; i < NO_OF_CHARS; i++) {
            visited[i] = -1;
        }

        // init first character index with 0.
        visited[str.charAt(0)] = 0;

        // traverse str
        for (int i = 1; i < n; i++) {
            prevIndex = visited[str.charAt(i)];

            if (prevIndex == -1 || i - curLen > prevIndex) {
                curLen++;
            } else {
                // update maxLen
                if (curLen > maxLen) {
                    maxLen = curLen;
                }

                curLen = i - prevIndex;
            }

            visited[str.charAt(i)] = i;
        }

        if (curLen > maxLen) {
            maxLen = curLen;
        }

        return maxLen;
    }

    public static void main(String[] args) {
        Assert.assertEquals(3, lengthOfLongestSubstring("pwwkew"));
        Assert.assertEquals(2, lengthOfLongestSubstring("au"));
        Assert.assertEquals(0, lengthOfLongestSubstring(""));
        Assert.assertEquals(1, lengthOfLongestSubstring("b"));
        Assert.assertEquals(1, lengthOfLongestSubstring("bbbbbbb"));
        Assert.assertEquals(2, lengthOfLongestSubstring("abba"));
    }
}
