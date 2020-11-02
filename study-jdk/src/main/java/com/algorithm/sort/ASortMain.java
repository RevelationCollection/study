package com.algorithm.sort;

import java.util.Arrays;

/**
 * https://www.cnblogs.com/guoyaohua/p/8600214.html
 */
public class ASortMain {

    public static void main(String[] args) {
        int[] arr = new int[]{8,9,1,7,2,3,5,4,6,0};
        //1、冒泡排序
        System.out.println(Arrays.toString(BubbleSort.bubbleSort(arr)));
        //2、选择排序
        System.out.println(Arrays.toString(SelectionSort.selectionSort(arr)));
        //3、插入排序
        System.out.println(Arrays.toString(InsertionSort.insertionSort(arr)));
        //4、希尔排序
        System.out.println(Arrays.toString(ShellSort.shellSort(arr)));
        //5、归并排序
        arr = new int[]{-7, 51, 3, 121, -3, 32, 21, 43, 4, 25, 56, 77, 16, 25, 87, 56, -11, 68, 99, 70};
        System.out.println(Arrays.toString(MergeSort.mergeSort(arr)));
        //6、快速排序
        System.out.println("快速排序，单指针随机基准排序："+Arrays.toString(QuickSort.quickSort(arr)));
        System.out.println("快速排序，双指针首坐标基准排序："+Arrays.toString(QuickSort.qucikSort2(arr)));
        //7、堆排序
        System.out.println(Arrays.toString(HeapSort.heapSort(arr)));
        //8、计数排序
        System.out.println(Arrays.toString(CountingSort.countingSort(arr)));
        //9、桶排序
        System.out.println(Arrays.toString(BucketSort.bucketSort(arr)));
        //10、基数排序
        arr = new int[]{-7, 51, 3, 121, -3, 32, 21, 43, 4, 25, 56, 77, 16, 25, 87, 56, -11, 68, 99, 70};
        System.out.println(Arrays.toString(RadixSort.radixSort(arr)));
        arr = new int[]{-7, 51, 3, 121, -3, 32, 21, 43, 4, 25, 56, 77, 16, 25, 87, 56, -11, 68, 99, 70};
        System.out.println(Arrays.toString(RadixSort.RadixSort(arr)));

    }












}
