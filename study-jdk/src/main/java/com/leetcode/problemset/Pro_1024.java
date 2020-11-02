package com.leetcode.problemset;

import java.util.Arrays;

public class Pro_1024 {
    public static void main(String[] args) {
        int[][] clips = new int[6][2];
        clips[1] = new int[]{1,2};
        clips[2] = new int[]{4,6};
        clips[3] = new int[]{8,11};
        clips[4] = new int[]{7,9};
        clips[5] = new int[]{10,14};
        System.out.println(videoStitching(clips,12));

    }


    public static  int videoStitching1(int[][] clips, int T) {
        //最远可达
        int[] maxn = new int[T];
        for (int[] clip : clips) {
            maxn[clip[0]] = Math.max(maxn[clip[0]],clip[1]);
        }
        int last = 0,pre =0,ans = 0;
        for (int i = 0; i < maxn.length; i++) {
            //保存当前的最大可达
            last = Math.max(last,maxn[i]);
            //最后可达和当前相等，代表永远不可完成
            if (last==i){
                return -1;
            }
            //到了，上一次的最后可达，获取该区间内的最大可达，并累加
            if (pre==i){
                ans++;
                pre = last;
            }
        }
        return ans;
    }
    public static  int videoStitching2(int[][] clips, int T) {
        int[] dp = new int[T + 1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;
        for (int i = 1; i <= T; i++) {
            for (int[] clip : clips) {
                if (clip[0] < i && i <= clip[1]) {
                    dp[i] = Math.min(dp[i], dp[clip[0]] + 1);
                }
            }
        }
        return dp[T] == Integer.MAX_VALUE - 1 ? -1 : dp[T];
    }

    public static int videoStitching(int[][] clips, int T) {
        //dp动态规划
        int[] dp = new int[T+1];
        Arrays.fill(dp,Integer.MAX_VALUE-1);
        dp[0] = 0;
        for (int i = 1; i <= T; i++) {
            for (int[] clip : clips) {
                if (clip[0]<i && i<=clip[1]){
                    dp[i] = Math.min(dp[i],dp[clip[0]]+1);
                }
            }
        }
        return dp[T]==Integer.MAX_VALUE-1?-1:dp[T];
    }
}
