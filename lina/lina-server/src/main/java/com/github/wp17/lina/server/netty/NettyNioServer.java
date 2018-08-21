package com.github.wp17.lina.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.server.NioServer;

@Component("nettyNioServer")
public class NettyNioServer extends NioServer{
	@Autowired @Qualifier("serverBootstrap") private ServerBootstrap bootstrap;
	@Autowired @Qualifier("channelGroup") private ChannelGroup allChannels;
	@Autowired @Qualifier("tcpSocketAddress") private InetSocketAddress socketAddress;
	
	protected NettyNioServer() {
		super(1);
	}

	@Override
	@PreDestroy
	public void shutdown() {
		try {
			allChannels.close().sync();
		} catch (InterruptedException e) {
			LoggerProvider.addExceptionLog(e);
		}finally{
			bootstrap.config().group().shutdownGracefully();
			bootstrap.config().childGroup().shutdownGracefully();
		}
 	}

	@Override
	protected void bind() {
		ChannelFuture future = bootstrap.bind(socketAddress);
		future.addListener(new GenericFutureListener<ChannelFuture>() {
			@Override
			public void operationComplete(ChannelFuture future)throws Exception {
				if(future.isSuccess()){
					allChannels.add(future.channel());
				}else {
					if (future.cause() != null) {
						LoggerProvider.addExceptionLog(future.cause());
					}
				}
			}
		});
	}

	@Override
	protected void preStartup() {
		// TODO Auto-generated method stub
		
	}

}
