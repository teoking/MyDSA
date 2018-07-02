package alg.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {


    static void threeSum(List<List<Integer>> result, int[] array, int target, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            List<List<Integer>> found = twoSum(array, target - array[i], ++i, hi);
            if (found != null) {
                for (List<Integer> v : found) {
                    result.add(Arrays.asList(i, v.get(0), v.get(1)));
                }
            }
        }
    }

    static List<List<Integer>> twoSum(int[] array, int target, int lo, int hi) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = lo; i < hi; i++) {
            for (int j = i + 1; j < hi; j++) {
                if (array[i] + array[j] == target) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
        List<List<Integer>> list = new ArrayList<>();
        int[] array = {0, 2, 3, 5, 4};

        Arrays.sort(array);
        // {0, 2, 3, 4, 5}

        threeSum(list, array, 7, 0, array.length);

        for (List<Integer> l : list) {
            System.out.print("[ ");

            for (int i : l) {
                System.out.print(i + ", ");
            }

            System.out.println("]");
        }

    }
}
