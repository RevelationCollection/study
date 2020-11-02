package com.leetcode.problemset;

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *longest-palindromic-substring
 */
public class Pro_5 {

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
        System.out.println(longestPalindrome2("babad"));
        System.out.println(longestPalindrome2("ac"));
    }

    /**
     * 动态规划
     * 1、假设s[i...j] 代表回文的区间，且 0<=i<j<=n-1
     * 2、用dp[i][j] 代表s[i...j]
     * 3、根据暴力破解得到状态转移方程，dp[i][j] = (s[i]==s[j]) and dp[i+1][j-1]
     * 4、考虑临界值：dp[i+1][j-1],的表达式展开为s[i+1,j-1] ，长度大于2才能得到区间
     * 则： (j-1) - (i+1) +1  < 2 是不满足状态转移方程的 , +1 (数组坐标从0开始)
     * 整理得 j - i < 3
     * 当 s[i][j] 小于2有两种情况
     * 当间距等于1 时，左右两边时相等的，s[i][j]=true
     * 当间距等于0时，空串，满足a[i]=a[j] --> s[i][j]=true
     *
     * 5、初始化
     * 单个字符串肯定是true
     * a[i][i] = true
     *
     * @param s 字符串
     * @return 回文字符串
     */
    public static String longestPalindrome2(String s) {
        int n = s.length();
        if (n<2){
            return s;
        }
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i]=true;
        }
        int begin=0,maxlen = 1;
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < j; i++) {
                if (s.charAt(i)!=s.charAt(j)){
                    //首字母和尾字母不想等不属于回文
                    dp[i][j] = false;
                }else {
                    if (j-i<3){
                        //当前属于极端情况，根据推导属于回文
                        dp[i][j]=true;
                    }else {
                        //当钱首尾相等则  true & dp[i+1][j-1] = dp[i+1][j-1]
                        dp[i][j] = dp[i+1][j-1];
                    }
                }
                //当前属于回文且间距大于之前的最大长度。。。此次是目前的最长回文
                if (dp[i][j] && (j-i+1>maxlen)){
                    //记录开始时间和长度
                    begin = i;
                    maxlen = j-i+1;
                }
            }
        }
        return s.substring(begin,begin+maxlen);
    }

    /**
     * 暴力破解
     * 回文的特性是首尾必须一样
     * 且每去掉首尾两个字符，剩余的字符首尾也还是相等，直到中间只剩一个或没有
     * abba
     * abbabba
     * */
    public static String longestPalindrome(String s) {
        if (s==null || s.length()<2){
            return s;
        }
        int len = s.length();
        int start = 0,max = 0;
        for (int i = 0; i < len-1; i++) {
            for (int j = i+1; j < len; j++) {
                if (j-i+1 >max && judgePalindromic(s,i,j) ){
                    start = i ;
                    max = j -i+1;
                }
            }
        }
        return s.substring(start,start+max);
    }

    /** 判断 */
    private static boolean judgePalindromic(String s,int left,int right){
        if (left >right){
            return false;
        }
        while (left<right){
            if (s.charAt(left) != s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
