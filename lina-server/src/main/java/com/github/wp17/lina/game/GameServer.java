package com.github.wp17.lina.game;

import com.github.wp17.lina.game.netty.GameNioServer;

public class GameServer extends AbstractServer {
    private final int port;
    private GameNioServer nioServer;

    protected GameServer(int id, int port) {
        super(id);
        this.port = port;
    }

    @Override
    public void startup() {
        super.startup();
        nioServer = new GameNioServer(port);
        nioServer.bind();
    }

    @Override
    public void shutdown() {
        nioServer.unbound();
        super.shutdown();
    }
}
