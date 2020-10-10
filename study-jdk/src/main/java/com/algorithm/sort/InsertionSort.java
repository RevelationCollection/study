package com.algorithm.sort;

public class InsertionSort {
    /**
     * 插入排序 ,提取出当前值，并遍历比较，把它放入到合适的位置
     * 最佳情况：T(n) = O(n)   最坏情况：T(n) = O(n^2)   平均情况：T(n) = O(n^2)
     * @param array 数组
     * @return 有序数组
     */
    public static int[] insertionSort(int[] array) {
        if (array.length==0) {
            return array;
        }
        for (int i = 1; i < array.length-1; i++) {
            int current = array[i+1];
            int preIndex = i;
            while (preIndex>=0 && current<array[preIndex]){
                array[preIndex+1] = array[preIndex];
                preIndex--;
            }
            array[preIndex+1] = current;
        }
        return array;
    }
}
