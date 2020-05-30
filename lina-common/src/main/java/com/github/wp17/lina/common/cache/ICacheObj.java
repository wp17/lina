package com.github.wp17.lina.common.cache;

public interface ICacheObj <T> {
    void loadDB(long id);
    void loadRedis(long id);
    T get();
    void set(T data);
    boolean isLoad();
}
