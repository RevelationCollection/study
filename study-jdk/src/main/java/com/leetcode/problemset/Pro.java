package com.leetcode.problemset;

public class Pro {
    public static void main(String[] args) {
        System.out.println(climbStairs(10));
        System.out.println(climbStairs(4));
        System.out.println(climbStairs(3));
        System.out.println(climbStairs(2));
        System.out.println(climbStairs(1));
    }

    public static int climbStairs(int n) {
        //f[i] = f[i-2] + f[i-1]
        if (n<2){
            return 1;
        }
        int[] arr = new int[n];
        arr[0] = 1;
        arr[1] = 2;
        int i = 2;
        while (i<n){
            arr[i] = arr[i-1] +arr[i-2];
            i++;
        }
        return arr[n-1];
    }
}
