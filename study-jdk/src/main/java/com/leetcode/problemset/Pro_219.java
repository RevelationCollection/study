package com.leetcode.problemset;

import java.util.HashSet;

public class Pro_219 {

    public static void main(String[] args) {
        int[] arr = new int[]{99,99};
        System.out.println(containsNearbyDuplicate(arr,3));
        arr = new int[]{1,2,1,3,5};
        System.out.println(containsNearbyDuplicate(arr,4));
    }

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null) {
            return false;
        }
        HashSet<Integer> cache = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (cache.contains(nums[i])){
                return true;
            }
            cache.add(nums[i]);
            if (cache.size()>k){
                cache.remove(nums[i-k]);
            }
        }
        return false;
    }
    public static boolean containsNearbyDuplicate2(int[] nums, int k) {
        if (nums==null){
            return false;
        }
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int j = 1;
            while (j<=k && i+j<len){
                if (nums[i]==nums[i+j]){
                    return true;
                }
                j++;
            }
        }
        return false;
    }
}
