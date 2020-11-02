package com.leetcode.problemset;

/**
 * 867. 转置矩阵
 * 给定一个矩阵 A， 返回 A 的转置矩阵。
 *
 * 矩阵的转置是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
 */
public class Pro_867 {
    public static void main(String[] args) {

    }

    public int[][] transpose(int[][] A) {
        if (A==null ||A.length==0){
            return A;
        }
        int x = A.length;
        int y = A[0].length;
        int[][] ans = new int[y][x];
        for (int i = 0; i < A.length; i++) {
            int[] arr = A[i];
            for (int j = 0; j < arr.length; j++) {
                ans[j][i] = A[i][j];
            }
        }
        return ans;
    }
}
