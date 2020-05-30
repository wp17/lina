package com.github.wp17.lina.common.net;

import com.github.wp17.lina.common.util.CommonUtil;

public class DefaultPacket extends AbstractPacket {
    public byte[] body;

    private DefaultPacket() {
    }

    public DefaultPacket(byte[] body) {
        this.body = body;
    }

    public byte[] toByte() {
        byte[] h = header.encode();
        return CommonUtil.merge(h, body);
    }
}
