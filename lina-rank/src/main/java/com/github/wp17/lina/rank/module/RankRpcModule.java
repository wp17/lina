package com.github.wp17.lina.rank.module;

import com.github.wp17.lina.rank.rpc.GRpcService;
import com.github.wp17.lina.rank.rpc.TestServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class RankRpcModule {
    private RankRpcModule() {}
    private static final RankRpcModule instance = new RankRpcModule();
    public static RankRpcModule getInstance() {
        return instance;
    }

    private GRpcService service = new GRpcService();

    public void init() {
        service.addService(new TestServiceImpl());
        try {
            service.start();
        } catch (IOException e) {
            log.error("rpc服务启动异常", e);
            System.exit(0);
        }
    }

    public void shutdown() {
        service.stop();
    }
}
