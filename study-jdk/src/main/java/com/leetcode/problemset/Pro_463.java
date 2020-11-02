package com.leetcode.problemset;

public class Pro_463 {
    public static void main(String[] args) {
        int[] nums = {1,3,1,6};
        System.out.println(minMoves2(nums));
    }

    public static int minMoves2(int[] nums) {
        if (nums==null || nums.length<2) return 0;
        int max = nums[0] ,min = nums[0];
        for (int num : nums) {
            if (max<num) max=num;
            else if(min>num) min=num;
        }
        int mid = (max+min)/2;
        int move = 0;
        for (int num : nums) {
            move += Math.abs(num-mid);
        }
        return move;
    }

    public int islandPerimeter(int[][] grid) {
        int land = 0;
        int border = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j]==1){
                    land++;
                    if(i<grid.length-1 && grid[i+1][j]==1){
                        border++;
                    }
                    if (j<grid[0].length-1 && grid[i][j+1]==1){
                        border++;
                    }
                }
            }
        }
        return land*4-border*2;
    }



}
