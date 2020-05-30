package com.github.wp17.lina.util;

import java.lang.management.ManagementFactory;

/**
 * 获取一些虚拟机信息的工具类
 */
public class RTUtil {
    /**获取当前进程id*/
    public static final long getPID() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return Long.valueOf(pid);
    }

    /**获取当前登录操作系统的用户名*/
    public static final String getSysName() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return name.split("@")[1];
    }
}
