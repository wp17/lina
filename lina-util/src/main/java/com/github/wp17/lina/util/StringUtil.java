package com.github.wp17.lina.util;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 把一个字符串中的\r\n或者单独的\r都替换成\n。
     */
    public static String normalizeCRLF(String source) {
        return source.replaceAll("\r\n", "\n").replace('\r', '\n');
    }

    public static int[] stringToIntArray(String str, char delimiter) {
        if (str == null || str.length() == 0) {
            return new int[0];
        }
        String[] tmp = splitString(str, delimiter);
        int[] ret = new int[tmp.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = NumberUtil.parseInt(tmp[i]);
        }
        return ret;
    }

    /**
     * 把一个字符串分成用指定字符隔开的多个字符串.
     *
     * @param s  原始字符串
     * @param ch 分隔字符
     * @return 分割后的数组
     */
    public static String[] splitString(String s, char ch) {
        int startIndex = 0;
        int endIndex = 0;
        List<String> vs = new ArrayList<String>();
        while (true) {
            endIndex = s.indexOf(ch, startIndex);
            if (endIndex == -1) {
                vs.add(s.substring(startIndex));
                break;
            } else {
                vs.add(s.substring(startIndex, endIndex));
                startIndex = endIndex + 1;
            }
        }
        String[] result = new String[vs.size()];
        vs.toArray(result);
        return result;
    }

    /**
     * 把格式为n,n,n的字符串解析为float数组。
     *
     * @param str
     * @param delimiter
     */
    public static float[] stringToFloatArray(String str, char delimiter) {
        if (str.length() == 0) {
            return new float[0];
        }
        String[] tmp = splitString(str, delimiter);
        float[] ret = new float[tmp.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Float.parseFloat(tmp[i]);
        }
        return ret;
    }
}
