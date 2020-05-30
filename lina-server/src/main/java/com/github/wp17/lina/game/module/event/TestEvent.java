package com.github.wp17.lina.game.module.event;

import com.github.wp17.lina.common.event.Event;

public class TestEvent implements Event {
    @Override
    public boolean isAsync() {
        return false;
    }
}
