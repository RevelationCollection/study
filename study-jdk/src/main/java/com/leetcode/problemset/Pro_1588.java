package com.leetcode.problemset;

/**
 * 1588. 所有奇数长度子数组的和
 * 给你一个正整数数组 arr ，请你计算所有可能的奇数长度子数组的和。
 *
 * 子数组 定义为原数组中的一个连续子序列。
 *
 * 请你返回 arr 中 所有奇数长度子数组的和
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-of-all-odd-length-subarrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Pro_1588 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,4,2,5,3};
        System.out.println(sumOddLengthSubarrays(arr));
//        arr = new int[]{1,2};
//        System.out.println(sumOddLengthSubarrays(arr));
    }

    public static int sumOddLengthSubarrays(int[] arr) {
        /**
         * 统计规律
         *  i 左边 i+1个
         *  i 右边 n-i个
         *  因为数奇数子数组，所以，i的左右两边要么都是奇数个，要么都是偶数个
         *  则有 ：
         *  奇数： (i+1 +1) /2 * (n-i +1) /2
         *  偶数： (i+1)/2 * (n-i)/2
         */
        int ans = 0;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            //左边的个数
            int left = i+1;
            //右边的个数
            int right = n-i;
            //偶数情况，左边出现个数
            int left_even = (left+1)/2;
            //偶数情况，右边出现个数
            int right_even = (right+1)/2;
            //奇数情况
            int left_odd =left/2;
            int right_odd = right/2;
            int even = left_even * right_even;
            int odd = left_odd * right_odd;
            //总的出现个数
            int count = odd +even;
            ans += (count*arr[i]);
        }
        return ans;
    }
    public static int sumOddLengthSubarrays2(int[] arr) {
        //java 前缀后计算
        int[] tmp = new int[arr.length];
        tmp[0] = arr[0];
        //先累计计算
        for (int i = 1; i < arr.length; i++) {
            tmp[i] = tmp[i-1]+arr[i];
        }
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j+=2) {
                //跳步计算时直接利用之前计算的结果
                //tmp[j] - tmp[i] +arr[i] = i右边可能出现的所有结果，tmp[i-1]有可能造成数组越界，固直接取arr[i]
                ans+= tmp[j] - tmp[i] + arr[i];
            }
        }
        return ans;
    }
    public static int sumOddLengthSubarrays1(int[] arr) {
        //暴力破解
        if (arr==null|| arr.length==0){
            return 0;
        }
        int ans = 0;
        int len = arr.length;
        int count = 0;
        for (int i = 0; i < len; i++) {
            int num = arr[i];
            ans += num;
            int step = 2;
            int end = i+step;
            while (end<len){
                for (int j = i; j <= end; j++) {
                    ans += arr[j];
                }
                end +=step;
            }
        }
        return ans;
    }
}
