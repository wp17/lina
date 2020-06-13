package com.github.wp17.lina.game.logic.room.lord;

import com.github.wp17.lina.game.logic.room.AbsRoom;
import com.github.wp17.lina.game.logic.room.RoomType;
import com.google.protobuf.Message;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class LandLordRoom extends AbsRoom {
    public LandLordRoom(String name) {
        super(name);
    }

    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<Runnable>();

    public void checkOver() {
    }

    @Override
    public RoomType roomType() {
        return RoomType.FightTheLord;
    }

    @Override
    public void upFrame() {
        Runnable task = null;
        while ((task = tasks.poll()) != null) {
            task.run();
        }

        checkOver();
    }

    @Override
    public void close() {
        destroy();
    }

    public void broadcast(Message message) {
        roles.forEach(r -> r.sendMsg(message));
    }
}
