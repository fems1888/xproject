package com.qbao.library.utility;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @ClassName: MD5Util
 * @Description: TODO MD5 加密方法
 * @date 2017年9月4日
 * @author guofeiyan
 *
 */
public final class MD5Util {
    private static Charset CHARSET = Charset.forName("UTF8");
    /**
     * 加密方法
     * @param inStr
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encodeMD5(String inStr) throws NoSuchAlgorithmException {
        MessageDigest md5 = null;
        md5 = MessageDigest.getInstance("MD5");
        char[] charArray = inStr.toCharArray();
//        byte[] byteArray = new byte[charArray.length];
//        for (int i = 0; i < charArray.length; i++) {
//            byteArray[i] = (byte) charArray[i];
//        }
        byte[] byteArray = inStr.getBytes(CHARSET);
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String YiMaMD5(String key, String value) throws UnsupportedEncodingException{
        String result = null;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(key.getBytes());
            digest.update(value.getBytes("GBK"));
            digest.update(key.getBytes());
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            result = null;
        }
        return result;
    }
}
