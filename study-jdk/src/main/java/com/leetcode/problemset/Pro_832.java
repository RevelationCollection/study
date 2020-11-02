package com.leetcode.problemset;

import java.util.Arrays;

public class Pro_832 {

    public static void main(String[] args) {
        int[][] A = new int[4][4];
        A[0] = new int[]{1,1,0,0};
        A[1] = new int[]{1,0,0,1};
        A[2] = new int[]{0,1,1,1};
        A[3] = new int[]{1,0,1,0};
        flipAndInvertImage(A);
        for (int i = 0; i < A.length; i++) {
            System.out.println(Arrays.toString(A[i]));
        }
        test();
    }
    private static void test(){
        int[][] A = new int[3][3];
        int[] data = {1,1,0};
        A[0] = data;
        A[1] = new int[]{1,0,1};
        A[2] = new int[]{0,0,0};
        flipAndInvertImage(A);
        for (int i = 0; i < A.length; i++) {
            System.out.println(Arrays.toString(A[i]));
        }
    }

    public static int[][] flipAndInvertImage(int[][] A) {
        for (int i = 0; i < A.length; i++) {
            int[] arr = A[i];
            int len = arr.length;
            int count = len/2;
            if (len%2!=0){
                count++;
            }
            for (int j = 0; j < count; j++) {
                int tmp = getReversal(arr[j]);
                int last = len-1-j;
                arr[j] = getReversal(arr[last]);
                arr[last] = tmp;
            }
        }
        return A;
    }

    private static int getReversal(int i){
        if (i==0) return 1;
        return 0;
    }
}
