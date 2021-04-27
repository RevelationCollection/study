package com.leetcode.problemset;

import java.util.Arrays;

public class Pro {
    public static void main(String[] args) {

    }


    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i],1);
        }
        for (int i = 1; i < dp.length; i++) {
            int[] x = dp[i];
            for (int j = 1; j < x.length; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    public int uniquePaths2(int m, int n) {
        int[] dp = new int[m];
        Arrays.fill(dp,1);
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[j] += dp[j-1] ;
            }
        }
        return dp[m-1];
    }

    public int uniquePathsHelper(int i,int j,int m ,int n){
        if (i>m || j>n) return 0;
        if (i==m && j==n) return 1;
        int right = uniquePathsHelper(i+1,j,m,n);
        int down = uniquePathsHelper(i,j+1,m,n);
        return right+down;
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid==null || obstacleGrid.length<1) return 0;
        if (obstacleGrid[0][0]==1) return 0;
        int m = obstacleGrid[0].length;
        int[] dp = new int[m];
        dp[0] = 1;
        //System.out.println(Arrays.toString(dp));
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < m; j++) {
                if (obstacleGrid[i][j]==1) dp[j]=0;
                else if(j!=0){
                     dp[j] += dp[j-1];
                }
            }
            //System.out.println(Arrays.toString(dp));
        }
        return dp[m-1];
    }

    public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        int width = obstacleGrid[0].length;
        int[] dp = new int[width];
        dp[0] = 1;
        for (int[] row : obstacleGrid) {
            for (int i = 0; i < width; i++) {
                if (row[i]==1)
                    dp[i] = 0;
                else if(i>0){
                    dp[i] += dp[i+1];
                }
            }
        }
        return dp[width-1];
    }

}
