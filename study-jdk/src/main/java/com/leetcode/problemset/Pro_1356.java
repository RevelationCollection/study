package com.leetcode.problemset;

import java.util.Arrays;

public class Pro_1356 {
    public static void main(String[] args) {
        int i = 1 * 10000;
        System.out.println(i);
    }
    public static int[] sortByBits(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = bitCount(arr[i])*100_000 + arr[i];
        }
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            arr[i] %=100_000;
        }
        return arr;
    }

    private static int bitCount(int n){
        int count=0;
        while (n!=0){
            //比较最末位是否是1，如果是加1
            count+= n&1;
            n >>=1; //向右移动1位
        }
        return count;
    }
}
