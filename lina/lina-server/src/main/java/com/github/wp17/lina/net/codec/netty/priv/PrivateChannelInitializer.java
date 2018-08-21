package com.github.wp17.lina.net.codec.netty.priv;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.wp17.lina.net.handler.NettyHeartbeatHandler;
import com.github.wp17.lina.net.handler.NettyInboundLogicHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class PrivateChannelInitializer extends ChannelInitializer<SocketChannel>{
	@Autowired private IdleStateHandler idleStateHandler;
	@Autowired private NettyHeartbeatHandler nettyHeartbeatHandler;
	@Autowired private ReadTimeoutHandler readTimeoutHandler;
	@Autowired private LengthFieldBasedFrameDecoder lengthFieldBasedFrameDecoder;
	@Autowired private NettyByte2MsgDecoder nettyByte2MsgDecoder;
	@Autowired private NettyMsg2ByteEncoder nettyMsg2ByteEncoder;
	@Autowired private NettyInboundLogicHandler nettyInboundLogicHandler;
//	@Autowired private SslHandler sslHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
//		.addLast(idleStateHandler)
//		.addLast(nettyHeartbeatHandler)
//		.addLast(readTimeoutHandler)
		.addLast(lengthFieldBasedFrameDecoder)
		.addLast(nettyByte2MsgDecoder)
		.addLast(nettyMsg2ByteEncoder)
		.addLast(nettyInboundLogicHandler)
//		.addLast(sslHandler)
		;
	}
}
