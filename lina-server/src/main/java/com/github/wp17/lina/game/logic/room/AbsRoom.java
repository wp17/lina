package com.github.wp17.lina.game.logic.room;

import com.github.wp17.lina.game.logic.Role;
import com.github.wp17.lina.game.module.line.LineServer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbsRoom implements IRoom {
    private static final AtomicLong idGen = new AtomicLong(0);

    @Getter
    private final long id;
    @Getter
    private String name;
    @Setter
    private LineServer lineServer;
    @Getter
    @Setter
    private Role owner;

    protected List<Role> roles = new ArrayList<>(3);

    protected AbsRoom(String name) {
        id = idGen.incrementAndGet();
        this.name = name;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void remRole(Role role) {
        roles.remove(role);
        if (isOwner(role)){
            owner = selectOwner();
        }

        if (null == owner) {
            destroy();
        }
    }

    public boolean isOwner(Role role) {
        return owner == role;
    }

    private Role selectOwner() {
        if (roles.isEmpty()) return null;
        return roles.get(0);
    }

    public  void destroy() {
        roles.forEach(role -> role.getRoomManager().leaveRoom());
        lineServer.remRoom(id);
    }
}
