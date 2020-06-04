package com.github.wp17.lina.client.net;

import com.github.wp17.lina.common.log.LoggerProvider;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;

import java.net.InetSocketAddress;

public class NettyClient {
    private static final Logger log = LoggerProvider.getLogger(NettyClient.class);

    private InetSocketAddress address;
    private Bootstrap bootstrap;
    private EventLoopGroup loopGroup;

    private String host;
    private int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    ChannelFuture future;

    public void connect() throws InterruptedException {
        bootstrap = new Bootstrap();
        loopGroup = new NioEventLoopGroup();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());

        address = new InetSocketAddress("localhost", 8001);

        future = bootstrap.connect(address).sync();
        if (future.isSuccess()) {
            log.info("R2G connect success");
        } else {
            if (future.cause() != null) {
                LoggerProvider.addExceptionLog(future.cause());
            }
        }
    }

    public void close() {
        log.info("R2G connect close");
        future.channel().flush().close().syncUninterruptibly();
        loopGroup.shutdownGracefully();
    }
}
