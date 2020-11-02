package com.leetcode.problemset;

/**
 * 1550. 存在连续三个奇数的数组
 */
public class Pro_1550 {

    public static void main(String[] args) {
        int[] arr = new int[]{2,6,4,1};
        System.out.println(threeConsecutiveOdds(arr));
        arr = new int[]{1,2,34,3,4,5,7,23,12};
        System.out.println(threeConsecutiveOdds(arr));
        arr = new int[]{1,2,34,3,4,6,5,7,23,12};
        System.out.println(threeConsecutiveOdds(arr));
        arr = new int[]{1,3,2};
        System.out.println(threeConsecutiveOdds(arr));
        arr = new int[]{1,3,1};
        System.out.println(threeConsecutiveOdds(arr));
    }

    public static boolean threeConsecutiveOdds(int[] arr) {
        if (arr==null|| arr.length<3){
            return false;
        }
        int len=arr.length;
        int i = 0;
        while (i<len-2){
            if (arr[i]%2==0){
                i++;
                continue;
            }
            if (i+1<len && arr[i+1]%2==0){
                i+=2;
                continue;
            }
            if (i+2<len && arr[i+2]%2==0){
                i+=3;
                continue;
            }
            return true;
        }
        return false;
    }
}
