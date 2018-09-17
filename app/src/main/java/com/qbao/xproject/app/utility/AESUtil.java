package com.qbao.xproject.app.utility;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Created by jackieyao on 2018/9/14 上午10:05.
 */

public class AESUtil {
    public static final String KEY = "16m@yn@NMX7l7V^P";
    static {
        System.loadLibrary("android_openssl");
    }

    public static String encryptSign(String src) {
        try {
            return CommonUtility.isNull(src) ? "" : Base64.encodeBytes(AESUtil.encryptToBytes(src,KEY));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String ENCRYPTION_IV = "&9c(eOYB$01N@~aU";

    private static Charset CHARSET = Charset.forName("UTF8");

    private static String AES_CIPHER = "AES/CBC/PKCS5Padding";

    public static String encrypt(String src, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, makeKey(key), makeIv());
            return new String(Base64.encodeBytes((cipher.doFinal(src.getBytes(CHARSET)))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptToBytes(String src, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, makeKey(key), makeIv());
            return cipher.doFinal(src.getBytes(CHARSET));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String src, String key) {
        String decrypted = "";
        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, makeKey(key), makeIv());
            decrypted = new String(cipher.doFinal(Base64.decode(src)), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return decrypted;
    }

    public static String decryptBytes(byte[] src, String key) {
        String decrypted = "";
        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, makeKey(key), makeIv());
            decrypted = new String(cipher.doFinal(src));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return decrypted;
    }

    private static AlgorithmParameterSpec makeIv() {
        return new IvParameterSpec(ENCRYPTION_IV.getBytes(CHARSET));
    }

    private static Key makeKey(String keyString) {
        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] key = md.digest(keyString.getBytes("UTF-8"));
            return new SecretKeySpec(keyString.getBytes(CHARSET), "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param plainText Base64编码明文
     * @return Base64编码密文
     * @desc AES-CFB Base64编码解密
     */
    public static native String AesCfbEncryptBase64(String plainText);

    /**
     * @param plainText Base64编码明文
     * @return Base64编码密文
     * @desc AES-CBC Base64编码加密
     */
    public static native String AesCbcEncryptBase64Default(String plainText);

    /**
     * @param arg1
     * @param arg2
     * @param arg3
     * @param arg4
     * @return 修正后的字符串
     * @desc 字符串格式化
     */

    public static native String formatString(String arg1, String arg2, String arg3, String arg4);
}
