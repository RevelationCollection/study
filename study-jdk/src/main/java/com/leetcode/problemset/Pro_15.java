package com.leetcode.problemset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和
 * https://leetcode-cn.com/problems/3sum/
 */
public class Pro_15 {

    public static void main(String[] args) {
        int[] arr = new int[]{-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(arr));
        arr = new int[]{0,0,0,0,0};
        System.out.println(threeSum(arr));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        //i+j+m = 0 ,新增一个排序
        int len = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            int sur = 0-nums[i];
            if (i>0 && nums[i]==nums[i-1]){
                continue;
            }
            int j = i+1;
            int k = len-1;
            for (; j < len; j++) {
                if (j>i+1 && nums[j]==nums[j-1]){
                    continue;
                }
                int ans = sur-nums[j];
                for (; k >j; k--) {
                    if (nums[k]<ans){
                        break;
                    }
                    if (ans==nums[k]){
                        save(nums[i],nums[j],ans,res);
                        break;
                    }
                }
            }
        }
        return res;
    }

    public static List<List<Integer>> threeSum1(int[] nums) {
        //i+j+m = 0
        //不符合题意，有重复，如果数组为[0,0,0,0,0] 无法给出一个解
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            int sur = 0-nums[i];
            for (int j = i+1; j < len; j++) {
                int ans = sur-nums[j];
                for (int k = len-1; k >j; k--) {
                    if (ans==nums[k]){
                        //// 判断是否有 a+b+c==0
                        //   check(first, second, third)
                        save(nums[i],nums[j],ans,res);
                    }
                }
            }
        }
        return res;
    }

    private static void save(int a,int b,int c,List<List<Integer>> ans){
        List<Integer> tmp = new ArrayList<>();
        tmp.add(a);
        tmp.add(b);
        tmp.add(c);
        ans.add(tmp);
    }
}
