package com.leetcode.problemset.face;

public class Pro_17_10 {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,5,9,5,9,5,5,5};
        System.out.println(majorityElement(arr));
        arr = new int[]{2,2,2,3,3,4,4};
        System.out.println(majorityElement(arr));
        arr = new int[]{2,2,2,1,1,1};
        System.out.println(majorityElement(arr));
        arr = new int[]{3,2,1};
        System.out.println(majorityElement(arr));
        arr = new int[]{1};
        System.out.println(majorityElement(arr));
        arr = new int[]{};
        System.out.println(majorityElement(arr));
    }


    public static int majorityElement(int[] nums) {
        if(nums==null || nums.length<0){
            return -1;
        }
        //1、暴力循环统计比较
        //2、摩尔投票法，过滤众数再匹配
        int len = nums.length;
        int major = -1,count = 0;
        for(int num: nums){
            if(count ==0){
                major = num;
            }
            if(num==major){
                ++count;
            }else{
                --count;
            }
        }
        if(count<=0){
            return -1;
        }
        //3、重置count，计算实际的值是否大于数组长度的一半
        count = 0;
        int flag = len/2;
        for(int num: nums){
            if(num==major){
                ++count;
            }
            if(count>flag){
                return major;
            }
        }
        return -1;
    }
    public static int majorityElement2(int[] nums) {
        //下面为摩尔投票法,只要不相同,就相互抵消,cnt --,给major赋值下一个,再继续比较,
        if (nums == null || nums.length <2) {
            return -1;
        }
        int len = nums.length;
        int major = nums[0];
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (count==0){
                major = nums[i];
            }
            if (nums[i] == major){
                count++;
            }else {
                count--;
            }
        }
        if (count<=0){
            return -1;
        }
        //重置
        count = 0;
        //可能不存在众数，还需再遍历一次
        for (int num : nums) {
            if (num==major){
                count++;
            }
        }
        if (count>len/2){
            return major;
        }
        return -1;
    }
}
