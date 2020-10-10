package com.algorithm.sort;

import java.util.Arrays;

public class MergeSort {

    /**
     * 归并排序
     * 把长度为n的输入序列分成两个长度为n/2的子序列；
     * 对这两个子序列分别采用归并排序；
     * 将两个排序好的子序列合并成一个最终的排序序列。
     * @param array 数组
     * @return 有序数组
     */
    public static int[] mergeSort(int[] array) {
        if (array==null || array.length<2){
            return array;
        }
        int mid = array.length/2;
        int[] left = Arrays.copyOfRange(array,0,mid);
        int[] right = Arrays.copyOfRange(array,mid,array.length);
        return merge(mergeSort(left),mergeSort(right));
    }

    /** 合并两个数组，遍历两个数组的时候依次比较，小的放左边 */
    public static int[] merge(int[] left,int[] right){
        int len = left.length+right.length;
        int[] merge = new int[len];
        for (int i = 0,x=0,y=0; i < len; i++) {
            if (x>=left.length){
                merge[i] = right[y++];
            }
            else  if (y>=right.length){
                merge[i] = left[x++];
            }
            else if (left[x] >right[y]){
                merge[i] = right[y++];
            }else {
                merge[i] = left[x++];
            }
        }
        return merge;
    }
}
