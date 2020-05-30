package com.github.wp17.lina.zone.net;

import com.github.wp17.lina.common.net.NioServer;
import io.netty.channel.ChannelInitializer;

public class ZoneNioServer extends NioServer {
    public ZoneNioServer(int port) {
        super(port);
    }

    @Override
    public ChannelInitializer channelInitializer() {
        return new ZoneChannelInitializer();
    }
}
