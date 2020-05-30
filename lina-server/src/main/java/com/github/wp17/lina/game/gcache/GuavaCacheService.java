package com.github.wp17.lina.game.gcache;

import com.github.wp17.lina.common.cache.ICacheObj;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Slf4j
public class GuavaCacheService {
    private Cache<String, ICacheObj> cache;

    public void init() {
        cache = CacheBuilder.newBuilder().maximumSize(3000).build();
    }

    public <T extends ICacheObj> T getIfPresent(String key) {
        T cacheObject = (T) cache.getIfPresent(key);
        return cacheObject;
    }

    public void invalidate(String key) {
        cache.invalidate(key);
    }

    public <T extends ICacheObj> T get(String key, Callable callable) {
        try {
            return (T) cache.get(key,callable);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(String key, ICacheObj obj) {
        cache.put(key, obj);
    }
}
