package com.github.wp17.lina.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;
import java.util.List;

import com.github.wp17.lina.net.codec.NettyByte2MsgDecoder;
import com.github.wp17.lina.net.handler.NettyInboundHandler;
import com.github.wp17.lina.net.packet.Packet;

public class NettyNioServer extends NioServer{
	private ServerBootstrap bootstrap = new ServerBootstrap();
	ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	protected NettyNioServer(List<SocketAddress> addresses, int id) {
		super(addresses, id);
	}

	@Override
	public void shutdown() {
		bootstrap.config().group().shutdownGracefully();
		bootstrap.config().childGroup().shutdownGracefully();
		allChannels.close().awaitUninterruptibly();
 	}

	@Override
	protected void bind() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroop = new NioEventLoopGroup();
		
		bootstrap.group(bossGroup, workerGroop).channel(NioServerSocketChannel.class);
		
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline()
				.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, Packet.HEADER_LEAGTH-Packet.LENGTHFIELD_LENGTH, Packet.LENGTHFIELD_LENGTH))
				.addLast(new NettyByte2MsgDecoder())
				.addLast(new NettyInboundHandler());
			}
		});
		
		try {
			for (SocketAddress socketAddress : getAddresses()) {
				ChannelFuture future = bootstrap.bind(socketAddress).sync();
				future.await();
				if(future.isSuccess()){
					allChannels.add(future.channel());
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void setBacklog(int backlog) {
		bootstrap.option(ChannelOption.SO_BACKLOG, backlog);
		
	}

	@Override
	protected void setTcpNoDelay(boolean tcpNoDelay) {
		bootstrap.option(ChannelOption.TCP_NODELAY, tcpNoDelay);
		
	}

	@Override
	protected void setSoLinger(int soLinger) {
		bootstrap.option(ChannelOption.SO_LINGER, soLinger);
		
	}

	@Override
	protected void setReuseAddress(boolean reuseAddress) {
		bootstrap.option(ChannelOption.SO_REUSEADDR, reuseAddress);
		
	}

	@Override
	protected void preStartup() {
		// TODO Auto-generated method stub
		
	}

}
