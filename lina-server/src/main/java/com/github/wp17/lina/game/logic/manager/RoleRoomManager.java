package com.github.wp17.lina.game.logic.manager;

import com.github.wp17.lina.game.logic.Role;
import com.github.wp17.lina.game.logic.room.AbsRoom;
import com.github.wp17.lina.game.logic.room.RoomFactory;
import com.github.wp17.lina.game.logic.room.RoomType;
import com.github.wp17.lina.game.module.line.LineServer;
import lombok.Getter;
import lombok.Setter;

public class RoleRoomManager {
    private final Role role;

    @Setter
    @Getter
    private volatile AbsRoom room;

    public RoleRoomManager(Role role) {
        this.role = role;
    }

    public void createRoom(String name, RoomType type) {
        room = RoomFactory.createRoom(type, name);
        if (null == room) {
            //todo
            return;
        }
        room.setOwner(role);
        room.addRole(role);
        room.setLineServer(role.getLineServer());
    }

    public void enterRoom(AbsRoom room) {
        Role owner = room.getOwner();
        LineServer lineServer = owner.getLineServer();
        role.changeLine(lineServer);
        room.addRole(role);
    }

    public void leaveRoom() {
        if (null == room) return;
        room.remRole(role);
        room = null;
    }

    public void downLine() {
        leaveRoom();
    }
}
