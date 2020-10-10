package com.algorithm.search;

/**
 * https://www.cnblogs.com/yw09041432/p/5908444.html
 */
public class AMainSearch {

    public static void main(String[] args) {
        int[] array = new int[]{8,9,1,7,2,3,5,4,6,0};
        //1、顺序查找
        System.out.println(SequenceSearch.search(array,3));
        //2、二分查找
        System.out.println(BinarySearch.search(array,0));
        //3、插值插值
        System.out.println(InsertionSearch.search(array,6));
        //4、斐波那契插值
        System.out.println(FibonacciSearch.search(array,6));

    }
}
