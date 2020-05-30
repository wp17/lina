package com.github.wp17.lina.rank;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.rank.spring.RankSpringContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class RankApplication {
    private static RankServer server;

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) ->{
            LoggerProvider.addExceptionLog(e);
        });
        RankSpringContext.getInstance().init();
        int serverId = RankSpringContext.getInstance().getIntProperty("server.id");
        int port = RankSpringContext.getInstance().getIntProperty("server.port");
        server = new RankServer(serverId, port);
        server.startup();

        Runtime.getRuntime().addShutdownHook(new Thread(RankApplication::shutdown));
    }

    private static void shutdown() {
        log.info("RankApp run shutdownHook");
        if (Objects.nonNull(server)) server.shutdown();
        RankSpringContext.getInstance().shutdown();
        log.info("rank server(id={}) closed", server.getId());
    }
}
