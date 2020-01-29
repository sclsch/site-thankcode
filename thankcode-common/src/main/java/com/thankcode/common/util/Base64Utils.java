package com.thankcode.common.util;

import com.thankcode.common.exception.Base64Exception;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;


public class Base64Utils {

    /** DES关键词. */
    private static final String DES = "DES";
    /** 加密/解密机制. */
    private static Cipher cipher = null;
    /** 编码格式. */
    private static final String BM = "utf-8";

    /**
     * Cipher对象实际完成加密操作。
     */
    static {
        try {
            cipher = Cipher.getInstance(DES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * DES解密处理.
     * @param data 未加密文字
     * @param key 秘钥
     * @return 加密后文字
     * @throws Exception 加密产生的异常
     */
    public static String decrypt(String data, String key) {
        try {
            String str= new String(decryptByte(data.getBytes(BM), key.getBytes(BM)));
            return str;
        }catch (Exception e){
            throw new Base64Exception(e);
        }

    }

    /**
     * 解密处理
     * @param src
     * @param key
     * @return
     * @throws Exception
     */

    private static byte[] decryptByte(byte[] src, byte[] key) throws Exception {

        SecureRandom random = new SecureRandom();

        DESKeySpec dESKeySpec = new DESKeySpec(key);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dESKeySpec);

        cipher.init(Cipher.DECRYPT_MODE, securekey, random);

        return cipher.doFinal(Base64.decodeBase64(src));
    }

    /**
     * 加密
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String data, String key)  {
        try {
            return new String(encryptByte(data.getBytes(BM), key.getBytes(BM)));
        }catch (Exception e){
            throw new Base64Exception(e);
        }

    }



    private static byte[] encryptByte(byte[] data, byte[] key) throws Exception {

        SecureRandom random = new SecureRandom();
        DESKeySpec dESKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dESKeySpec);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);

        return Base64.encodeBase64(cipher.doFinal(data));
    }

    /**
     * 16进制的key
     * @param param
     * @return
     */
    private static String byte2hex(byte[] param) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < param.length; n++) {
            stmp = (Integer.toHexString(param[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }



    public static String getKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(56);
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        return byte2hex(b);
    }



    public static void main(String[] args) throws Exception {
        String key=getKey();
        System.out.print(key);
        String content="{\"accountRegIdSno\":\"ZZH20190811101\",\"accountUnitName\":\"\"}";
        content = Base64Utils.encrypt(content, "ABADEA4AFBD39198");
        content = Base64Utils.decrypt(content, "1BADEA4AFBD39198");
        System.out.println(content);

    }
}
