package com.leetcode.problemset;


/**
 * 844. 比较含退格的字符串
 * https://leetcode-cn.com/problems/backspace-string-compare/
 *
 */
public class Pro_844 {

    public static void main(String[] args) {
        System.out.println(backspaceCompare("ab#c","ad#c"));
        System.out.println(backspaceCompare("ab##","c#d#"));
        System.out.println(backspaceCompare("a##c","#a#c"));
        System.out.println(backspaceCompare("a#c","b"));
    }

    public static boolean backspaceCompare(String S, String T) {
        //循环遍历
        if (S==null||T==null){
            return false;
        }
        char[] chars = S.toCharArray();
        int start= 0;
        char[] newChar = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch!='#'){
                newChar[start++] = ch;
                continue;
            }
            if (start==0){
                continue;
            }
            newChar[--start]=0;
        }
        int secondStart = 0;
        chars = T.toCharArray();
        char[] secondChar = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch!='#'){
                secondChar[secondStart++] = ch;
                continue;
            }
            if (secondStart==0){
                continue;
            }
            secondChar[--secondStart]=0;
        }
        if (secondStart!=start){
            return false;
        }
        for (int i = 0; i < start; i++) {
            if (newChar[i]!=secondChar[i]){
                return false;
            }
        }
        return true;
    }
}
