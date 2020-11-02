package com.leetcode.problemset;


import java.util.Arrays;

/**
 *   4  5  6  7
 *
 *   1  2  3
 *
 *   1
 *   4
 */
public class Pro_88 {

    public static void main(String[] args) {
        int[] nums1 = {1,2,3,7,0,0,0};
        int[] nums2 = {4,5,6};
        merge(nums1,4,nums2,3);
        System.out.println(Arrays.toString(nums1));
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        //总长度
       int count = m-- + n-- -1;
       while (n>=0){
//           System.out.printf("count:%s,m:%s,n:%s",count,m,n);
//           System.out.println();
           nums1[count--] = m>=0 &&nums1[m]>nums2[n]?nums1[m--]:nums2[n--];
       }
    }
}
