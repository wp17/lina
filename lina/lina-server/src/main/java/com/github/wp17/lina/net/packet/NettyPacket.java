package com.github.wp17.lina.net.packet;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.AbstractPacket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class NettyPacket extends AbstractPacket<ByteBuf> {
	private ByteBuf body = ByteBufAllocator.DEFAULT.buffer(2014);
	
	public NettyPacket(){}
	
	@Override
	public ByteBuf encode(IMessage msg) {
		NettyOutbound b = new NettyOutbound();
		msg.encode(b);
		
		int bodyLength = b.getByteBuf().readableBytes();
		header.setBodyLength(bodyLength);
		header.setMsgID(msg.getMsgID());
		header.setCheckSum(-1);
		
		NettyOutbound h = new NettyOutbound();
		header.encode(h);
		
		body.writeBytes(h.getByteBuf());
		body.writeBytes(b.getByteBuf());
		
		h.release();
		b.release();
		
		return body;
	}

	@Override
	public void decode() {
		
	}

}