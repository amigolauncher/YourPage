/*******************************************************************************
 * Filename:
 * ---------
 * GNDecodeUtils.java
 * <p/>
 * Project:
 * --------
 * Browser
 * <p/>
 * Description:
 * ------------
 * decode mobile IMEI
 * <p/>
 * Author:
 * -------
 * 2012.06.25 caody
 ****************************************************************************/
package com.gionee.amisystem.depencylib.help;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GNEncodeIMEIUtils {
    private static final String AES = "AES";
    private static final String VIPARA = "0102030405060708";
    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    private static final String SEED = "GIONEE2012061900";
    private static final String HEX = "0123456789ABCDEF";
    private static final String CHARSET = "UTF-8";

    public static String encrypt(String seed, String cleartext) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes(CHARSET));
        byte[] result = encrypt(rawKey, cleartext.getBytes(CHARSET));
        return toHex(result);
    }

    public static String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes(CHARSET));
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, CHARSET);
    }

    public static byte[] getRawKey(byte[] seed) throws Exception {
        return seed;
    }

    public static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes(CHARSET));
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
        // System.out.println("size:" + cipher.getBlockSize());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, zeroIv);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    public static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes(CHARSET));
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, zeroIv);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String toHex(String txt) {
        try {
            return toHex(txt.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String fromHex(String hex) {
        try {
            return new String(toByte(hex), CHARSET);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static byte[] toByte(String hexString) {

        int len = hexString.length() / 2;
        byte[] result = new byte[len];

        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;

    }

    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    public static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    public static String get(String str) {
        if (str == null) {
            str = "";
        }

        String masterPassword = SEED;

        try {
            String encryptingCode = GNEncodeIMEIUtils.encrypt(masterPassword, str);
            // String decode = GNDecodeUtils.decrypt(masterPassword, encryptingCode);
            // System.out.println("encryptingCode: " + encryptingCode);
            // System.out.println("decode: " + decode);
            return encryptingCode;
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }

    public static String decrypt(String encrypted) {
        try {
            return decrypt(SEED, encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
