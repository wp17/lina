package com.github.wp17.lina.common.util;


public class CommonUtil {
    public static byte[] merge(byte[] first, byte[] second) {
        byte[] r = new byte[first.length + second.length];
        System.arraycopy(first, 0, r, 0, first.length);
        System.arraycopy(second, 0, r, first.length, second.length);
        return r;
    }
}
