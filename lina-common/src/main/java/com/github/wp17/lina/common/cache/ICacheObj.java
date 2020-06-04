package com.github.wp17.lina.common.cache;

public interface ICacheObj <T, P> {
    void loadDB(P id);
    void loadRedis(P id);
    T get();
    void set(T data);
    boolean isLoad();
}
