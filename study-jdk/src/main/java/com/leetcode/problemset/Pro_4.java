package com.leetcode.problemset;

/**
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
 *
 * 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Pro_4 {

    public static void main(String[] args) {
//        System.out.println(findMedianSortedArrays3(new int[]{1,3},new int[]{2})==2);
//        System.out.println(findMedianSortedArrays3(new int[]{1,2},new int[]{3,4})==2.5);
//        System.out.println(findMedianSortedArrays3(new int[]{0,0},new int[]{0,0})==0);
//        System.out.println(findMedianSortedArrays3(new int[]{},new int[]{1})==1);
        System.out.println(findMedianSortedArrays3(new int[]{2},new int[]{})==2);
//        System.out.println(findMedianSortedArrays3(new int[]{1,3,4,9},new int[]{1,2,3,4,5,6,7,8,9,10}));
    }

    /**
     * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-2/
     * 有序数组m、n
     * 1.中位数下标是可知的，k = (m+n)/2
     * 2.根据解法2，一次遍历去除一个不是中位数的值，由此可以改造成一半一半去除
     * 假设，有数组
     * m = 1,3,4,9
     * n = 1,2,3,4,5,6,7,8,9,10
     * 1.k=(4+10)/2 = 7
     * 2. k/2 = 3
     * 3.取出 m[3]=4 和 n[3]=3比较，小的那一部分肯定不可能是k
     * 4.然后n-n[3] 再和 m比较 。 7(上一个k)-3(上一个k/2) = 4 得出现在的k。再k/2 = 2比较
     * m = 1,3,4,9
     * n = 4,5,6,7,8,9,10
     * 5.取出m[2]=3和n[2]=5比较，得出m = m-m[2]
     * 6.本次计算 k = 7-3-2=2 ,k/2 =1
     * m = 4,9
     * n = 4,5,6,7,8,9,10
     * 7.取出m[1]=4 和n[1]=4比较，现在值一样，剔除谁都一样，n-n[1]
     * m = 4,9
     * n = 5,6,7,8,9,10
     * 8.再次计算k = 7-3-2-1 =1 ，得出本次数组最左边最小的就是k
     * 9.m[1]=4 和n[1]=5比较，得出4比较小，所以最后第7小的数是4
     *
     *
     * 特殊情况，k/2大于数组长度
     * m = 1,2
     * n = 1,2,3,4,5,6,7,8,9,10
     * 1. k = (2+10)/2 =6 ,k/2=3
     * 2。则用m[2]=2 和n[3]=3比较， 则数组m全部舍去，剩余数组n
     * 3。k = 6-2 =4 ,得出n[4]=4为 4为第6小的数
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        //奇数
        int left = (m+n+1)/2;
        //偶数
        int right = (m+n+2)/2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getIndexMinNum(nums1, 0, n - 1, nums2, 0, m - 1, left)
                    + getIndexMinNum(nums1, 0, n - 1, nums2, 0, m - 1, right))
                * 0.5;

    }

    /** 找到第k小的数 */
    private static int getIndexMinNum(int[] nums1,int start1,int end1,int[] nums2,int start2,int end2,int k){
        int len1 = end1-start1+1;
        int len2 = end2-start2+1;
        if (len1>len2) {
            //保证len1一定是最短的
            return getIndexMinNum(nums2,start2,end2,nums1,start1,end1,k);
        }
        if (len1==0) {
            //特殊情况，有一个数组为空，直接取k  -1 下标比k少1
            int len = start2+k-1 ;
            len = len>end2?end2:len;
            return nums2[len];
        }
        if (k==1) {
            return Math.min(nums1[start1],nums2[start2]);
        }
        //坐标减1，防止k/2超出数组长度.计算本轮需要比较的长度
        int i = start1 + Math.min(len1,k/2)-1;
        int j = start2 + Math.min(len2,k/2)-1;
        if (nums1[i]>nums2[j]){
            // k= k-(j-start2+1) 刚刚加上的坐标得扣除掉/
            return getIndexMinNum(nums1,start1,end1,nums2,j+1,end2,k-Math.min(len2,k/2));
        }else {
            return getIndexMinNum(nums1, i + 1, end1, nums2, start2, end2, k - Math.min(len1,k/2));
        }
    }


    /**
     * 不合并数组，控制数组指针，找到中位数
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;
        int left = -1, right = -1;
        int aStart = 0, bStart = 0;
        for (int i = 0; i <= len / 2; i++) {
            left = right;
            //第一个数组没有遍历结束且第一个数组的值小雨第二个数组的时候，取第一个数组的值
            if (aStart < m && (bStart >= n || nums1[aStart] < nums2[bStart])) {
                right = nums1[aStart++];
            } else {
                right = nums2[bStart++];
            }
        }
        if ((len & 1) == 0)
            return (left + right) / 2.0;
        else
            return right;
    }

    /**
     * 思路一：合并两个数组，奇数长度取中间，偶数长度取中间值和中间值+1的平均值
     * @param nums1 有序数组1
     * @param nums2 有序数组2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        //是否是奇数
        boolean isOdd = (m+n)%2!=0;
        //中间索引
        int halfIndex = (m+n)/2;
        //过滤掉数组为空的情况
        if (m==0){
            //为偶数
            if (isOdd){
                return nums2[halfIndex];
            }
            if (n==1){
                return nums2[0];
            }
            return (double)(nums2[halfIndex]+nums2[halfIndex-1])/2.0;
        }
        if (n==0){
            if (isOdd){
                return  nums1[halfIndex];
            }
            if (m==1){
                return nums1[0];
            }
            return (double)(nums1[halfIndex]+nums2[halfIndex-1])/2.0;
        }
        int[] newArr = new int[m+n];
        int x =0,y=0,i=0;
        int sum = m+n;
        //是否合并完成
        while (i!=sum){
            //数组1已经全部读取完成
            if (x==m){
                while (y!=n){
                    newArr[i++] = nums2[y++];
                }
                break;
            }
            else if (y==n){
                while (x!=m){
                    newArr[i++] = nums1[x++];
                }
                break;
            }
            if (nums1[x]>nums2[y]){
                newArr[i++] = nums2[y++];
            }else {
                newArr[i++] = nums1[x++];
            }
        }
        if (isOdd){
            return newArr[halfIndex];
        }
        return (double)(newArr[halfIndex]+newArr[halfIndex-1])/2.0;
    }
}
