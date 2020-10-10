package com.algorithm.search;

/**
 * 顺序查找
 *  遍历查找
 */
public class SequenceSearch {

    public static int search(int[] array,int n){
        if (array==null ||array.length<=1){
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (n==array[i])
                return n;
        }
        return -1;
    }
}
