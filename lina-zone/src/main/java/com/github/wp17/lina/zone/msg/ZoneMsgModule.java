package com.github.wp17.lina.zone.msg;

import com.github.wp17.lina.common.msg.AbsMsgService;
import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.msg.MessageClazz;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.util.ClassUtil;
import com.google.protobuf.MessageLite;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ZoneMsgModule extends AbsMsgService {
    private ZoneMsgModule(){}
    private static final ZoneMsgModule instance = new ZoneMsgModule();
    public static final ZoneMsgModule getInstance() {
        return instance;
    }

    @Override
    protected String getPackage() {
        return "com.github.wp17.lina.zone.msg.processor";
    }
}
