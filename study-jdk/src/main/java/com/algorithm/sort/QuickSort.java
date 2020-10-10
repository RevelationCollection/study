package com.algorithm.sort;

public class QuickSort {

    /**
     * 快速排序
     * 快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：
     *  从数列中挑出一个元素，称为 “基准”（pivot）；
     *  重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
     *  递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
     * @param array
     * @return
     */
    public static int[] quickSort(int[] array) {
        if (array==null) return array;
        return quickSort(array,0,array.length-1);
    }

    public static int[] quickSort(int[] array,int start, int end){
        if (array==null|| start<0 || end<start || end>=array.length){
            return null;
        }
        int smallIndex = partition(array, start, end);
        if (smallIndex>start){
            //左边排序
            quickSort(array,start,smallIndex-1);
        }
        if (smallIndex<end){
            //右边排序
            quickSort(array,smallIndex+1,end);
        }
        return array;
    }

    // 从开始位置遍历，如果当前值小于基准值则从开始节点开始递增替换
    private static int partition(int[] array,int start,int end){
        int pivot = (int) (start + Math.random()*(end-start+1));
        //开始替换节点
        int smallIndex = start - 1;
        //将基准放到最后
        swap(array,pivot,end);
        for (int i = start; i <= end; i++) {
            //当前的值小于基准值
            if (array[i]<= array[end]){
                //将要替换的坐标递增，1、因为之前-1  2、上一个坐标默认已经粗粝，当前要处理下一个坐标
                smallIndex++;
                //当前的坐标要大于要开始替换的下标，避免相同坐标替换
                if (i>smallIndex){
                    //将小于基准值的元素，在开始节点从左到右放置
                    swap(array,i,smallIndex);
                }
            }
        }
        return smallIndex;
    }

    /** 交换数组内的两个元素 */
    private static void swap(int[] array,int x,int y){
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }


    /**
     * 根据第一个值为基准值排序，维护两个指针
     * @param array 数组
     * @return 有序数组
     */
    public static int[] qucikSort2(int[] array){
        if (array==null) return array;
        return quickSort2(array,0,array.length-1);
    }

    private static int[] quickSort2(int[] array,int start,int end){
        if (start>=end || array==null || array.length<2){
            return array;
        }
        int index = getIndex(array, start, end);
        //继续根据之前的逻辑将基准值左右两边的数据再次重新排列
        quickSort(array,0,index-1);
        quickSort(array,index+1,end);
        return array;
    }

    private static int getIndex(int[] array,int start,int end){
        if (start>=end){
            return start;
        }
        //取首个元素为基准值
        int temp = array[start];
        while (start<end){
            //从队尾往前遍历，如果发现元素大于基准值则指针往前移动
            while (start<end && array[end]>=temp){
                end--;
            }
            //队尾元素小于基准值，替换内容
            array[start] = array[end];
            //从开始往后遍历，如果发现元素小于基准值则指针往后移动
            while (start<end && array[start]<=temp){
                start++;
            }
            //队首元素大于基准值，替换内容
            array[end] = array[start];
        }
        //跳出循环时，开始和结束指针相同，此时的start或end都是temp的正确索引位置，直接赋值即可
        array[start] = temp;
        return start;
    }
}
