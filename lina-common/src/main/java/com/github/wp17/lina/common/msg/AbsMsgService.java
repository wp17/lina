package com.github.wp17.lina.common.msg;

import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbsMsgService {
    protected final Map<Short, IMsgProcessor> processors = new HashMap<Short, IMsgProcessor>();

    protected abstract String getPackage();

    public void process(AbstractSession session, DefaultPacket packet) {
        getProcessor(packet.getMsgId()).process(session, packet);
    }

    public IMsgProcessor getProcessor(int msgID) {
        return processors.get((short) msgID);
    }

    public void init() {
        List<Class<?>> clazzes = null;
        try {
            clazzes = ClassUtil.getClasses(getPackage(), false);
        } catch (Exception e) {
            log.error("", e);
            System.exit(0);
        }

        if (null != clazzes) {
            for (Class<?> clazz : clazzes) {
                try {
                    short msgID = clazz.getAnnotation(MessageID.class).value();
                    IMsgProcessor processor = (IMsgProcessor) clazz.newInstance();
                    processors.put(msgID, processor);
                } catch (Exception e) {
                    log.error("", e);
                    System.exit(0);
                }
            }
        }
    }
}
