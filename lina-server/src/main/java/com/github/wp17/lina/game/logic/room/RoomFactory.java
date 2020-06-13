package com.github.wp17.lina.game.logic.room;

import com.github.wp17.lina.game.logic.room.lord.LandLordRoom;

public class RoomFactory {
    public static AbsRoom createRoom(RoomType type, String name) {
        switch (type) {
            case FightTheLord:
                return new LandLordRoom(name);
        }
        return null;
    }
}
