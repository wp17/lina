package com.github.wp17.lina.net.codec.netty.priv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.net.packet.NettyInBound;
import com.github.wp17.lina.net.packet.Packet;
import com.github.wp17.lina.net.packet.PacketHeader;
import com.github.wp17.lina.server.message.MessageFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyByte2MsgDecoder extends ByteToMessageDecoder {
	@Autowired private MessageFactory messageFactory;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		ByteBuf headerBuf = in.readBytes(Packet.HEADER_LEAGTH);
		PacketHeader header = new PacketHeader();
		header.decode(new NettyInBound(headerBuf));
		
		ByteBuf bodyBuf = in.readBytes(header.getBodyLength());
		
		IMessage message = messageFactory.getMessage(header.getMsgID());
		message.decode(new NettyInBound(bodyBuf));
		out.add(message);
	}
}
