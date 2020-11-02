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
        return mergeSort2(array);
//        return mergeSort1(array);
    }

    //归并排序 2 -- start
    private static int[] mergeSort2(int[] arr) {
        return mergeSort2(arr, 0, arr.length - 1);
    }

    public static int[] mergeSort2(int[] nums, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            mergeSort2(nums, low, mid);
            // 右边
            mergeSort2(nums, mid + 1, high);
            // 左右归并
            merge2(nums, low, mid, high);
        }
        return nums;
    }

    public static void merge2(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;

        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = nums[i++];
        }

        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = nums[j++];
        }

        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            nums[k2 + low] = temp[k2];
        }
    }
    //归并排序 2 -- end

    //归并排序 3 -- start
    private static int[] mergeSort3(int[] arr) {
        return mergeSort3(arr, 0, arr.length - 1);
    }

    private static int[] mergeSort3(int[] arr, int from, int to) {
        if ((to - from) < 1)
            return arr;
        int mid = (from + to) / 2;
        mergeSort3(arr, from, mid);
        mergeSort3(arr, mid + 1, to);
        return merge3(arr, from, mid, mid + 1, to);
    }

    private static int[] merge3(int[] arr, int lfrom, int lto, int rfrom, int rto) {
        int li = lfrom, ri = rfrom;
        int[] res = new int[rto + 1];
        while (li <= lto || ri <= rto) {
            res[li + ri - rfrom] = li <= lto && (ri > rto || arr[li] < arr[ri]) ? arr[li++] : arr[ri++];
        }
        System.arraycopy(res, lfrom, arr, lfrom, rto - lfrom + 1);
        return arr;
    }
    //归并排序 3 -- end

    /**
     * 空间复杂度太高了，O(n^2)
     * @param array
     * @return
     */
    private static int[] mergeSort1(int[] array){
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
