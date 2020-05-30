package com.github.wp17.lina.common.net;

import com.github.wp17.lina.common.msg.AbsMsgService;
import com.google.protobuf.MessageLite;

public interface IConnection {
    void sendMsg(MessageLite message, AbsMsgService msgService);
}
