package com.leetcode.problemset;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Pro_127 {
    public static void main(String[] args) {
        System.out.println(ladderLength("hit","cog", Arrays.asList("cog","hot","dot","dog","lot","log")));
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)){
            return 0;
        }
        HashSet<String> cache = new HashSet<>();
        LinkedList<String> queue = new LinkedList<>();
        cache.add(beginWord);
        queue.add(beginWord);
        int count = 0;
        while (!queue.isEmpty()){
            int size = queue.size();
            count++;
            for (int i = 0; i < size; i++) {
                String start = queue.poll();
                for (String s : wordList) {
                    if (cache.contains(s)) {
                        continue;
                    }
                    if (!canConvert(start,s)){
                        continue;
                    }
                    if (s.equals(endWord)){
                        return ++count;
                    }
                    cache.add(s);
                    queue.offer(s);
                }
            }
        }
        return 0;
    }

    private static boolean canConvert(String old,String s){
        if (old.length()!=s.length()) return false;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (old.charAt(i)!=s.charAt(i)){
                count++;
                if (count>1) return false;
            }
        }
        return count==1;
    }

}
