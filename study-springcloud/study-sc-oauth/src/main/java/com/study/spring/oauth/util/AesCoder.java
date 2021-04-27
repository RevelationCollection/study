package com.study.spring.oauth.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesCoder {

    public static final String AES = "AES";

    public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static final String CHAR_SET = "UTF-8";

    public static final Integer AES_SIZE = 128;

    /**
     * AES加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String aesKey,String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(HexBin.decode(aesKey), AES);
        // 创建密码器
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bs = cipher.doFinal(data.getBytes(CHAR_SET));
        return HexBin.encode(bs);
    }

    /**
     * AES解密
     * @return
     * @throws Exception
     */
    public static String decryptData(String aesKey,String data) throws Exception{
        SecretKeySpec key = new SecretKeySpec(HexBin.decode(aesKey), AES);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = HexBin.decode(data);
        byte[] bs = cipher.doFinal(decode);
        return new String(bs,CHAR_SET);
    }

    /**
     * 生成AES KEY
     * @return
     * @throws Exception
     */
    public static String  genKeyAES() throws Exception {
        KeyGenerator kenGen = KeyGenerator.getInstance(AES);
        kenGen.init(AES_SIZE);
        SecretKey key = kenGen.generateKey();
        return HexBin.encode(key.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        String aesKey = genKeyAES();
        System.out.println(aesKey);
        String content = "123456789";
        String encode = encryptData(aesKey, content);
        System.out.println(decryptData(aesKey, encode));
    }
}