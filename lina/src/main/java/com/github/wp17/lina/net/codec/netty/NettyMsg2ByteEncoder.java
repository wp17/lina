package com.github.wp17.lina.net.codec.netty;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.connection.LogicSession;
import com.github.wp17.lina.net.connection.NettySession;
import com.github.wp17.lina.net.packet.NettyPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyMsg2ByteEncoder extends MessageToByteEncoder<IMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, IMessage msg, ByteBuf out) throws Exception {
		LogicSession session = NettySession.getLogicSession(ctx.channel());
		NettyPacket packet = new NettyPacket();
		
		packet.setSeq(session.getNextOutgoingSeq());
		
		out.writeBytes(packet.encode(msg));
	}
}
