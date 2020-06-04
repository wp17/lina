package com.github.wp17.lina.common.net;

public abstract class AbstractPacket implements IPacket {
    public final PacketHeader header;

    public AbstractPacket(PacketHeader header) {
        this.header = header;
    }

    public void setSeq(int seq) {
        header.setSeq(seq);
    }

    public void setMsgId(int msgID) {
        header.setMsgId(msgID);
    }

    public void setCheckSum(int checksum) {
        header.setCheckSum(checksum);
    }

    public void setBodyLength(int length) {
        header.setBodyLength(length);
    }

    public int getSeq() {
        return header.getSeq();
    }

    public int getMsgId() {
        return header.getMsgId();
    }

    public int getCheckSum() {
        return header.getCheckSum();
    }

    public int getBodyLength() {
        return header.getBodyLength();
    }
}
