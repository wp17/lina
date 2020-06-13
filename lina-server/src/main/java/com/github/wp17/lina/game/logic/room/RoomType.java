package com.github.wp17.lina.game.logic.room;

public enum RoomType {
    Default(0),
    FightTheLord(1);

    public int id;
    RoomType(int id){
        this.id = id;
    }

    public static final RoomType getById(int id) {
        for (int i = 0; i < RoomType.values().length; i++) {
            if (RoomType.values()[i].id == id) {
                return RoomType.values()[i];
            }
        }
        return Default;
    }
}
