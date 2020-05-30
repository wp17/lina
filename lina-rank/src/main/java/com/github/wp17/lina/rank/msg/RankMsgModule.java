package com.github.wp17.lina.rank.msg;

import com.github.wp17.lina.common.msg.AbsMsgService;
import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.msg.MessageClazz;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.util.ClassUtil;
import com.google.protobuf.MessageLite;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RankMsgModule extends AbsMsgService {
    private RankMsgModule() {}
    private static RankMsgModule instance = new RankMsgModule();
    public static RankMsgModule getInstance() {
        return instance;
    }

    @Override
    protected String getPackage() {
        return "com.github.wp17.lina.rank.msg.processor";
    }
}
