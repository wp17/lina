package com.github.wp17.lina.game.module.line;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.config.data.LineData;
import com.github.wp17.lina.game.module.execute.ExecutorModule;
import com.github.wp17.lina.game.logic.Role;

public class LineServer {
    private LineData lineData;
    private CountDownLatch countDownLatch;
    private final AtomicBoolean running = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private final Map<Long, Role> roles = new ConcurrentHashMap<Long, Role>();
    private final AtomicReference<Processor> processorRef = new AtomicReference<Processor>();

    public LineServer() {
    }

    public void setLineData(LineData lineData) {
        this.lineData = lineData;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void startup() {
        running.compareAndSet(false, true);
        Processor processor = new Processor();
        processorRef.compareAndSet(null, processor);
        ExecutorModule.getInstance().executeFUService(processor);
    }

    public void close() {
        if (closed.get() || !running.get()) {
            return;
        }
        running.compareAndSet(true, false);
        for (Role role : roles.values()) {
            role.downLine();
        }
        closed.compareAndSet(false, true);
    }

    public int getId() {
        return lineData.getId();
    }

    public void addRole(Role role) {
        roles.put(role.getRoleUuid(), role);
    }

    public void remRole(Role role) {
        roles.remove(role.getRoleUuid());
    }

    public Role getRole(Long roleId) {
        return roles.get(roleId);
    }

    private class Processor implements Runnable {
        @Override
        public void run() {
            countDownLatch.countDown();
            while (running.get()) {
                for (Role role : roles.values()) {
                    processMsg(role);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    LoggerProvider.addExceptionLog("", e);
                }
            }
        }
    }

    private void processMsg(Role role) {
        AbstractPacket packet = null;
        while ((packet = role.pollMsg()) != null) {
            role.processMsg(packet);
        }
    }
}
