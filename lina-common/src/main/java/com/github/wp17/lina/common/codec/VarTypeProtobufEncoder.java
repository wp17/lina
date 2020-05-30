package com.github.wp17.lina.common.codec;

import com.github.wp17.lina.common.net.AbstractPacket;

import com.github.wp17.lina.common.net.DefaultPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class VarTypeProtobufEncoder extends MessageToByteEncoder<AbstractPacket> {

	@Override
	protected void encode(ChannelHandlerContext ctx, AbstractPacket p, ByteBuf out) {
        DefaultPacket packet = (DefaultPacket) p;

        byte[] header = packet.header.encode();
		byte[] body = packet.body;

        out.writeBytes(header);
        out.writeBytes(body);
        ctx.flush();
	}
}
