package com.algorithm.search;

import com.algorithm.sort.InsertionSort;

/**
 * 插值查找
 * https://www.cnblogs.com/yw09041432/p/5908444.html
 * 对二分查找的优化。
 *
 * 二分查找公式为：
 *  mid = (low+high)/2 = (high-low)/2 + low = (1/2 * (high-low)) + low
 *
 * 根据字典查询可知，我们可以通过自适应的知道要查找的key大概在哪个区间内
 * 对1/2进行改造，则公式为：
 * mid = (key-a[low])/(a[high]-a[low]) * (high-low) + low
 *
 *也就是将上述的比例参数1/2改进为自适应的，根据关键字在整个有序表中所处的位置，让mid值的变化更靠近关键字key，这样也就间接地减少了比较次数。
 * 　　基本思想：基于二分查找算法，将查找点的选择改进为自适应选择，可以提高查找效率。当然，差值查找也属于有序查找。
 * 　　注：对于表长较大，而关键字分布又比较均匀的查找表来说，插值查找算法的平均性能比折半查找要好的多。反之，数组中如果分布非常不均匀，那么插值查找未必是很合适的选择。
 * 　　复杂度分析：查找成功或者失败的时间复杂度均为O(log2(log2n))。
 */
public class InsertionSearch {

    public static int search(int[] array,int n){
        InsertionSort.insertionSort(array);
        return insertionSearch(array,n,0, array.length-1);
    }

    public static int insertionSearch(int[] a,int key,int low,int high){
        if (low>high){
            return -1;
        }
        if (low==high){
            if (a[high]==key){
                return key;
            }
            return -1;
        }
        int mid = (key-a[low])/(a[high]-a[low]) * (high-low) + low;
        int val = a[mid];
        if (val==key)
            return key;
        else if (val >key)
            return insertionSearch(a,key,low,mid-1);
        else if (val < key)
            return insertionSearch(a,key,mid+1,high);
        return -1;
    }
}
