package com.github.wp17.lina.common.net;

import java.util.EventListener;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface LogicFuture<V> extends Future<V> {
    boolean awaitUninterruptibly(long timeout, TimeUnit unit);

    boolean awaitUninterruptibly(long timeoutMillis);

    void addListener(EventListener listener);

    void removeListener(EventListener listener);
}
