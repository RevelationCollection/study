package com.leetcode.problemset;

import java.util.Arrays;

/**
 * 628. 三个数的最大乘积
 * https://leetcode-cn.com/problems/maximum-product-of-three-numbers/
 */
public class Pro_628 {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3};
        System.out.println(maximumProduct(arr));
        arr = new int[]{1,2,3,4};
        System.out.println(maximumProduct(arr));
        arr = new int[]{-5,-6,0,1,2,3};
        System.out.println(maximumProduct(arr));
        arr = new int[]{-1,-2,-3,-4};
        System.out.println(maximumProduct(arr));
    }

    public static int maximumProduct(int[] nums) {
        //扫描得出 在方法一中，我们实际上只要求出数组中最大的三个数以及最小的两个数，因此我们可以不用排序，用线性扫描直接得出这五个数。
        Arrays.sort(nums);
        return Math.max(nums[0] * nums[1] * nums[nums.length - 1], nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3]);
    }
    public static int maximumProduct1(int[] nums) {
        if (nums==null ||nums.length<3){
            return 0;
        }
        Arrays.sort(nums);
        int first = nums[0];
        int last = nums[nums.length-1];
        int ans = last*nums[nums.length-2]*nums[nums.length-3];
        if (first>=0 ){
            //首值大于0
            return ans;
        }
        //第一个是负数，最后一个是正数
        int second = nums[1];
        int tmp = first*last*second;
        int temp = ans>tmp?ans:tmp;
        return temp;
    }
}
