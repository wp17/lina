package com.github.wp17.lina.game.module.msg;

import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;

/**rpc消息处理器*/
public abstract class RPCMsgProcessor implements IMsgProcessor {
    @Override
    public final void process(AbstractSession session, AbstractPacket packet) {
        process((DefaultPacket) packet);
    }

    public abstract void process(DefaultPacket packet);
}
