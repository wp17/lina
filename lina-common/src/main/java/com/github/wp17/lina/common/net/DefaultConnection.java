package com.github.wp17.lina.common.net;

import com.github.wp17.lina.common.msg.AbsMsgService;
//import com.github.wp17.lina.common.net.AbstractSession;
//import com.github.wp17.lina.common.net.DefaultPacket;
//import com.github.wp17.lina.common.net.IConnection;
import com.google.protobuf.MessageLite;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultConnection implements IConnection {
    @Setter
    private volatile boolean isClosed = false;
    protected final AbstractSession session;

    public DefaultConnection(AbstractSession session) {
        this.session = session;
        session.setObj(this);
    }

    @Override
    public void sendMsg(MessageLite message, AbsMsgService msgService) {
        if (isClosed) {
            // todo 此处应该有日志
            return;
        }
        byte[] body = message.toByteArray();
        DefaultPacket packet = new DefaultPacket(body);
        packet.setBodyLength(body.length);
        packet.setSeq(session.getNextOutgoingSeq());
        packet.setMsgID(msgService.getMessageID(message.getClass()));
        packet.setCheckSum(-1);
        session.sendMsg(packet);
    }
}
