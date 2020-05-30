package com.github.wp17.lina.rank.module;

import com.github.wp17.lina.rank.msg.RankMsgModule;
import com.github.wp17.lina.rank.spring.RankSpringContext;
//todo
public class RankMQModule {

    public static void main(String[] args) {
        RankMsgModule.getInstance().init();
        RankSpringContext.getInstance().init();
    }
}
