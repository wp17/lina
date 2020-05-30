package com.github.wp17.lina.game.module.event;

import com.github.wp17.lina.common.event.Event;
import com.github.wp17.lina.common.event.EventDispatcher;
import com.github.wp17.lina.game.module.AbsModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventModule implements AbsModule {
    private EventModule() {}
    private static final EventModule instance = new EventModule();
    public static EventModule getInstance() {
        return instance;
    }

    private EventDispatcher dispatcher;

    @Override
    public void init() {
        dispatcher = new EventDispatcher("com.github.wp17.lina.game.module.event", false);
        try {
            dispatcher.init();
        } catch (Exception e) {
            log.error("初始化事件模块失败", e);
            System.exit(0);
        }
    }

    @Override
    public void shutdown() {
        dispatcher.shutdown();
    }

    public <T extends Event> void post(T event) {
        dispatcher.post(event);
    }

    public void post(Object o, boolean async) {
        dispatcher.post(o, async);
    }

    public static void main(String[] args) {
        EventModule.getInstance().init();
        EventModule.getInstance().post(new TestEvent());
    }
}
