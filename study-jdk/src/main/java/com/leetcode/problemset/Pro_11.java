package com.leetcode.problemset;

public class Pro_11 {

    public static void main(String[] args) {
        int[] arr = {1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(arr));
    }

    public static int maxArea(int[] height) {
        //i,j收敛判断
        int i=0,j=height.length-1;
        int ans = 0;
        while (i<j){
            int minHeight = height[i]<height[j]?height[i++]:height[j--];
            ans = Math.max(ans,(j-i)*minHeight);
        }
        return ans;
    }
    public static int maxArea1(int[] height) {
        if (height==null){
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < height.length; i++) {
            int h = height[i];
            for (int j = i+1; j < height.length; j++) {
                int w = j-i;
                int min = Math.min(height[i], height[j]);
                ans = Math.max(ans,min*w);
            }
        }
        return ans;
    }
}
