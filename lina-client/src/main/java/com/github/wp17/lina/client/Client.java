package com.github.wp17.lina.client;

import com.github.wp17.lina.client.module.msg.ClientMsgModule;
import com.github.wp17.lina.client.net.NettyClient;

public class Client {
    private NettyClient client;

    public void startup() throws InterruptedException {
        ClientMsgModule.getInstance().init();
        client = new NettyClient("localhost", 8001);
        client.connect();
    }

    public void shutdown() {
        client.close();
    }
}
