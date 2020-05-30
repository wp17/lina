package com.github.wp17.lina.game;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.game.spring.SpringContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameApplication {
    @Getter
    private static volatile boolean started;

    @Getter
    private static GameServer server;

    public static void main(String[] args) {
//        LogModule.getInstance().init();
        SpringContext.getInstance().init();
        server = new GameServer(ServerConfig.serverId, ServerConfig.port);
        server.startup();
        Runtime.getRuntime().addShutdownHook(new Thread(GameApplication::shutdown));
        setDefaultExceptionHandler();
        started = true;
        log.info("server started");
    }

    private static void shutdown() {
        server.shutdown();
        SpringContext.getInstance().shutdown();
        log.info("server shutdown");
    }

    private static void setDefaultExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> LoggerProvider.addExceptionLog(
                String.format("未捕获异常, threadId: %d, threadName: %s", t.getId(), t.getName()), e));
    }
}
