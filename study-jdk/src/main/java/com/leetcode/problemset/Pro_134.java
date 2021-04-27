package com.leetcode.problemset;

public class Pro_134 {
    public static void main(String[] args) {
        int[] gas  = new int[]{1,2,3,4,5};
        int[] cost = new int[]{3,4,5,1,2};
//        System.out.println(canCompleteCircuit(gas,cost));
        gas  = new int[]{2,3,4};
        cost = new int[]{3,4,3};
        System.out.println(canCompleteCircuit(gas,cost));
        gas  = new int[]{3,1,1};
        cost = new int[]{1,2,2};
        System.out.println(canCompleteCircuit(gas,cost));
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int i = 0;

        // 从头到尾遍历每个加油站，并且检查以该加油站为起点，能否行驶一周
        while(i < n){
            int sumOfGas  = 0; // 总共加的油
            int SumOfCost = 0; // 总共消费的油
            int count = 0;     // 记录能走过几个站点
            while(count < n){  // 退出循环的条件是走过所有的站点
                int j = (i + count) % n; // 加油站是环形的
                sumOfGas += gas[j];
                SumOfCost += cost[j];
                if(SumOfCost > sumOfGas){ // 如果这个站点发现油不够了
                    break;
                }
                count++; // 这个站点满足情况
            }

            if(count == n){  // 如果能环绕一圈
                return i;
            }else{ // 不行的话 从下一个站点开始 检查
                i = i + count + 1;
            }
        }
        // 所有加油站作为起点都不满足
        return -1;
    }
}
