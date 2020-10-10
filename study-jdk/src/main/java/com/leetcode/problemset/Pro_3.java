package com.leetcode.problemset;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 输入: "abcabcbb"
 * 输出: 3
 */
public class Pro_3 {

    public static void main(String[] args) {
        System.out.println("abcabcbb".substring(2, 3));
        System.out.println(lengthOfLongestSubstring2("pwwkew"));
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0)
            return 0;
        Map<Character, Integer> cache = new HashMap<>();
        int left = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer index = cache.get(c);
            if (index != null) {
                left = Math.max(left, index + 1);
            }
            cache.put(c, i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }


    public  static  int lengthOfLongestSubstring2(String s) {
        int len = 0;
        // 窗口起始位置
        int start = 0;
        for (int cur = 1; cur < s.length(); cur++) {
            // 拿到每个当前指针元素来从窗口起始位置遍历来判断是否已存在该元素
            for (int i = start; i < cur; i++) {
                if (s.charAt(i) == s.charAt(cur)) {
                    // 存在截取长度
                    len = Math.max(len, cur - start);
                    // 窗口起始点变成出现重复第一个元素的后面
                    start = i + 1;
                    // 跳出检查
                    break;
                }
            }
        }
        // 最后比较最大长度和遍历完窗口的长度
        return Math.max(s.length() - start, len);
    }
}
