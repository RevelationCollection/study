package com.leetcode.problemset;

import java.util.Arrays;

/**
 * 给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
 */
public class Pro_977 {

    public static void main(String[] args) {
        int[] arr = new int[]{0,3,10,24,69};
        System.out.println(Arrays.toString(sortedSquares(arr)));
        arr = new int[]{-4,-1,0,3,10};
        System.out.println(Arrays.toString(sortedSquares(arr)));
        arr = new int[]{};
        System.out.println(Arrays.toString(sortedSquares(arr)));
        arr = new int[]{-4,-1};
        System.out.println(Arrays.toString(sortedSquares(arr)));
        arr = new int[]{-7,-3,2,3,11};
        System.out.println(Arrays.toString(sortedSquares(arr)));
    }

    //双指针，找到负值左边，比较填充
    public static int[] sortedSquares(int[] A) {
        //双指针 ，比较最大值，反向填充
        int n = A.length;
        int[] ans = new int[n];
        for (int i = 0,j=n-1,pos=n-1; i<=j ; ) {
            if (A[i]*A[i]>A[j]*A[j]){
                ans[pos] = A[i]*A[i];
                i++;
            }
            else {
                ans[pos] = A[j]*A[j];
                j--;
            }
            pos--;
        }
        return ans;
    }
    public static int[] sortedSquares1(int[] A) {
        if (A==null||A.length<1){
            return A;
        }
        if (A.length==1){
            A[0] *=A[0];
            return A;
        }
        int[] tmp = new int[A.length];
        int endIndex = 0;
        int startIndex = 0;
        for (int i = 0; i < A.length; i++) {
            int val = A[i];
            if (val<0){
                tmp[endIndex++] = val*val;
                continue;
            }
            val *=val;
            while (endIndex>0 && tmp[endIndex-1]<val){
                A[startIndex++] = tmp[endIndex-1];
                endIndex--;
            }
            A[startIndex++] = val;
        }
        while (endIndex>0){
            A[startIndex++] = tmp[endIndex-1];
            endIndex--;
        }
        return A;
    }


}
