package com.github.wp17.lina.net.codec.netty.priv;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.LogicSession;
import com.github.wp17.lina.net.connection.NettySession;
import com.github.wp17.lina.net.packet.NettyPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class NettyMsg2ByteEncoder extends MessageToByteEncoder<IMessage> {
	@Override
	protected void encode(ChannelHandlerContext ctx, IMessage msg, ByteBuf out) throws Exception {
		LogicSession session = NettySession.getLogicSession(ctx.channel());
		NettyPacket packet = new NettyPacket();
		packet.setSeq(session.getNextOutgoingSeq());
		ByteBuf buf = packet.encode(msg);
		out.writeBytes(buf);
	}
}
