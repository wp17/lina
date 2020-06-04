package com.github.wp17.lina.rank.msg;

import com.github.wp17.lina.common.msg.AbsMsgService;
import lombok.extern.slf4j.Slf4j;

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
