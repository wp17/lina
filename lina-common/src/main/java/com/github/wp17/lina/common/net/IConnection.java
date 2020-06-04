package com.github.wp17.lina.common.net;

import com.github.wp17.lina.proto.msg.Options;
import com.google.protobuf.Message;

public interface IConnection {

    default void sendMsg(Message message) {
        DefaultPacket packet = new DefaultPacket(message.toByteArray());
        int msgId = message.getDescriptorForType().getOptions().getExtension(Options.messageId);
        packet.setMsgId(msgId);
        packet.setCheckSum(-1);
        getSession().sendMsg(packet);
    }

    AbstractSession getSession();
}
