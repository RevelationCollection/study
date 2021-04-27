package com.leetcode.problemset;

public class Pro_242 {
    public static void main(String[] args) {
        System.out.println(isAnagram("anagram","nagaram"));
        System.out.println(isAnagram("rat","car"));
    }

    public static boolean isAnagram1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] counter = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a']++;
            counter[t.charAt(i) - 'a']--;
        }
        for (int count : counter) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
    public static boolean isAnagram(String s, String t) {
        if (s.length()!=t.length()){
            return false;
        }
        int[] words = new int[26];
        int[] other = new int[26];
        for (char c : s.toCharArray()) {
            words[c-'a']++;
        }
        for (char c : t.toCharArray()) {
            other[c-'a']++;
        }
        for (int i = 0; i < words.length; i++) {
            if (words[i]!=other[i]){
                return false;
            }
        }
        return true;
    }
}
