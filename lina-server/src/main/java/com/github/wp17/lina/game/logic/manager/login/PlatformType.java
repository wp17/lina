package com.github.wp17.lina.game.logic.manager.login;

public enum PlatformType {
    UNKnow(-1),
    Private(1),
    WeChat(2);

    private int type;

    PlatformType(int type) {
        this.type = type;
    }

    public static PlatformType getByType(int type) {
        for (PlatformType value : PlatformType.values()) {
            if (value.type == type) {
                return value;
            }
        }
        return UNKnow;
    }
}
