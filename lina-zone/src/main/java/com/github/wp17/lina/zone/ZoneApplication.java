package com.github.wp17.lina.zone;

import com.github.wp17.lina.common.log.LoggerProvider;

public class ZoneApplication {
    private static final ZoneServer server = new ZoneServer(100);
    public static void main(String[] args) {
        server.startup();
        Runtime.getRuntime().addShutdownHook(new Thread(ZoneApplication::shutdown));
        setDefaultExceptionHandler();
    }

    private static void shutdown() {
        server.shutdown();
    }

    private static void setDefaultExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> LoggerProvider.addExceptionLog(
                String.format("未捕获异常, threadId: %d, threadName: %s", t.getId(), t.getName()), e));
    }
}
