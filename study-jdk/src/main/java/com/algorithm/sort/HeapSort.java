package com.algorithm.sort;

/**
 * https://www.cnblogs.com/chengxiao/p/6129630.html
 * 堆排序
 * 将初始待排序关键字序列(R1,R2….Rn)构建成大顶堆，此堆为初始的无序区；
 * 将堆顶元素R[1]与最后一个元素R[n]交换，此时得到新的无序区(R1,R2,……Rn-1)和新的有序区(Rn),且满足R[1,2…n-1]<=R[n]；
 * 由于交换后新的堆顶R[1]可能违反堆的性质，因此需要对当前无序区(R1,R2,……Rn-1)调整为新堆，然后再次将R[1]与无序区最后一个元素交换，得到新的无序区(R1,R2….Rn-2)和新的有序区(Rn-1,Rn)。不断重复此过程直到有序区的元素个数为n-1，则整个排序过程完成。
 *
 * 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
 * 50 45 40 20 25 35 30 10 15
 *              50
 *      45      |  40
 *   20  |  25  | 35 30
 * 10 15 |
 *
 * 小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
 * 10 20 15 25 50 30 40 35 45
 *
 *              10
 *      20      |  15
 *   25  |  50  | 30 40
 * 35 45 |
 */
public class HeapSort {


    public static int[] heapSort(int[] array){
        int len = array.length;
        buildMaxHeap(array);
        while (len>0){
            //将最大的值换到最后
            swap(array,0,len-1);
            len--;
            adjustHeap(array,0,len);
        }
        return array;
    }

    public static void buildMaxHeap(int[] array){
        //1.构建大顶堆
        for (int i = array.length/2-1; i >=0 ; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(array,i,array.length);
        }
    }

    public static void adjustHeap(int[] array,int start,int end){
        //大顶堆，第一个最大
        int maxIndex = start;
        int left = start*2 +1;
        int right = start*2 +2;
        //如果有左子树，且左子树大于父节点，则将最大指针指向左子树
        if(left<end && array[left]>array[maxIndex]){
            maxIndex = left;
        }
        //如果有右子树，且右子树大于父节点，则将最大指针指向右子树
        if(right <end && array[right]>array[maxIndex]){
            maxIndex = right;
        }
        //如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。
        if (maxIndex!=start){
            swap(array,maxIndex,start);
            adjustHeap(array,maxIndex,end);
        }
    }

    //交换元素
    private static void swap(int[] array,int x,int y){
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}
