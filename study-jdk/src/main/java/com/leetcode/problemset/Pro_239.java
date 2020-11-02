package com.leetcode.problemset;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Pro_239 {
    public static void main(String[] args) {
        int[] nums = {-20,-21,-23,1,3,-1,-3,5,3,6,7};
        System.out.println(Arrays.toString(maxSlidingWindow(nums,3)));
    }

    public static int[] maxSlidingWindow2(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        int index = 0;
        //双端队列，就是两边都可以插入和删除数据的队列，注意这里存储
        //的是元素在数组中的下标，不是元素的值
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            //如果队列中队头元素和当前元素位置相差i-k，相当于队头元素要
            //出窗口了，就把队头元素给移除，注意队列中存储
            //的是元素的下标（函数peekFirst()表示的是获取队头的下标，函数
            //pollFirst()表示的是移除队头元素的下标）
            if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            //在添加一个值之前，前面比他小的都要被移除掉，并且还要保证窗口
            //中队列头部元素永远是队列中最大的
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            //当前元素的下标加入到队列的尾部
            deque.addLast(i);
            //当窗口的长度大于等于k个的时候才开始计算（注意这里的i是从0开始的）
            if (i >= k - 1) {
                //队头元素是队列中最大的，把队列头部的元素加入到数组中
                res[index++] = nums[deque.peekFirst()];
            }
        }
        return res;
    }
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int len = nums.length-k+1;
        int[] ans = new int[len];
        int start = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[deque.peekLast()] <nums[i]){
                deque.pollLast();
            }
            deque.addLast(i);
            if (i>=k-1){
                ans[start++] = nums[deque.peekFirst()];
            }
        }

        return ans;
    }




    public static int[] maxSlidingWindow1(int[] nums, int k) {
        int len = nums.length-k+1;
        int[] ans = new int[len];
        int start = 0;
        for (int i = 0; i < len; i++) {
            int max = i;
            for (int j = i+1; j <i+k ; j++) {
                if (nums[max]<nums[j]){
                    max = j;
                }
            }
            ans[start++] = nums[max];
        }
        return ans;
    }

    class CustomStack{



    }
}
