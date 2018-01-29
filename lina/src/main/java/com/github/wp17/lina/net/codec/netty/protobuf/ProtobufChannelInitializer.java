package com.github.wp17.lina.net.codec.netty.protobuf;

import java.nio.ByteBuffer;

import com.github.wp17.lina.net.packet.Packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ProtobufChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
		.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, Packet.LENGTHFIELD_OFFSET, Packet.LENGTHFIELD_LENGTH))
		.addLast(new VarTypeProtobufDecoder())
		.addLast(new VarTypeProtobufEncoder())
		.addLast(new SimpleChannelInboundHandler<ByteBuffer>() {
			@Override
			protected void channelRead0(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {
				
			}
		});
	}
}
