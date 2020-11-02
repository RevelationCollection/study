package com.leetcode.problemset;

/**
 * 回文数
 */
public class Pro_9 {


    public static void main(String[] args) {
//        System.out.println(isPalindrome(121));
        System.out.println(isPalindrome(8));
//        System.out.println(isPalindrome(-121));
//        System.out.println(isPalindrome(-221));
    }


    /**
     * 反转数字..对半一样即回文数，负数不是，位数0也不是，个位数是
     * @param x
     * @return
     */
    public static boolean isPalindrome(int x) {
        if (x<0){
            return false;
        }
        int old = x;
        int ans = 0;
        int max = Integer.MAX_VALUE/10;
        int min = Integer.MIN_VALUE/10;
        int maxSur = Integer.MAX_VALUE%10;
        int minSur = Integer.MIN_VALUE%10;
        while (x!=0){
            int pop = x %10;
            if (ans>max && (ans==max && pop>maxSur)){
                return false;
            }
            if (ans<min && (ans==min && pop<minSur)){
                return false;
            }
            ans = ans*10 + pop;
            x /=10;
        }
        return ans==old;
    }
}
