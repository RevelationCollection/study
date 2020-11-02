package com.leetcode.problemset;

public class Pro_925 {
    public static void main(String[] args) {
        System.out.println(isLongPressedName("alex","aaleex")==true);
        System.out.println(isLongPressedName("saeed","ssaaedd")==false);
        System.out.println(isLongPressedName("leelee","lleeelee")==true);
        System.out.println(isLongPressedName("laiden","laiden")==true);
        System.out.println(isLongPressedName("pyplrz","ppyypllr")==false);
        System.out.println(isLongPressedName("alex","alexxr")==false);
    }

    public static boolean isLongPressedName(String name, String typed) {
        if (name==null || typed==null){
            return false;
        }
        if (name.length()==typed.length()){
            return name.equals(typed);
        }
        if (name.charAt(name.length()-1)!=typed.charAt(typed.length()-1)){
            return false;
        }
        char prev = 0;
        int i=0;
        char[] chars = typed.toCharArray();
        n: for (char c : name.toCharArray()) {
            while (i<chars.length){
                char t = chars[i];
                if (c==t){
                    prev = c;
                    ++i;
                    continue n;
                }
                if (prev==t){
                    ++i;
                    continue ;
                }
                return false;
            }
        }
        return true;
    }
}
