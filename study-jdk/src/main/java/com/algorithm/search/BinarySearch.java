package com.algorithm.search;

import com.algorithm.sort.InsertionSort;

/**
 * 二分查找
 * 必须保证元素是有序的，无序需排序后再查找
 */
public class BinarySearch {

    public static int search(int[] array,int n){
        InsertionSort.insertionSort(array);
        return binarySearch(array,n,0,array.length-1);
    }

    private static int binarySearch(int[] array,int n,int start,int end){
        if (start>end){
            return -1;
        }
        int mid = (end+start) /2 ; // (low+high)/2 = (high-low)2 + low = 1/2 * (high-low) + low
        int val = array[mid];
        if (val==n)
            return n;
        else if (val >n)
            return binarySearch(array,n,start,mid-1);
        else if (val < n)
            return binarySearch(array,n,mid+1,end);
        return -1;
    }
}
