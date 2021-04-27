package com.leetcode.problemset;

public class Pro_1370 {
    public static void main(String[] args) {
        System.out.println(sortString(sortString("aaaabbbbcccc")));
        System.out.println(sortString(sortString("rat")));
        System.out.println(sortString(sortString("leetcode")));
        System.out.println(sortString(sortString("ggggggg")));
        System.out.println(sortString(sortString("spo")));
    }

    public static String sortString(String s) {
        StringBuilder ans = new StringBuilder();
        int[] words = new int[26];
        for (char c : s.toCharArray()) {
            words[c-'a']++;
        }
        while (ans.length()<s.length()){
            for (int i = 0; i <26 ; i++) {
                if (words[i]>0){
                    ans.append((char)('a'+i));
                    words[i]--;
                }
            }
            for (int i = 25; i >=0 ; i--) {
                if (words[i]>0){
                    ans.append((char)('a'+i));
                    words[i]--;
                }
            }
        }
        return ans.toString();
    }
}
