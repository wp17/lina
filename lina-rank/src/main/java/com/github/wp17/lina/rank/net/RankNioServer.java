package com.github.wp17.lina.rank.net;

import com.github.wp17.lina.common.net.NioServer;
import io.netty.channel.ChannelInitializer;

public class RankNioServer extends NioServer {

    public RankNioServer(int port) {
        super(port);
    }

    @Override
    public ChannelInitializer channelInitializer() {
        return new RankChannelInitializer();
    }
}
