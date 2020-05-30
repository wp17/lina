package com.github.wp17.lina.zone;

import com.github.wp17.lina.common.net.NioServer;
import com.github.wp17.lina.common.interfaces.Server;
import com.github.wp17.lina.zone.net.ZoneNioServer;

public class ZoneServer implements Server {
    private final int id;
    private NioServer nioServer;

    public ZoneServer(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void startup() {
        ZoneModuleManager.getInstance().init();
        nioServer = new ZoneNioServer(9001);
        nioServer.bind();
    }

    @Override
    public void shutdown() {
        nioServer.unbound();
        ZoneModuleManager.getInstance().shutdown();
    }
}
