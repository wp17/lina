package com.github.wp17.lina.game.netty;

import com.github.wp17.lina.common.net.NioServer;
import io.netty.channel.ChannelInitializer;

public class GameNioServer extends NioServer {
    public GameNioServer(int port) {
        super(port);
    }

    @Override
    public ChannelInitializer channelInitializer() {
        return new GameChannelInitializer();
    }
}
