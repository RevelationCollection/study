package com.leetcode.problemset;

/**
 * 假设你有一个很长的花坛，一部分地块种植了花，另一部分却没有。可是，花卉不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
 *
 * 给定一个花坛（表示为一个数组包含0和1，其中0表示没种植花，1表示种植了花），和一个数 n 。能否在不打破种植规则的情况下种入 n 朵花？能则返回True，不能则返回False。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/can-place-flowers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 输入: flowerbed = [1,0,0,0,1], n = 1
 * 输出: True
 *
 * [0,0,1,0,1]
 * [0,0,0,1]
 * [1,0,0,1,0,0,0]
 * [1,0,0,0,1]
 */
public class Pro_605 {
    public static void main(String[] args) {
        int[] arr = new int[]{0,0,1,0,0};
        System.out.println(canPlaceFlowers(arr,1)==true);
        arr = new int[]{1,0,0};
        System.out.println(canPlaceFlowers(arr,1)==true);
        arr = new int[]{0,0,1};
        System.out.println(canPlaceFlowers(arr,1)==true);
        arr = new int[]{1,0,0,1,0,0};
        System.out.println(canPlaceFlowers(arr,1)==true);
        arr = new int[]{1,0,0,0,1};
        System.out.println(canPlaceFlowers(arr,1)==true);
        arr = new int[]{1};
        System.out.println(canPlaceFlowers(arr,1)==false);
        arr = new int[]{0};
        System.out.println(canPlaceFlowers(arr,1)==true);
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        int i = 0,count=0;
        int len = flowerbed.length;
        while (i<len){
            if (flowerbed[i]==0
                    && (i==0||flowerbed[i-1]==0)
                    && (i==len-1 || flowerbed[i+1]==0)){
                flowerbed[i]=1;
                count++;
            }
            i++;
        }
        return count==n;
    }
}
