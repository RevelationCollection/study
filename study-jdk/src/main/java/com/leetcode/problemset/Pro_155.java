package com.leetcode.problemset;

import java.util.LinkedList;
import java.util.Stack;


/**
 * https://leetcode-cn.com/problems/min-stack/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-38/
 */
public class Pro_155 {
    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.push(-3);
        System.out.println(minStack.getMin()==-3);;
        minStack.pop();
        minStack.pop();
        System.out.println(minStack.top()==0);;
        System.out.println(minStack.getMin()==-2);
    }

    /**
     * 不用辅助栈，用一个栈+最小值完成
     * 当有最小值存入时，将之前的最小值压入栈中
     */
    private static class MinStack2 {

        private Stack<Integer> stack ;

        private int min = Integer.MAX_VALUE;

        public MinStack2() {
            stack = new Stack<>();
        }

        public void push(int x) {
            if (stack.isEmpty()){
                min = x;
            }
            if (x<=min){
                //值比最小值小
                stack.push(min);
                min = x;
            }
            stack.push(x);
        }

        public void pop() {
            if (stack.isEmpty()){
                return;
            }
            Integer pop = stack.pop();
            if (pop==min){
                min = stack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }

    /**
     * 存入和最小值两者的差，取的时候min+差值
     */
    private static class MinStack {

        private Stack<Integer> stack ;

        private int min = Integer.MAX_VALUE;

        public MinStack() {
            stack = new Stack<>();
        }

        public void push(int x) {
            if (stack.isEmpty()){
                min = x;
                stack.push(x-min);
                return;
            }
            stack.push(x-min);
            if (x<min){
                min =x; // 更新最小值
            }
        }

        public void pop() {
            if (!stack.isEmpty()){
                Integer val = stack.pop();
                if (val<0){
                    //弹出的是负数，需要更小最小值，因为只有比原来更小才会出现负数
                    min = min -val;
                }
            }
        }

        public int top() {
            int val = stack.peek();
            //负数的话，出栈的值保存在 min 中
            if (val<0){
                return min;
            }
            //出栈元素加上最小值即可
            return val+min;
        }

        public int getMin() {
            return min;
        }
    }

    /**
     * 辅助栈
     * 同步存取：存的时候用最小值替换，存取两个栈同时操作
     * 不同步：当弹出最小值的时候，再进行辅助栈弹出操作
     */
    private static class MinStack1 {

        private LinkedList<Integer> stack = new LinkedList<>();

        private LinkedList<Integer> minStack = new LinkedList<>();

        /** initialize your data structure here. */
        public MinStack1() {
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            stack.push(x);
            Integer peek = minStack.peek();
            if (peek>x){
                minStack.push(x);
            }else {
                minStack.push(peek);
            }
        }

        public void pop() {
            stack.poll();
            minStack.poll();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
