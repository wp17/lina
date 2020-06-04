package com.github.wp17.lina.common.net;

public interface IPacket<T> {
    /**
     * 消息头总长度
     */
    int HEADER_LEAGTH = 16;
    /**
     * 长度字段的长度
     */
    int LENGTHFIELD_LENGTH = 4;
    /**
     * 长度字段的偏移量
     */
    int LENGTHFIELD_OFFSET = 12;

}
