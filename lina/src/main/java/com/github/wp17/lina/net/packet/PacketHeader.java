package com.github.wp17.lina.net.packet;

import org.apache.mina.core.buffer.IoBuffer;

public class PacketHeader {
	public static final int HEADER_LEAGTH = 14;
	
	private short msgID;
	private int checkSum;
	private int seq;
	private int bodyLength;
	
	public short getMsgID() {
		return msgID;
	}
	public void setMsgID(short msgID) {
		this.msgID = msgID;
	}
	public int getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getBodyLength() {
		return bodyLength;
	}
	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}
	
	public void encode(IoBuffer buffer){
		buffer.putShort(msgID);
		buffer.putInt(checkSum);
		buffer.putInt(seq);
		buffer.putInt(bodyLength);
	}
	
	public void decode(IoBuffer buffer){
		msgID = buffer.getShort();
		checkSum = buffer.getInt();
		seq = buffer.getInt();
		bodyLength = buffer.getInt();
	}
}
