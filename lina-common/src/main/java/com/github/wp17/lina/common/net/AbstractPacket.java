package com.github.wp17.lina.common.net;

public abstract class AbstractPacket implements IPacket {
    public final PacketHeader header = new PacketHeader();

    public void setSeq(int seq) {
        header.setSeq(seq);
    }

    public void setMsgID(short msgID) {
        header.setMsgId(msgID);
    }

    public void setCheckSum(int checksum) {
        header.setCheckSum(checksum);
    }

    public void setBodyLength(int length) {
        header.setBodyLength(length);
    }
}
