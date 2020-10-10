package com.algorithm.search;

import com.algorithm.sort.InsertionSort;

import java.util.Arrays;

/**
 * 斐波那契查找
 * 在介绍斐波那契查找算法之前，我们先介绍一下很它紧密相连并且大家都熟知的一个概念——黄金分割。
 *
 * 　　黄金比例又称黄金分割，是指事物各部分间一定的数学比例关系，即将整体一分为二，较大部分与较小部分之比等于整体与较大部分之比，其比值约为1:0.618或1.618:1。
 *
 * 　　0.618被公认为最具有审美意义的比例数字，这个数值的作用不仅仅体现在诸如绘画、雕塑、音乐、建筑等艺术领域，而且在管理、工程设计等方面也有着不可忽视的作用。因此被称为黄金分割。
 *
 * 　　大家记不记得斐波那契数列：1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89…….（从第三个数开始，后边每一个数都是前两个数的和）。然后我们会发现，随着斐波那契数列的递增，前后两个数的比值会越来越接近0.618，利用这个特性，我们就可以将黄金比例运用到查找技术中。
 * 基本思想：也是二分查找的一种提升算法，通过运用黄金比例的概念在数列中选择查找点进行查找，提高查找效率。同样地，斐波那契查找也属于一种有序查找算法。
 * 相对于折半查找，一般将待比较的key值与第mid=（low+high）/2位置的元素比较，比较结果分三种情况：
 * 　　1）相等，mid位置的元素即为所求
 *
 * 　　2）>，low=mid+1;
 *
 *      3）<，high=mid-1。
 *
 * 　　斐波那契查找与折半查找很相似，他是根据斐波那契序列的特点对有序表进行分割的。他要求开始表中记录的个数为某个斐波那契数小1，及n=F(k)-1;
 *
 *  开始将k值与第F(k-1)位置的记录进行比较(及mid=low+F(k-1)-1),比较结果也分为三种
 *
 * 　　1）相等，mid位置的元素即为所求
 *
 * 　　2）>，low=mid+1,k-=2;
 *
 * 　　说明：low=mid+1说明待查找的元素在[mid+1,high]范围内，k-=2 说明范围[mid+1,high]内的元素个数为n-(F(k-1))= Fk-1-F(k-1)=Fk-F(k-1)-1=F(k-2)-1个，所以可以递归的应用斐波那契查找。
 *
 * 　　3）<，high=mid-1,k-=1。
 *
 * 　　说明：low=mid+1说明待查找的元素在[low,mid-1]范围内，k-=1 说明范围[low,mid-1]内的元素个数为F(k-1)-1个，所以可以递归 的应用斐波那契查找。
 *
 * 　　复杂度分析：最坏情况下，时间复杂度为O(log2n)，且其期望复杂度也为O(log2n)
 *
 * (mid - low) / (high - low)= F(k - 1) / F(k)接近于0.618
 * F(k) = high - low
 * 则： mid-low = F(k - 1)
 * mid = low + F(k - 1) - 1
 */
public class FibonacciSearch {

    public static int search(int[] array,int n){
        InsertionSort.insertionSort(array);
        return fibonacciSearch(array,n,0,array.length-1);
    }

    public static int maxSize = 20;

    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for(int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    // 斐波那契查找（其实就是每次查找的点，都接近把数组划分成0.618比率，左边元素个数/总元素个数 约等于 0.618）
    // 大致就是想要每次查找按照（查找点左侧元素/总元素个数）接近0.618的比率获取查找元素下标。
    // 因为斐波那契查找，相邻两项前后的比值接近0.618，然后就根据斐波那契数列的规律利用到查找算法中。
    private static int fibonacciSearch(int a[], int key, int low, int high){
        // 表示斐波那契分割数值的下标
        int k = 0;
        int mid = 0;
        int f[] = fib();
        // 获取斐波那契分割数值下标，根据查找数组的长度，决定需要多少个斐波那契数值
        while(high > f[k] - 1) {
            k++;
        }
        // 因为f[k]值可能大于a的长度，因此我们需要使用Arrays类，构造一个新的数组，并指向a[]
        // 不足的部分会使用0填充
        int[] temp = Arrays.copyOf(a, f[k]);
        // 实际上需求使用a数组最后的数填充temp
        // 例如：temp = {1,8,10,89,1000,1234,0,0,0} => {1,8,10,89,1000,1234,1234,1234}
        for(int i = high + 1; i < temp.length; i++) {
            temp[i] = a[high];
        }
        // 使用while来循环处理，找到我们的数key
        while(low <= high) {
            mid = low + f[k - 1] - 1;
            // 我们应该继续向数组左边查找
            if(key < temp[mid]) {
                high = mid - 1;
                // 为什么是k--
                // 全部元素 = 前面元素 + 后边元素 f[k] = f[k - 1] + f[k - 2]
                // 因为前面有f[k - 1]个元素，所以可以继续拆分f[k - 1] = f[k - 2] + f[k - 3]
                // 即在f[k - 1]左部继续查找k--，即下次循环mid = f[k - 1 - 1] - 1
                k--;
            } else if(key > temp[mid]) {
                // 全部元素 = 前面元素 + 后边元素 f[k] = f[k - 1] + f[k - 2]
                // 右边查找
                low = mid + 1;
                k -= 2;
            } else {
                return Math.min(mid, high);
            }
        }
        return -1;
    }
}
