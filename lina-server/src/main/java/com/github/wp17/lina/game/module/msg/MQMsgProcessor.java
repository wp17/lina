package com.github.wp17.lina.game.module.msg;

import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;

/**消息队列消息处理器*/
public abstract class MQMsgProcessor implements IMsgProcessor {
    @Override
    public final void process(AbstractSession session, AbstractPacket packet) {
        DefaultPacket defaultPacket = (DefaultPacket) packet;
        process(defaultPacket);
    }

    public abstract void process(DefaultPacket packet);
}
