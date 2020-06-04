package com.github.wp17.lina.game.module.msg;

import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;

/**服务器间内部消息处理器*/
public abstract class InnerMsgProcessor implements IMsgProcessor {
    @Override
    public void process(AbstractSession session, AbstractPacket packet) {

    }
}
