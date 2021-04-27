package com.study.spring.oauth.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaCoder {
    /**
     * 数字签名，密钥算法
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 数字签名 签名/验证算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 数据集
     */
    public static final String CHAR_SET = "UTF-8";

    /**
     * RSA密钥长度，RSA算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    // private static final int KEY_SIZE = 512;

    /**
     * RSA最大加密 明文大小
     */
    // private static final int MAX_ENCRYPT_BLOCK = KEY_SIZE / 8 - 11;

    /**
     * RSA最大解密 密文大小
     */
    // private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;
    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    // private static final Base64.Decoder decoder = Base64.getDecoder();
    //
    // private static final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * 初始化密钥对
     *
     * @return Map 甲方密钥的Map
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return String 私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return HexBin.encode(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return String 公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return HexBin.encode(key.getEncoded());
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return RSAPrivateKey 私钥
     */
    public static RSAPrivateKey getRSAPrivateKey(Map<String, Object> keyMap) throws Exception {
        RSAPrivateKey key = (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
        return key;
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return RSAPublicKey 公钥
     */
    public static RSAPublicKey getRSAPublicKey(Map<String, Object> keyMap) throws Exception {
        RSAPublicKey key = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
        return key;
    }

    /**
     * 使用私钥签名
     *
     * @param data       待签名字符串
     * @param privateKey 签名使用的私钥
     * @return
     * @throws Exception
     */
    public static String sign(String data, String privateKey) throws Exception {
        return HexBin.encode(sign(data.getBytes(CHAR_SET), HexBin.decode(privateKey)));
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 密钥
     * @return byte[] 数字签名
     */
    public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 验证签名
     *
     * @param data      待验证数据
     * @param publicKey 验证签名使用的公钥
     * @param sign      签名字符串
     * @return
     */
    public static boolean verify(String data, String publicKey, String sign) throws Exception {
        return verify(data.getBytes(CHAR_SET), HexBin.decode(publicKey), HexBin.decode(sign));
    }

    /**
     * 校验数字签名
     *
     * @param data      待校验数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return boolean 校验成功返回true，失败返回false
     */
    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * <p>
     * 私钥解密
     * </p>
     *
     * @param encryptedData
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {
        byte[] bytes = decryptByPrivateKey(HexBin.decode(encryptedData), HexBin.decode(privateKey));
        return new String(bytes, CHAR_SET);
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKey privateK = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        byte[] decryptedData = rsaCipherDoFinal(cipher, encryptedData, privateK.getModulus().bitLength() / 8);
        return decryptedData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String encryptedData, String publicKey) throws Exception {
        return new String(decryptByPublicKey(HexBin.decode(encryptedData), HexBin.decode(publicKey)), CHAR_SET);
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, byte[] publicKey) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKey publicK = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        byte[] decryptedData = rsaCipherDoFinal(cipher, encryptedData, publicK.getModulus().bitLength() / 8);
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String publicKey) throws Exception {
        return HexBin.encode(encryptByPublicKey(data.getBytes(CHAR_SET), HexBin.decode(publicKey)));
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKey publicK = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        byte[] encryptedData = rsaCipherDoFinal(cipher, data, publicK.getModulus().bitLength() / 8 - 11);
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String privateKey) throws Exception {

        return HexBin.encode(encryptByPrivateKey(data.getBytes(CHAR_SET), HexBin.decode(privateKey)));
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKey privateK = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        byte[] encryptedData = rsaCipherDoFinal(cipher, data, privateK.getModulus().bitLength() / 8 - 11);
        return encryptedData;
    }

    /**
     * The RSA algorithm can only encrypt data that has a maximum byte length of the
     * RSA key length in bits divided with eight minus eleven padding bytes, i.e. number
     * of maximum bytes = key length in bits / 8 - 11.
     *
     * @param cipher    密码核心
     * @param data      待处理数据
     * @param blockSize 处理数据的块大小限制
     * @return
     */
    private static byte[] rsaCipherDoFinal(Cipher cipher, byte[] data, int blockSize) {
        byte[] encryptedData = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > blockSize) {
                    cache = cipher.doFinal(data, offSet, blockSize);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                offSet = ++i * blockSize;
            }
            encryptedData = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> initKey = RsaCoder.initKey();
        String publicKey = RsaCoder.getPublicKey(initKey);
        System.out.println("公钥：" + publicKey);
        String privateKey = RsaCoder.getPrivateKey(initKey);
        System.out.println("私钥：" + privateKey);
        publicKey = "305C300D06092A864886F70D0101010500034B003048024100A614D23641AE5BADC42E9DA1D41C8B4FC1B908A178B6308AE615AD9FBE915CD20B87F9FBA0F02F7FF168414D41B5FD1356669E4F8EADC6AE30C9A23005D6F1790203010001";
        privateKey = "30820156020100300D06092A864886F70D0101010500048201403082013C020100024100A614D23641AE5BADC42E9DA1D41C8B4FC1B908A178B6308AE615AD9FBE915CD20B87F9FBA0F02F7FF168414D41B5FD1356669E4F8EADC6AE30C9A23005D6F1790203010001024100A0D0BA780503730C80516F1B7B0B24AE56F3F7727B33572FB7F937C4DDBF5DB309695E43DA54CC9E52F39EB8091E09419AE44AC379AF223C5F62A69FAEAADBB1022100DCDD3F664E7658661D6BBCA7910F02A73DCFE2102F3A620B8A10F7D5D5C43975022100C080867F84FD025CB1ED3D01CC1BA9BEE747C90C76F89C8DD4294F41C5B113750220096B5A27D7D2CE44549EE2A5D6D6E80B0FE4CCC459EF487D2B7F3BC29FE2457D022100B63DF716EA740CB46CCDC81FD777A8EFCE4D2B8CDD64E7F16D95DE2ECA55A5B1022100C088AF868F9953D95C70BF7C525674B7F6FA44581342FE42CAFFC58BC8AE1C79";
        String content = "test";
        // RSA公钥加密
        String newContent = RsaCoder.encryptByPublicKey(content, publicKey);
        System.out.println(newContent);
        // RSA私钥解密
        String new2Content = RsaCoder.decryptByPrivateKey(newContent, privateKey);
        System.out.println(new2Content);

        String content2 = "test";
        // RSA私钥加密
        String newContent2 = RsaCoder.encryptByPrivateKey(content2, privateKey);
        System.out.println(newContent2);
        // RSA公钥解密
        String new2Content2 = RsaCoder.decryptByPublicKey(newContent2, publicKey);
        System.out.println(new2Content2);
    }
}