package com.github.wp17.lina.rank.net;

import com.github.wp17.lina.rank.client.NettyClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectManager {
    private ConnectManager() {}
    private static final ConnectManager instance = new ConnectManager();
    public static ConnectManager getInstance() {
        return instance;
    }

    private NettyClient r2gClient;

    public void init() {
        r2gClient = new NettyClient("localhost",8001);
        try {
            r2gClient.connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
