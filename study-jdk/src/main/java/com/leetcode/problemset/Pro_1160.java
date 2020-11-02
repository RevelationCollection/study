package com.leetcode.problemset;

import java.util.HashMap;
import java.util.Map;

public class Pro_1160 {

    public static void main(String[] args) {
        String[] words = {"cat","bt","hat","tree"};
        String chars = "atach";
        System.out.println(countCharacters(words,chars));
    }

    public static int countCharacters(String[] words, String chars) {
        if (words==null || chars==null){
            return 0;
        }
        int[] cache = new int[26];
        for (char c : chars.toCharArray()) {
            cache[c-'a']++;
        }
        int ans = 0;
        a: for (String word : words) {
            int[] wordCount = new int[26];
            for (char c : word.toCharArray()) {
                wordCount[c-'a']++;
            }
            for (int i = 0; i < 26; i++) {
                if (cache[i]<wordCount[i]){
                    continue a;
                }
            }
            ans+=word.length();
        }
        return ans;
    }

    public static int countCharacters1(String[] words, String chars) {
        if (words==null || chars==null){
            return 0;
        }
        HashMap<Character,Integer> cache = new HashMap<>(chars.length()*2);
        for (char c : chars.toCharArray()) {
            Integer count = cache.get(c);
            if (count==null){
                count=0;
            }
            cache.put(c,++count);
        }
        int ans = 0;
        for (String word : words) {
            Map<Character,Integer> wordCount = new HashMap<>(word.length()*2);
            for (char c : word.toCharArray()) {
                Integer count = wordCount.get(c);
                if (count==null){
                    count=0;
                }
                wordCount.put(c,++count);
            }
            boolean isAns = true;
            for (int i = 0; i < word.length(); ++i) {
                char c = word.charAt(i);
                if (cache.getOrDefault(c, 0) < wordCount.getOrDefault(c, 0)) {
                    isAns = false;
                    break;
                }
            }
            if (isAns) {
                ans += word.length();
            }
        }
        return 0;
    }
}
