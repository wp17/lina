package com.github.wp17.lina.net.codec.netty.priv;

import java.util.concurrent.TimeUnit;

import com.github.wp17.lina.net.handler.NettyHeartbeatHandler;
import com.github.wp17.lina.net.handler.NettyInboundLogicHandler;
import com.github.wp17.lina.net.packet.Packet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class PrivateChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
		.addLast(new IdleStateHandler(10, 10, 5, TimeUnit.SECONDS))
		.addLast(new NettyHeartbeatHandler())
		.addLast(new ReadTimeoutHandler(30))
		.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, Packet.LENGTHFIELD_OFFSET, Packet.LENGTHFIELD_LENGTH))
		.addLast(new NettyByte2MsgDecoder())
		.addLast(new NettyMsg2ByteEncoder())
		.addLast(new NettyInboundLogicHandler());
	}

}
