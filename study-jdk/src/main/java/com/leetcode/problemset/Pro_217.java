package com.leetcode.problemset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 存在重复元素
 */
public class Pro_217 {

    public static void main(String[] args) {

    }

    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i-1]==nums[i]){
                return true;
            }
        }
        return false;
    }

    //for循环
    public boolean containsDuplicate3(int[] nums) {
        if (nums == null || nums.length<2) {
            return false;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i]==nums[j]){
                    return true;
                }
            }
        }
        return false;
    }

    //hash表
    public boolean containsDuplicate1(int[] nums) {
        if (nums == null || nums.length<2) {
            return false;
        }
        Map<Integer,Integer> cache = new HashMap<>();
        for (int num : nums) {
            if (cache.get(num) == null) {
                cache.put(num,num);
            }else {
                return true;
            }
        }
        return  false;
    }
}
