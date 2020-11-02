package com.leetcode.problemset;

import java.util.Arrays;

public class Pro_218 {

    public static void main(String[] args) {
        int[] arr = {1};
        moveZeroes(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void moveZeroes(int[] nums) {
        if (nums==null || nums.length<1){
            return;
        }
        int tmp = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]!=0){
                nums[tmp] = nums[i];
                if (i!=tmp){
                    nums[i] = 0;
                }
                tmp++;
            }
        }
    }
}
