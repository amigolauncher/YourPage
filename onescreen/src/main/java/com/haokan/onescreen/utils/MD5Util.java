package com.haokan.onescreen.utils;

import java.security.MessageDigest;



/**
 * 字符串MD5签名 Copyright: Copyright (c) 2014 <br>
 * Company:尤尼科技<br>
 * Date:2014-09-01<br>
 *
 * @author LiXiaoZhen<br>
 * @version Revision: 1.0
 */
public class MD5Util {
  private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
          "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

  /**
   * 把字节数组转换为十六进制的字符串
   *
   * @param b
   *            字节数组
   * @return 十六进制的字符串
   */
  private static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }

  /**
   * 把一个字节转换为一个十六进制的字符串
   *
   * @param b
   *            字节
   * @return 十六进制的字符串
   */
  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0) {
      n = 256 + n;
    }
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  /**
   * 把字符串以MD5的方式加密
   *
   * @param origin
   *            需要加密的字符串
   * @return 加密后的字符串
   */
  public static String MD5Encode(String origin) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      resultString = byteArrayToHexString(md.digest(resultString
              .getBytes()));
    } catch (Exception ex) {
    }
    return resultString;
  }

  public static String MD5ShiroEncode(String origin, String salt, int hashIterations) {
    String resultString = null;
    try {
      byte[] bytes = origin.getBytes("UTF-8");
      byte[] salts = salt.getBytes("UTF-8");

      MessageDigest digest = MessageDigest.getInstance("MD5");
      if (salt != null) {
        digest.reset();
        digest.update(salts);
      }
      byte[] hashed = digest.digest(bytes);
      int iterations = hashIterations - 1; // already hashed once above
      // iterate remaining number:
      for (int i = 0; i < iterations; i++) {
        digest.reset();
        hashed = digest.digest(hashed);
      }
      resultString = byteArrayToHexString(hashed);
    } catch (Exception e) {

    }
    return resultString;
  }

  /**
   * 把字符串以16位MD5的方式加密
   *
   * @param origin
   *            需要加密的字符串
   * @return 加密后的字符串
   */
  public static String MD5Encode16Bit(String origin) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      resultString = byteArrayToHexString(
              md.digest(resultString.getBytes())).substring(8, 24);
    } catch (Exception ex) {
    }
    return resultString;
  }

  public static String byte2hex(byte[] b) {
    StringBuilder hs = new StringBuilder("");
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1) {
        hs.append("0").append(stmp);
      } else {
        hs.append(stmp);
      }

    }
    return hs.toString().toUpperCase();
  }

  public static byte[] getCipherStr(String input) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(input.getBytes("UTF-8"));
      return byte2hex(md5.digest()).getBytes();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return input.getBytes();

  }

}
