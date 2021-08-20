package com.example.springbootdemo.util;

import com.alibaba.fastjson.JSONException;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成密钥对(公钥和私钥)
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 拿到生成的密钥对里的私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 拿到生成的密钥对里的公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 公钥加密
     * 注意：使用同一个公钥对待处理的数据进行多次加密，每次得到加密后的结果都是不一样的
     *   这是因为使用RSA私钥进行签名还是公钥进行加密，都需要对待处理的数据先进行填充，再对填充后的数据进行加密处理。 公钥加密时，使用伪随机的16进制字符串进行填充，
     *              每次填充的伪随机数都是不一样的，所以每次加密得到的结果就不一样了
     *      https://blog.csdn.net/guyongqiangx/article/details/74930951
     */
    public static String encryptByPublicKey(String content, String publicKey, String charset) throws Exception {
        byte[] data = content.getBytes(charset);
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeBase64String(encryptedData);
    }

    /**
     * 私钥解密
     */
    public static String decryptByPrivateKey(String content, String privateKey, String charset) throws Exception {
        byte[] encryptedData = Base64.decodeBase64(content);
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, charset);
    }

    /**
     * 私钥加密
     *
     * @param content
     *            加密数据
     * @param privateKey
     *            私钥
     * 使用同一私钥进行多次加密，得到的结果都是相同的
     *   私钥加密时，填充的内容都是FF， 每次填充的内容都是一样的，加密得到的结果也是一样的
     */
    public static String encryptByPrivateKey(String content, String privateKey, String charset) throws Exception {
        byte[] data = content.getBytes(charset);
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeBase64String(encryptedData);

    }

    /**
     * 公钥解密
     *
     * @param content
     *            加密数据
     * @param publicKey
     *            公钥
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String content, String publicKey, String charset) throws Exception {
        byte[] data = Base64.decodeBase64(content);
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, charset);
    }

    /**
     * 签名
     */
    public static String sign(String content, String privateKey, String charset) throws Exception {
        byte[] data = content.getBytes(charset);
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 验签
     */
    public static boolean verify(String content, String publicKey, String sign, String charset) throws Exception {

        byte[] data = content.getBytes(charset);
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 加解密 加签验签
     * @param args
     */
    public static void main(String[] args) {
        try {
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDrKWklonbo4ZFgwLgE63wt+sHiB4Uq0Tl6V6g2Aoj5IPRd4TL8mJlMkaJu/c3G212jhjbUjYkWn5HOnrUwrVN1q+mJGGUCED6ruTNMiAc1Oe5gbLwGIeyk4IMuZFUVwq7XdTgVfFo9cVRAM8jIWftJrCm63ZrqqU/AdBoU4FSTQIDAQAB";
            //String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJH57hBhScAEZYvj8IF6WOR3QmVpS34iirdyksSzJ4OFjg/0375MhoujosLnNz0KCKJTA80p489mvu7k6pd4msI3mxQo8crnVG5VBUfg20LloV/qoDqjwUxre6yoSHlZCEYTXl0uNAv9CuuWTc+ZdC9PTlJLCSXiAyNHcTlGDb9nAgMBAAECgYApMOpRavKBoXv6DnbmQlzlExYEQcMQs7SK8WSi5p7myP/+Vq3wJ+3rZ2BdORtoOPWYwG4ZLZHgC3EjPitIVLruV88lr3MXa9xTj04xRHw1agxYn4npPmoyTycYF1rVJpVPIHUAC+CBsRXbslIo+cVssEC/TtoEKHw/qeBRtwirQQJBAMjNIKw/qGy0F06YmAqKUrHT8sQH5sWQRYfEX7GLA7AuUDZZ9GbulXnpW79bdiPbCmfQ72TW8Qc6BuH8Yfj8V8cCQQC6GqKJ0nlROaVsbHwxcEQ9d93rBVhHhHL4rsUaBvIMNeyJTm9iHMGk7aO3P34ohz0bPjZtTNAOZpn+KNyuXZthAkB0CZYH9AFwT+HoGWZDMQv4l3KCtc41/NkxS9nAz4SiGyFfRzxk/xqCCMhsHH542et5ctPkghFnC7FOhnpXV/uhAkEAligTaPxnYkAO1vKZKhKZHLdwWMZROjMFyNzKr3tXJL8FgKvGdvUY0QfkhmA2XGDxzt9dbscBP/1M1qMFm7JvQQJAFtHgiDkhFjCk2Q4wHac7hG35CMkMlXMVI+l5zhJQVyXBHjshkzDjiFD88oVIFQflrZRQ1Awd83khZmEeikMF9w==";
            //System.out.println(String.format("rsa privateKey:%s\nrsa publicKey:%s", privateKey, publicKey));

            // 明文
            String password = "1234abc#";
            //publicKey加密
            String encryptData = encryptByPublicKey(password, publicKey, "UTF-8");
            System.out.println("密码加密结果为：" + encryptData);


            //还可以参考：https://blog.csdn.net/securitit/article/details/108206180
        } catch (IllegalArgumentException | JSONException | InvalidKeySpecException e) {
            //e.printStackTrace();
            System.out.println("解密失败啦！！");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常");
        }
    }

}