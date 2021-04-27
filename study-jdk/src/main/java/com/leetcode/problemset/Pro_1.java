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
        nums = new int[]{-2,-1,-1,1,1,2,2};
        System.out.println(fourSum(nums,0));
        nums = new int[]{-3,-2,-1,0,0,1,2,3};
        System.out.println(fourSum(nums,0));
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

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums==null || nums.length<4) return ans;
        //对数组进行排序
        Arrays.sort(nums);
        int len = nums.length;
        //找出最小最大值
        int minValue = nums[0] + nums[1] + nums[2] +nums[3] ;
        int maxValue =  nums[len-1] + nums[len-2] +nums[len-3] +nums[len-4];
        //找的数不存在
        if (minValue>target || maxValue<target) return ans;
        for (int i = 0; i < len -3 ; i++) {
            //跳过重复数
            if (i>0 && nums[i]==nums[i-1]) continue;
            for (int j = i+1; j < len-2; j++) {
                if (j>i+1 && nums[j]==nums[j-1]) continue;
                int left = j+1;
                int right = len -1;
                //找出最大、最小的和
                minValue = nums[i] + nums[j] + nums[left] + nums[left+1];
                maxValue = nums[i] + nums[j] + nums[right] + nums[right-1];
                //找的数不存在
                if (minValue>target || maxValue<target) continue;
                while (left<right){
                    int temp = nums[i] + nums[j] + nums[left] + nums[right];
                    if (temp==target){
                        ans.add(Arrays.asList( nums[i],nums[j],nums[left],nums[right]));
                        // 左指针右移 跳过重复的数
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        // 右指针左移 跳过重复的数
                        while (left < right && nums[right] == nums[right - 1])  right--;
                        left++;
                        right--;
                    }
                    else if (temp>target){
                        right--;
                    }else {
                        left++;
                    }
                }
            }
        }
        return ans;
    }

}
