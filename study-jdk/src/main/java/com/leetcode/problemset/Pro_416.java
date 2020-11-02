package com.leetcode.problemset;

import java.util.Arrays;

public class Pro_416 {
    public static void main(String[] args) {
        int[] nums = new int[]{2,2,1,1};
        System.out.println(canPartition(nums)==true);
        nums = new int[]{1,1,2,2,6,6};
        System.out.println(canPartition(nums)==true);
        nums = new int[]{1,2,2,3,5,6};
        System.out.println(canPartition(nums)==true);
        nums = new int[]{3,3,3,4,5};
        System.out.println(canPartition(nums)==true);
        nums = new int[]{1, 5, 11, 5};
        System.out.println(canPartition(nums)==true);
        nums = new int[]{1, 1, 5,5, 12};
        System.out.println(canPartition(nums)==true);
        nums = new int[]{1, 2, 3, 5};
        System.out.println(canPartition(nums)==false);
    }

    public static boolean canPartition(int[] nums) {
        if (nums==null || nums.length<2){
            return false;
        }
        Arrays.sort(nums);
        int i = 0;
        int j = nums.length-1;
        int pre = nums[i++];
        int last = nums[j--];
        while (i<=j){
            while (i<=j && last>=pre){
                pre += nums[i++];
            }
            while (i<=j && last<=pre){
                last += nums[j--];
            }
        }
        return last==pre;
    }
}
