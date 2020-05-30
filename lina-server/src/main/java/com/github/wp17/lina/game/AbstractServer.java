package com.github.wp17.lina.game;

import com.github.wp17.lina.common.interfaces.Server;
import com.github.wp17.lina.game.module.ModuleManager;

public abstract class AbstractServer implements Server {
    private final int id;

    protected AbstractServer(int id) {
        this.id = id;
    }

    @Override
    public void startup() {
        ModuleManager.getInstance().init();
    }

    @Override
    public void shutdown() {
        ModuleManager.getInstance().shutdown();
    }

    @Override
    public int getId() {
        return id;
    }
}
