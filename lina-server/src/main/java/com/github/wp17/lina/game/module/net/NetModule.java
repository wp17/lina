package com.github.wp17.lina.game.module.net;

import com.github.wp17.lina.common.net.NettySession;
import com.github.wp17.lina.game.module.execute.ExecutorModule;

import java.util.concurrent.ConcurrentHashMap;

public class NetModule {
    private NetModule() {
    }

    private static final NetModule instance = new NetModule();

    public static NetModule getInstance() {
        return instance;
    }

    private ConcurrentHashMap<String, NettySession> sessions = new ConcurrentHashMap<>();

    public void init() {
        ExecutorModule.getInstance().addSessionCheckTask(() -> check());
    }

    public void addSession(NettySession session) {
        sessions.put(session.getId(), session);
    }

    public void remSession(String id) {
        sessions.remove(id);
    }

    private void check() {
        long now = System.currentTimeMillis();
        sessions.values().forEach(session -> {
            if (!session.verify(now)) {
                sessions.remove(session.getId());
                session.close();
            }
        });
    }
}
