/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/7/23 13:35
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密
 *
 * @author: jiang_qian
 * @date: 2019/7/23 13:35
 * @version: V1.0
 */
public class MD5Util {

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    public static String md5(String text) {
        return md5(new String[] { text });
    }

    public static String md5(String[] text) {
        byte[] bytes = digest(text);
        return new String(encodeHex(bytes));
    }

    public static byte[] digest(String... text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        try {
            for (String str : text) {
                msgDigest.update(str.getBytes("utf-8"));
            }

        } catch (UnsupportedEncodingException e) {

            throw new IllegalStateException("System doesn't support your  EncodingException.");

        }

        return msgDigest.digest();
    }


    public static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

}
