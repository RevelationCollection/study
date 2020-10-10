package com.leetcode.problemset;

public class Pro_6 {

    public static void main(String[] args) {

        System.out.println("LCIRETOESIIGEDHN".equals(convert("LEETCODEISHIRING",3)));
        System.out.println("LDREOEIIECIHNTSG".equals(convert("LEETCODEISHIRING",4)));
        System.out.println("ABC".equals(convert("ACB",2)));
    }

    public static String convert(String s, int numRows) {
        if (numRows<2){
            return s;
        }
        StringBuilder temp = new StringBuilder();
        //几列
        int gaps = numRows + numRows-2;
        int len = s.length();
        int columns = len/gaps;
        int surplus = len%gaps;
        //循环列数
        for (int row = 0; row < numRows; row++) {
            //循环行数
            for (int column = 0; column <=columns; column++) {
                int index = column * gaps  + row ;
                saveChar(s, index,temp);
                if (row!=0 && row<numRows-1){
                    //不是第一行和最后一行
                    index = (column+1) * gaps - row;
                    saveChar(s,index,temp);
                }
            }
        }
        s = temp.toString();
        return s;
    }

    private static void saveChar(String str,int index,StringBuilder temp){
        int length = str.length();
        if (index>length-1){
            return;
        }
        temp.append(str.charAt(index));
    }
}
