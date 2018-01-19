package com.github.wp17.lina.net.packet;

public class PacketHeader {
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
	
	public void encode(Outbound outbound){
		outbound.writeShort(msgID);
		outbound.writeInt(checkSum);
		outbound.writeInt(seq);
		outbound.writeInt(bodyLength);
	}
	
	public void decode(Inbound inbound){
		msgID = inbound.readShort();
		checkSum = inbound.readInt();
		seq = inbound.readInt();
		bodyLength = inbound.readInt();
	}
}
