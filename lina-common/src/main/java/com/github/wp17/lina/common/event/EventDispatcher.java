package com.github.wp17.lina.common.event;

import com.github.wp17.lina.common.util.NamedThreadFactory;
import com.github.wp17.lina.util.ClassUtil;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class EventDispatcher {

    private String listerPath;
    private boolean isDeep;

    public EventDispatcher(String listerPath, boolean isDeep) {
        this.listerPath = listerPath;
        this.isDeep = isDeep;
    }

    private EventBus eventBus;
    private AsyncEventBus asyncEventBus;
    private ExecutorService eventBusExecutor;

    public void init() throws Exception {
        eventBus = new EventBus();
        eventBusExecutor =
                Executors.newFixedThreadPool(4, new NamedThreadFactory("lister_thread_"));
        asyncEventBus = new AsyncEventBus(eventBusExecutor);

        List<Class<?>> listerList = ClassUtil.getClassesByAnnotation(listerPath, isDeep, EventListener.class);
        for (Class clazz : listerList) {
            Object lister = clazz.newInstance();
            eventBus.register(lister);
            asyncEventBus.register(lister);
        }
    }

    public void shutdown() {
        eventBusExecutor.shutdown();
    }

    public void dispatch(Object event) {
        eventBus.post(event);
    }

    public void dispatchAsync(Object event) {
        asyncEventBus.post(event);
    }

    public <T extends Event> void post(T event) {
        if (event.isAsync()) {
            dispatchAsync(event);
        } else {
            dispatch(event);
        }
    }

    public void post(Object o, boolean async) {
        if (async) dispatchAsync(o);
        else dispatch(o);
    }
}
