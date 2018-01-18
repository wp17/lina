package com.github.wp17.lina.net.codec;

import java.util.List;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.message.MessageFactory;
import com.github.wp17.lina.net.packet.NettyInBound;
import com.github.wp17.lina.net.packet.PacketHeader;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyByte2MsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		ByteBuf headerBuf = in.slice(0, PacketHeader.HEADER_LEAGTH);
		
		PacketHeader header = new PacketHeader();
		header.decode(new NettyInBound(headerBuf));
		
		ByteBuf bodyBuf = in.slice(PacketHeader.HEADER_LEAGTH, header.getBodyLength());
		
		IMessage message = MessageFactory.getFactory().getMessage(header.getMsgID());
		
		message.decode(new NettyInBound(bodyBuf));
		
		ctx.write(message);

	}
}
