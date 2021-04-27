package com.leetcode.problemset;

import java.util.Stack;

public class Pro_42 {

    public static int trap(int[] height) {
        if(height==null || height.length<3) return 0;
        //找到最大的高度，以便遍历。
        int max = height[0];
        for (int i : height) {
            max = Math.max(max, i);
        }
        int ans = 0;
        for (int i = 1; i <= max; i++) {
            //标记是否开始更新 temp
            boolean isStart = false;
            int sum = 0;
            for (int j = 0; j < height.length; j++) {
                if(isStart && height[j]<i){
                    sum++;
                }
                if(height[j]>=i){
                    ans+=sum;
                    isStart = true;
                    sum = 0;
                }
            }
        }
        return ans;
    }

    public static int trap1(int[] height) {
        int sum = 0;
        for (int i = 1; i < height.length-1; i++) {
            int maxLeft = 0;
            //find max left
            for (int j = i-1; j >= 0 ; j--) {
                maxLeft = Math.max(maxLeft, height[j]);
            }
            //find max right
            int maxRight = 0;
            for (int j = i+1; j < height.length; j++) {
                maxRight = Math.max(maxRight, height[j]);
            }
            int min = Math.min(maxLeft, maxRight);
            if(min>height[i]){
                sum+=(min-height[i]);
            }
        }
        return sum;
    }

    public static int trap2(int[] height) {
        if(height.length<3) return 0;
        int sum = 0;
        //缓存左边最大值
        int[] maxLeft = new int[height.length];
        maxLeft[0] = height[0];
        for (int i = 1; i < height.length-1; i++) {
            //find max left
            maxLeft[i] = Math.max(maxLeft[i-1], height[i-1]);
            //find max right
            int maxRight = 0;
            for (int j = i+1; j < height.length; j++) {
                maxRight = Math.max(maxRight, height[j]);
            }
            int min = Math.min(maxLeft[i], maxRight);
            if(min>height[i]){
                sum+=(min-height[i]);
            }
        }
        return sum;
    }

    public static int trap3(int[] height) {
        if(height.length<3) return 0;
        int sum = 0;
        //缓存左边最大值
        int[] maxLeft = new int[height.length];
        //find max right,缓存右边最大值
        int[] maxRight = new int[height.length];
        for (int j = height.length-2; j>=0; j--) {
            maxRight[j] = Math.max(maxRight[j+1], height[j+1]);
        }
        maxLeft[0] = height[0];
        for (int i = 1; i < height.length-1; i++) {
            //find max left
            maxLeft[i] = Math.max(maxLeft[i-1], height[i-1]);
            int min = Math.min(maxLeft[i], maxRight[i]);
            if(min>height[i]){
                sum+=(min-height[i]);
            }
        }
        return sum;
    }

    public static int trap4(int[] height) {
        if(height.length<3) return 0;
        int sum = 0;
        //缓存左边最大值,不用数组，左边只用一次
        int maxLeft = 0;
        //find max right,缓存右边最大值
        int[] maxRight = new int[height.length];
        for (int j = height.length-2; j>=0; j--) {
            maxRight[j] = Math.max(maxRight[j+1], height[j+1]);
        }
        for (int i = 1; i < height.length-1; i++) {
            //find max left
            maxLeft = Math.max(maxLeft, height[i-1]);
            int min = Math.min(maxLeft, maxRight[i]);
            if(min>height[i]){
                sum+=(min-height[i]);
            }
        }
        return sum;
    }

    public static int trap5(int[] height) {
        /*
         * max_left = Math.max(max_left, height[i - 1]);
         * max_right = Math.max(max_right,height[i+1]);
         * height[i-1]<height[i+1] => max_left < max_right
         */
        int sum = 0;
        int left = 1,maxLeft=0,maxRight=0;
        int right = height.length-2;//右指针
        for (int i = 1; i < height.length-1; i++) {
            if(height[left-1]<height[right+1]){
                //从左到右更
                maxLeft = Math.max(maxLeft, height[left-1]);
                //左边是 | | 的最小值，左边的最小值减去当前柱子=接水的水量
                if(maxLeft>height[left]){
                    sum+=(maxLeft-height[left]);
                }
                left++;
            }else{
                //从右边往左更新
                maxRight = Math.max(maxRight, height[right+1]);
                if(maxRight>height[right]){
                    sum+=(maxRight-height[right]);
                }
                right--;
            }
        }
        return sum;
    }

    public static int trap6(int[] height) {
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        int current = 0;
        while(current<height.length){
            //如果栈不为空，且当前指向的高度大于栈顶的高度，出栈计算
            while(!stack.isEmpty()
                    && height[current]>height[stack.peek()]){
                //获取出栈的元素高度（宽度）
                int h = height[stack.pop()];
                //如果栈里面没有元素，跳出循环，两堵墙才可以接水
                if(stack.isEmpty()) break;
                //获得两堵墙之间的距离
                int distance = current - stack.peek()-1;
                //获得更矮的墙的距离,（长度）
                int min = Math.min(height[stack.peek()], height[current]);
                //获得长方形的面积
                sum+=(distance * (min-h));
            }
            //当前指向的墙入栈
            stack.push(current);
            //指针后移
            current++;
        }
        return sum;
    }
}
