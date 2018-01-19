package com.github.wp17.lina.net.packet;

import org.apache.mina.core.buffer.IoBuffer;

public class MinaPacket extends AbstractPacket<IoBuffer> {
	private IoBuffer body;
	
	public MinaPacket(IoBuffer body){
		this.body = body;
	}
	
	public IoBuffer encode(){
		IoBuffer buffer = IoBuffer.allocate(20480).setAutoExpand(true);
		
		body.flip();
		header.setBodyLength(body.limit());
		
		header.encode(new MinaOutbound());
		buffer.put(body);
		
		buffer.flip();
		
		return buffer;
	}
	
	public void decode(){
		
	}
}
