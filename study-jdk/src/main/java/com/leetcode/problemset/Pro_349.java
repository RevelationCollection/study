package com.leetcode.problemset;

import java.util.*;

/**
 * 给定两个数组，编写一个函数来计算它们的交集。
 */
public class Pro_349 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(intersection(new int[]{1,2,2,1},new int[]{2,2})));
        System.out.println(Arrays.toString(intersection(new int[]{4,9,5},new int[]{9,4,9,8,4})));
    }


    public static int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> ans = new ArrayList<>();
        int i =0,j=0;
        while (i<nums1.length && j<nums2.length){
            if((i>0 && nums1[i-1]==nums1[i])
                || nums1[i]<nums2[j]){
                i++;
            }
            else if((j>0 && nums2[j-1]==nums2[j])
                || nums1[i]>nums2[j]){
                j++;
            }
            else {
                ans.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] res=new int[ans.size()];
        int index=0;
        for(int num:ans){
            res[index++]=num;
        }
        return res;
    }


    public static int[] intersection1(int[] nums1, int[] nums2) {
        Set<Integer> ans = new HashSet<>();
        Set<Integer> cache = new HashSet<>();
        for (int i : nums1) {
            cache.add(i);
        }
        for (int i : nums2) {
            if (cache.contains(i)){
                ans.add(i);
            }
        }
        return ans.stream().mapToInt(Integer::valueOf).toArray();
    }
}
