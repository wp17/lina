package com.github.wp17.lina.game.module.rpc;

import com.github.wp17.lina.game.grpc.HelloWorldClient;
import com.github.wp17.lina.game.module.AbsModule;

public class RpcModule implements AbsModule {
    private RpcModule() {}
    private static final RpcModule instance = new RpcModule();
    public static final RpcModule getInstance() {
        return instance;
    }

    private HelloWorldClient client;


    @Override
    public void init() {
        client = new HelloWorldClient("127.0.0.1", 50051);
    }

    @Override
    public void shutdown() {
        client.shutdown();
    }

    public void greet(String name) {
        client.greet(name);
    }
}
