package com.github.wp17.lina.common.msg;

import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.util.ClassUtil;
import com.google.protobuf.MessageLite;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbsMsgService {
    protected final Map<Class<? extends MessageLite>, Short> messages = new HashMap<Class<? extends MessageLite>, Short>();
    protected final Map<Short, IMsgProcessor> processors = new HashMap<Short, IMsgProcessor>();

    protected abstract String getPackage();

    public void process(AbstractSession session, DefaultPacket message) {
        getProcessor(message.header.getMsgId()).process(session, message);
    }

    public IMsgProcessor getProcessor(int msgID) {
        return processors.get((short) msgID);
    }

    public short getMessageID(Class<? extends MessageLite> clazz) {
        Short id = messages.get(clazz);
        if (null != id) {
            return id.shortValue();
        }
        return (short) -1;
    }

    public void init() {
        List<Class<?>> clazzes = null;
        try {
            clazzes = ClassUtil.getClasses(getPackage(), false);
        } catch (Exception e) {
            log.error("", e);
            System.exit(0);
        }

        if(null != clazzes){
            for (Class<?> clazz : clazzes) {
                try {
                    short msgID = clazz.getAnnotation(MessageID.class).value();
                    IMsgProcessor processor = (IMsgProcessor) clazz.newInstance();
                    processors.put(msgID, processor);

                    Class<? extends MessageLite> msgClazz = clazz.getAnnotation(MessageClazz.class).value();
                    messages.put(msgClazz, msgID);

                } catch (Exception e) {
                    log.error("", e);
                    System.exit(0);
                }
            }
        }
    }
}
