package com.github.wp17.lina.game.module.line;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.config.template.LineTemplate;
import com.github.wp17.lina.game.logic.room.IRoom;
import com.github.wp17.lina.game.module.execute.ExecutorModule;
import com.github.wp17.lina.game.logic.Role;

public class LineServer {
    private LineTemplate lineTemplate;
    private CountDownLatch countDownLatch;
    private final AtomicBoolean running = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private final Map<Long, Role> roles = new ConcurrentHashMap<Long, Role>();
    private final Map<Long, IRoom> rooms = new ConcurrentHashMap<>();
    private final AtomicReference<Processor> processorRef = new AtomicReference<Processor>();

    public LineServer() {
    }

    public void setLineTemplate(LineTemplate lineTemplate) {
        this.lineTemplate = lineTemplate;
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
        for (IRoom room : rooms.values()) {
            room.close();
        }
        for (Role role : roles.values()) {
            role.downLine();
        }
        closed.compareAndSet(false, true);
    }

    public int getId() {
        return lineTemplate.getId();
    }

    public void addRole(Role role) {
        roles.put(role.getRoleUuid(), role);
    }

    public void remRole(Role role) {
        roles.remove(role.getRoleUuid());
    }

    public void remRole(long roleUuid) {
        roles.remove(roleUuid);
    }

    public Role getRole(Long roleId) {
        return roles.get(roleId);
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getId(), room);
    }

    public void remRoom(long roomId) {
        rooms.remove(roomId);
    }

    public IRoom getRoom(long roomId) {
        return rooms.get(roomId);
    }

    public int getRoleNum() {
        return  roles.size();
    }

    public boolean isBusy() {
        return roles.size() >= 500;
    }

    private class Processor implements Runnable {
        @Override
        public void run() {
            countDownLatch.countDown();
            while (running.get()) {
                for (IRoom room : rooms.values()) {
                    room.upFrame();
                }
                for (Role role : roles.values()) {
                    role.upFrame();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    LoggerProvider.addExceptionLog("", e);
                }
            }
        }
    }
}
