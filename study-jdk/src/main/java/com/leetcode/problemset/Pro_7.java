package com.leetcode.problemset;

/**
 *
 * 整数反转
 *
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 */
public class Pro_7 {

    public static void main(String[] args) {
//        System.out.println(reverse(1234));
        System.out.println(reverse(-123));
//        System.out.println(reverse(0));
//        System.out.println(reverse(1534236469));
    }

    public static int reverse(int x) {
        int ans = 0;
        int max = Integer.MAX_VALUE/10;
        int min = Integer.MIN_VALUE/10;
        while (x!=0){
            int pop = x %10;
            //反转后。数超过了int的范围
            if (ans>max || (ans==max && pop>7)){
                return 0;
            }
            if (ans<min || (ans==min && pop<-8)){
                return 0;
            }
            ans = ans*10 + pop;
            x /=10;
        }
        return ans;
    }
}
