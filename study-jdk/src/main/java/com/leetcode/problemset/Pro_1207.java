package com.leetcode.problemset;

import java.util.*;

public class Pro_1207 {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,2,1,1,3};
        System.out.println(uniqueOccurrences(arr)==true);
        arr = new int[]{1,1,1,2,2,3,3,4,4,4};
        System.out.println(uniqueOccurrences(arr)==false);
        arr = new int[]{1,2};
        System.out.println(uniqueOccurrences(arr)==false);
        arr = new int[]{-3,0,1,-3,1,1,1,-3,10,0};
        System.out.println(uniqueOccurrences(arr)==true);
    }

    public static boolean uniqueOccurrences(int[] arr) {
        Arrays.sort(arr);
        int count =1;
        Set<Integer> cache = new HashSet<>();
        for (int i = 1; i < arr.length; i++) {
            //和前一个相等，累计+1
            if (arr[i-1]==arr[i]){
                count++;
            }else {
                //数量不能和之前的一样
                if (!cache.add(count)){
                    return false;
                }else {
                    count = 1;
                }
            }
        }
        if (!cache.add(count)){
            return false;
        }
        return true;
    }
    public boolean uniqueOccurrences1(int[] arr) {
        HashMap<Integer,Integer> cache = new HashMap<>();
        for(int i : arr){
            int count = cache.getOrDefault(i,0);
            cache.put(i,++count);
        }
        HashMap<Integer,Integer> count = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : cache.entrySet()) {
            if (count.get(entry.getValue())!=null){
                return false;
            }
            count.put(entry.getValue(),entry.getKey());
        }
        return true;
    }
}
