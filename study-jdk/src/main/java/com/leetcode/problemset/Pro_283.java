package com.leetcode.problemset;

import java.util.Arrays;

public class Pro_283 {
    public static void main(String[] args) {
        int[] nums = new int[]{0,1,0,3,12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]==0){
                continue;
            }
            nums[k++] = nums[i];
        }
        while (k<=nums.length-1){
            nums[k++] = 0;
        }
    }

}
