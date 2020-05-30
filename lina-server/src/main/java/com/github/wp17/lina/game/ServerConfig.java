package com.github.wp17.lina.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:server.properties")
public class ServerConfig {
    public static int serverId;
    public static int port;

    @Value("${server.id}")
    public void setServerId(int serverId) {
        ServerConfig.serverId = serverId;
    }

    @Value("${server.port}")
    public void setPort(int port) {
        ServerConfig.port = port;
    }
}
