package com.github.wp17.lina.game.module.msg;

import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;

/**rank服务器发送给game服务器消息的处理器*/
public abstract class RPCMsgProcessor implements IMsgProcessor {
    @Override
    public void process(AbstractSession session, AbstractPacket packet) {
        process((DefaultPacket) packet);
    }

    public abstract void process(DefaultPacket packet);
}
