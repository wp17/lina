package com.github.wp17.lina.util;

import static java.lang.String.valueOf;

public class StringUtil {

    public static boolean isEmpty(String arg) {
        if (null == arg) {
            return true;
        }

        return arg.trim().equals("");
    }

    public static boolean isNotEmpty(String arg) {
        return !isEmpty(arg);
    }

    /**
     * 简单地电话号码加密
     */
    public static String encryptTelNum(Long telNum) {
        String original = "" + telNum;
        StringBuilder encrypt = new StringBuilder();
        char[] cs = original.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            encrypt.append(i + cs[i]);
        }
        return encrypt.toString();
    }

    /**
     * 简单地解密电话号码
     */
    public static long decryptTelNum(String encrypted) {
        char[] original = encrypted.toCharArray();
        StringBuilder telNum = new StringBuilder();
        for (int i = 0; i < original.length; i += 2) {
            int charCode = Integer.parseInt(valueOf(original[i]) + original[i + 1]);
            telNum.append((char) (charCode - i / 2));
        }

        return Long.parseLong(telNum.toString());
    }
}
