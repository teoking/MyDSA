package alg.array;

import java.util.*;

public class ThreeSum {


    // TODO need to improve performance, HashMap is slow here.
    static List<List<Integer>> threeSum(int[] array) {
        Map<String, List<Integer>> map = new HashMap<>();
        final String format = "%d,%d,%d";

        Arrays.sort(array);
        for (int i = 0; i < array.length - 2; i++) {
            int a = array[i];
            int start = i + 1;
            int end = array.length - 1;
            while (start < end) {
                int b = array[start];
                int c = array[end];
                if (a + b + c == 0) {
                    if (i != start && i != end) {
                        //System.out.println(String.format("i=%d, start=%d, end=%d", i, start, end));
                        map.put(String.format(format, a, b, c), Arrays.asList(a, b, c));
                        //result.add(Arrays.asList(a, b, c));
                    }
                    // Continue search for all triplet combinations summing to zero.
                    if (b == array[start + 1]) {
                        start++;
                    } else {
                        end--;
                    }
                } else if (a + b + c > 0) {
                    end--;
                } else {
                    start++;
                }
            }
        }
        return new ArrayList<>(map.values());
    }

    // O(n^3)
    // runtime: 160~230
    static public List<List<Integer>> threeSum1(int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        Map<String, List<Integer>> temp = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            map.put(i, array[i]);
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                int a = map.get(i);
                int b = map.get(j);
                int c = -(a + b);

                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == c
                            && entry.getKey() != i
                            && entry.getKey() != j
                            && i != j) {
                        int[] r = new int[]{a, b, c};
                        Arrays.sort(r);
                        List<Integer> l = new ArrayList<>(r.length);
                        for (int k : r) {
                            l.add(k);
                        }
                        temp.put(Arrays.toString(r), l);
                    }
                }
            }
        }

        return new ArrayList<>(temp.values());
    }


    public static void main(String[] args) {
        int[] array = {7, -13, -1, 1, -6, 14, 10, -2, 1, 9, 11, -10, 8,
                -10, 14, 13, -1, 4, -6, -3, -5, 3, 3, 12, -5, 11, 5, -6,
                -2, 0, -6, 12, 3, 0, -2, 12, -1, -7, -5, 8, 10, 13, 13,
                3, 10, 12, -7, -6, -7, -5, -1, 3, 5, -13, -8, -15, 13, 13,
                -14, -12, -2, -5, -15, 8, 11, -1, 6, -13, -1, 8, 10, -14,
                -1, 0, -4, -6, -3, 5, -4, -2, 7, 10, 8, -3, 12, -14, -10,
                3, 14, -9, -2, -11, -6, -9, 13, 12, -3, 4, 14, 3, -11, 2,
                5, -5, -13, -14, -3, -8};

//        int[] array = {-1,0,1,2,-1,-4};

        // {0, 2, 3, 4, 5}

        long t = System.currentTimeMillis();
        List<List<Integer>> list = threeSum(array);
        System.out.println("cost~" + (System.currentTimeMillis() - t));

        for (List<Integer> l : list) {
            System.out.print("[ ");

            for (int i : l) {
                System.out.print(i + ", ");
            }

            System.out.println("]");
        }
    }
}
