package com.github.wp17.lina.client.module.msg;

import com.github.wp17.lina.common.msg.AbsMsgService;

public class ClientMsgModule extends AbsMsgService {
    private ClientMsgModule() {}
    private static final ClientMsgModule instance = new ClientMsgModule();
    public static ClientMsgModule getInstance() {
        return  instance;
    }

    @Override
    protected String getPackage() {
        return "com.github.wp17.lina.client.module.msg.processor.impl";
    }
}
