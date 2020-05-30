package com.github.wp17.lina.common.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;

public interface INioServer {
    ChannelInitializer channelInitializer();

    default NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(1);
    }

    default NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(4);
    }

    default ChannelGroup channelGroup() {
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    default Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, true);
        options.put(ChannelOption.SO_BACKLOG, 3000);
        options.put(ChannelOption.TCP_NODELAY, true);
        return options;
    }
}
