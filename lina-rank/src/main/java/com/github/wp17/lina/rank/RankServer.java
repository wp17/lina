package com.github.wp17.lina.rank;

import com.github.wp17.lina.common.net.NioServer;
import com.github.wp17.lina.common.interfaces.Server;
import com.github.wp17.lina.rank.module.LogModule;
import com.github.wp17.lina.rank.module.RankDBModule;
import com.github.wp17.lina.rank.module.RankLogicModule;
import com.github.wp17.lina.rank.module.RankRpcModule;
import com.github.wp17.lina.rank.msg.RankMsgModule;
import com.github.wp17.lina.rank.net.RankNioServer;

public class RankServer implements Server {
    private final int id;
    private final int port;
    private volatile NioServer nioServer;

    public RankServer(int id, int port) {
        this.id = id;
        this.port = port;
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void startup() {
        LogModule.getInstance().init();
        RankMsgModule.getInstance().init();
        RankDBModule.getInstance().init();
        RankRpcModule.getInstance().init();
        RankLogicModule.getInstance().init();

        nioServer = new RankNioServer(port);
        nioServer.bind();
    }

    @Override
    public void shutdown() {
        nioServer.unbound();
        RankRpcModule.getInstance().shutdown();
        RankLogicModule.getInstance().shutdown();
    }
}
