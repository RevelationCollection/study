package com.leetcode.problemset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Pro_1365 {

    public static void main(String[] args) {
        int[] nums = {8,1,2,2,3};
        System.out.println(Arrays.toString(smallerNumbersThanCurrent(nums)));
        nums = new int[]{6,5,4,8};
        System.out.println(Arrays.toString(smallerNumbersThanCurrent(nums)));
        nums = new int[]{7,7,7,7};
        System.out.println(Arrays.toString(smallerNumbersThanCurrent(nums)));
    }

    public static int[] smallerNumbersThanCurrent(int[] nums) {
        int[] arr = Arrays.copyOf(nums, nums.length);
        Arrays.sort(arr);
        Map<Integer,Integer> cache = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];
            if (!cache.containsKey(val)){
                cache.put(val,i);
            }

        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = cache.get(nums[i]);
        }
        return nums;
    }



}
