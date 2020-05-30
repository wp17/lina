package com.github.wp17.lina.zone.msg.processor;

import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;

public abstract class G2ZMsgProcessor implements IMsgProcessor {
    @Override
    public void process(AbstractSession session, AbstractPacket packet) {
        process((DefaultPacket) packet);
    }

    public abstract void process(DefaultPacket packet);
}
