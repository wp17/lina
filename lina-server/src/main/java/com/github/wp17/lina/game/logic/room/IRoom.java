package com.github.wp17.lina.game.logic.room;

public interface IRoom {
    long getId();
    String getName();
    RoomType roomType();
    void upFrame();

    void close();
}
