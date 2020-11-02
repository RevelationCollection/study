package com.leetcode.problemset;

import java.util.LinkedList;

public class Pro_20 {

    public static void main(String[] args) {
        System.out.println((char)('{'+1));
    }

    private static boolean isValid(String s) {
        if (s==null||s.length()<1){
            return true;
        }
        LinkedList<Character> stack = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (c=='{' || c=='(' || c=='['){
                stack.push(c);
                continue;
            }
            if (c=='}'||c==')'||c==']'){
                Character ch = stack.poll();
                if (ch==null){
                    return false;
                }
                if (getRever(ch)!=c){
                    return false;
                }
            }
        }
        if (stack.isEmpty()){
            return true;
        }
        return false;
    }

    private static char getRever(char c){
        switch (c){
            case '{': return '}';
            case '[': return ']';
            case '(': return ')';
            default: return 0;
        }
    }
}
