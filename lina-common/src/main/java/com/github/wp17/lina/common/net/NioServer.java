package com.github.wp17.lina.common.net;

import com.github.wp17.lina.common.log.LoggerProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;

public abstract class NioServer implements INioServer {
    private ServerBootstrap bootstrap;
    private ChannelGroup channelGroup;
    private InetSocketAddress socketAddress;
    private int port;

    protected NioServer(int port) {
        this.port = port;
    }

    public void bind() {
        bootstrap = bootstrap();
        channelGroup = channelGroup();
        socketAddress = new InetSocketAddress(port);

        bootstrap.bind(socketAddress).addListener((GenericFutureListener<ChannelFuture>) future -> {
            if (future.isSuccess()) {
                channelGroup.add(future.channel());
            } else {
                if (future.cause() != null) {
                    LoggerProvider.addExceptionLog(future.cause());
                }
                LoggerProvider.addExceptionLog("端口绑定异常, port:" + port);
                System.exit(0);
            }
        });
    }

    public void unbound() {
        try {
            channelGroup.close().sync();
        } catch (InterruptedException e) {
            LoggerProvider.addExceptionLog(e);
        } finally {
            bootstrap.config().group().shutdownGracefully();
            bootstrap.config().childGroup().shutdownGracefully();
        }
    }

    private ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer());

        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (ChannelOption option : keySet) {
            b.option(option, tcpChannelOptions.get(option));
        }
        return b;
    }
}
