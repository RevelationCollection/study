package com.leetcode.problemset;

public class Pro_8 {

    public static void main(String[] args) {
//        System.out.println(myAtoi("42"));
//        System.out.println(myAtoi("   -42"));
//        System.out.println(myAtoi("4193 with words"));
//        System.out.println(myAtoi("words and 987"));
//        System.out.println(myAtoi("-91283472332"));
//        System.out.println(myAtoi("+1"));
//        System.out.println(myAtoi("+-1"));
        System.out.println(myAtoi("-2147483649"));
    }

    public static int myAtoi(String s) {
        char[] chars = s.toCharArray();
        int start = 0;
        //过滤空格
        for (int i = 0; i < chars.length; i++) {
            if(chars[i]!=' '){
                start = i;
                break;
            }
        }
        if (start==chars.length){
            return 0;
        }
        //判断字母首位是否有正负号
        int sign = 1;
        char c = chars[start];
        if (c=='-'){
            sign = -1;
            start++;
        }
        else if (c=='+'){
            sign = 1;
            start++;
        }
        int ans = 0;
        int max = Integer.MAX_VALUE/10;
        int min = Integer.MIN_VALUE/10;
        int maxSur = Integer.MAX_VALUE %10;
        int minSur = Integer.MIN_VALUE%10;
        //将后面数字进行填充
        for (int i = start; i < chars.length; i++) {
            //不在数字范围的直接跳出循环，结束计算
            char ch = chars[i];
            if (ch<'0' || ch>'9'){
                break;
            }
            //注意这个不是负数，是正数
            int pop = ch - '0';
            if (ans>max || (ans==max && pop>maxSur)){
                return Integer.MAX_VALUE;
            }
            if (ans<min || (ans==min && pop>-minSur)){
                return Integer.MIN_VALUE;
            }
            ans = ans*10 +pop * sign;
        }
        return ans;
    }

    public static int myAtoi2(String s) {
        // 32 是空
        // 45 是负号 -
        // 48 是 0
        // 57 是 9
        byte[] bytes = s.getBytes();
        //是否字符串开头
        boolean isStart = true;
        //是否正数
        boolean isPlus = true;
        int n = 0;
        int max = Integer.MAX_VALUE/10;
        int min = Integer.MIN_VALUE/10;
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b==0){
                //结束标记
                break;
            }
            if (isStart){
                if (b==32){
                    //开始是空字符串，跳过
                    continue;
                }
                else if (b==45 || b==43){
                }
                else if(!isNumber(b)){
                    //开头且不是数字
                    return 0;
                }
            }
            if (!isNumber(b) && n!=0){
                //当前不是数字且数字已经拼凑
                break;
            }
            if (b==45){
                isPlus=false;
            }
            if (isNumber(b)){
                isStart =false;
                //是数字
                int pop = getInt(b);
                if (n>max || (n==max && pop>7)){
                    return isPlus?Integer.MAX_VALUE:Integer.MIN_VALUE;
                }
                if (n<min || (n==min && pop<-8)){
                    return isPlus?Integer.MAX_VALUE:Integer.MIN_VALUE;
                }
                n = n*10 +pop;
            }
        }
        if (!isPlus && n>0){
            return -n;
        }
        return n;
    }

    private static int getInt(byte b){
        return b-48;
    }

    private static boolean isNumber(byte b){
        return b >=48 && b<=57;
    }
}
