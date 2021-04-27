package com.leetcode.problemset;

public class Pro_941 {

    public static void main(String[] args) {
        int[] nums = new int[]{2,1};
        System.out.println(validMountainArray(nums)==false);
        nums = new int[]{3,5,5};
        System.out.println(validMountainArray(nums)==false);
        nums = new int[]{0,3,2,1};
        System.out.println(validMountainArray(nums)==true);
        nums = new int[]{0,2,3,3,5,2,1};
        System.out.println(validMountainArray(nums)==false);
        nums = new int[]{0,3,3,3,4,3,3,2,1};
        System.out.println(validMountainArray(nums)==false);
        nums = new int[]{9,8,7,6,5,4,3,2,1,0};
        System.out.println(validMountainArray(nums)==false);
        nums = new int[]{14,82,89,84,79,70,70,68,67,66,63,60,58,54,44,43,32,28,26,25,22,15,13,12,10,8,7,5,4,3};
        System.out.println(validMountainArray(nums)==false);
    }

    public static boolean validMountainArray(int[] A) {
        //山峰最少长度是3
        if (A==null|| A.length<3){
            return false;
        }
        //双指针，左边必须i>i+1,右边j<j-1
        int i = 0,j=A.length-1;
        while (i<j){
            if (A[i]<A[i+1]) i++;
            else if(A[j]<A[j-1]) j--;
            else break;
        }
        //指针是否碰撞，碰撞即为山峰
        return i!=0 && j!=A.length-1 && i>=j;
    }
    public static boolean validMountainArray1(int[] A) {
        if (A==null|| A.length<3){
            return false;
        }
        // flag = 0,初始化，第二个大于第一个。左斜，第二个大于第一个，flag=1 第二个小于第一个
        int flag = 0;
        for (int i = 1; i < A.length; i++) {
            //初始化，第二个必须大于第一个
            if (flag==0 && (A[i]<A[i-1])) return false;
            //左侧开始，状态改为1
            else if (flag==0 && (A[i]>A[i-1])) flag=1;
            //右侧开始，状态改为2
            else if (flag==1 && (A[i]<A[i-1])) flag=2;
            //右侧，第二个必须小于第一个
            else if (flag==2 && (A[i]>=A[i-1])) return false;
        }
        return flag==2;
    }
}
