package com.github.wp17.lina.net.packet;

import org.apache.mina.core.buffer.IoBuffer;

import com.github.wp17.lina.message.IMessage;

public class MinaPacket extends AbstractPacket<IoBuffer> {
	private IoBuffer body = IoBuffer.allocate(1024).setAutoExpand(true);
	
	public MinaPacket(){}
	
	public IoBuffer encode(IMessage msg){
		MinaOutbound b = new MinaOutbound();
		msg.encode(b);
		
		header.setCheckSum(-1);
		header.setMsgID(msg.getMsgID());
		header.setBodyLength(b.getBuffer().position());
		
		MinaOutbound h = new MinaOutbound();
		header.encode(h);
		
		body.put(h.getBuffer().flip());
		body.put(b.getBuffer().flip());
		
		body.flip();
		
		return body;
	}
	
	public void decode(){
		
	}
}
