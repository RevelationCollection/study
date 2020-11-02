package com.leetcode.problemset;

public class Pro_845 {

    public static void main(String[] args) {
        int[] A = {2,1,4,7,3,2,5};
        System.out.println(longestMountain(A));
        A = new int[]{1,2,1};
        System.out.println(longestMountain(A));
        A = new int[]{2,2,2};
        System.out.println(longestMountain(A));
        A = new int[]{1,2,1,5,6,3,2,3,4,5,6,7,6,5,4,3,2,1};
        System.out.println(longestMountain(A));
    }

    public static int longestMountain(int[] A) {
        if (A==null || A.length<3){
            return 0;
        }
        int[] left = new int[A.length];
        int[] right = new int[A.length];
        //累计左边山峰的高度
        for (int i = 1; i < left.length; i++) {
            left[i] = A[i]>A[i-1]?1+left[i-1]:0;
        }
        //累计右边山峰的高度
        for (int i = right.length-2; i >=0; i--) {
            right[i] = A[i+1]<A[i]?1+right[i+1]:0;
        }
        int len = 0;
        //统计最大值
        for (int i = 1; i < right.length; i++) {
            if (left[i]>0 && right[i]>0){
                len = Math.max(left[i]+right[i]+1,len);
            }
        }
        return len;
    }
    public static int longestMountain1(int[] A) {
        if (A==null || A.length<3){
            return 0;
        }
        int len = 0;
        for (int i = 1; i < A.length; i++) {
            int leftLength = findLeftLength(i, A);
            if (leftLength>0){
                int rightLength = findRightLength(i, A);
                if (rightLength>0){
                    len = Math.max(len,leftLength+rightLength+1);
                }
            }
        }
        return len;
    }

    private static int findLeftLength(int index,int[] arr){
        int len = 0;
        for (int i = index-1; i>=0; i--) {
            if (arr[i]>=arr[i+1]){
                break;
            }
            len++;
        }
        return len;
    }

    private static int findRightLength(int index,int[] arr){
        int len = 0;
        for (int i = index+1; i < arr.length; i++) {
            if (arr[i-1]<=arr[i]){
                break;
            }
            len++;
        }
        return len;
    }
}
