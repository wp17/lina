package com.github.wp17.lina.net.packet;

public abstract class AbstractPacket <T> implements Packet<T>{
	protected final PacketHeader header = new PacketHeader();
	
	public void setSeq(int seq){
		header.setSeq(seq);
	}
//	
//	public void setMsgID(short msgID){
//		header.setMsgID(msgID);
//	}
//	
//	public void setCheckSum(int checksum){
//		header.setCheckSum(checksum);
//	}
}
