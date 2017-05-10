package com.gionee.amisystem.depencylib.http;


import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by lee on 16-5-16.
 */
public class Md5Utils {

    private static final String TAG = "Md5Utils";

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String md5(String s) {
        try {
            byte[] btInput = s.getBytes(); //NOSONAR
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();

            return byte2Hex(md);

        } catch (Exception e) {
            Log.e(TAG, "md5 is error", e);
            return null;
        }
    }


    private static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte by : bytes) {
            sb.append(HEX_DIGITS[by >>> 4 & 0xf]);
            sb.append(HEX_DIGITS[by & 0xf]);
        }
        return sb.toString();
    }
}
