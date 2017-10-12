package com.github.wp17.lina.net.packet;

import org.apache.mina.core.buffer.IoBuffer;

public class Packet {
	private PacketHeader header = new PacketHeader();
	private IoBuffer body;
	
	public Packet(IoBuffer body){
		this.body = body;
	}
	
	public IoBuffer encode(){
		IoBuffer buffer = IoBuffer.allocate(20480).setAutoExpand(true);
		
		body.flip();
		header.setBodyLength(body.limit());
		
		header.encode(buffer);
		buffer.put(body);
		
		buffer.flip();
		
		return buffer;
	}
	
	public void decode(){
		
	}

	public void setSeq(int seq){
		header.setSeq(seq);
	}
	
	public void setMsgID(short msgID){
		header.setMsgID(msgID);
	}
	
	public void setCheckSum(int checksum){
		header.setCheckSum(checksum);
	}
}
