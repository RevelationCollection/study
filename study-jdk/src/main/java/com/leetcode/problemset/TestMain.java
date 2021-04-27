package com.leetcode.problemset;

import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) {

    }


    public int search(int[] nums, int target) {
        if (nums==null || nums.length<1) return -1;
        int min = nums[0],minIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (min>nums[i]){
                min = nums[i];
                minIndex = i;
            }
        }
        Arrays.sort(nums);
        int res = search(nums,target,0,nums.length-1);
        if (res==-1) return res;
        res = (res + minIndex)%nums.length;
        return res;
    }

    public int search(int[] nums, int target,int left,int right) {
        if (left>right) return -1;
        int mid = left+(right-left)/2;
        if (nums[mid]==target){
            return mid;
        }
        if (nums[mid]<target){
            return search(nums,target,mid+1,right);
        }
        return search(nums,target,left,mid-1);
    }
}
