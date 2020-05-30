package com.github.wp17.lina.game.module.msg;

import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;

/**game服务器通过消息服务器转发消息*/
public abstract class MQMsgProcessor implements IMsgProcessor {
    @Override
    public void process(AbstractSession session, AbstractPacket packet) {
        DefaultPacket defaultPacket = (DefaultPacket) packet;
        process(defaultPacket);
    }

    public abstract void process(DefaultPacket packet);
}
