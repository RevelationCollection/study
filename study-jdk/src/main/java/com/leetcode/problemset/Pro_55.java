package com.leetcode.problemset;

public class Pro_55 {
    public static void main(String[] args) {
        int[] nums = {1,1,2,2,0,1,1};
        System.out.println(canJump(nums));
        nums = new int[]{3,2,1,0,4};
        System.out.println(canJump(nums));
        nums = new int[]{0};
        System.out.println(canJump(nums));
    }

    public static boolean canJump(int[] nums) {
        //最远可达
        int maxLen = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (maxLen>=i){
                maxLen = Math.max(maxLen,nums[i]+i);
                if (maxLen>=len-1){
                    return true;
                }
            }else {
                return false;
            }
        }
        return false;
    }
    public static boolean canJump1(int[] nums) {
        if (nums==null || nums.length<1){
            return true;
        }
        return isEnd(nums,0,0);
    }

    private static boolean isEnd(int[] nums,int start,int step){
        if ((start+step+1)>=nums.length){
            return true;
        }
        int val = nums[start+step];
        if (val<=0){
            return false;
        }
        for (int i = val; i >0; i--) {
            if (isEnd(nums,start+step,i)){
                return true;
            }
        }
        return false;
    }
}
