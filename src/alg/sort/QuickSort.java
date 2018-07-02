package alg.sort;

public class QuickSort {

    // http://www.java2novice.com/java-sorting-algorithms/quick-sort/

    static void quickSort(int[] nums, int lo, int hi) {
        int pivot = nums[lo + (hi - lo)/2];
        int i = lo, j = hi;
        while (i <= j) {
            while (nums[i] < pivot) {
                i++;
            }
            while (nums[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(nums, i, j);
                i++;
                j--;
            }
        }
        if (lo < j)
            quickSort(nums, lo, j);
        if (i < hi)
            quickSort(nums, i, hi);
    }

    static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {3, 5, 4, 2, 9, 7, 1, 8, 6};
        quickSort(nums, 0, nums.length - 1);

        for (int i : nums) {
            System.out.print(i + " ");
        }
    }
}
