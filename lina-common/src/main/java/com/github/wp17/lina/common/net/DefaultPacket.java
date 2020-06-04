package com.github.wp17.lina.common.net;

import com.github.wp17.lina.common.util.CommonUtil;

public class DefaultPacket extends AbstractPacket {
    public byte[] body;

    public DefaultPacket(byte[] body) {
        super(new PacketHeader());
        this.body = body;
        setBodyLength(body.length);
    }

    public DefaultPacket(byte[] body, PacketHeader header) {
        super(header);
        this.body = body;
        setBodyLength(body.length);
    }

    public byte[] toByte() {
        byte[] h = header.encode();
        return CommonUtil.merge(h, body);
    }
}
