package com.study.util.spi;

import java.io.*;

/**
 * @author wy
 * @version 创建时间：2018年11月16日 下午3:09:45
 */
public class TestMain {

    public static void main(String[] args) {
        doHandle("G:\\c.txt","G:\\copy_c.txt","CUSTOMER_NO");
    }

    public static void doHandle(String fileName,String newFileName,String field) {
        File file = new File(fileName);
        InputStreamReader read = null;
        OutputStreamWriter output = null;
        BufferedReader bufferedReader = null;
        try {
            if(!file.exists()) {
                System.err.println("文件不存在。"+fileName);
                return;
            }
            File newFile = new File(newFileName);
            System.out.println("文件开始处理中");
            read = new InputStreamReader(new FileInputStream(file),"utf-8");//考虑到编码格式
            output = new OutputStreamWriter(new FileOutputStream(newFile),"utf-8");//GB2312
//            read = new InputStreamReader(new FileInputStream(file));
//            output = new OutputStreamWriter(new FileOutputStream(newFile));
            bufferedReader = new BufferedReader(read);
            StringBuffer buffer = new StringBuffer();
            String lineTxt = "";
            int i =1;
            while((lineTxt = bufferedReader.readLine()) != null){
                if(lineTxt==null || lineTxt.length()==0 || "".equals(lineTxt)){
                    continue;
                }
                if(isNumeric(lineTxt)){
                    continue;
                }
                if(i++!=1){
                    buffer.append("\r\n");
                }
                buffer.append(lineTxt);

            }
            if(i!=1) {
                output.write(buffer.toString());
            }
            System.out.println("文件开始处理成功");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }finally {
            if(read!=null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(output!=null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufferedReader!=null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}


