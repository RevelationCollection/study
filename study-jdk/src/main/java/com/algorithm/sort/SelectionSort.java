package com.algorithm.sort;

public class SelectionSort {
    /**
     * 选择排序  找到最小值的下标，然后依次替换
     * 最佳情况：T(n) = O(n^2)  最差情况：T(n) = O(n^2)  平均情况：T(n) = O(n^2)
     * @param array 数组
     * @return 有序数组
     */
    public static int[] selectionSort(int[] array) {
        if (array.length==0){
            return array;
        }
        for (int i = 0; i <array.length ; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex]){
                    minIndex = j;
                }
            }
            int temp =  array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        return array;
    }
}
