package com.leetcode.problemset;

import java.util.*;

/**
 * 两数之和
 */
public class Pro_1 {

    public static void main(String[] args) {
        int target = 10;
        int[] nums = new int[]{2,3,4,5,8};
        int[] twoSum = twoSum(nums, target);
        System.out.println(Arrays.toString(twoSum));
    }

    public static int[] twoSum(int[] nums, int target) {
        int  size = (int) ((nums.length / 0.75) + 1);
        Map<Integer,Integer> cache = new HashMap<>(size);
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int diff = target - current;
            Integer cacheIndex = cache.get(diff);
            if(cacheIndex!=null &&cacheIndex!=i){
                return new int[]{cacheIndex,i};
            }
            cache.put(current,i);
        }
        return null;
    }


}
