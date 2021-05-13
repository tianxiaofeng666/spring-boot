package com.example.springbootutils.utils;

/**
 * @author gaoh28
 * @version 1.0
 * @date 2020/6/3 14:26
 */
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;

@Slf4j
@Component
public class AesUtils {
    /**
     * AES加解密
     */
    private static final String ALGORITHM = "AES";
    /**
     * 默认的初始化向量值
     */
    private static final String IV_DEFAULT = "wjIZ5M4JcAL4Tz8F";
    /**
     * 默认加密的KEY
     */
    private static final String KEY_DEFAULT = "nHwsk9MvRJhc5wRN";
    /**
     * 工作模式：CBC
     */
    private static final String TRANSFORM_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
    /**
     * 字符串长度
     */
    private static final int KEY_LENGTH = 16;
    /**
     * 工作模式：ECB
     */
    private static final String TRANSFORM_ECB_PKCS5 = "AES/ECB/PKCS5Padding";


    /**
     * 基于CBC工作模式的AES加密
     * @param value 待加密字符串
     * @param key 秘钥，如果不填则使用默认值
     * @param iv 初始化向量值，如果不填则使用默认值
     * @return java.lang.String
     */
    public static String encryptCbcMode(final String value, String key, String iv){
        if(!"".equals(value) && value != null){
            //如果key或iv为空，则使用默认值
            if(key == null || key.length() != KEY_LENGTH){
                key = KEY_DEFAULT;
            }
            if(iv == null || iv.length() != KEY_LENGTH){
                iv = IV_DEFAULT;
            }

            //密码
            final SecretKeySpec keySpec = new SecretKeySpec(getUtf8Bytes(key),"AES");

            //初始化向量器
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(getUtf8Bytes(iv));
            try {
                Cipher encipher = Cipher.getInstance(TRANSFORM_CBC_PKCS5);

                //加密模式
                encipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
                //使用AES加密
                byte[] encrypted = encipher.doFinal(getUtf8Bytes(value));
                //然后转成BASE64返回
                return Base64.encodeBase64String(encrypted);
            } catch (Exception e) {
                System.out.println(MessageFormat.format("基于CBC工作模式的AES加密失败,VALUE:{0},KEY:{1}",value,key));
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 基于CBC工作模式的AES解密
     * @param encryptedStr AES加密之后的字符串
     * @param key 秘钥，如果不填则使用默认值
     * @param iv 初始化向量值，如果不填则使用默认值
     * @return java.lang.String
     */
    public static String decryptCbcMode(final String encryptedStr, String key, String iv){
        if(!"".equals(encryptedStr) && encryptedStr != null){
            //如果key或iv为空，则使用默认值
            if(key == null || key.length() != KEY_LENGTH){
                key = KEY_DEFAULT;
            }
            if(iv == null || iv.length() != KEY_LENGTH){
                iv = IV_DEFAULT;
            }

            //密码
            final SecretKeySpec keySpec = new SecretKeySpec(getUtf8Bytes(key),"AES");
//            初始化向量器
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(getUtf8Bytes(iv));
            try {
                Cipher encipher = Cipher.getInstance(TRANSFORM_CBC_PKCS5);

                //加密模式
                encipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
                //先用BASE64解密
                byte[] encryptedBytes = Base64.decodeBase64(encryptedStr);
                //然后再AES解密
                byte[] originalBytes = encipher.doFinal(encryptedBytes);
                //返回字符串
                return new String(originalBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                System.out.println(MessageFormat.format("基于CBC工作模式的AES解密失败,encryptedStr:{0},KEY:{1}",encryptedStr,key));
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 基于ECB工作模式的AES加密
     * @param value 待加密字符串
     * @param key 秘钥，如果不填则使用默认值
     * @return java.lang.String
     */
    public static String encryptEcbMode(String value, String key){
        if(!"".equals(value) && value != null){
            //如果key为空，则使用默认值
            if(key == null || key.length() != KEY_LENGTH){
                key = KEY_DEFAULT;
            }

            //密码
            final SecretKeySpec keySpec = getSecretKey(key);

            try {
                Cipher encipher = Cipher.getInstance(TRANSFORM_ECB_PKCS5);

                //加密模式
                encipher.init(Cipher.ENCRYPT_MODE, keySpec);
                //使用AES加密
                byte[] encrypted = encipher.doFinal(getUtf8Bytes(value));
                //然后转成BASE64返回
                return Base64.encodeBase64String(encrypted);
            } catch (Exception e) {

            }
        }

        return null;
    }

    /**
     * 基于ECB工作模式的AES解密
     * @param encryptedStr AES加密之后的字符串
     * @param key 秘钥，如果不填则使用默认值
     * @return java.lang.String
     */
    public static String decryptEcbMode(String encryptedStr, String key){
        if(!"".equals(encryptedStr) && encryptedStr != null){
            //如果key为空，则使用默认值
            if(key == null || key.length() != KEY_LENGTH){
                key = KEY_DEFAULT;
            }

            //密码
            final SecretKeySpec keySpec = getSecretKey(key);

            try {
                Cipher encipher = Cipher.getInstance(TRANSFORM_ECB_PKCS5);

                //加密模式
                encipher.init(Cipher.DECRYPT_MODE, keySpec);
                //先用BASE64解密
                byte[] encryptedBytes = Base64.decodeBase64(encryptedStr);
                //然后再AES解密
                byte[] originalBytes = encipher.doFinal(encryptedBytes);
                //返回字符串
                return new String(originalBytes);
            } catch (Exception e) {

            }
        }

        return null;
    }

    /**
     * 将字符串转化为UTF8格式的byte数组
     *
     * @param input the input string
     * @return UTF8 bytes
     */
    public static byte[] getUtf8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 生成加密秘钥
     * @param key 明文秘钥
     * @return SecretKeySpec
     */
    private static SecretKeySpec getSecretKey(final String key) {
        //生成指定算法密钥
        KeyGenerator generator = null;

        try {
            generator = KeyGenerator.getInstance(ALGORITHM);

            //指定AES密钥长度（128、192、256）
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(getUtf8Bytes(key));
            generator.init(128, secureRandom);

            //生成一个密钥
            SecretKey secretKey = generator.generateKey();
            //转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            log.info("the error info --->"+ex.getMessage());
        }
        return null;
    }
}

