package com.leetcode.problemset;

public class Pro_125 {
    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama")==true);
        System.out.println(isPalindrome("race a car")==false);
        System.out.println(isPalindrome("")==true);
        System.out.println(isPalindrome("1")==true);
        System.out.println(isPalindrome("abba")==true);
        System.out.println(isPalindrome("op")==false);
        System.out.println(isPalindrome("0P")==false);
    }

    public static boolean isPalindrome(String s) {
        if (s==null || s.length()<0){
            return false;
        }
        int left = 0;
        int right = s.length()-1;
        while (left<right){
            if (!isWorld(s.charAt(left))){
                left++;
            }
            else  if(!isWorld(s.charAt(right))){
                right--;
            }
            else if (getUppercase(s.charAt(left))!=getUppercase(s.charAt(right))){
                return false;
            }else {
                left++;
                right--;
            }
        }
        return true;
    }

    private static char getUppercase(char c){
        if (c>='a'){
            return (char) (c -32);
        }
        return c;
    }

    private static boolean isWorld(char c){
        return (c<='z' && c>='a') || (c>='A' && c<='Z') || (c>='0' && c<='9');
    }
}
