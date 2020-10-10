package com.algorithm.sort;

public class ShellSort {

    /**
     * 希尔排序
     * 将数组线分为length/2 个组，然后2个互相比较大小，进行插入排序，保证小的在左边，大的在右边
     * 再将数组分为 length/2/2  个组，组内进行插入排序
     * 最后 gap=1 ，全部数组进行插入排序
     * 最佳情况：T(n) = O(n log2 n)  最坏情况：T(n) = O(n log2 n)  平均情况：T(n) =O(nlog2n)　
     * @param array 数组
     * @return 有序数组
     */
    public static int[] shellSort(int[] array) {
        int len = array.length;
        int temp,gap = len/2 ;
        while (gap>0){
            for (int i = gap; i < len; i++) {
                temp = array[i];
                //前一个索引
                int preIndex = i - gap;
                while (preIndex>=0 && temp < array[preIndex]){
                    array[preIndex+gap] = array[preIndex];
                    preIndex -=gap;
                }
                array[preIndex+gap] = temp;
            }
            gap /= 2;
        }
        return array;
    }
}
