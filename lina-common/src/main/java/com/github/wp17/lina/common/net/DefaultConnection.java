package com.github.wp17.lina.common.net;

import com.google.protobuf.Message;
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
    public void sendMsg(Message message) {
        if (isClosed) {
            // todo 此处应该有日志
            return;
        }
        ((IConnection) this).sendMsg(message);
    }

    @Override
    public AbstractSession getSession() {
        return session;
    }
}
