package com.github.wp17.lina.common.net;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Setter
@Getter
public class PacketHeader {
    private int msgId;
    private int checkSum;
    private int seq;
    private int bodyLength;

    public byte[] encode() {
        byte[] header = new byte[16];
        header[0] = (byte) (msgId & 0xff);
        header[1] = (byte) ((msgId >> 8) & 0xff);
        header[2] = (byte) ((msgId >> 16) & 0Xff);
        header[3] = (byte) ((msgId >> 24) & 0Xff);

        header[4] = (byte) (checkSum & 0Xff);
        header[5] = (byte) ((checkSum >> 8) & 0Xff);
        header[6] = (byte) ((checkSum >> 16) & 0Xff);
        header[7] = (byte) ((checkSum >> 24) & 0Xff);

        header[8] = (byte) (seq & 0xff);
        header[9] = (byte) ((seq >> 8) & 0xff);
        header[10] = (byte) ((seq >> 16) & 0xff);
        header[11] = (byte) ((seq >> 24) & 0xff);

        header[12] = (byte) (bodyLength & 0xff);
        header[13] = (byte) ((bodyLength >> 8) & 0xff);
        header[14] = (byte) ((bodyLength >> 16) & 0xff);
        header[15] = (byte) ((bodyLength >> 24) & 0xff);
        return header;
    }

    public void decode(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        msgId = buffer.getInt();
        checkSum = buffer.getInt();
        seq = buffer.getInt();
        bodyLength = buffer.getInt();
    }

    public void decode(ByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        msgId = buffer.getInt();
        checkSum = buffer.getInt();
        seq = buffer.getInt();
        bodyLength = buffer.getInt();
    }
}
